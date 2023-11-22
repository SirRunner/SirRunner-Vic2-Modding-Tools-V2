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
import java.util.ArrayList;
import java.util.List;

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

        for (LeaderAgeConfiguration leaderAgeConfiguration : LeaderAgeConfiguration.values()) {
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

            EffectScope loopScope = ScriptingUtils.getEffectScope("country_event");
            loopScope.addEffect(ScriptingUtils.getEffect("id", Integer.toString(leaderAgeConfiguration.getId())));
            loopScope.addEffect(ScriptingUtils.getEffect("days", "365"));

            option.addEffect(loopScope);

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

        ConditionScope lowerBound = ScriptingUtils.getConditionScope("check_variable");
        Condition LBvariable = ScriptingUtils.getCondition("which", leaderAgeConfiguration.getVariable());
        Condition LBvalue = ScriptingUtils.getCondition("value", (age - 1) + ".9");
        lowerBound.addCondition(LBvariable);
        lowerBound.addCondition(LBvalue);

        ConditionScope NOT = ScriptingUtils.getNOTCondition();
        ConditionScope upperBound = ScriptingUtils.getConditionScope("check_variable");
        Condition UBvariable = ScriptingUtils.getCondition("which", leaderAgeConfiguration.getVariable());
        Condition UBvalue = ScriptingUtils.getCondition("value", age + ".1");
        upperBound.addCondition(UBvariable);
        upperBound.addCondition(UBvalue);
        NOT.addCondition(upperBound);

        FOR.addCondition(lowerBound);
        FOR.addCondition(NOT);
        ownerCondition.addCondition(FOR);
        limit.addCondition(ownerCondition);

        region.setLimit(limit);

        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        Effect changeRegionName = ScriptingUtils.getEffect("change_region_name", "\"§Y" + leaderAgeConfiguration.getText() + "§! is currently §Y" + age + "§! years old\"");

        stateScope.addEffect(changeRegionName);
        region.addEffect(stateScope);

        return region;
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
