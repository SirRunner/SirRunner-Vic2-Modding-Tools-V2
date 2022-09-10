package decisions.nodes;

public abstract class DecisionPart {
    protected int indent = 2;

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }
}
