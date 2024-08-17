package common;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

public class Country {
    protected String tag;
    protected String pathToDefinition;
    protected boolean dynamic;
    protected String adjective;

    public Country(String tag, String pathToDefinition) {
        this.tag = tag;
        this.pathToDefinition = pathToDefinition;
    }

    public Country(Node node) {
        this(node, false);
    }

    public Country(Node node, boolean dynamic) {
        setTag(node.getName());
        setPathToDefinition(node.getValue());
        setDynamic(dynamic);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPathToDefinition() {
        return pathToDefinition;
    }

    public void setPathToDefinition(String pathToDefinition) {
        this.pathToDefinition = StringUtils.remove(pathToDefinition, "\"");
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getAdjective() {
        return adjective;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }
}
