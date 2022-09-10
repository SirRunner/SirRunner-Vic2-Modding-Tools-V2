package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.scripting.effects.BasicEffect;

import java.util.ArrayList;
import java.util.List;

public class DecisionEffect extends DecisionPart{
    List<BasicEffect> effects;

    public DecisionEffect() {
        this.effects = new ArrayList<>();
    }

    public DecisionEffect(List<BasicEffect> effects) {
        this();

        this.effects.addAll(effects);
    }

    public List<BasicEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<BasicEffect> effects) {
        for (BasicEffect effect:effects) {
            addEffect(effect);
        }
    }

    public void addEffect(BasicEffect effect) {
        effect.setIndent(3);

        this.effects.add(effect);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append("effect = {\n");

        for (BasicEffect scope : getEffects()) {
            string.append(scope.toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }
}
