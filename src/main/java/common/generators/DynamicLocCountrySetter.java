package common.generators;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import common.Country;
import common.Government;
import common.Ideology;
import common.readers.CountryListReader;
import common.readers.GovernmentReader;
import common.readers.IdeologyReader;
import events.nodes.Event;
import events.nodes.Immediate;
import events.nodes.Option;
import events.writer.EventWriter;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.parsing.localisation.ParadoxLocalisationParser;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.EffectScope;
import utils.paradox.scripting.localisation.Localisation;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicLocCountrySetter {
    protected String countriesFilename;
    protected String countriesLocFilename;
    protected String ideologiesFilename;
    protected String governmentsFilename;
    protected String outputFilename;

    protected final int NUMBER_OF_COUNTRY_SLOTS = 24;

    public void setCountriesFilename(String countriesFilename) {
        this.countriesFilename = countriesFilename;
    }

    public void setCountriesLocFilename(String countriesLocFilename) {
        this.countriesLocFilename = countriesLocFilename;
    }

    public void setIdeologiesFilename(String ideologiesFilename) {
        this.ideologiesFilename = ideologiesFilename;
    }

    public void setGovernmentsFilename(String governmentsFilename) {
        this.governmentsFilename = governmentsFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public void run() throws Exception {
        List<Country> countries = new CountryListReader(countriesFilename).readFile();
        List<Localisation> localisations = new ParadoxLocalisationParser().parseFile(new File(countriesLocFilename));

        List<Ideology> ideologies = new ArrayList<>();
        new IdeologyReader(ideologiesFilename).readFile().stream().map(Ideology.IdeologyGroup::getIdeologies).forEach(ideologies::addAll);

        Government.initIdeologies(ideologies);

        List<Government> governments = new GovernmentReader(governmentsFilename).readFile();
        Map<String, Government> nameToGovernment = governments.stream().collect(Collectors.toMap(government -> government.getName().toLowerCase(), government -> government));

        List<CountryRenaming> countryRenamings = countries.stream().map(CountryRenaming::new).collect(Collectors.toList());
        countryRenamings.forEach(country -> country.initializeRenamingMappings(governments));

        Map<String, CountryRenaming> tagToCountryRenaming = countryRenamings.stream().collect(Collectors.toMap(countryRenaming -> countryRenaming.getCountry().getTag(), countryRenaming -> countryRenaming));

        for (Localisation localisation : localisations) {
            /* Assuming that this is the standard default tag definition */
            if (StringUtils.length(localisation.getTitle()) == 3) {
                CountryRenaming countryRenaming = tagToCountryRenaming.get(localisation.getTitle());

                if (countryRenaming == null) {
                    continue;
                }

                countryRenaming.setDefaultName(localisation.getLocalisation());
                continue;
            }

            /* If a string doesn't start with 3 capital letters or digits, it won't be any tag names or adjective names */
            if (!localisation.getTitle().matches("[A-Z|0-9]{3}.+")) {
                continue;
            }

            String tag = localisation.getTitle().substring(0, 3);
            CountryRenaming countryRenaming = tagToCountryRenaming.get(tag);

            /* Handle country adjectives -- it'll be in the format of TAG_ADJ */
            if (localisation.getTitle().matches("[A-Z|0-9]{3}_ADJ")) {
                countryRenaming.getCountry().setAdjective(localisation.getLocalisation());
                continue;
            }

            String governmentName = localisation.getTitle().substring(4).toLowerCase();

            if (nameToGovernment.containsKey(governmentName)) {
                countryRenaming.setGovernmentName(localisation.getLocalisation(), nameToGovernment.get(governmentName));
            }
        }

        EventWriter writer = new EventWriter(outputFilename, generateDynamicLocEvents(countryRenamings), "Dynamic Loc Country Selection", 300000, 300001);
        writer.writeFile();
    }

    protected List<Event> generateDynamicLocEvents(List<CountryRenaming> countryRenamings) {
        List<Event> events = new ArrayList<>();

        events.add(getHandlerEvent());
        events.add(getRenamingEvent(countryRenamings));

        return events;
    }

    protected Event getHandlerEvent() {
        Event event = new Event();

        event.setId(300000);
        event.setTitle("Dynamic Loc Country Selection Handler");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getHandlerImmediate());
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getHandlerImmediate() {
        Immediate immediate = new Immediate();

        immediate.addEffect(getHandlerChangeVariableEffect());

        for (int i = 1; i <= NUMBER_OF_COUNTRY_SLOTS; i++) {
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
        countryCountMinimum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_country_count"));
        countryCountMinimum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i - 0.1)));
        owner.addCondition(countryCountMinimum);

        /* We can only display 8 countries at a time. When there are more than 8 countries, we display "next" -- meaning that if there are 9 potential options, we only want to display the first 7 */
        if (i == 8) {
            ConditionScope countryCountNot = ScriptingUtils.getNOTCondition();
            ConditionScope countryCountMaximum = ScriptingUtils.getConditionScope("check_variable");
            countryCountMaximum.addCondition(ScriptingUtils.getCondition("which", "dynamic_loc_country_count"));
            countryCountMaximum.addCondition(ScriptingUtils.getCondition("value", Double.toString(i + 0.1)));
            countryCountNot.addCondition(countryCountMaximum);
            owner.addCondition(countryCountNot);
        }

        limit.addCondition(owner);

        randomOwned.setLimit(limit);

        EffectScope ownerEffect = ScriptingUtils.getEffectScope("owner");

        EffectScope countryEvent = ScriptingUtils.getEffectScope("country_event");
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300001"));
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

    protected Event getRenamingEvent(List<CountryRenaming> countryRenamings) {
        Event event = new Event();

        event.setId(300001);
        event.setTitle("Dynamic Loc Country Selection Renaming");
        event.setDescription("");
        event.setTriggeredOnly(true);

        event.setImmediate(getRenamingImmediate(countryRenamings));
        event.addOption(getBasicOption());

        return event;
    }

    protected Immediate getRenamingImmediate(List<CountryRenaming> countryRenamings) {
        Immediate immediate = new Immediate();

        for (int i = 1; i <= NUMBER_OF_COUNTRY_SLOTS; i++) {
            immediate.addEffect(getRenamingImmediateSetup(i));
        }

        for (CountryRenaming countryRenaming : countryRenamings) {
            immediate.addEffects(getRenamings(countryRenaming));
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
        countryEvent.addEffect(ScriptingUtils.getEffect("id", "300000"));
        countryEvent.addEffect(ScriptingUtils.getEffect("days", "0"));
        countryEvent.setComment("Go back to the handler");

        immediate.addEffect(countryEvent);

        return immediate;
    }

    protected BasicEffect getRenamingImmediateSetup(int i) {

        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        randomOwned.setLimit(getRenamingImmediateRandomOwnedLimit(i));

        EffectScope countrySlot = ScriptingUtils.getEffectScope("dynamic_loc_country_slot_" + i);
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
        limit.addCondition(ScriptingUtils.getCondition("has_country_flag", "dynamic_loc_potential_country_target"));

        randomCountry.setLimit(limit);

        randomCountry.addEffect(ScriptingUtils.getEffect("set_country_flag", "dynamic_loc_country_target_" + i));
        randomCountry.addEffect(ScriptingUtils.getEffect("set_country_flag", "dynamic_loc_slot_selector"));
        randomCountry.addEffect(ScriptingUtils.getEffect("clr_country_flag", "dynamic_loc_potential_country_target"));

        return randomCountry;
    }

    protected List<BasicEffect> getRenamings(CountryRenaming countryRenaming) {

        List<BasicEffect> renamings = new ArrayList<>();

        int numberOfGroups = countryRenaming.getGovernmentGroupings().asMap().entrySet().size();

        for (Map.Entry<String, Collection<Government>> entry : countryRenaming.getGovernmentGroupings().asMap().entrySet()) {
            EffectScope dynamicLocSlots = ScriptingUtils.getEffectScope("dynamic_loc_slots");

            dynamicLocSlots.setLimit(getDynamicLocLimit(numberOfGroups, entry, countryRenaming.getCountry().getTag()));

            EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
            stateScope.addEffect(ScriptingUtils.getEffect("change_region_name", "\"@" + countryRenaming.getCountry().getTag() + " " + entry.getKey() + "\""));

            dynamicLocSlots.addEffect(stateScope);

            renamings.add(dynamicLocSlots);
        }

        return renamings;
    }

    protected ConditionScope getDynamicLocLimit(int numberOfGroups, Map.Entry<String, Collection<Government>> entry, String tag) {
        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        limit.addCondition(ScriptingUtils.getCondition("has_province_modifier", "dynamic_loc_slot_selector"));

        ConditionScope tagScope = ScriptingUtils.getConditionScope(tag);

        tagScope.addCondition(ScriptingUtils.getCondition("has_country_flag", "dynamic_loc_slot_selector"));

        if (numberOfGroups != 1 && entry.getValue().size() != 1) {
            ConditionScope or = ScriptingUtils.getORCondition();

            entry.getValue().forEach(government -> or.addCondition(ScriptingUtils.getCondition("government", government.getName())));

            tagScope.addCondition(or);
        } else if (numberOfGroups != 1) {
            entry.getValue().forEach(government -> tagScope.addCondition(ScriptingUtils.getCondition("government", government.getName())));
        }

        limit.addCondition(tagScope);

        return limit;
    }

    public static void main(String[] args) {
        try {
            DynamicLocCountrySetter generator = new DynamicLocCountrySetter();

            generator.setCountriesFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\countries.txt");
            generator.setCountriesLocFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\localisation\\countries.csv");
            generator.setIdeologiesFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\ideologies.txt");
            generator.setGovernmentsFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\governments.txt");
            generator.setOutputFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/events/Dynamic Loc Country Selection.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class CountryRenaming {
        protected Country country;
        protected Map<Government, String> namePerGovernment;
        protected String defaultName = "";

        public CountryRenaming() {
            namePerGovernment = new HashMap<>();
        }

        public CountryRenaming(Country country) {
            this();

            this.country = country;
        }

        public void initializeRenamingMappings(List<Government> governments) {
            if (country == null) {
                Logger.error("Unable to initialize renaming mappings -- country is not defined!");
                return;
            }

            for (Government government : governments) {
                namePerGovernment.putIfAbsent(government, country.getTag());
            }

            defaultName = country.getTag();

        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        public void setDefaultName(String newDefaultName) {

            for (Government government : namePerGovernment.keySet()) {
                if (StringUtils.equalsIgnoreCase(defaultName, namePerGovernment.get(government))) {
                    namePerGovernment.put(government, newDefaultName);
                }
            }

            this.defaultName = newDefaultName;
        }

        public void setGovernmentName(String name, Government government) {
            namePerGovernment.put(government, name);
        }

        public ListMultimap<String, Government> getGovernmentGroupings() {
            ListMultimap<String, Government> governmentGroupings = ArrayListMultimap.create();

            namePerGovernment.forEach((key, value) -> governmentGroupings.put(value, key));

            return governmentGroupings;
        }
    }
}
