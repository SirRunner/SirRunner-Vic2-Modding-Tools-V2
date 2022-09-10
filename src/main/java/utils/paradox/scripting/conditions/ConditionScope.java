package utils.paradox.scripting.conditions;

import java.util.ArrayList;
import java.util.List;

public class ConditionScope extends BasicCondition {
    List<BasicCondition> conditions = new ArrayList<>();

    public List<BasicCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<BasicCondition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(BasicCondition condition) {
        this.conditions.add(condition);
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

        for (BasicCondition condition:getConditions()) {
            string.append(condition.toString());
        }

        string.append("\n");

        string.append(getIndent()).append("}");

        return string.toString();
    }
}
