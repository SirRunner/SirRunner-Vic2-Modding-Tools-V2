package utils.paradox.scripting.effects;

import utils.paradox.scripting.ScriptItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BasicEffect extends ScriptItem {
    protected enum VALIDCOUNTRYEFFECTS {
    }

    protected enum VALIDPROVINCEEFFECTS {
    }

    protected enum VALIDPOPEFFECTS {
    }

    protected enum VALIDCOUNTRYEFFECTSCOPES {
    }

    protected enum VALIDPROVINCEEFFECTSCOPES {
    }

    protected enum VALIDPOPEFFECTSCOPES {
    }

    protected static Map<ITEMSCOPE, Set<String>> validEffects = new HashMap<>();
    protected static Map<ITEMSCOPE, Set<String>> validEffectscopes = new HashMap<>();

    public static Map<ITEMSCOPE, Set<String>> getValidEffects() {
        if (!validEffects.isEmpty()) {
            validEffects.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validEffects.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validEffects.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYEFFECTS c : VALIDCOUNTRYEFFECTS.values()) {
                validEffects.get(ITEMSCOPE.COUNTRY).add(c.name());
            }

            for (VALIDPROVINCEEFFECTS c : VALIDPROVINCEEFFECTS.values()) {
                validEffects.get(ITEMSCOPE.PROVINCE).add(c.name());
            }

            for (VALIDPOPEFFECTS c : VALIDPOPEFFECTS.values()) {
                validEffects.get(ITEMSCOPE.POP).add(c.name());
            }
        }

        return validEffects;
    }

    public static Map<ITEMSCOPE, Set<String>> getValidEffectScopes() {
        if (!validEffectscopes.isEmpty()) {
            validEffectscopes.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validEffectscopes.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validEffectscopes.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYEFFECTSCOPES c : VALIDCOUNTRYEFFECTSCOPES.values()) {
                validEffectscopes.get(ITEMSCOPE.COUNTRY).add(c.name());
            }

            for (VALIDPROVINCEEFFECTSCOPES c : VALIDPROVINCEEFFECTSCOPES.values()) {
                validEffectscopes.get(ITEMSCOPE.PROVINCE).add(c.name());
            }

            for (VALIDPOPEFFECTSCOPES c : VALIDPOPEFFECTSCOPES.values()) {
                validEffectscopes.get(ITEMSCOPE.POP).add(c.name());
            }
        }

        return validEffectscopes;
    }
}
