package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EffectScope extends BasicEffect {
    List<BasicEffect> effects;
    ConditionScope limit;
    // TODO: Handle limits

    public EffectScope() {
        this.effects = new ArrayList<>();
    }

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
            effect.setIndent(getIndent() + 1);
        }
    }

    public void addEffects(List<BasicEffect> effects) {

        if (effects == null || effects.isEmpty()) {
            return;
        }

        effects.forEach(this::addEffect);
    }

    public void addEffect(String name, String value) {
        addEffect(ScriptingUtils.getEffect(name, value));
    }

    public ConditionScope getLimit() {
        return limit;
    }

    public void setLimit(ConditionScope limit) {
        this.limit = limit;
    }

    @Override
    public void setIndent(int indent) {
        for (BasicEffect effect : effects) {
            effect.setIndent(indent + 1);
        }

        if (this.limit != null) {
            this.limit.setIndent(indent + 1);
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
    protected boolean isOneLiner() {
        if (getOneLineOverride() != null) {
            return getOneLineOverride();
        }

        if (getEffects().isEmpty()) {
            return true;
        }

        if (getEffects().size() == 1) {
            if (getLimit() != null) {
                return false;
            }

            /* Effects will assume that they are a one liner. Scopes will not do anything special */
            return getEffects().get(0).isOneLiner(true);
        }

        if (getEffects().size() == 2) {
            boolean hasWhich = false;
            boolean hasValue = false;

            BasicEffect c1 = getEffects().get(0);
            BasicEffect c2 = getEffects().get(1);

            if (StringUtils.equalsIgnoreCase(c1.getName(), "WHICH") || StringUtils.equalsIgnoreCase(c2.getName(), "WHICH")) {
                hasWhich = true;
            }

            if (StringUtils.equalsIgnoreCase(c1.getName(), "VALUE") || StringUtils.equalsIgnoreCase(c2.getName(), "VALUE")) {
                hasValue = true;
            }

            return hasWhich && hasValue;
        }

        return false;
    }

    @Override
    public boolean isOneLiner(boolean isParentOneLiner) {
        return isOneLiner();
    }

    public boolean shouldUseNewline(boolean parentOneLiner) {
        if (!getEffects().isEmpty()) {
            return getEffects().get(0).isOneLiner(parentOneLiner);
        }

        return false;
    }

    @Override
    protected String getInnerContent(boolean parentOneLiner, boolean previousOneLiner) {
        StringBuilder string = new StringBuilder();

        String lineEnd = " ";
        String tabs = "";

        if (!parentOneLiner && !isOneLiner()) {
            lineEnd = "\n";
        }

        if (!parentOneLiner) {
            tabs = StringUtils.repeat("\t", getIndent());
        }

        string.append(tabs).append(getName()).append(" = {").append(lineEnd);

        if (getLimit() != null) {
            string.append(getLimit().getContentToString(isOneLiner(), false));
        }

        boolean isPreviousOneLiner = false;

        for (int i = 0; i < getEffects().size(); i++) {
            BasicEffect condition = getEffects().get(i);

            string.append(condition.getContentToString(isOneLiner(), isPreviousOneLiner || isOneLiner()));

            isPreviousOneLiner = condition.isOneLiner(parentOneLiner);
        }

        /* If the current condition scope is a one-liner, no need to put tabs before the closing bracket */
        if (isOneLiner()) {
            tabs = "";
        }

        /* A new line is needed if the parent is not a one liner, but the previous condition was, or when the current condition is a one liner but the parent wasn't
           In both of these cases, we don't want a newline appended after opening the bracket, but we do want one after closing the bracket */
        if (isPreviousOneLiner && !parentOneLiner || !parentOneLiner && isOneLiner()) {
            lineEnd = "\n";
        }

        string.append(tabs).append("}").append(lineEnd);

        return string.toString();
    }
}
