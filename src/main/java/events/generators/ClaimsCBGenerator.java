package events.generators;

import com.google.common.collect.Sets;
import events.nodes.Event;
import events.nodes.Option;
import map.regions.Region;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.EffectScope;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ClaimsCBGenerator extends AbstractMultiEventGenerator {

    protected List<Region> regions;
    protected final Set<Integer> handledIds = Sets.newHashSet(getLowerId(), getLowerId() + 1);

    public ClaimsCBGenerator(String filename, String headerName, int lowerId, int upperId) throws Exception {
        super(filename, headerName, lowerId, upperId);
        throw new Exception("Needs to receive list of regions too");
    }

    public ClaimsCBGenerator(String filename, String headerName, int lowerId, int upperId, List<Region> regions) {
        super(filename, headerName, lowerId, upperId);

        /* Get the regions in ascending order by the id */
        this.regions = regions.stream().filter(region -> region.getSortingId() != 0).sorted((o1, o2) -> {
            if (o1.getSortingId() > o2.getSortingId()) {
                return 1;
            } else if (o2.getSortingId() > o1.getSortingId()) {
                return -1;
            }

            return 0;
        }).toList();
    }

    protected Event getEvent(int id) {

        if (!handledIds.contains(id)) {
            return null;
        }

        Event event = new Event();

        event.setId(id);
        event.setPicture(getPicture(id));
        event.setTitle(getTitle(id));
        event.setDescription(getDescription(id));
        event.setTriggeredOnly(getTriggeredOnly(id));
        event.setFireOnlyOnce(getFireOnlyOnce(id));
        event.setTrigger(getTrigger(id));
        event.setMeanTimeToHappen(getMTTH(id));
        event.setImmediate(getImmediate(id));
        event.setOptions(getOptions(id));

        return event;

    }

    @Override
    protected String getPicture() {
        return "production";
    }

    @Override
    protected String getTitle(int id) {
        return "Unowned Claims";
    }

    @Override
    protected List<Option> getOptions(int id) {

        if (id == getLowerId()) {

            Option option = new Option();

            option.setName("To War!");

            for (Region region : regions) {
                EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");
                randomOwned.setIndent(2);

                ConditionScope limit = ScriptingUtils.getConditionScope("limit");
                ConditionScope conditionOwner = ScriptingUtils.getConditionScope("owner");
                conditionOwner.addCondition(ScriptingUtils.getCondition("has_country_flag", "claimed_" + region.getCode()));

                ConditionScope not = ScriptingUtils.getNOTCondition();
                for (int provinceId : region.getProvinces()) {
                    not.addCondition(ScriptingUtils.getCondition("owns", Integer.toString(provinceId)));
                }

                conditionOwner.addCondition(not);
                limit.addCondition(conditionOwner);
                limit.setIndent(3);
                randomOwned.setLimit(limit);

                EffectScope effectOwner = ScriptingUtils.getEffectScope("owner");
                EffectScope anyCountry = ScriptingUtils.getEffectScope("any_country");
                ConditionScope innerLimit = ScriptingUtils.getConditionScope("limit");
                ConditionScope or = ScriptingUtils.getORCondition();

                for (int provinceId : region.getProvinces()) {
                    or.addCondition(ScriptingUtils.getCondition("owns", Integer.toString(provinceId)));
                }

                innerLimit.addCondition(or);
                anyCountry.setLimit(innerLimit);

                EffectScope addCasusBelli = ScriptingUtils.getEffectScope("add_casus_belli");
                addCasusBelli.addEffect(ScriptingUtils.getEffect("target", "THIS"));
                addCasusBelli.addEffect(ScriptingUtils.getEffect("type", "acquire_claimed_state"));
                addCasusBelli.addEffect(ScriptingUtils.getEffect("months", "9999"));

                anyCountry.addEffect(addCasusBelli);

                effectOwner.addEffect(anyCountry);

                randomOwned.addEffect(effectOwner);

                option.addEffect(randomOwned);
            }

            return Collections.singletonList(option);
        }

        if (id == getLowerId() + 1) {

            Option option = new Option();

            option.setName("Clear unneeded CBs");

            for (Region region : regions) {
                EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

                ConditionScope limit = ScriptingUtils.getConditionScope("limit");
                ConditionScope or = ScriptingUtils.getORCondition();

                for (int provinceId : region.getProvinces()) {
                    or.addCondition(ScriptingUtils.getCondition("province_id", Integer.toString(provinceId)));
                }

                limit.addCondition(or);
                randomOwned.setLimit(limit);

                EffectScope owner = ScriptingUtils.getEffectScope("owner");
                EffectScope anyCountry = ScriptingUtils.getEffectScope("any_country");

                ConditionScope effectLimit = ScriptingUtils.getConditionScope("limit");
                effectLimit.addCondition(ScriptingUtils.getCondition("has_country_flag", "claimed_" + region.getCode()));

                anyCountry.setLimit(effectLimit);

                anyCountry.addEffect("set_country_flag", "can_keep_cbs");
                owner.addEffect(anyCountry);
                randomOwned.addEffect(owner);

                option.addEffect(randomOwned);

            }

            EffectScope anyCountry1 = ScriptingUtils.getEffectScope("any_country");

            ConditionScope limit1 = ScriptingUtils.getConditionScope("limit");

            limit1.addCondition("casus_belli", "THIS");
            limit1.addCondition("has_country_flag", "can_keep_cbs", ScriptingUtils.getNOTCondition());
            anyCountry1.setLimit(limit1);

            EffectScope removeCasusBelli = ScriptingUtils.getEffectScope("remove_casus_belli");

            removeCasusBelli.addEffect("target", "THIS");
            removeCasusBelli.addEffect("type", "acquire_claimed_state");
            anyCountry1.addEffect(removeCasusBelli);
            option.addEffect(anyCountry1);

            EffectScope anyCountry2 = ScriptingUtils.getEffectScope("any_country");

            ConditionScope limit2 = ScriptingUtils.getConditionScope("limit");

            limit2.addCondition("has_country_flag", "can_keep_cbs");
            anyCountry2.setLimit(limit2);

            anyCountry2.addEffect("clr_country_flag", "can_keep_cbs");
            option.addEffect(anyCountry2);

            return Collections.singletonList(option);

        }

        return Collections.emptyList();
    }
}
