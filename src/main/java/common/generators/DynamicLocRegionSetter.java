package common.generators;

import com.google.common.collect.Sets;
import decisions.generators.RenamingDecisionGenerator;
import decisions.reader.ProvinceRenamingCSVReader;
import decisions.reader.RegionRenamingCSVReader;
import decisions.renaming.ProvinceRenaming;
import decisions.renaming.RegionRenaming;
import events.nodes.Event;
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

    public static final int NUMBER_REGION_SELECTION_EVENTS = 8;
    public static final int STARTING_EVENT_ID = 300008;

    protected enum WASTELAND_REGIONS {
        GON_796, // White Mountains
        FOR_1042, // Forodwaith
        DOR_551, // Eastern Hills
        ERE_151, // Ered Mithrin
        ERE_1166, // Emyn Engrin
        ANG_1374; // Ephel Angmar
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
        for (String code: Sets.difference(codeToRegion.keySet(), codeToRegionRenaming.keySet())) {

            if (StringUtils.endsWith(code, "_loc")) {
                continue;
            }

            System.out.println( code );
        }

        /* In the spreadsheet, but not in regions.txt */
        for (String code: Sets.difference(codeToRegionRenaming.keySet(), codeToRegion.keySet())) {
            System.out.println( code );
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

        EventWriter writer = new EventWriter(outputFilename, generateDynamicLocEvents(wtfIsThisTest, codeToRegionRenaming), "Dynamic Loc Region Selection", STARTING_EVENT_ID, STARTING_EVENT_ID + NUMBER_REGION_SELECTION_EVENTS - 1);
        writer.writeFile();
    }

    protected List<Event> generateDynamicLocEvents(Map<Region, Set<Set<String>>> regionRenamings, Map<String, RegionRenaming> codeToRegionRenaming) {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < NUMBER_REGION_SELECTION_EVENTS; i++) {
            Event event = new Event();

            event.setId(STARTING_EVENT_ID + i);
            event.setTitle("Dynamic Loc Region Selection " + (i + 1));
            event.setDescription("");
            event.setTriggeredOnly(true);

            event.addOption(getEventOption(i + 1, regionRenamings, codeToRegionRenaming));

            events.add(event);
        }

        return events;
    }

    protected Option getEventOption(int i, Map<Region, Set<Set<String>>> regionRenamings, Map<String, RegionRenaming> codeToRegionRenaming) {

        Option option = new Option();

        option.setName("Assign loc");

        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");


        for (Region region : regionRenamings.keySet()) {
            randomOwned.addEffects(getEffects(i, region, regionRenamings.get(region), codeToRegionRenaming.get(region.getCode())));
        }

        option.addEffect(randomOwned);

        return option;

    }

    protected List<BasicEffect> getEffects(int i, Region region, Set<Set<String>> nameGroups, RegionRenaming regionRenaming) {

        List<BasicEffect> anyNeighborProvinces = new ArrayList<>();
        int count = 0;

        if (nameGroups.isEmpty()) {
            nameGroups.add(Sets.newHashSet(region.getCode()));
        }

        for (Set<String> nameGroup : nameGroups) {

            EffectScope anyNeighborProvince = ScriptingUtils.getEffectScope("any_neighbor_province");

            anyNeighborProvince.setLimit(getDynamicLocLimit(i, count, region, nameGroup, regionRenaming));
            anyNeighborProvince.addEffect(getStateNameChange(i, region, nameGroup, regionRenaming));
            anyNeighborProvince.addEffect(getModifierUpdates(i, region, nameGroup, regionRenaming));
            anyNeighborProvince.addEffect(addPreventativeFlag(i));

            anyNeighborProvinces.add(anyNeighborProvince);

            count++;

        }


        return anyNeighborProvinces;
    }

    protected ConditionScope getDynamicLocLimit(int i, int count, Region region, Set<String> nameGroup, RegionRenaming regionRenaming) {

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        limit.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_potential_region_target"));
        limit.addCondition(ScriptingUtils.getCondition("region", region.getCode()));

        ConditionScope not = ScriptingUtils.getNOTCondition();
        ConditionScope owner = ScriptingUtils.getConditionScope("owner");

        owner.addCondition(ScriptingUtils.getCondition("has_global_flag", "dynamic_loc_option_" + i + "_found"));
        not.addCondition(owner);

        limit.addCondition(not);

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

    protected EffectScope getStateNameChange(int i, Region region, Set<String> nameGroup, RegionRenaming regionRenaming) {

        EffectScope dynamicLocRegion = ScriptingUtils.getEffectScope("dynamic_loc_region_slot_" + i);
        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");

        stateScope.addEffect(ScriptingUtils.getEffect("change_region_name", "\"" + getNameForCulture(nameGroup, regionRenaming) + "\""));

        dynamicLocRegion.addEffect(stateScope);

        return dynamicLocRegion;
    }

    protected String getNameForCulture(Set<String> nameGroup, RegionRenaming regionRenaming) {

        String regionName = regionRenaming.getNameForCultureGroup(nameGroup.iterator().next());

        return StringUtils.isNotEmpty(regionName) ? regionName : regionRenaming.getStartingName();
    }

    protected EffectScope getModifierUpdates(int i, Region region, Set<String> nameGroup, RegionRenaming regionRenaming) {
        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        EffectScope anyOwned = ScriptingUtils.getEffectScope("any_owned");

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        limit.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_potential_region_target"));

        anyOwned.setLimit(limit);

        EffectScope addProvinceModifier = ScriptingUtils.getEffectScope("add_province_modifier");
        addProvinceModifier.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_region_target_" + i));
        addProvinceModifier.addEffect(ScriptingUtils.getEffect("duration", "-1"));

        anyOwned.addEffect(addProvinceModifier);
        anyOwned.addEffect(ScriptingUtils.getEffect("remove_province_modifier", "dynamic_loc_potential_region_target"));

        stateScope.addEffect(anyOwned);

        return stateScope;
    }

    protected EffectScope addPreventativeFlag(int i) {

        EffectScope owner = ScriptingUtils.getEffectScope("owner");
        owner.addEffect(ScriptingUtils.getEffect("set_global_flag", "dynamic_loc_option_" + i + "_found"));

        return owner;
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
