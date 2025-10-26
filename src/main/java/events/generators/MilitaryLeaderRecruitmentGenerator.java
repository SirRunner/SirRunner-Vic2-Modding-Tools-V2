package events.generators;

import common.Culture;
import common.readers.CultureReader;
import common.traits.Background;
import common.traits.Personality;
import events.nodes.Event;
import events.nodes.Option;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.EffectScope;

import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.*;

public class MilitaryLeaderRecruitmentGenerator extends AbstractMultiEventGenerator {

    private final int NUM_YEARS_BETWEEN_RECRUITMENT = 5;
    private final int NUM_RECRUITED_LEADERS = 10;
    private final int NUM_OPTIONS_FOR_EACH_LEADER = 50;

    public static final String GONDORIAN = "gondorian";
    public static final String DRUEDAIN = "druedain";

    protected final LinkedHashMap<String, Culture.CultureGroup> cultureGroups;

    public MilitaryLeaderRecruitmentGenerator(String filename, String headerName, int lowerId, int upperId, LinkedHashMap<String, Culture.CultureGroup> cultures) {
        super(filename, headerName, lowerId, upperId);

        this.cultureGroups = cultures;
    }

    protected String getDescription(int id) {
        return "EVTDESC" + lowerId;
    }

    @Override
    protected String getPicture() {
        return "supportwar";
    }

    @Override
    protected String getTitle(int id) {
        if (Utils.isEven(id)) {
            return "New Generals";
        }

        return "New Admirals";
    }

    @Override
    protected List<Option> getOptions(int id) throws Exception {
        Option option = new Option();

        option.setName("Let them prove their worth on the battlefield");

        option.addEffect("set_global_flag", "hidden_effects");

        EffectScope randomOwned1 = ScriptingUtils.getEffectScope("random_owned");
        ConditionScope limit1 = ScriptingUtils.getConditionScope("limit");
        ConditionScope owner1 = ScriptingUtils.getConditionScope("owner");
        owner1.addCondition("has_global_flag", "hidden_effects");

        ConditionScope not1 = ScriptingUtils.getNOTCondition();
        not1.addCondition("invention", "military_schools");
        owner1.addCondition(not1);
        limit1.addCondition(owner1);
        randomOwned1.setLimit(limit1);

        EffectScope ownerEffect1 = ScriptingUtils.getEffectScope("owner");

        ownerEffect1.addEffect("remove_country_modifier", getCountryModifierName(id));

        EffectScope addCountryModifier1 = ScriptingUtils.getEffectScope("add_country_modifier");
        addCountryModifier1.addEffect("name", getCountryModifierName(id));
        addCountryModifier1.addEffect("duration", Integer.toString(NUM_YEARS_BETWEEN_RECRUITMENT * 365));

        ownerEffect1.addEffect(addCountryModifier1);

        for (int i = 0; i < NUM_RECRUITED_LEADERS; i++) {
            ownerEffect1.addEffect(getLeaderSelection(id, true));
        }

        randomOwned1.addEffect(ownerEffect1);
        option.addEffect(randomOwned1);

        EffectScope randomOwned2 = ScriptingUtils.getEffectScope("random_owned");
        ConditionScope limit2 = ScriptingUtils.getConditionScope("limit");
        ConditionScope owner2 = ScriptingUtils.getConditionScope("owner");
        owner2.addCondition("has_global_flag", "hidden_effects");
        owner2.addCondition("invention", "military_schools");
        limit2.addCondition(owner2);
        randomOwned2.setLimit(limit2);

        EffectScope ownerEffect2 = ScriptingUtils.getEffectScope("owner");

        ownerEffect2.addEffect("remove_country_modifier", getCountryModifierName(id));

        EffectScope addCountryModifier2 = ScriptingUtils.getEffectScope("add_country_modifier");
        addCountryModifier2.addEffect("name", getCountryModifierName(id));
        addCountryModifier2.addEffect("duration", Integer.toString(NUM_YEARS_BETWEEN_RECRUITMENT * 365));

        ownerEffect2.addEffect(addCountryModifier2);

        for (int i = 0; i < NUM_RECRUITED_LEADERS; i++) {
            ownerEffect2.addEffect(getLeaderSelection(id, false));
        }

        randomOwned2.addEffect(ownerEffect2);
        option.addEffect(randomOwned2);

        option.addEffect("clr_global_flag", "hidden_effects");
        option.addEffect(ScriptingUtils.getEffectScope(getLocName(id)));

        return Collections.singletonList(option);
    }

    protected EffectScope getLeaderSelection(int id, boolean allowNegativeTraits) throws Exception {
        EffectScope scope = ScriptingUtils.getEffectScope("random_list");

        for (int i = 0; i < NUM_OPTIONS_FOR_EACH_LEADER; i++) {
            EffectScope weight = ScriptingUtils.getEffectScope("1");
            EffectScope defineLeader = ScriptingUtils.getEffectScope(getDefineScopeName(id));
            defineLeader.addEffect("name", getLeaderName(id));

            Background background = Background.getRandomBackground();
            Personality personality = null;

            if (!allowNegativeTraits && background.equals(Background.NOBLEMAN_BAD)) {
                personality = Personality.getRandomCombinationPersonality();
            } else {
                personality = Personality.getRandomPersonality(); // all personalities are neutral or positive
            }

            defineLeader.addEffect("background", background.name().toLowerCase());
            defineLeader.addEffect("personality", personality.name().toLowerCase());

            weight.addEffect(defineLeader);
            scope.addEffect(weight);
        }

        return scope;
    }

    private String getLocName(int id) {
        if (Utils.isEven(id)) {
            return "recruit_generals_loc";
        }

        return "recruit_admirals_loc";
    }

    private String getCountryModifierName(int id) {
        if (Utils.isEven(id)) {
            return "recruited_generals";
        }

        return "recruited_admirals";
    }

    private String getDefineScopeName(int id) {
        if (Utils.isEven(id)) {
            return "define_general";
        }

        return "define_admiral";
    }

    private String getLeaderName(int id) throws Exception {
        int index = getCultureGroupIndex(id);

        Map.Entry<String, Culture.CultureGroup> entry = new ArrayList<>(cultureGroups.entrySet()).get(index);
        Culture.CultureGroup culture = entry.getValue();

        /* Special circumstances */
        if (entry.getKey().equalsIgnoreCase(GONDORIAN)) {
            return "\"" + culture.getRandomLeaderName(GONDORIAN) + "\"";
        } else if (entry.getKey().equalsIgnoreCase(DRUEDAIN)) {
            return "\"" + culture.getRandomLeaderName(DRUEDAIN) + "\"";
        }

        return "\"" + culture.getRandomLeaderName() + "\"";
    }

    private int getCultureGroupIndex(int id) throws Exception {
        int eventNumber = id - lowerId;
        int index = eventNumber / 2;

        if (index >= cultureGroups.size()) {
            throw new Exception(String.format("index %s, calculated from id %s and lowerId %s surpassed the number of indexes in the culture list, %s", index, id, lowerId, cultureGroups.size()));
        }

        return index;
    }

    /* Overriding to put the culture group in the "comment title" */
    @Override
    public void generate() {
        try (FileWriter writer = new FileWriter(getFilename(), Charset.forName("windows-1252"))) {
            writer.write("# DO NOT MANUALLY UPDATE THIS FILE -- IS AUTO-GENERATED\n");
            writer.write("# Remove this line to have the validator check for strings in localisation\n");
            writer.write("# Audax Validator \"!\" Ignore_1004\n");
            writer.write("\n");
            writer.write("# " + getHeaderName() + " Events: {\n");
            writer.write("#\t" + getLowerId() + " - " + getUpperId() + " }\n");

            for (Event event : getEvents()) {
                writer.write("\n");

                String groupName = new ArrayList<>(cultureGroups.entrySet()).get(getCultureGroupIndex(event.getId())).getKey();

                writer.write("# " + event.getTitle() + "(" + groupName + ")\n");
                writer.write(event.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save events!");
        }
    }

    public static void main(String[] args) {
        try {
            List<Culture.CultureGroup> cultureGroups = new CultureReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\cultures.txt").readFile();
            cultureGroups.removeIf(cultureGroup -> StringUtils.equalsIgnoreCase(cultureGroup.getName(), "Banker") || StringUtils.equalsIgnoreCase(cultureGroup.getName(), "Religions") || StringUtils.equalsIgnoreCase(cultureGroup.getName(), "good_evil") || StringUtils.endsWith(cultureGroup.getName(), "_culture"));

            LinkedHashMap<String, Culture.CultureGroup> usableCultureGroups = new LinkedHashMap<>();

            for (Culture.CultureGroup group : cultureGroups) {
                /* Northern Dunedain/Gondor split + Druedain/Wildmen split  */
                if (group.getName().equalsIgnoreCase("dunedain")) {
                    usableCultureGroups.put(MilitaryLeaderRecruitmentGenerator.GONDORIAN, group);
                }
                if (group.getName().equalsIgnoreCase("wildmen")) {
                    usableCultureGroups.put(MilitaryLeaderRecruitmentGenerator.DRUEDAIN, group);
                }

                /* Northmen/Gramavuld Northmen + Dwarves/Wicked Dwarves + Uruk Hai/Mordorian/Northern Goblins use the same names  */
                if (!group.getName().equalsIgnoreCase("gramavuld_northmen") && !group.getName().equalsIgnoreCase("wicked_dwarves") && !group.getName().equalsIgnoreCase("uruk_hai") && !group.getName().equalsIgnoreCase("northern_goblins")) {
                    usableCultureGroups.put(group.getName(), group);
                }
            }

            MilitaryLeaderRecruitmentGenerator generator = new MilitaryLeaderRecruitmentGenerator("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\events\\Military Leader Recruitment.txt", "Military Leader Events", 40000, 40000 + ((usableCultureGroups.size() - 1) * 2) + 1, usableCultureGroups);
            generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
