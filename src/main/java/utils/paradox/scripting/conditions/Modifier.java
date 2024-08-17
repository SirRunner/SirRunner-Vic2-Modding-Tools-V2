package utils.paradox.scripting.conditions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class Modifier {
    protected double factor;
    protected List<BasicCondition> conditions;

    protected static final String FACTOR = "factor";

    public Modifier() {
        conditions = new ArrayList<>();
    }

    public Modifier(Node node) {
        this();

        for (Node childNode : node.getNodes()) {
            if (childNode.hasComment()) {
                continue;
            }

            addNodeByName(childNode);
        }
    }

    protected void addNodeByName(Node node) {
        switch (node.getName().toLowerCase()) {
            case FACTOR -> setFactor(node);
            default -> addCondition(node);
        }
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public void setFactor(Node node) {
        setFactor(node.getValue());
    }

    public void setFactor(String value) {
        setFactor(Double.parseDouble(value));
    }

    public List<BasicCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<BasicCondition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(Node node) {
        if (StringUtils.isNotEmpty(node.getValue())) {
            Condition condition = new Condition(node);
            addCondition(condition);
        } else if (!node.getNodes().isEmpty()) {
            ConditionScope conditionScope = new ConditionScope(node);
            addCondition(conditionScope);
        } else {
            Logger.error("Node has neither value nor children " + node.getName() + " = " + node.getValue());
        }
    }

    public void addCondition(BasicCondition condition) {
        condition.setIndent(6);

        if (condition != null) {
            this.conditions.add(condition);
        } else {
            Logger.error("Condition is null");
        }
    }
}
