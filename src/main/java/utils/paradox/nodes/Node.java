package utils.paradox.nodes;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Node> nodes;
    String value;
    String comment;
    String name;
    Node parent;
    int layer;

    public Node() {
        this.nodes = new ArrayList<>();
        this.layer = 0;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNodes(Node node) {
        this.nodes.add(node);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean hasComment() {
        return StringUtils.isNotEmpty(this.comment);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String toString() {
        if (getComment() != null) {
            return "# " + getComment() + "\n";
        } else if (getValue() != null) {
            return StringUtils.repeat("\t", getLayer()) + getName() + " = " + getValue() + "\n";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(StringUtils.repeat("\t", getLayer())).append(getName()).append(" = {\n");

        for (Node node:getNodes()) {
            stringBuilder.append(StringUtils.repeat("\t", getLayer())).append(node.toString()).append("\n");
        }

        stringBuilder.append(StringUtils.repeat("\t", getLayer())).append("}");

        return stringBuilder.toString();
    }
}
