package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.scripting.conditions.BasicCondition;

import java.util.ArrayList;
import java.util.List;

public class Potential extends DecisionPart{
    protected List<BasicCondition> conditions;

    public Potential() {
        this.conditions = new ArrayList<>();
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
        condition.setIndent(3);

        this.conditions.add(condition);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append("potential = {\n");

        for (BasicCondition condition : getConditions()) {
            string.append(condition.toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }
}
