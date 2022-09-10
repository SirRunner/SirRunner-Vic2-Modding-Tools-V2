package utils.paradox.scripting;

import utils.Logger;

public abstract class ScriptItem {
    protected String comment;
    protected String name;
    protected int indent = 0;
    protected COMMENTLOCATION commentLocation = COMMENTLOCATION.BEFORE;
    protected ITEMSCOPE itemScope;

    protected enum COMMENTLOCATION {
        BEFORE,
        AFTER
    }

    protected enum ITEMSCOPE {
        COUNTRY,
        PROVINCE,
        POP
    }

    protected String getComment() {
        return comment;
    }

    protected void setComment(String comment) {
        this.comment = comment;
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

    protected abstract String getContentToString();

    public String toString() {
        if (getIndent() == 0) {
            Logger.error("Indent is 0! That doesn't seem right...");
        }

        StringBuilder string = new StringBuilder();

        if (getCommentLocation() == COMMENTLOCATION.BEFORE) {
            string.append(getIndent()).append("# ").append(getComment()).append("\n");
        }

        string.append(getContentToString());

        if (getCommentLocation() == COMMENTLOCATION.AFTER) {
            string.append(getIndent()).append(" # ").append(getComment());
        }

        string.append("\n");

        return string.toString();
    }
}
