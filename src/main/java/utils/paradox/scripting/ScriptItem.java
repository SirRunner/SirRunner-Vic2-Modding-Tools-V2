package utils.paradox.scripting;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.scripting.conditions.ConditionScope;

public abstract class ScriptItem {
    protected String comment;
    protected String name;
    protected int indent = 0;
    protected COMMENTLOCATION commentLocation = COMMENTLOCATION.BEFORE;
    protected ITEMSCOPE itemScope;

    protected enum LOGICKEYWORDS {
        AND,
        NOT,
        OR
    }

    protected enum COMMENTLOCATION {
        BEFORE,
        AFTER
    }

    public enum ITEMSCOPE {
        COUNTRY,
        PROVINCE,
        POP
    }

    public ScriptItem() {}

    public ScriptItem(Node node) {
        setComment(node.getComment());
        setName(node.getName());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean hasComment() {
        return StringUtils.isNotEmpty(getComment());
    }

    public COMMENTLOCATION getCommentLocation() {
        return commentLocation;
    }

    public void setCommentLocation(COMMENTLOCATION commentLocation) {
        this.commentLocation = commentLocation;
    }

    public String getName() {
        return name;
    }

    protected abstract boolean validateName(String name);

    protected abstract String getFriendlyClassName();

    public void setName(String name) {
        if (validateName(name)) {
            this.name = name;
        } else {
            Logger.error(name + " is not a valid " + getFriendlyClassName());
        }
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public ITEMSCOPE getItemScope() {
        if (itemScope == null) {
            Logger.error("itemscope is null");
            return null;
        }

        return itemScope;
    }

    public void setItemScope(ITEMSCOPE itemScope) {
        this.itemScope = itemScope;
    }

    protected abstract String getInnerContent(boolean parentOneLiner, boolean previousOneLiner);

    protected abstract boolean isOneLiner();

    public abstract boolean isOneLiner(boolean isParentOneLiner);

    protected String getLineEnd() {
        if (isOneLiner()) {
            return " ";
        }

        return "\n";
    }

    public boolean shouldUseNewline(boolean parentOneLiner) {
        boolean isOneLiner = isOneLiner();
        return !isOneLiner() && !parentOneLiner || isOneLiner() && !parentOneLiner;
    }

    public String getContentToString(boolean parentOneLiner, boolean previousOneLiner) {
        if (getIndent() == 0) {
            Logger.error("Indent is 0! That doesn't seem right...");
        }

        StringBuilder string = new StringBuilder();

        String lineEnd = " ";
        String tabs = "";

        if (!parentOneLiner && !isOneLiner()) {
            lineEnd = "\n";
        }

        if (!parentOneLiner) {
            tabs = StringUtils.repeat("\t", getIndent());
        }

        if (hasComment() && getCommentLocation() == COMMENTLOCATION.BEFORE) {
            string.append(tabs).append("# ").append(getComment()).append(lineEnd);
        }

        /* We only want to apply tabs to inner items if this is not a one liner */
        string.append(getInnerContent(parentOneLiner, previousOneLiner));

        if (hasComment() && getCommentLocation() == COMMENTLOCATION.AFTER) {
            string.append(" # ").append(getComment());
        }

        return string.toString();
    }

    public String toString() {
        /* By default, we want tabs and a newline for the outside-most item */
        return getContentToString(false, false);
    }
}
