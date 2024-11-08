package events.generators;

import events.nodes.Option;
import map.regions.Region;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.EffectScope;

import java.util.Collections;
import java.util.List;

public class ClaimsCBGenerator extends AbstractEventGenerator {

    List<Region> regions;

    public ClaimsCBGenerator(String filename, String headerName, int lowerId, int upperId) throws Exception {
        super(filename, headerName, lowerId, upperId);
        throw new Exception("Needs to receive list of regions too");
    }

    public ClaimsCBGenerator(String filename, String headerName, int lowerId, int upperId, List<Region> regions) {
        super(filename, headerName, lowerId, upperId );

        this.regions = regions;
    }

    @Override
    protected int getId() {
        return 2900;
    }

    @Override
    protected String getPicture() {
        return "production";
    }

    @Override
    protected String getTitle() {
        return "Unowned Claims";
    }

    @Override
    protected List<Option> getOptions() {

        /* Get the regions in ascending order by the id */
        regions = regions.stream().filter(region -> region.getSortingId() != 0).sorted((o1, o2) -> {
            if (o1.getSortingId() > o2.getSortingId()) {
                return 1;
            } else if (o2.getSortingId() > o1.getSortingId()) {
                return -1;
            }

            return 0;
        }).toList();

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
}
