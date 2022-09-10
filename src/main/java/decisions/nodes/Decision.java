package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.effects.Effect;

public class Decision {
    protected String name;
    protected Picture picture;
    protected Potential potential;
    protected Allow allow;
    protected DecisionEffect decisionEffect;
    protected AIWillDo aiWillDo;
    protected int indent = 1;

    public static final String PICTURE = "picture";
    public static final String POTENTIAL = "potential";
    public static final String ALLOW = "allow";
    public static final String EFFECT = "effect";
    public static final String AIWILLDO = "ai_will_do";

    public Decision() {
        this.potential = new Potential();
        this.allow = new Allow();
        this.decisionEffect = new DecisionEffect();
    }

    public Decision(Node node) {
        this();

        setName(node.getName());

        for (Node innerNode : node.getNodes()) {
            addNodeByName(innerNode);
        }
    }

    protected void addNodeByName(Node node) {
        switch(node.getName()) {
            case PICTURE -> setPicture(new Picture(node));
            case POTENTIAL -> setPotential(new Potential(node));
            case ALLOW -> setAllow(new Allow(node));
            case EFFECT -> setEffect(new DecisionEffect(node));
            case AIWILLDO -> setAiWillDo(new AIWillDo(node));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Potential getPotential() {
        return potential;
    }

    public void setPotential(Potential potential) {
        this.potential = potential;
    }

    public Allow getAllow() {
        return allow;
    }

    public void setAllow(Allow allow) {
        this.allow = allow;
    }

    public DecisionEffect getEffect() {
        return decisionEffect;
    }

    public void setEffect(DecisionEffect decisionEffect) {
        this.decisionEffect = decisionEffect;
    }

    public AIWillDo getAiWillDo() {
        return aiWillDo;
    }

    public void setAiWillDo(AIWillDo aiWillDo) {
        this.aiWillDo = aiWillDo;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(StringUtils.repeat("\t", getIndent())).append(getName()).append(" = {\n");

        if (getPicture() != null) {
            string.append(getPicture().toString());
        }

        string.append(getPotential().toString());
        string.append(getAllow().toString());
        string.append(getEffect().toString());

        if (getAiWillDo() != null) {
            string.append(getAiWillDo().toString());
        }

        string.append(StringUtils.repeat("\t", getIndent())).append("}\n");

        return string.toString();
    }
}
