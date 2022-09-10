package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.conditions.BasicCondition;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;

import java.util.ArrayList;
import java.util.List;

public class Allow extends DecisionPart {
    protected List<BasicCondition> conditions;

    public Allow() {
        this.conditions = new ArrayList<>();
    }

    public Allow(Node node) {
        this();

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
        for (BasicCondition condition : conditions) {
            addCondition(condition);
        }
    }

    public void addCondition(BasicCondition condition) {
        condition.setIndent(3);

        if (condition != null) {
            this.conditions.add(condition);
        } else {
            Logger.error("Condition is null");
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append("allow = {\n");

        for (BasicCondition condition : getConditions()) {
            string.append(condition.toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }
}
