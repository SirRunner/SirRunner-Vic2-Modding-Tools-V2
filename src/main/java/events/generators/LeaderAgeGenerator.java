package events.generators;

import events.nodes.Event;
import events.nodes.Option;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.Effect;
import utils.paradox.scripting.effects.EffectScope;

import java.io.FileWriter;
import java.nio.charset.Charset;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LeaderAgeGenerator {

    protected String eventsFilename;

    public String getEventsFilename() {
        return eventsFilename;
    }

    public void setEventsFilename(String eventsFilename) {
        this.eventsFilename = eventsFilename;
    }

    public void run() {

        List<Event> events = new ArrayList<>();

        for (LeaderAgeConfiguration leaderAgeConfiguration : LeaderAgeConfiguration.getConfigurationsByType(LeaderAgeConfiguration.ConfigurationType.AGE)) {
            Event event = new Event();

            event.setId(leaderAgeConfiguration.getId());
            event.setPicture(leaderAgeConfiguration.getPicture());
            event.setTitle("Updating " + leaderAgeConfiguration.getText() + "'s Age");
            event.setDescription("");
            event.setTriggeredOnly(true);

            Option option = new Option();
            option.setName("Update please");

            EffectScope variableTag = ScriptingUtils.getEffectScope("FOR");
            EffectScope changeVariable = ScriptingUtils.getEffectScope("change_variable");

            Effect which = ScriptingUtils.getEffect("which", leaderAgeConfiguration.getVariable());
            Effect value = ScriptingUtils.getEffect("value", "1");
            changeVariable.addEffect(which);
            changeVariable.addEffect(value);
            variableTag.addEffect(changeVariable);

            option.addEffect(variableTag);

            EffectScope secedeScope = ScriptingUtils.getEffectScope(Integer.toString(leaderAgeConfiguration.getProvince()));
            secedeScope.addEffect(ScriptingUtils.getEffect("secede_province", "THIS"));

            option.addEffect(secedeScope);

            for (int i = leaderAgeConfiguration.getStartAge(); i <= leaderAgeConfiguration.getMaxAge(); i++) {
                option.addEffect(getUpdateEffect(i, leaderAgeConfiguration));
            }

            EffectScope returnScope = ScriptingUtils.getEffectScope(Integer.toString(leaderAgeConfiguration.getProvince()));
            Effect secede = ScriptingUtils.getEffect("secede_province", "---");
            secede.setComment("Audax Validator \".\" Ignore_NEXT");
            secede.setOneLineOverride(false);
            returnScope.setOneLineOverride(false);
            returnScope.addEffect(secede);

            option.addEffect(returnScope);

            /* All to make sure that the event stops looping when a leader dies -- -2 = death, -1 = not born */
            EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");
            ConditionScope limit = ScriptingUtils.getConditionScope("limit");
            ConditionScope ownerCondition = ScriptingUtils.getConditionScope("owner");
            ConditionScope FOR = ScriptingUtils.getConditionScope("FOR");

            ConditionScope lowerBound = ScriptingUtils.getConditionScope("check_variable");
            Condition LBvariable = ScriptingUtils.getCondition("which", leaderAgeConfiguration.getVariable());
            Condition LBvalue = ScriptingUtils.getCondition("value", "-0.1");
            lowerBound.addCondition(LBvariable);
            lowerBound.addCondition(LBvalue);

            FOR.addCondition(lowerBound);
            ownerCondition.addCondition(FOR);
            limit.addCondition(ownerCondition);
            randomOwned.setLimit(limit);

            EffectScope owner = ScriptingUtils.getEffectScope("owner");
            EffectScope loopScope = ScriptingUtils.getEffectScope("country_event");
            loopScope.addEffect(ScriptingUtils.getEffect("id", Integer.toString(leaderAgeConfiguration.getId())));
            loopScope.addEffect(ScriptingUtils.getEffect("days", "365"));

            owner.addEffect(loopScope);
            randomOwned.addEffect(owner);
            option.addEffect(randomOwned);

            event.addOption(option);

            events.add(event);
        }

        for (LeaderAgeConfiguration leaderAgeConfiguration : LeaderAgeConfiguration.getConfigurationsByType(LeaderAgeConfiguration.ConfigurationType.BIRTH)) {
            Event event = new Event();

            event.setId(leaderAgeConfiguration.getId());
            event.setPicture(leaderAgeConfiguration.getPicture());
            event.setTitle("Setting " + leaderAgeConfiguration.getText() + "'s Birth Month and Year");
            event.setDescription("");
            event.setTriggeredOnly(true);

            Option option = new Option();
            option.setName("Update please");

            EffectScope secedeScope = ScriptingUtils.getEffectScope(Integer.toString(leaderAgeConfiguration.getProvince()));
            secedeScope.addEffect(ScriptingUtils.getEffect("secede_province", "THIS"));

            option.addEffect(secedeScope);

            /* Start Age = first year leader could be born. Max Age = last year leader could be born */
            for (int i = leaderAgeConfiguration.getStartAge(); i <= leaderAgeConfiguration.getMaxAge(); i++) {
                for (int month = 1; month <= 12; month++) {
                    option.addEffect(getBirthUpdateEffect(i, month, leaderAgeConfiguration));
                }
            }

            EffectScope returnScope = ScriptingUtils.getEffectScope(Integer.toString(leaderAgeConfiguration.getProvince()));
            Effect secede = ScriptingUtils.getEffect("secede_province", "---");
            secede.setComment("Audax Validator \".\" Ignore_NEXT");
            secede.setOneLineOverride(false);
            returnScope.setOneLineOverride(false);
            returnScope.addEffect(secede);

            option.addEffect(returnScope);

            event.addOption(option);

            events.add(event);
        }

        for (LeaderAgeConfiguration leaderAgeConfiguration: LeaderAgeConfiguration.getConfigurationsByType(LeaderAgeConfiguration.ConfigurationType.DEATH)) {
            Event event = new Event();

            event.setId(leaderAgeConfiguration.getId());
            event.setPicture(leaderAgeConfiguration.getPicture());
            event.setTitle("Setting " + leaderAgeConfiguration.getText() + "'s Death Month and Year");
            event.setDescription("");
            event.setTriggeredOnly(true);

            Option option = new Option();
            option.setName("Update please");

            EffectScope variableTag = ScriptingUtils.getEffectScope("FOR");
            EffectScope setVariable = ScriptingUtils.getEffectScope("set_variable");

            Effect which = ScriptingUtils.getEffect("which", leaderAgeConfiguration.getVariable());
            Effect value = ScriptingUtils.getEffect("value", "-3");
            setVariable.addEffect(which);
            setVariable.addEffect(value);
            variableTag.addEffect(setVariable);

            option.addEffect(variableTag);

            EffectScope secedeScope = ScriptingUtils.getEffectScope(Integer.toString(leaderAgeConfiguration.getProvince()));
            secedeScope.addEffect(ScriptingUtils.getEffect("secede_province", "THIS"));

            option.addEffect(secedeScope);

            /* Start Age = first year leader could die. Max Age = last year leader could die */
            for (int i = leaderAgeConfiguration.getStartAge(); i <= leaderAgeConfiguration.getMaxAge(); i++) {
                for (int month = 1; month <= 12; month++) {
                    option.addEffect(getDeathUpdateEffect(i, month, leaderAgeConfiguration));
                }
            }

            EffectScope returnScope = ScriptingUtils.getEffectScope(Integer.toString(leaderAgeConfiguration.getProvince()));
            Effect secede = ScriptingUtils.getEffect("secede_province", "---");
            secede.setComment("Audax Validator \".\" Ignore_NEXT");
            secede.setOneLineOverride(false);
            returnScope.setOneLineOverride(false);
            returnScope.addEffect(secede);

            option.addEffect(returnScope);

            event.addOption(option);

            events.add(event);

        }

        try (FileWriter writer = new FileWriter(getEventsFilename(), Charset.forName("windows-1252"))) {
            for (Event event : events) {
                writer.write(event.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save events!");
        }
    }

    protected BasicEffect getUpdateEffect(int age, LeaderAgeConfiguration leaderAgeConfiguration) {
        EffectScope region = ScriptingUtils.getEffectScope(leaderAgeConfiguration.getRegion());

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");

        ConditionScope ownerCondition = ScriptingUtils.getConditionScope("owner");
        ConditionScope FOR = ScriptingUtils.getConditionScope("FOR");

        String lowerBoundValue = (age - 1) + ".9";

        if (age < 0) {
            lowerBoundValue = age + ".1";
        } else if (age == 0) {
            lowerBoundValue = "-0.1";
        }

        ConditionScope lowerBound = ScriptingUtils.getConditionScope("check_variable");
        Condition LBvariable = ScriptingUtils.getCondition("which", leaderAgeConfiguration.getVariable());
        Condition LBvalue = ScriptingUtils.getCondition("value", lowerBoundValue);
        lowerBound.addCondition(LBvariable);
        lowerBound.addCondition(LBvalue);

        String upperBoundValue = age + ".1";

        if (age < -1) {
            upperBoundValue = (age + 1) + ".9";
        } else if (age == -1) {
            upperBoundValue = "-0.9";
        }

        ConditionScope NOT = ScriptingUtils.getNOTCondition();
        ConditionScope upperBound = ScriptingUtils.getConditionScope("check_variable");
        Condition UBvariable = ScriptingUtils.getCondition("which", leaderAgeConfiguration.getVariable());
        Condition UBvalue = ScriptingUtils.getCondition("value", upperBoundValue);
        upperBound.addCondition(UBvariable);
        upperBound.addCondition(UBvalue);
        NOT.addCondition(upperBound);

        FOR.addCondition(lowerBound);
        FOR.addCondition(NOT);
        ownerCondition.addCondition(FOR);
        limit.addCondition(ownerCondition);

        region.setLimit(limit);

        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");

        String text = "\"§Y" + leaderAgeConfiguration.getText() + "§! is currently §Y" + age + "§! years old§I\"";

        if (age == -1) {
            text = "\"§Y" + leaderAgeConfiguration.getText() + "§! has not yet been born§I\"";
        }

        Effect changeRegionName = ScriptingUtils.getEffect("change_region_name", text);

        stateScope.addEffect(changeRegionName);
        region.addEffect(stateScope);

        return region;
    }

    protected BasicEffect getBirthUpdateEffect(int year, int monthInt, LeaderAgeConfiguration configuration) {

        Month month = Month.of(monthInt);

        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");
        Condition provinceId = ScriptingUtils.getCondition("province_id", Integer.toString(configuration.getProvince()));
        ConditionScope owner = ScriptingUtils.getConditionScope("owner");
        Condition currentYear = ScriptingUtils.getCondition("year", Integer.toString(year));
        ConditionScope notYear = ScriptingUtils.getNOTCondition();
        Condition nextYear = ScriptingUtils.getCondition("year", Integer.toString(year + 1));
        Condition currentMonth = ScriptingUtils.getCondition("month", Integer.toString(monthInt - 1));
        ConditionScope notMonth = ScriptingUtils.getNOTCondition();
        Condition nextMonth = ScriptingUtils.getCondition("month", Integer.toString(monthInt));

        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        Effect changeRegionName = ScriptingUtils.getEffect("change_region_name", "\"§Y" + configuration.getText() + "§! was born in §Y" + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) +"§! of §YTA " + year + "§!§I\"");

        notYear.addCondition(nextYear);
        notMonth.addCondition(nextMonth);

        owner.addCondition(currentYear);
        owner.addCondition(notYear);
        owner.addCondition(currentMonth);
        owner.addCondition(notMonth);

        limit.addCondition(provinceId);
        limit.addCondition(owner);

        stateScope.addEffect(changeRegionName);
        randomOwned.addEffect(stateScope);

        randomOwned.setLimit(limit);

        return randomOwned;
    }

    protected BasicEffect getDeathUpdateEffect(int year, int monthInt, LeaderAgeConfiguration configuration) {

        Month month = Month.of(monthInt);

        EffectScope randomOwned = ScriptingUtils.getEffectScope("random_owned");

        ConditionScope limit = ScriptingUtils.getConditionScope("limit");
        Condition provinceId = ScriptingUtils.getCondition("province_id", Integer.toString(configuration.getProvince()));
        ConditionScope owner = ScriptingUtils.getConditionScope("owner");
        Condition currentYear = ScriptingUtils.getCondition("year", Integer.toString(year));
        ConditionScope notYear = ScriptingUtils.getNOTCondition();
        Condition nextYear = ScriptingUtils.getCondition("year", Integer.toString(year + 1));
        Condition currentMonth = ScriptingUtils.getCondition("month", Integer.toString(monthInt - 1));
        ConditionScope notMonth = ScriptingUtils.getNOTCondition();
        Condition nextMonth = ScriptingUtils.getCondition("month", Integer.toString(monthInt));

        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        Effect changeRegionName = ScriptingUtils.getEffect("change_region_name", "\"§Y" + configuration.getText() + "§! died in §Y" + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) +"§! of §YTA " + year + "§!§I\"");

        notYear.addCondition(nextYear);
        notMonth.addCondition(nextMonth);

        owner.addCondition(currentYear);
        owner.addCondition(notYear);
        owner.addCondition(currentMonth);
        owner.addCondition(notMonth);

        limit.addCondition(provinceId);
        limit.addCondition(owner);

        stateScope.addEffect(changeRegionName);
        randomOwned.addEffect(stateScope);

        randomOwned.setLimit(limit);

        return randomOwned;
    }

    public static void main(String[] args) {
        try {
            LeaderAgeGenerator generator = new LeaderAgeGenerator();

            generator.setEventsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/events/Leader Age Calculations.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
