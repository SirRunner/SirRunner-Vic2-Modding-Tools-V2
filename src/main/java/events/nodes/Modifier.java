package events.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.conditions.BasicCondition;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;

import java.util.ArrayList;
import java.util.List;

public class Modifier extends EventPart {
    protected double factor;
    protected List<BasicCondition> conditions;

    public Modifier() {
        conditions = new ArrayList<>();
    }

    public Modifier(Node node) {
        this();

        // TODO: Handle the factor part correctly
        for (Node innerNode : node.getNodes()) {
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

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return this.factor;
    }

    public List<BasicCondition> getConditions() {
        return conditions;
    }

    public boolean isEmpty() {
        return conditions.isEmpty();
    }

    public void setConditions(List<BasicCondition> conditions) {
        for (BasicCondition condition : conditions) {
            addCondition(condition);
        }
    }

    public void addCondition(BasicCondition condition) {
        if (condition != null) {
            condition.setIndent(3);
            this.conditions.add(condition);
        } else {
            Logger.error("Condition is null");
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append("modifier = {\n");
        string.append(StringUtils.repeat("\t", getIndent() + 1)).append("factor = ").append(getFactor()).append("\n");

        for (BasicCondition condition : getConditions()) {
            string.append(condition.toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }


}
