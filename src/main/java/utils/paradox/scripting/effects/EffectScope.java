package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EffectScope extends BasicEffect {
    List<BasicEffect> effects;
    // TODO: Handle limits

    public EffectScope(Node node) {
        super(node);

        this.effects = new ArrayList<>();
        setItemScope(getScopeOfItem(node.getName()));

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
    protected String getFriendlyClassName() {
        return "effect scope";
    }

    @Override
    protected Map<ITEMSCOPE, Set<String>> getCorrectEffectMap() {
        return getValidEffectScopes();
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
