package decisions.nodes;

import org.apache.commons.lang3.StringUtils;

public class Decision {
    protected String name;
    protected Picture picture;
    protected Potential potential;
    protected Allow allow;
    protected DecisionEffect decisionEffect;
    protected AIWillDo aiWillDo;
    protected int indent = 1;

    public Decision() {
        super();

        this.potential = new Potential();
        this.allow = new Allow();
        this.decisionEffect = new DecisionEffect();
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
