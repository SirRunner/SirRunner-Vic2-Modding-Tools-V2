package utils.paradox.scripting.effects;

import java.util.ArrayList;
import java.util.List;

public class EffectScope extends BasicEffect {
    List<BasicEffect> effects = new ArrayList<>();

    public List<BasicEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<BasicEffect> effects) {
        this.effects = effects;
    }

    public void addEffect(BasicEffect effect) {
        this.effects.add(effect);
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

        string.append(getIndent()).append(getName()).append(" = {\n");

        for (BasicEffect condition : getEffects()) {
            string.append(condition.toString());
        }

        string.append("\n");

        string.append(getIndent()).append("}");

        return string.toString();
    }
}
