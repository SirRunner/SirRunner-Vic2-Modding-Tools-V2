package common.generators;

import com.google.common.collect.Sets;
import decisions.generators.RenamingDecisionGenerator;
import decisions.reader.ProvinceRenamingCSVReader;
import decisions.reader.RegionRenamingCSVReader;
import decisions.renaming.ProvinceRenaming;
import decisions.renaming.RegionRenaming;
import events.nodes.Event;
import events.nodes.Immediate;
import events.nodes.Option;
import events.writer.EventWriter;
import map.reader.RegionReader;
import map.regions.Region;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.EffectScope;

import java.util.*;

public class DynamicLocRegionSetter extends RenamingDecisionGenerator {

    protected final int NUMBER_OF_REGION_SLOTS = 8;

    protected enum WASTELAND_REGIONS {
        GON_796, // White Mountains
        FOR_1042, // Forodwaith
        DOR_551, // Eastern Hills
        ERE_151, // Ered Mithrin
        ERE_1166, // Emyn Engrin
        ANG_1374 // Ephel Angmar
    }

    protected void run() throws Exception {
        ProvinceRenamingCSVReader provinceRenamingReader = new ProvinceRenamingCSVReader(provinceCSVFilename);
        RegionReader regionReader = new RegionReader(regionsFilename);
        RegionRenamingCSVReader regionRenamingCSVReader = new RegionRenamingCSVReader(regionsCSVFilename);

        Map<Integer, ProvinceRenaming> idToProvinceRenamings = provinceRenamingReader.readFile();
        Logger.info("Read " + idToProvinceRenamings.size() + " province renamings");

        Map<String, Region> codeToRegion = regionReader.readFile();
        Set<String> allRegionCodes = codeToRegion.keySet();
        Logger.info("Read " + allRegionCodes.size() + " regions");

        Map<String, RegionRenaming> codeToRegionRenaming = regionRenamingCSVReader.readFile();
        Logger.info("Read " + codeToRegionRenaming.size() + " region renamings groups");

        Map<Region, Set<Set<String>>> wtfIsThisTest = new HashMap<>();

        /* In regions.txt, but not in the spreadsheet */
        for (String code : Sets.difference(codeToRegion.keySet(), codeToRegionRenaming.keySet())) {

            if (StringUtils.endsWith(code, "_loc")) {
                continue;
            }

            System.out.println(code);
        }

        /* In the spreadsheet, but not in regions.txt */
        for (String code : Sets.difference(codeToRegionRenaming.keySet(), codeToRegion.keySet())) {
            System.out.println(code);
        }

        for (String regionCode : codeToRegionRenaming.keySet()) {
            if (!allRegionCodes.contains(regionCode)) {
                Logger.error(regionCode + " is in the region renaming file, but is not a defined region");
                continue;
            }

            RegionRenaming regionRenaming = codeToRegionRenaming.get(regionCode);

            Set<Set<String>> cultureGroupings = getFinalCultureGrouping(regionRenaming.getNamesToCultureGroups(), idToProvinceRenamings, codeToRegion.get(regionCode).getProvinces());
            wtfIsThisTest.put(codeToRegion.get(regionCode), cultureGroupings);
        }

        EventWriter writer = new EventWriter(outputFilename, generateDynamicLocEvents(wtfIsThisTest, codeToRegionRenaming), "Dynamic Loc Region Selection", 300008, 300009);
        writer.writeFile();
    }

    protected List<Event> generateDynamicLocEvents(Map<Region, Set<Set<String>>> regionRenamings, Map<String, RegionRenaming> codeToRegionRenaming) {
        List<Event> events = new ArrayList<>();

        events.add(getHandlerEvent());
        events.add(getRenamingEvent(regionRenamings, codeToRegionRenaming));

        return events;
    }

    protected Event getHandlerEvent() {
        Event event = new Event();

        event.setId(300008);
        event.setTitle("Dynamic Loc Region Selection Handler");
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

        effectScope.addEffect(ScriptingUtils.getEffect("which", "dynamic_loc_names"));
        effectScope.addEffect(ScriptingUtils.getEffect("value", "1"));

        return effectScope;
    }

    protected BasicEffect getHandlerEventTriggeringEffect(int i) {
        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");
        ConditionScope owner = ScriptingUtils.getConditionScope("owner");

        ConditionScope lowerScope = ScriptingUtils.getConditionScope("check_variable");
        lowerScope.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names"));
        lowerScope.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));
        owner.addCondition(lowerScope);

        ConditionScope not = ScriptingUtils.getNOTCondition();
        ConditionScope upperScope = ScriptingUtils.getConditionScope("check_variable");
        upperScope.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names"));
        upperScope.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
        not.addCondition(upperScope);
        owner.addCondition(not);

        ConditionScope regionCountMinimum = ScriptingUtils.getConditionScope("check_variable");
        regionCountMinimum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_region_count"));
        regionCountMinimum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));
        owner.addCondition(regionCountMinimum);

        /* We can only display 8 regions at a time. When there are more than 8 regions, we display "next" -- meaning that if there are 9 potential options, we only want to display the first 7 */
        if (i == 8) {
            ConditionScope regionCountNot = ScriptingUtils.getNOTCondition();
            ConditionScope regionCountMaximum = ScriptingUtils.getConditionScope("check_variable");
            regionCountMaximum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_region_count"));
            regionCountMaximum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
            regionCountNot.addCondition(regionCountMaximum);
            owner.addCondition(regionCountNot);
        }

        limit.addCondition(owner);

        randomOwned.setLimit(limit);

        EffectScope ownerEffect = ScriptingUtils.getEffectScope("owner");

        EffectScope countryEvent = ScriptingUtils.getEffectScope("country_event");
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300009"));
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

    protected Event getRenamingEvent(Map<Region, Set<Set<String>>> regionRenamings, Map<String, RegionRenaming> codeToRegionRenaming) {
        Event event = new Event();

        event.setId(300009);
        event.setTitle("Dynamic Loc Region Selection Renaming");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getRenamingImmediate(regionRenamings, codeToRegionRenaming));
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getRenamingImmediate(Map<Region, Set<Set<String>>> regionRenamings, Map<String, RegionRenaming> codeToRegionRenaming) {
        Immediate immediate = new Immediate();

        for (int i = 1; i <= NUMBER_OF_REGION_SLOTS; i++) {
            immediate.addEffect(getRenamingImmediateSetup(i));
        }

        for (Region region : regionRenamings.keySet()) {
            immediate.addEffects(getRenamings(region, regionRenamings.get(region), codeToRegionRenaming.get(region.getCode())));
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
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300008"));
        countryEvent.addEffect(ScriptingUtils.getEffect("days", "0"));
        countryEvent.setComment("Go back to the handler");

        immediate.addEffect(countryEvent);

        return immediate;
    }

    protected BasicEffect getRenamingImmediateSetup(int i) {
        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        randomOwned.setLimit(getRenamingImmediateRandomOwnedLimit(i));

        EffectScope regionSlot = ScriptingUtils.getEffectScope("dynamic_loc_region_slot_" + i);
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
        checkVariableLower.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names"));
        checkVariableLower.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));

        ConditionScope not = ScriptingUtils.getNOTCondition();
        ConditionScope checkVariableUpper = ScriptingUtils.getConditionScope("check_variable");
        checkVariableUpper.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_names"));
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
        limit1.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_potential_region_target"));

        randomNeighborProvince.setLimit(limit1);

        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        EffectScope anyOwned = ScriptingUtils.getEffectScope("any_owned");

        ConditionScope limit2 = ScriptingUtils.getConditionScope("limit");
        limit2.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_potential_region_target"));

        anyOwned.setLimit(limit2);

        EffectScope addProvinceModifier = ScriptingUtils.getEffectScope("add_province_modifier");
        addProvinceModifier.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_region_target_" + i));
        addProvinceModifier.addEffect(ScriptingUtils.getEffect("duration", "-1"));

        anyOwned.addEffect(addProvinceModifier);

        EffectScope addProvinceModifierSelector = ScriptingUtils.getEffectScope("add_province_modifier");
        addProvinceModifierSelector.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_slot_selector"));
        addProvinceModifierSelector.addEffect(ScriptingUtils.getEffect("duration", "-1"));

        anyOwned.addEffect(addProvinceModifierSelector);
        anyOwned.addEffect(ScriptingUtils.getEffect("remove_province_modifier", "dynamic_loc_potential_region_target"));

        stateScope.addEffect(anyOwned);
        randomNeighborProvince.addEffect(stateScope);

        return randomNeighborProvince;
    }

    protected List<BasicEffect> getRenamings(Region region, Set<Set<String>> nameGroups, RegionRenaming regionRenaming) {

        List<BasicEffect> renamings = new ArrayList<>();
        int count = 0;

        if (nameGroups.isEmpty()) {
            nameGroups.add(Sets.newHashSet(region.getCode()));
        }

        for (Set<String> nameGroup : nameGroups) {

            EffectScope dynamicLocSlots = ScriptingUtils.getEffectScope("dynamic_loc_slots");

            dynamicLocSlots.setLimit(getDynamicLocLimit(count, region, regionRenaming));

            EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
            stateScope.addEffect(ScriptingUtils.getEffect("change_region_name", "\"" + getNameForCulture(nameGroup, regionRenaming) + "\""));

            dynamicLocSlots.addEffect(stateScope);

            renamings.add(dynamicLocSlots);

            count++;

        }

        return renamings;

    }

    protected ConditionScope getDynamicLocLimit(int count, Region region, RegionRenaming regionRenaming) {

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        limit.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_slot_selector"));

        ConditionScope regionCode = ScriptingUtils.getConditionScope(region.getCode());
        regionCode.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_slot_selector"));

        limit.addCondition(regionCode);

        String variableName = regionRenaming.getVariableName();

        ConditionScope lowerLimit = ScriptingUtils.getConditionScope("FOR");
        ConditionScope checkVariableLower = ScriptingUtils.getConditionScope("check_variable");
        Condition whichConditionLower = ScriptingUtils.getCondition("which", variableName);
        Condition variableValueLower = ScriptingUtils.getCondition("value", Double.toString(count - 0.1));

        checkVariableLower.addCondition(whichConditionLower);
        checkVariableLower.addCondition(variableValueLower);
        lowerLimit.addCondition(checkVariableLower);

        limit.addCondition(lowerLimit);

        ConditionScope upperLimit = ScriptingUtils.getConditionScope("FOR");
        ConditionScope notUpper = ScriptingUtils.getNOTCondition();
        ConditionScope checkVariableUpper = ScriptingUtils.getConditionScope("check_variable");
        Condition whichConditionUpper = ScriptingUtils.getCondition("which", variableName);
        Condition variableValueUpper = ScriptingUtils.getCondition("value", Double.toString(count + 0.1));

        checkVariableUpper.addCondition(whichConditionUpper);
        checkVariableUpper.addCondition(variableValueUpper);
        notUpper.addCondition(checkVariableUpper);
        upperLimit.addCondition(notUpper);

        limit.addCondition(upperLimit);

        return limit;
    }

    protected String getNameForCulture(Set<String> nameGroup, RegionRenaming regionRenaming) {

        String regionName = regionRenaming.getNameForCultureGroup(nameGroup.iterator().next());

        return StringUtils.isNotEmpty(regionName) ? regionName : regionRenaming.getStartingName();
    }

    public static void main(String[] args) {
        try {
            DynamicLocRegionSetter generator = new DynamicLocRegionSetter();

            generator.setProvinceCSVFilename(System.getProperty("user.home") + "/Downloads/Provinces - Renaming (Province).csv");
            generator.setRegionsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/region.txt");
            generator.setRegionsCSVFilename(System.getProperty("user.home") + "/Downloads/Provinces - Renaming (Region).csv");
            generator.setOutputFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/events/Dynamic Loc Region Selection.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
