package utils.paradox.scripting.conditions;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

import java.util.Map;
import java.util.Set;

public class Condition extends BasicCondition {
    protected String value;

    public Condition(Node node) {
        super(node);

        setValue(node.getValue());
        setItemScope(getScopeOfItem(node.getName()));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected String getFriendlyClassName() {
        return "condition";
    }

    @Override
    protected Map<ITEMSCOPE, Set<String>> getCorrectConditionMap() {
        return getValidConditions();
    }

    @Override
    protected String getContentToString() {
        return StringUtils.repeat("\t", getIndent()) + getName() + " = " + getValue();
    }
}
