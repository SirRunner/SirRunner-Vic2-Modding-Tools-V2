package test;

import utils.paradox.parsing.ParadoxParsingUtils;
import utils.paradox.parsing.localisation.ParadoxLocalisationParser;
import utils.paradox.scripting.localisation.Localisation;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws IOException {

//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Harrison Greene\\Downloads\\test.txt"), "Cp1252"));
//
//        reader.lines().filter(line -> !StringUtils.contains(line, "_")).forEach(line -> {
//            String[] parts = line.split(";");
//            System.out.println(parts[0] + "_loc = { } # @" + parts[0] + " §Y" + parts[1] + "§!\\n");
//            System.out.println("\t\t\trandom_owned = {");
//            System.out.println("\t\t\t\tlimit = {");
//            System.out.println("\t\t\t\t\towner = {");
//            System.out.println("\t\t\t\t\t\t" + parts[0] + " = { has_country_modifier = trading_with_DAL }");
//            System.out.println("\t\t\t\t\t}");
//            System.out.println("\t\t\t\t}");
//            System.out.println("\t\t\t\towner = { " + parts[0] + "_loc = {} }");
//            System.out.println("\t\t\t}");
//            System.out.println("\t\t\t");
//        });

//        for (int i = 0; i < 501; i++) {
//            EffectScope region = ScriptingUtils.getEffectScope("DAL_trading_realm_count");
//            region.setIndent(2);
//
//            ConditionScope limit = ScriptingUtils.getConditionScope("limit");
//            limit.setIndent(3);
//
//            ConditionScope ownerCondition = ScriptingUtils.getConditionScope("owner");
//            ConditionScope FOR = ScriptingUtils.getConditionScope("FOR");
//
//            ConditionScope lowerBound = ScriptingUtils.getConditionScope("check_variable");
//            Condition LBvariable = ScriptingUtils.getCondition("which", "esgaroth_trading");
//            Condition LBvalue = ScriptingUtils.getCondition("value", (i - 1) + ".9");
//            lowerBound.addCondition(LBvariable);
//            lowerBound.addCondition(LBvalue);
//
//            ConditionScope NOT = ScriptingUtils.getNOTCondition();
//            ConditionScope upperBound = ScriptingUtils.getConditionScope("check_variable");
//            Condition UBvariable = ScriptingUtils.getCondition("which", "esgaroth_trading");
//            Condition UBvalue = ScriptingUtils.getCondition("value", i + ".1");
//            upperBound.addCondition(UBvariable);
//            upperBound.addCondition(UBvalue);
//            NOT.addCondition(upperBound);
//
//            FOR.addCondition(lowerBound);
//            FOR.addCondition(NOT);
//            ownerCondition.addCondition(FOR);
//            limit.addCondition(ownerCondition);
//            region.setLimit(limit);
//
//            EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
//
//            Effect changeRegionName = ScriptingUtils.getEffect("change_region_name", "We are currently trading with §Y" + i + "§! realms:\\n");
//
//            stateScope.addEffect(changeRegionName);
//            region.addEffect(stateScope);
//
//            System.out.println(region);
//        }

        try {
            ParadoxLocalisationParser parser = new ParadoxLocalisationParser();
            List<Localisation> localisations = parser.parseFile(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\localisation\\events.csv"));

            localisations.sort((o1, o2) -> {

                int firstLineCount = countLines(o1);
                int secondLineCount = countLines(o2);

                return -1 * Integer.compare(firstLineCount, secondLineCount);

            });

            System.out.println(localisations.get(0).getTitle());
            System.out.println(localisations.get(1).getTitle());
            System.out.println(localisations.get(2).getTitle());
            System.out.println(localisations.get(3).getTitle());
            System.out.println(localisations.get(4).getTitle());
            System.out.println(localisations.get(5).getTitle());
            System.out.println(localisations.get(6).getTitle());
            System.out.println(localisations.get(7).getTitle());
            System.out.println(localisations.get(8).getTitle());
            System.out.println(localisations.get(9).getTitle());

            System.out.println(localisations.get(10).getTitle());
            System.out.println(localisations.get(11).getTitle());
            System.out.println(localisations.get(12).getTitle());
            System.out.println(localisations.get(13).getTitle());
            System.out.println(localisations.get(14).getTitle());
            System.out.println(localisations.get(15).getTitle());
            System.out.println(localisations.get(16).getTitle());
            System.out.println(localisations.get(17).getTitle());
            System.out.println(localisations.get(18).getTitle());
            System.out.println(localisations.get(19).getTitle());

            System.out.println(localisations.get(20).getTitle());
            System.out.println(localisations.get(21).getTitle());
            System.out.println(localisations.get(22).getTitle());
            System.out.println(localisations.get(23).getTitle());
            System.out.println(localisations.get(24).getTitle());
            System.out.println(localisations.get(25).getTitle());
            System.out.println(localisations.get(26).getTitle());
            System.out.println(localisations.get(27).getTitle());
            System.out.println(localisations.get(28).getTitle());
            System.out.println(localisations.get(29).getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int countLines(Localisation localisation) {
        String description = localisation.getLocalisation(Localisation.Language.ENGLISH);

        String[] newlineSplits = description.split("\\\\n");
        int count = 0;

        for (String section: newlineSplits) {
            count++;
            count += section.length() / ParadoxParsingUtils.EVENT_DESC_AVERAGE_CHARACTERS_PER_LINE;
        }

        return count;
    }


}
