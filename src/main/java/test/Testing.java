package test;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.Effect;
import utils.paradox.scripting.effects.EffectScope;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class Testing {
    public static void main(String[] args) throws IOException {

//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Harrison Greene\\Downloads\\test.txt"), "Cp1252"));

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

        for (int i = 0; i < 501; i++) {
            EffectScope region = ScriptingUtils.getEffectScope("DAL_trading_realm_count");
            region.setIndent(2);

            ConditionScope limit = ScriptingUtils.getConditionScope("limit");
            limit.setIndent(3);

            ConditionScope ownerCondition = ScriptingUtils.getConditionScope("owner");
            ConditionScope FOR = ScriptingUtils.getConditionScope("FOR");

            ConditionScope lowerBound = ScriptingUtils.getConditionScope("check_variable");
            Condition LBvariable = ScriptingUtils.getCondition("which", "esgaroth_trading");
            Condition LBvalue = ScriptingUtils.getCondition("value", (i - 1) + ".9");
            lowerBound.addCondition(LBvariable);
            lowerBound.addCondition(LBvalue);

            ConditionScope NOT = ScriptingUtils.getNOTCondition();
            ConditionScope upperBound = ScriptingUtils.getConditionScope("check_variable");
            Condition UBvariable = ScriptingUtils.getCondition("which", "esgaroth_trading");
            Condition UBvalue = ScriptingUtils.getCondition("value", i + ".1");
            upperBound.addCondition(UBvariable);
            upperBound.addCondition(UBvalue);
            NOT.addCondition(upperBound);

            FOR.addCondition(lowerBound);
            FOR.addCondition(NOT);
            ownerCondition.addCondition(FOR);
            limit.addCondition(ownerCondition);
            region.setLimit(limit);

            EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");

            Effect changeRegionName = ScriptingUtils.getEffect("change_region_name", "We are currently trading with §Y" + i + "§! realms:\\n");

            stateScope.addEffect(changeRegionName);
            region.addEffect(stateScope);

            System.out.println(region);
        }
    }


}
