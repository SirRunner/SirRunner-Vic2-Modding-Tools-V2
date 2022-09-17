package utils.paradox.scripting.conditions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.effects.BasicEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConditionScope extends BasicCondition {
    List<BasicCondition> conditions = new ArrayList<>();

    public ConditionScope() {
        this.conditions = new ArrayList<>();
    }

    public ConditionScope(Node node) {
        super(node);

        this.conditions = new ArrayList<>();
        setItemScope(getScopeOfItem(node.getName()));

        for (Node innerNode: node.getNodes()) {
            if (StringUtils.isNotEmpty(innerNode.getValue()) && !innerNode.getNodes().isEmpty()) {
                Logger.error("Node has both a value and child nodes " + innerNode.getName() + " = " + innerNode.getValue());
            } else if (StringUtils.isNotEmpty(innerNode.getValue())) {
                Condition condition = new Condition(innerNode);
                addCondition(condition);
            } else if (!innerNode.getNodes().isEmpty()) {
                ConditionScope conditionScope = new ConditionScope(innerNode);
                addCondition(conditionScope);
            } else {
                Logger.error("Node has neither value nor children " + innerNode.getName() + " = " + innerNode.getValue());
            }
        }
    }

    public List<BasicCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<BasicCondition> conditions) {
        for (BasicCondition condition: conditions) {
            addCondition(condition);
        }
    }

    public void addCondition(BasicCondition condition) {
        if (this == condition) {
            Logger.error("Cannot add itself to list of conditions");
        } else {
            this.conditions.add(condition);
            condition.setIndent(getIndent() + 1);
        }
    }

    @Override
    public void setIndent(int indent) {
        for(BasicCondition condition:conditions) {
            condition.setIndent(indent + 1);
        }

        this.indent = indent;
    }

    @Override
    protected String getFriendlyClassName() {
        return "condition scope";
    }

    @Override
    protected Map<ITEMSCOPE, Set<String>> getCorrectConditionMap() {
        return getValidConditionScopes();
    }

    @Override
    protected boolean isOneLiner() {
        if (getConditions().isEmpty()) {
            return true;
        }

        if (getConditions().size() == 1) {
            /* Conditions will assume that they are a one liner. Scopes will not do anything special */
            return getConditions().get(0).isOneLiner(true);
        }

        if (getConditions().size() == 2) {
            boolean hasWhich = false;
            boolean hasValue = false;

            BasicCondition c1 = getConditions().get(0);
            BasicCondition c2 = getConditions().get(1);

            if (StringUtils.equalsIgnoreCase(c1.getName(), "WHICH" ) || StringUtils.equalsIgnoreCase(c2.getName(), "WHICH")) {
                hasWhich = true;
            }

            if (StringUtils.equalsIgnoreCase(c1.getName(), "VALUE" ) || StringUtils.equalsIgnoreCase(c2.getName(), "VALUE")) {
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

        boolean isPreviousOneLiner = false;

        for (int i = 0; i < getConditions().size(); i++) {
            BasicCondition condition = getConditions().get(i);

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
