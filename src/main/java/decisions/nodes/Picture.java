package decisions.nodes;

import org.apache.commons.lang3.StringUtils;

public class Picture extends DecisionPart{
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return StringUtils.repeat("\t", getIndent()) + "picture = \"" + value + "\"\n";
    }
}
