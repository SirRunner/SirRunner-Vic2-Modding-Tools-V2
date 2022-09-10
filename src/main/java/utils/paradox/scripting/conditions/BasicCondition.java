package utils.paradox.scripting.conditions;

import utils.paradox.scripting.ScriptItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BasicCondition extends ScriptItem {
    protected enum VALIDCOUNTRYCONDITIONS {
    }

    protected enum VALIDPROVINCECONDITIONS {
    }

    protected enum VALIDPOPCONDITIONS {
    }

    protected enum VALIDCOUNTRYCONDITIONSCOPES {
    }

    protected enum VALIDPROVINCECONDITIONSCOPES {
    }

    protected enum VALIDPOPCONDITIONSCOPES {
    }

    protected static Map<ITEMSCOPE, Set<String>> validConditions = new HashMap<>();
    protected static Map<ITEMSCOPE, Set<String>> validConditionScopes = new HashMap<>();

    public static Map<ITEMSCOPE, Set<String>> getValidConditions() {
        if (!validConditions.isEmpty()) {
            validConditions.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validConditions.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validConditions.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYCONDITIONS c : VALIDCOUNTRYCONDITIONS.values()) {
                validConditions.get(ITEMSCOPE.COUNTRY).add(c.name());
            }

            for (VALIDPROVINCECONDITIONS c : VALIDPROVINCECONDITIONS.values()) {
                validConditions.get(ITEMSCOPE.PROVINCE).add(c.name());
            }

            for (VALIDPOPCONDITIONS c : VALIDPOPCONDITIONS.values()) {
                validConditions.get(ITEMSCOPE.POP).add(c.name());
            }
        }

        return validConditions;
    }

    public static Map<ITEMSCOPE, Set<String>> getValidConditionScopes() {
        if (!validConditionScopes.isEmpty()) {
            validConditionScopes.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validConditionScopes.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validConditionScopes.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYCONDITIONSCOPES c : VALIDCOUNTRYCONDITIONSCOPES.values()) {
                validConditionScopes.get(ITEMSCOPE.COUNTRY).add(c.name());
            }

            for (VALIDPROVINCECONDITIONSCOPES c : VALIDPROVINCECONDITIONSCOPES.values()) {
                validConditionScopes.get(ITEMSCOPE.PROVINCE).add(c.name());
            }

            for (VALIDPOPCONDITIONSCOPES c : VALIDPOPCONDITIONSCOPES.values()) {
                validConditionScopes.get(ITEMSCOPE.POP).add(c.name());
            }
        }

        return validConditionScopes;
    }
}
