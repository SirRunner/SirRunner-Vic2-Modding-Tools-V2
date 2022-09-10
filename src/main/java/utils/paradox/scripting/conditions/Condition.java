package utils.paradox.scripting.conditions;

public class Condition extends BasicCondition {
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected boolean validateName(String name) {
        return getValidConditions().get(getItemScope()).contains(name);
    }

    @Override
    protected String getFriendlyClassName() {
        return "condition";
    }

    @Override
    protected String getContentToString() {
        return getIndent() + getName() + " = " + getValue();
    }
}
