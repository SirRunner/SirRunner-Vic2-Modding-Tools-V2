package decisions.nodes;

public abstract class DecisionPart {
    protected int indent = 2;
    protected String comment;

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
