package events.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class MTTH extends EventPart {
    protected int months;
    protected List<Modifier> modifiers;

    public MTTH() {
        modifiers = new ArrayList<>();
    }

    public MTTH(Node node) {
        this();

        for (Node innerNode : node.getNodes()) {
            if (StringUtils.isNotEmpty(innerNode.getValue()) && !innerNode.getNodes().isEmpty()) {
                Logger.error("Node has both a value and child nodes " + innerNode.getName() + " = " + innerNode.getValue());
            } else if (!innerNode.getNodes().isEmpty()) {
                Modifier modifier = new Modifier(innerNode);
                addModifier(modifier);
            } else {
                Logger.error("Node has neither value nor children " + innerNode.getName() + " = " + innerNode.getValue());
            }
        }
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        modifiers = new ArrayList<>();
        addModifiers(modifiers);
    }

    public void addModifiers(List<Modifier> modifiers) {
        for (Modifier modifier : modifiers) {
            addModifier(modifier);
        }
    }

    public void addModifier(Modifier modifier) {
        if (modifier != null) {
            modifier.setIndent(2);
            this.modifiers.add(modifier);
        } else {
            Logger.error("Modifier is null");
        }
    }

    public boolean isEmpty() {
        return getMonths() == 0 && getModifiers().isEmpty();
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append("mean_time_to_happen = {\n");

        for (Modifier modifier : getModifiers()) {
            string.append(modifier.toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }
}
