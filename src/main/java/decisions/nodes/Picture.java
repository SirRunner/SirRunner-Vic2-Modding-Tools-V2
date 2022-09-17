package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

public class Picture extends DecisionPart {
    protected String value;

    public Picture(Node node) {
        // TODO: Finish implementation
    }

    public Picture(String value) {
        this.value = value;
    }

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
