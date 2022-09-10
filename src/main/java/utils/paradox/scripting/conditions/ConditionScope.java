package utils.paradox.scripting.conditions;

import utils.Logger;
import utils.paradox.scripting.effects.BasicEffect;

import java.util.ArrayList;
import java.util.List;

public class ConditionScope extends BasicCondition {
    List<BasicCondition> conditions = new ArrayList<>();

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
    protected boolean validateName(String name) {
        return getValidConditionScopes().get(getItemScope()).contains(name);
    }

    @Override
    protected String getFriendlyClassName() {
        return "condition scope";
    }

    @Override
    protected String getContentToString() {
        StringBuilder string = new StringBuilder();

        string.append(getIndent()).append(getName()).append(" = {\n");

        for (BasicCondition condition : getConditions()) {
            string.append(condition.toString());
        }

        string.append("\n");

        string.append(getIndent()).append("}");

        return string.toString();
    }
}
