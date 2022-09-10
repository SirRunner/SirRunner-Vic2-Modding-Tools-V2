package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;

public class Effect extends BasicEffect {
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected boolean validateName(String name) {
        return getValidEffects().get(getItemScope()).contains(name);
    }

    @Override
    protected String getFriendlyClassName() {
        return "effect";
    }

    @Override
    protected String getContentToString() {
        return StringUtils.repeat("\t", getIndent()) + getName() + " = " + getValue();
    }
}
