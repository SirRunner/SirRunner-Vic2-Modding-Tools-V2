package events.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.Effect;
import utils.paradox.scripting.effects.EffectScope;

import java.util.ArrayList;
import java.util.List;

public class Option extends EventPart {
    String name;
    List<BasicEffect> effects;
    AIChance aiChance;

    public Option() {
        this.effects = new ArrayList<>();
        aiChance = new AIChance();
        setIndent(1);
    }

    public Option(Node node) {
        this();

        // TODO: Handle name and aiChance
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (effect != null) {
            effect.setIndent(2);
            this.effects.add(effect);
        } else {
            Logger.error("Effect is null");
        }
    }

    public AIChance getAiChance() {
        return aiChance;
    }

    public void setAiChance(AIChance aiChance) {
        this.aiChance = aiChance;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append("option = {\n");
        string.append(StringUtils.repeat("\t", getIndent() + 1)).append("name = \"").append(getName()).append("\"\n");

        for (BasicEffect scope : getEffects()) {
            string.append(scope.toString());
        }

        if (!getAiChance().isEmpty()) {
            string.append(getAiChance().toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }
}
