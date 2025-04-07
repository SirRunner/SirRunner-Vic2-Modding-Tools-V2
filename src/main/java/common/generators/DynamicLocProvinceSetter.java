package common.generators;

import com.google.common.collect.Sets;
import common.Culture;
import common.readers.CultureReader;
import decisions.reader.ProvinceRenamingCSVReader;
import decisions.renaming.ProvinceRenaming;
import events.nodes.Event;
import events.nodes.Immediate;
import events.nodes.Option;
import events.writer.EventWriter;
import utils.Logger;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.EffectScope;

import java.util.*;
import java.util.stream.Collectors;

public class DynamicLocProvinceSetter {
    protected String provinceCSVFilename;
    protected String outputFilename;
    protected String cultureFilename;
    protected final Set<String> nonTagCultureGroups = Sets.newHashSet("banker", "religions", "good_evil", "boromir_culture", "faramir_culture", "madril_culture", "bryn_culture", "girloth_culture");

    protected final int NUMBER_OF_REGION_SLOTS = 40;

    public void setProvinceCSVFilename(String provinceCSVFilename) {
        this.provinceCSVFilename = provinceCSVFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public void setCultureFilename(String cultureFilename) {
        this.cultureFilename = cultureFilename;
    }

    protected void run() throws Exception {
        Map<Integer, ProvinceRenaming> idToProvinceRenamings = new ProvinceRenamingCSVReader(provinceCSVFilename).readFile();
        Logger.info("Read " + idToProvinceRenamings.size() + " province renamings");

        List<Culture.CultureGroup> cultureGroups = new CultureReader(cultureFilename).readFile();
        Set<String> cultureGroupNames = cultureGroups.stream().map(Culture.CultureGroup::getName).collect(Collectors.toSet());
        cultureGroupNames.removeIf(nonTagCultureGroups::contains);

        EventWriter writer = new EventWriter(outputFilename, generateDynamicLocEvents(idToProvinceRenamings, cultureGroupNames), "Dynamic Loc Province Selection", 300016, 300017);
        writer.writeFile();
    }

    protected List<Event> generateDynamicLocEvents(Map<Integer, ProvinceRenaming> idToProvinceRenamings, Set<String> cultureGroups) {
        List<Event> events = new ArrayList<>();

        events.add(getHandlerEvent());
        events.add(getRenamingEvent(idToProvinceRenamings, cultureGroups));

        return events;
    }

    protected Event getHandlerEvent() {
        Event event = new Event();

        event.setId(300016);
        event.setTitle("Dynamic Loc Province Selection Handler");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getHandlerImmediate());
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getHandlerImmediate() {
        Immediate immediate = new Immediate();

        immediate.addEffect(getHandlerChangeVariableEffect());

        for (int i = 1; i <= NUMBER_OF_REGION_SLOTS; i++) {
            immediate.addEffect(getHandlerEventTriggeringEffect(i));
        }

        return immediate;
    }

    protected BasicEffect getHandlerChangeVariableEffect() {
        EffectScope effectScope = ScriptingUtils.getEffectScope("change_variable");

        effectScope.addEffect(ScriptingUtils.getEffect("which", "dynamic_loc_names_province"));
        effectScope.addEffect(ScriptingUtils.getEffect("value", "1"));

        return effectScope;
    }

    protected BasicEffect getHandlerEventTriggeringEffect(int i) {
        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");
        ConditionScope owner = ScriptingUtils.getConditionScope("owner");

        ConditionScope lowerScope = ScriptingUtils.getConditionScope("check_variable");
        lowerScope.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names_province"));
        lowerScope.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));
        owner.addCondition(lowerScope);

        ConditionScope not = ScriptingUtils.getNOTCondition();
        ConditionScope upperScope = ScriptingUtils.getConditionScope("check_variable");
        upperScope.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names_province"));
        upperScope.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
        not.addCondition(upperScope);
        owner.addCondition(not);

        ConditionScope regionCountMinimum = ScriptingUtils.getConditionScope("check_variable");
        regionCountMinimum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_province_count"));
        regionCountMinimum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));
        owner.addCondition(regionCountMinimum);

        /* We can only display 8 regions at a time. When there are more than 8 regions, we display "next" -- meaning that if there are 9 potential options, we only want to display the first 7 */
        if (i == 8) {
            ConditionScope regionCountNot = ScriptingUtils.getNOTCondition();
            ConditionScope regionCountMaximum = ScriptingUtils.getConditionScope("check_variable");
            regionCountMaximum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_province_count"));
            regionCountMaximum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
            regionCountNot.addCondition(regionCountMaximum);
            owner.addCondition(regionCountNot);
        }

        limit.addCondition(owner);

        randomOwned.setLimit(limit);

        EffectScope ownerEffect = ScriptingUtils.getEffectScope("owner");

        EffectScope countryEvent = ScriptingUtils.getEffectScope("country_event");
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300017"));
        countryEvent.addEffect(ScriptingUtils.getEffect("days", "0"));
        ownerEffect.addEffect(countryEvent);

        randomOwned.addEffect(ownerEffect);

        return randomOwned;
    }

    protected Option getBasicOption() {
        Option option = new Option();

        option.setName("");

        return option;
    }

    protected Event getRenamingEvent(Map<Integer, ProvinceRenaming> idToProvinceRenamings, Set<String> cultureGroups) {
        Event event = new Event();

        event.setId(300017);
        event.setTitle("Dynamic Loc Province Selection Renaming");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getRenamingImmediate(idToProvinceRenamings, cultureGroups));
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getRenamingImmediate(Map<Integer, ProvinceRenaming> idToProvinceRenamings, Set<String> cultureGroups) {
        Immediate immediate = new Immediate();

        for (int i = 1; i <= NUMBER_OF_REGION_SLOTS; i++) {
            immediate.addEffect(getRenamingImmediateSetup(i));
        }

        for (ProvinceRenaming provinceRenaming : idToProvinceRenamings.values()) {
            immediate.addEffects(getRenamings(provinceRenaming, cultureGroups));
        }

        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");
        EffectScope anyNeighborProvince = ScriptingUtils.getEffectScope("any_neighbor_province");
        anyNeighborProvince.addEffect(ScriptingUtils.getEffect("remove_province_modifier", "dynamic_loc_slot_selector"));
        randomOwned.addEffect(anyNeighborProvince);
        randomOwned.setComment("Cleans up land provinces");

        immediate.addEffect(randomOwned);

        EffectScope dynamicLocSlots = ScriptingUtils.getEffectScope("dynamic_loc_slots");
        dynamicLocSlots.addEffect(ScriptingUtils.getEffect("remove_province_modifier", "dynamic_loc_slot_selector"));
        dynamicLocSlots.setComment("Cleans up sea provinces");

        immediate.addEffect(dynamicLocSlots);

        EffectScope countryEvent = ScriptingUtils.getEffectScope("country_event");
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300016"));
        countryEvent.addEffect(ScriptingUtils.getEffect("days", "0"));
        countryEvent.setComment("Go back to the handler");

        immediate.addEffect(countryEvent);

        return immediate;
    }

    protected BasicEffect getRenamingImmediateSetup(int i) {
        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        randomOwned.setLimit(getRenamingImmediateRandomOwnedLimit(i));

        EffectScope regionSlot = ScriptingUtils.getEffectScope("dynamic_loc_slot_" + i);
        EffectScope provinceModifier = ScriptingUtils.getEffectScope("add_province_modifier");
        provinceModifier.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_slot_selector"));
        provinceModifier.addEffect(ScriptingUtils.getEffect("duration", "1"));
        regionSlot.addEffect(provinceModifier);

        randomOwned.addEffect(regionSlot);
        randomOwned.addEffect(getRenamingImmediateRandomOwnedRandomNeighborProvince(i));

        return randomOwned;
    }

    protected ConditionScope getRenamingImmediateRandomOwnedLimit(int i) {
        ConditionScope limit = ScriptingUtils.getConditionScope("limit");
        ConditionScope owner = ScriptingUtils.getConditionScope("owner");

        ConditionScope checkVariableLower = ScriptingUtils.getConditionScope("check_variable");
        checkVariableLower.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names_province"));
        checkVariableLower.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));

        ConditionScope not = ScriptingUtils.getNOTCondition();
        ConditionScope checkVariableUpper = ScriptingUtils.getConditionScope("check_variable");
        checkVariableUpper.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names_province"));
        checkVariableUpper.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
        not.addCondition(checkVariableUpper);

        owner.addCondition(checkVariableLower);
        owner.addCondition(not);
        limit.addCondition(owner);

        return limit;
    }

    protected EffectScope getRenamingImmediateRandomOwnedRandomNeighborProvince(int i) {
        EffectScope randomNeighborProvince = ScriptingUtils.getEffectScope("random_neighbor_province");

        ConditionScope limit1 = ScriptingUtils.getConditionScope("limit");
        limit1.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_potential_province_target"));

        randomNeighborProvince.setLimit(limit1);

        EffectScope addProvinceModifier = ScriptingUtils.getEffectScope("add_province_modifier");
        addProvinceModifier.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_province_target_" + i));
        addProvinceModifier.addEffect(ScriptingUtils.getEffect("duration", "-1"));

        randomNeighborProvince.addEffect(addProvinceModifier);

        EffectScope addProvinceModifierSelector = ScriptingUtils.getEffectScope("add_province_modifier");
        addProvinceModifierSelector.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_slot_selector"));
        addProvinceModifierSelector.addEffect(ScriptingUtils.getEffect("duration", "-1"));

        randomNeighborProvince.addEffect(addProvinceModifierSelector);
        randomNeighborProvince.addEffect("remove_province_modifier", "dynamic_loc_potential_province_target");

        return randomNeighborProvince;
    }

    protected List<BasicEffect> getRenamings(ProvinceRenaming renaming, Set<String> cultureGroups) {

        List<BasicEffect> renamings = new ArrayList<>();

        /* We need to cover every culture. If a province only has one name regardless of the owner, then it doesn't matter. However, if there's multiple renamings, then we need to add all of the unused cultures to the starting name */
        if (!renaming.getNamesToCultureGroups().isEmpty()) {
            Set<String> usedCultures = new HashSet<>();
            for (Set<String> culturesForName : renaming.getNamesToCultureGroups().values()) {
                usedCultures.addAll(culturesForName);
            }

            Set<String> unusedCultures = Sets.difference(cultureGroups, usedCultures);

            if (!unusedCultures.isEmpty()) {
                try {
                    renaming.getNamesToCultureGroups().get(renaming.getStartingName()).addAll(unusedCultures);
                } catch (Exception e) {
                    Logger.error("Starting province name isn't used in province " + renaming.getProvinceId());
                }
            }
        } else {
            renaming.getNamesToCultureGroups().put(renaming.getStartingName(), new HashSet<>());
        }

        for (String name : renaming.getNamesToCultureGroups().keySet()) {

            EffectScope dynamicLocSlots = ScriptingUtils.getEffectScope("dynamic_loc_slots");
            Set<String> cultures = renaming.getNamesToCultureGroups().get(name);

            dynamicLocSlots.setLimit(getDynamicLocLimit(renaming.getProvinceId(), cultures));

            EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
            stateScope.addEffect(ScriptingUtils.getEffect("change_region_name", "\"" + name + "\""));

            dynamicLocSlots.addEffect(stateScope);

            renamings.add(dynamicLocSlots);

        }

        return renamings;

    }

    protected ConditionScope getDynamicLocLimit(int provinceId, Set<String> cultureGroups) {

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        limit.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_slot_selector"));

        ConditionScope regionCode = ScriptingUtils.getConditionScope(Integer.toString(provinceId));
        regionCode.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_slot_selector"));

        if (!cultureGroups.isEmpty()) {
            List<String> cultureList = cultureGroups.stream().toList();
            ConditionScope owner = ScriptingUtils.getConditionScope("owner");

            if (cultureGroups.size() == 1) {
                owner.addCondition("is_culture_group", cultureList.get(0));
            } else {
                ConditionScope or = ScriptingUtils.getORCondition();

                for (String culture : cultureList) {
                    or.addCondition("is_culture_group", culture);
                }

                owner.addCondition(or);
            }

            regionCode.addCondition(owner);
        }

        limit.addCondition(regionCode);

        return limit;
    }

    public static void main(String[] args) {
        try {
            DynamicLocProvinceSetter generator = new DynamicLocProvinceSetter();

            generator.setProvinceCSVFilename(System.getProperty("user.home") + "/Downloads/Provinces - Renaming (Province).csv");
            generator.setOutputFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/events/Dynamic Loc Province Selection.txt");
            generator.setCultureFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/common/cultures.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
