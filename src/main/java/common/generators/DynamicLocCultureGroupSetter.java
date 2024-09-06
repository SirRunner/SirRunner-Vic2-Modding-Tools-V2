package common.generators;

import common.Culture;
import common.readers.CultureReader;
import events.nodes.Event;
import events.nodes.Immediate;
import events.nodes.Option;
import events.writer.EventWriter;
import utils.paradox.parsing.localisation.ParadoxLocalisationParser;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.EffectScope;
import utils.paradox.scripting.localisation.Localisation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicLocCultureGroupSetter {
    protected String culturesLocFilename;
    protected String culturesFilename;
    protected String outputFilename;

    protected final int NUMBER_OF_CULTURE_GROUP_SLOTS = 8;

    public void setCulturesLocFilename(String culturesLocFilename) {
        this.culturesLocFilename = culturesLocFilename;
    }

    public void setCulturesFilename(String culturesFilename) {
        this.culturesFilename = culturesFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public void run() throws Exception {
        List<Culture.CultureGroup> cultureGroups = new CultureReader(culturesFilename).readFile();
        List<Localisation> localisations = new ParadoxLocalisationParser().parseFile(new File(culturesLocFilename));

        Map<String, Localisation> nameToLoc = new HashMap<>();

        localisations.forEach(localisation -> nameToLoc.put(localisation.getTitle(), localisation));

        List<CultureGroupLocalisation> cultureGroupLocalisations = cultureGroups.stream().map(cultureGroup -> new CultureGroupLocalisation(nameToLoc.get(cultureGroup.getName()), cultureGroup)).collect(Collectors.toList());

        EventWriter writer = new EventWriter(outputFilename, generateDynamicLocEvents(cultureGroupLocalisations), "Dynamic Loc Culture Group Selection", 300002, 300003);
        writer.writeFile();
    }

    protected List<Event> generateDynamicLocEvents(List<CultureGroupLocalisation> cultureGroupLocalisations) {
        List<Event> events = new ArrayList<>();

        events.add(getHandlerEvent());
        events.add(getRenamingEvent(cultureGroupLocalisations));

        return events;
    }

    protected Event getHandlerEvent() {
        Event event = new Event();

        event.setId(300002);
        event.setTitle("Dynamic Loc Culture Group Selection Handler");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getHandlerImmediate());
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getHandlerImmediate() {
        Immediate immediate = new Immediate();

        immediate.addEffect(getHandlerChangeVariableEffect());

        for (int i = 1; i <= NUMBER_OF_CULTURE_GROUP_SLOTS; i++) {
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

        ConditionScope countryCountMinimum = ScriptingUtils.getConditionScope("check_variable");
        countryCountMinimum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_culture_group_count"));
        countryCountMinimum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));
        owner.addCondition(countryCountMinimum);

        /* We can only display 8 cultures at a time. When there are more than 8 cultures, we display "next" -- meaning that if there are 9 potential options, we only want to display the first 7 */
        if (i == 8) {
            ConditionScope countryCountNot = ScriptingUtils.getNOTCondition();
            ConditionScope countryCountMaximum = ScriptingUtils.getConditionScope("check_variable");
            countryCountMaximum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_culture_group_count"));
            countryCountMaximum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
            countryCountNot.addCondition(countryCountMaximum);
            owner.addCondition(countryCountNot);
        }

        limit.addCondition(owner);

        randomOwned.setLimit(limit);

        EffectScope ownerEffect = ScriptingUtils.getEffectScope("owner");

        EffectScope countryEvent = ScriptingUtils.getEffectScope("country_event");
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300003"));
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

    protected Event getRenamingEvent(List<CultureGroupLocalisation> cultureGroupLocalisations) {
        Event event = new Event();

        event.setId(300003);
        event.setTitle("Dynamic Loc Culture Group Selection Renaming");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getRenamingImmediate(cultureGroupLocalisations));
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getRenamingImmediate(List<CultureGroupLocalisation> cultureGroupLocalisations) {
        Immediate immediate = new Immediate();

        for (int i = 1; i <= NUMBER_OF_CULTURE_GROUP_SLOTS; i++) {
            immediate.addEffect(getRenamingImmediateSetup(i));
        }

        for (CultureGroupLocalisation cultureGroupLocalisation : cultureGroupLocalisations) {
            immediate.addEffects(getRenamings(cultureGroupLocalisation));
        }

        EffectScope anyCountry = ScriptingUtils.getEffectScope("any_country");
        anyCountry.addEffect(ScriptingUtils.getEffect("clr_country_flag", "dynamic_loc_slot_selector"));
        anyCountry.setComment("Cleans up countries");

        immediate.addEffect(anyCountry);

        EffectScope dynamicLocSlots = ScriptingUtils.getEffectScope("dynamic_loc_slots");
        dynamicLocSlots.addEffect(ScriptingUtils.getEffect("remove_province_modifier", "dynamic_loc_slot_selector"));
        dynamicLocSlots.setComment("Cleans up sea provinces");

        immediate.addEffect(dynamicLocSlots);

        EffectScope countryEvent = ScriptingUtils.getEffectScope("country_event");
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300002"));
        countryEvent.addEffect(ScriptingUtils.getEffect("days", "0"));
        countryEvent.setComment("Go back to the handler");

        immediate.addEffect(countryEvent);

        return immediate;
    }

    protected BasicEffect getRenamingImmediateSetup(int i) {

        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        randomOwned.setLimit(getRenamingImmediateRandomOwnedLimit(i));

        EffectScope countrySlot = ScriptingUtils.getEffectScope("dynamic_loc_culture_group_slot_" + i);
        EffectScope provinceModifier = ScriptingUtils.getEffectScope("add_province_modifier");
        provinceModifier.addEffect(ScriptingUtils.getEffect("name", "dynamic_loc_slot_selector"));
        provinceModifier.addEffect(ScriptingUtils.getEffect("duration", "1"));
        countrySlot.addEffect(provinceModifier);

        randomOwned.addEffect(countrySlot);
        randomOwned.addEffect(getRenamingImmediateRandomOwnedRandomCountry(i));

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

    protected EffectScope getRenamingImmediateRandomOwnedRandomCountry(int i) {

        EffectScope randomCountry = ScriptingUtils.getEffectScope("random_country");

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");
        limit.addCondition(ScriptingUtils.getCondition("has_country_flag", "dynamic_loc_potential_culture_group_target"));

        randomCountry.setLimit(limit);

        randomCountry.addEffect(ScriptingUtils.getEffect("set_country_flag", "dynamic_loc_culture_group_target_" + i));
        randomCountry.addEffect(ScriptingUtils.getEffect("set_country_flag", "dynamic_loc_slot_selector"));
        randomCountry.addEffect(ScriptingUtils.getEffect("clr_country_flag", "dynamic_loc_potential_culture_group_target"));

        return randomCountry;
    }

    protected List<BasicEffect> getRenamings(CultureGroupLocalisation cultureGroupLocalisation) {

        List<BasicEffect> renamings = new ArrayList<>();

        EffectScope dynamicLocSlots = ScriptingUtils.getEffectScope("dynamic_loc_slots");

        dynamicLocSlots.setLimit(getDynamicLocLimit(cultureGroupLocalisation.getCultureGroup()));

        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        stateScope.addEffect(ScriptingUtils.getEffect("change_region_name", "\"" + cultureGroupLocalisation.getLocedName() + "\""));

        dynamicLocSlots.addEffect(stateScope);

        renamings.add(dynamicLocSlots);

        return renamings;
    }

    protected ConditionScope getDynamicLocLimit(Culture.CultureGroup cultureGroup) {
        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        limit.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_slot_selector"));

        ConditionScope tagScope = ScriptingUtils.getConditionScope("FOR");
        ConditionScope anyNeighborCountry = ScriptingUtils.getConditionScope("any_neighbor_country");

        anyNeighborCountry.addCondition(ScriptingUtils.getCondition("has_country_flag", "dynamic_loc_slot_selector"));
        anyNeighborCountry.addCondition(ScriptingUtils.getCondition("is_culture_group", cultureGroup.getName()));
        tagScope.addCondition(anyNeighborCountry);

        limit.addCondition(tagScope);

        return limit;
    }

    public static void main(String[] args) {
        try {
            DynamicLocCultureGroupSetter generator = new DynamicLocCultureGroupSetter();

            generator.setCulturesFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\cultures.txt");
            generator.setCulturesLocFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\localisation\\cultures.csv");
            generator.setOutputFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/events/Dynamic Loc Culture Group Selection.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class CultureGroupLocalisation {
        String locedName;
        Culture.CultureGroup cultureGroup;

        public CultureGroupLocalisation(Localisation localisation, Culture.CultureGroup cultureGroup) {
            this.locedName = localisation.getLocalisation();
            this.cultureGroup = cultureGroup;
        }

        public String getLocedName() {
            return locedName;
        }

        public Culture.CultureGroup getCultureGroup() {
            return cultureGroup;
        }
    }
}
