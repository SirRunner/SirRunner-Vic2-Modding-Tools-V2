package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.ScriptItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BasicEffect extends ScriptItem {
    Boolean oneLineOverride = null;

    public Boolean getOneLineOverride() {
        return oneLineOverride;
    }

    public void setOneLineOverride(Boolean oneLineOverride) {
        this.oneLineOverride = oneLineOverride;
    }
    // TODO: Handle keywords in effects (such as name)
    protected enum VALIDCOUNTRYEFFECTS {
        COUNTRY_EVENT,
        DIPLOMATIC_INFLUENCE,
        POLITICAL_REFORM,
        RELEASE_VASSAL
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

    public BasicEffect() {
    }

    public BasicEffect(Node node) {
        super(node);
    }

    protected static Map<ITEMSCOPE, Set<String>> validEffects = new HashMap<>();
    protected static Map<ITEMSCOPE, Set<String>> validEffectscopes = new HashMap<>();

    public static Map<ITEMSCOPE, Set<String>> getValidEffects() {
        if (!validEffects.isEmpty()) {
            validEffects.put(ITEMSCOPE.COUNTRY, new HashSet<>());
            validEffects.put(ITEMSCOPE.PROVINCE, new HashSet<>());
            validEffects.put(ITEMSCOPE.POP, new HashSet<>());

            for (VALIDCOUNTRYEFFECTS c : VALIDCOUNTRYEFFECTS.values()) {
                validEffects.get(ITEMSCOPE.COUNTRY).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPROVINCEEFFECTS c : VALIDPROVINCEEFFECTS.values()) {
                validEffects.get(ITEMSCOPE.PROVINCE).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPOPEFFECTS c : VALIDPOPEFFECTS.values()) {
                validEffects.get(ITEMSCOPE.POP).add(StringUtils.upperCase(c.name()));
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
                validEffectscopes.get(ITEMSCOPE.COUNTRY).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPROVINCEEFFECTSCOPES c : VALIDPROVINCEEFFECTSCOPES.values()) {
                validEffectscopes.get(ITEMSCOPE.PROVINCE).add(StringUtils.upperCase(c.name()));
            }

            for (VALIDPOPEFFECTSCOPES c : VALIDPOPEFFECTSCOPES.values()) {
                validEffectscopes.get(ITEMSCOPE.POP).add(StringUtils.upperCase(c.name()));
            }
        }

        return validEffectscopes;
    }

    protected abstract Map<ITEMSCOPE, Set<String>> getCorrectEffectMap();

    protected abstract boolean isOneLiner();

    //    @Override
//    protected boolean validateName(String name) {
//        return getScopeOfItem(name) != null;
//    }
// TODO: Fix validation
    @Override
    protected boolean validateName(String name) {
        return true;
    }

    protected ITEMSCOPE getScopeOfItem(String value) {
        Map<ITEMSCOPE, Set<String>> itemscopeToValidNames = getCorrectEffectMap();

        String upperValue = StringUtils.upperCase(value);

        for (ITEMSCOPE itemscope : itemscopeToValidNames.keySet()) {
            if (itemscopeToValidNames.get(itemscope).contains(upperValue)) {
                return itemscope;
            }
        }

        Logger.error(value + " is not a valid effect or effect scope");
        return null;
    }
}
