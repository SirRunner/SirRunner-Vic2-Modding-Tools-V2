package events.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event {
    protected int id;
    protected String picture;
    protected String title;
    protected String description;
    protected boolean isTriggeredOnly;
    protected boolean fireOnlyOnce;
    protected Trigger trigger;
    protected MTTH meanTimeToHappen;
    protected List<Option> options;

    public Event() {
        this.trigger = new Trigger();
        this.meanTimeToHappen = new MTTH();
        this.options = new ArrayList<>();
    }

    public Event(Node node) {
        this();

        for (Node innerNode : node.getNodes()) {
            addNodeByName(innerNode);
        }
    }

    public void addNodeByName(Node node) {

        KEYWORDS keyword = KEYWORDS.getKeywordByName(node.getName());

        if (keyword == null) {
            Logger.error("Unable to determine type of node: " + node.getName() + " = " + node.getValue());
            return;
        }

        switch (keyword) {
            case ID -> setId(node);
            case PICTURE -> setPicture(node);
            case TITLE -> setTitle(node);
            case DESC -> setDescription(node);
            case FIRE_ONLY_ONCE -> setFireOnlyOnce(node);
            case IS_TRIGGERED_ONLY -> setTriggeredOnly(node);
            case TRIGGER -> setTrigger(node);
            case MEAN_TIME_TO_HAPPEN -> setMeanTimeToHappen(node);
            case OPTION -> addOption(node);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(Node node) {
        setId(Integer.parseInt(node.getValue()));
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(Node node) {
        setPicture(node.getValue());
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(Node node) {
        setTitle(node.getValue());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(Node node) {
        setDescription(node.getValue());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTriggeredOnly() {
        return isTriggeredOnly;
    }

    public void setTriggeredOnly(Node node) {
        setTriggeredOnly(Boolean.parseBoolean(node.getValue()));
    }

    public void setTriggeredOnly(boolean triggeredOnly) {
        isTriggeredOnly = triggeredOnly;
    }

    public boolean isFireOnlyOnce() {
        return fireOnlyOnce;
    }

    public void setFireOnlyOnce(Node node) {
        setFireOnlyOnce(Boolean.parseBoolean(node.getValue()));
    }

    public void setFireOnlyOnce(boolean fireOnlyOnce) {
        this.fireOnlyOnce = fireOnlyOnce;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public void setTrigger(Node node) {
        setTrigger(new Trigger(node));
    }

    public MTTH getMeanTimeToHappen() {
        return meanTimeToHappen;
    }

    public void setMeanTimeToHappen(Node node) {
        setMeanTimeToHappen(new MTTH(node));
    }

    public void setMeanTimeToHappen(MTTH meanTimeToHappen) {
        this.meanTimeToHappen = meanTimeToHappen;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = new ArrayList<>();

        for (Option option : options) {
            addOption(option);
        }
    }

    public void addOption(Node node) {
        addOption(new Option(node));
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    public enum KEYWORDS {
        ID,
        PICTURE,
        TITLE,
        DESC,
        FIRE_ONLY_ONCE,
        IS_TRIGGERED_ONLY,
        TRIGGER,
        MEAN_TIME_TO_HAPPEN,
        OPTION;

        public static KEYWORDS getKeywordByName(String word) {
            return Arrays.stream(values()).filter(keyword -> StringUtils.equalsAnyIgnoreCase(word, keyword.name())).findFirst().orElse(null);
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("country_event = {\n");
        string.append("\tid = ").append(getId()).append("\n");
        if (StringUtils.isNotEmpty(getPicture())) {
            string.append("\tpicture = \"").append(getPicture()).append("\"\n");
        }
        string.append("\ttitle = \"").append(getTitle()).append("\"\n");
        string.append("\tdesc = \"").append(getDescription()).append("\"\n");

        if (isFireOnlyOnce()) {
            string.append("\tfire_only_once = yes\n");
        }

        if (isTriggeredOnly()) {
            string.append("\tis_triggered_only = yes\n");
        }

        if (!getTrigger().isEmpty()) {
            string.append(getTrigger().toString());
        }

        if (!getMeanTimeToHappen().isEmpty()) {
            string.append(getMeanTimeToHappen().toString());
        }

        for (Option option : options) {
            string.append(option.toString());
        }

        string.append("}\n");

        return string.toString();
    }
}
