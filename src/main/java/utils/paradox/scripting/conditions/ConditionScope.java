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
    protected String getContentToString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append(getName()).append(" = {\n");

        for (BasicCondition condition : getConditions()) {
            string.append(condition.toString());
        }

        string.append("\n");

        string.append(StringUtils.repeat("\t", getIndent())).append("}");

        return string.toString();
    }
}
