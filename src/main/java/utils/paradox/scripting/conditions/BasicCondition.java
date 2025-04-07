package utils.paradox.scripting.conditions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.ScriptItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BasicCondition extends ScriptItem {
    protected enum VALIDCOUNTRYCONDITIONS {
        AI,
        ALWAYS,
        HAS_COUNTRY_MODIFIER,
        IS_VASSAL,
        MONTH,
        NATIONALVALUE,
        RULING_PARTY_IDEOLOGY,
        TAG,
        VASSAL_OF,
        WAR,
        YEAR
    }

    protected enum VALIDPROVINCECONDITIONS {
    }

    protected enum VALIDPOPCONDITIONS {
    }

    protected enum VALIDCOUNTRYCONDITIONSCOPES {
    }

    protected enum VALIDPROVINCECONDITIONSCOPES {
        OWNER
    }

    protected enum VALIDPOPCONDITIONSCOPES {
    }

    protected static Map<ITEMSCOPE, Set<String>> validConditions = new HashMap<>();
    protected static Map<ITEMSCOPE, Set<String>> validConditionScopes = new HashMap<>();

    public BasicCondition() {}

    public BasicCondition(Node node) {
        super(node);
    }

    public static Map<ITEMSCOPE, Set<String>> getValidConditions() {
        if (validConditions.isEmpty()) {
            validConditions.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validConditions.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validConditions.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYCONDITIONS c : VALIDCOUNTRYCONDITIONS.values()) {
                validConditions.get(ITEMSCOPE.COUNTRY).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPROVINCECONDITIONS c : VALIDPROVINCECONDITIONS.values()) {
                validConditions.get(ITEMSCOPE.PROVINCE).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPOPCONDITIONS c : VALIDPOPCONDITIONS.values()) {
                validConditions.get(ITEMSCOPE.POP).add(StringUtils.upperCase(c.name()));
            }
        }

        return validConditions;
    }

    public static Map<ITEMSCOPE, Set<String>> getValidConditionScopes() {
        if (validConditionScopes.isEmpty()) {
            validConditionScopes.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validConditionScopes.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validConditionScopes.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYCONDITIONSCOPES c : VALIDCOUNTRYCONDITIONSCOPES.values()) {
                validConditionScopes.get(ITEMSCOPE.COUNTRY).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPROVINCECONDITIONSCOPES c : VALIDPROVINCECONDITIONSCOPES.values()) {
                validConditionScopes.get(ITEMSCOPE.PROVINCE).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPOPCONDITIONSCOPES c : VALIDPOPCONDITIONSCOPES.values()) {
                validConditionScopes.get(ITEMSCOPE.POP).add(StringUtils.upperCase(c.name()));
            }
        }

        return validConditionScopes;
    }

    protected abstract Map<ITEMSCOPE, Set<String>> getCorrectConditionMap();

    protected abstract boolean isOneLiner();

//    @Override
//    protected boolean validateName(String name) {
//        return getScopeOfItem(name) != null;
//    }
    // TODO: Fix validation
    @Override
    protected boolean validateName(String name) { return true; }

    protected ITEMSCOPE getScopeOfItem(String value) {
        Map<ITEMSCOPE, Set<String>> itemscopeToValidNames = getCorrectConditionMap();

        String upperValue = StringUtils.upperCase(value);

        for (ITEMSCOPE itemscope : itemscopeToValidNames.keySet()) {
            if (itemscopeToValidNames.get(itemscope).contains(upperValue)) {
                return itemscope;
            }
        }

        Logger.error(value + " is not a valid condition or condition scope");
        return null;
    }
}
