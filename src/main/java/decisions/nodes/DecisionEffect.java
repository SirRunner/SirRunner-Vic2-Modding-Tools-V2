package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.Effect;
import utils.paradox.scripting.effects.EffectScope;

import java.util.ArrayList;
import java.util.List;

public class DecisionEffect extends DecisionPart {
    List<BasicEffect> effects;

    public DecisionEffect() {
        this.effects = new ArrayList<>();
    }

    public DecisionEffect(Node node) {
        this();

        for (Node innerNode : node.getNodes()) {
            if (StringUtils.isNotEmpty(innerNode.getValue()) && !innerNode.getNodes().isEmpty()) {
                Logger.error("Node has both a value and child nodes " + innerNode.getName() + " = " + innerNode.getValue());
            } else if (StringUtils.isNotEmpty(innerNode.getValue())) {
                Effect effect = new Effect(innerNode);
                addEffect(effect);
            } else if (!innerNode.getNodes().isEmpty()) {
                EffectScope effectScope = new EffectScope(innerNode);
                addEffect(effectScope);
            } else {
                Logger.error("Node has neither value nor children " + innerNode.getName() + " = " + innerNode.getValue());
            }
        }
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
        effect.setIndent(3);

        if (effect != null) {
            this.effects.add(effect);
        } else {
            Logger.error("Effect is null");
        }
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
