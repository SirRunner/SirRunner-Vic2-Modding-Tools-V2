package utils.paradox.scripting.conditions;

import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class ChanceGroup {
    protected List<Modifier> modifiers;

    public ChanceGroup() {
        this.modifiers = new ArrayList<>();
    }

    public ChanceGroup(Node node) {
        this();

        for (Node childNode: node.getNodes()) {
            addModifier(childNode);
        }
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public void addModifier(Node node) {
        modifiers.add(new Modifier(node));
    }
}
