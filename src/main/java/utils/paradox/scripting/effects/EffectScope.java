package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class EffectScope extends BasicEffect {
    List<BasicEffect> effects;

    public EffectScope() {
        this.effects = new ArrayList<>();
    }

    public List<BasicEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<BasicEffect> effects) {
        for (BasicEffect effect : effects) {
            addEffect(effect);
        }
    }

    public void addEffect(BasicEffect effect) {
        if (this == effect) {
            Logger.error("Cannot add itself to list of effects");
        } else {
            this.effects.add(effect);
        }
    }

    @Override
    public void setIndent(int indent) {
        for (BasicEffect effect : effects) {
            effect.setIndent(indent + 1);
        }

        this.indent = indent;
    }

    @Override
    protected boolean validateName(String name) {
        return getValidEffectScopes().get(getItemScope()).contains(name);
    }

    @Override
    protected String getFriendlyClassName() {
        return "effect scope";
    }

    @Override
    protected String getContentToString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append(getName()).append(" = {\n");

        for (BasicEffect condition : getEffects()) {
            string.append(condition.toString());
        }

        string.append("\n");

        string.append(StringUtils.repeat("\t", getIndent())).append("}");

        return string.toString();
    }
}
