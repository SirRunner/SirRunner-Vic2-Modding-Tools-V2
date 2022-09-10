package utils.paradox.scripting.effects;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

import java.util.Map;
import java.util.Set;

public class Effect extends BasicEffect {
    protected String value;

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
    protected String getContentToString() {
        return StringUtils.repeat("\t", getIndent()) + getName() + " = " + getValue();
    }
}
