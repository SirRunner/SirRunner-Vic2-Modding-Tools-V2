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
import events.nodes.Option;
import events.writer.EventWriter;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.parsing.localisation.ParadoxLocalisationParser;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
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

    public static final int NUMBER_COUNTRY_SELECTION_EVENTS = 8;
    public static final int STARTING_EVENT_ID = 300000;

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

        EventWriter writer = new EventWriter(outputFilename, generateDynamicLocEvents(countryRenamings), "Dynamic Loc Country Selection", STARTING_EVENT_ID, STARTING_EVENT_ID + NUMBER_COUNTRY_SELECTION_EVENTS - 1);
        writer.writeFile();
    }

    protected List<Event> generateDynamicLocEvents(List<CountryRenaming> countryRenamings) {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < NUMBER_COUNTRY_SELECTION_EVENTS; i++) {
            Event event = new Event();

            event.setId(STARTING_EVENT_ID + i);
            event.setTitle("Dynamic Loc Country Selection " + (i + 1));
            event.setDescription("");
            event.setTriggeredOnly(true);

            event.addOption(getEventOption(i, countryRenamings));

            events.add(event);
        }

        return events;
    }

    protected Option getEventOption(int i, List<CountryRenaming> countryRenamings) {
        Option option = new Option();

        option.setName("Assign Loc");
        option.addEffect(ScriptingUtils.getEffect("clr_global_flag", "dynamic_loc_option_" + (i + 1) + "_found"));

        for (CountryRenaming countryRenaming : countryRenamings) {

            int numberOfGroups = countryRenaming.getGovernmentGroupings().asMap().entrySet().size();

            for (Map.Entry<String, Collection<Government>> entry : countryRenaming.getGovernmentGroupings().asMap().entrySet()) {

                EffectScope randomCountry = ScriptingUtils.getEffectScope("random_country");

                ConditionScope limit = ScriptingUtils.getConditionScope("limit");

                limit.addCondition(ScriptingUtils.getCondition("tag", countryRenaming.getCountry().getTag()));
                limit.addCondition(ScriptingUtils.getCondition("has_country_flag", "dynamic_loc_potential_country_target"));

                if (numberOfGroups != 1 && entry.getValue().size() != 1) {
                    ConditionScope or = ScriptingUtils.getORCondition();

                    entry.getValue().forEach(government -> or.addCondition(ScriptingUtils.getCondition("government", government.getName())));

                    limit.addCondition(or);
                } else if (numberOfGroups != 1) {
                    entry.getValue().forEach(government -> limit.addCondition(ScriptingUtils.getCondition("government", government.getName())));
                }

                ConditionScope not = ScriptingUtils.getNOTCondition();

                not.addCondition(ScriptingUtils.getCondition("has_global_flag", "dynamic_loc_option_" + (i + 1) + "_found"));
                limit.addCondition(not);

                randomCountry.setLimit(limit);

                EffectScope locRegion = ScriptingUtils.getEffectScope("dynamic_loc_country_slot_" + (i + 1));
                EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");

                stateScope.addEffect(ScriptingUtils.getEffect("change_region_name", "\"@" + countryRenaming.getCountry().getTag() + " " + entry.getKey() + "\""));
                locRegion.addEffect(stateScope);

                randomCountry.addEffect(locRegion);
                randomCountry.addEffect(ScriptingUtils.getEffect("clr_country_flag", "dynamic_loc_potential_country_target"));
                randomCountry.addEffect(ScriptingUtils.getEffect("set_country_flag", "dynamic_loc_country_target_" + (i + 1)));
                randomCountry.addEffect(ScriptingUtils.getEffect("set_global_flag", "dynamic_loc_option_" + (i + 1) + "_found"));

                option.addEffect(randomCountry);

            }
        }

        return option;
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
