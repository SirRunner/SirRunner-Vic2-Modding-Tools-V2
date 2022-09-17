package utils.paradox.scripting;

import utils.Logger;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.Effect;
import utils.paradox.scripting.effects.EffectScope;

public class ScriptingUtils {
    public ScriptingUtils() {
        Logger.error("ScriptingUtils is a static class - should not be instantiated!");
    }

    public static ConditionScope getConditionScope(String name) {
        ConditionScope scope = new ConditionScope();
        scope.setName(name);

        return scope;
    }

    public static ConditionScope getANDCondition() {
        return getConditionScope("AND");
    }

    public static ConditionScope getORCondition() {
        return getConditionScope("OR");
    }

    public static ConditionScope getNOTCondition() {
        return getConditionScope("NOT");
    }

    public static EffectScope getEffectScope(String name) {
        EffectScope scope = new EffectScope();
        scope.setName(name);

        return scope;
    }

    public static Effect getEffect(String name, String value) {
        Effect effect = new Effect();
        effect.setName(name);
        effect.setValue(value);

        return effect;
    }

    public static Condition getCondition(String name, String value) {
        Condition condition = new Condition();
        condition.setName(name);
        condition.setValue(value);

        return condition;
    }
}
