package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

import java.util.Map;
import java.util.Set;

public class Effect extends BasicEffect {
    protected String value;

    public Effect() {
    }

    public Effect(Node node) {
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
        return "effect";
    }

    @Override
    protected Map<ITEMSCOPE, Set<String>> getCorrectEffectMap() {
        return getValidEffects();
    }

    @Override
    protected boolean isOneLiner() {

        if (getOneLineOverride() != null) {
            return getOneLineOverride();
        }

        return true;
    }

    @Override
    public boolean isOneLiner(boolean isParentOneLiner) {
        return isParentOneLiner;
    }

    @Override
    protected String getInnerContent(boolean parentOneLiner, boolean previousOneLiner) {
        String tabs = "";
        String lineEnd = " ";

        if (!parentOneLiner) {
            lineEnd = "\n";
        }

        if (!previousOneLiner || !parentOneLiner) {
            tabs = StringUtils.repeat("\t", getIndent());
        }

        return tabs + getName() + " = " + getValue() + lineEnd;
    }
}
