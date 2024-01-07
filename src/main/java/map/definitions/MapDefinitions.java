package map.definitions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.Utils;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapDefinitions {
    protected int id;
    protected Color color;
    protected String name;
    protected String comment;
    // TODO: Province object?

    private String redValue = null;
    private String greenValue = null;
    private String blueValue = null;

    public static final String ID = "province";
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String NAME = "name";
    public static final String END_LINE = "x";

    public static final String EMPTY_STRING = "";

    public static final String DEFAULT = "-";

    public static Set<String> HANDLED_COLUMNS = new HashSet<>(Arrays.asList(ID, RED, GREEN, BLUE, NAME, END_LINE));

    public MapDefinitions() {}

    public MapDefinitions(Map<String, String> line) {
        this();

        for (String key: line.keySet()) {
            if (!HANDLED_COLUMNS.contains(key)) {
                if (StringUtils.equals(EMPTY_STRING, key) && Utils.mapContainsAll(line, HANDLED_COLUMNS)) {
                    Logger.debug("File contains unhandled key: " + key + "\n\tThis is likely no issue " +
                            "(at the end of the line), but may be worth a check");
                } else {
                    Logger.info("File contains unhandled key: " + key);
                }
            } else {
                String value = StringUtils.trim(line.get(key));
                if (!StringUtils.equalsIgnoreCase(DEFAULT, value) && !StringUtils.isEmpty(value)) {
                    setByName(key, StringUtils.trim(value));
                }
            }
        }

        if (!StringUtils.isNumeric(redValue)) {
            Logger.error("Red value of " + getId() + " is not a number. Defaulting to 0");
            redValue = "0";
        }

        if (!StringUtils.isNumeric(greenValue)) {
            Logger.error("Green value of " + getId() + " is not a number. Defaulting to 0");
            greenValue = "0";
        }

        if (!StringUtils.isNumeric(blueValue)) {
            Logger.error("Blue value of " + getId() + " is not a number. Defaulting to 0");
            blueValue = "0";
        }

        setColor(redValue, greenValue, blueValue);
    }

    public void setByName(String name, String value) {
        switch(name) {
            case ID -> setId(value);
            case RED -> redValue = value;
            case GREEN -> greenValue = value;
            case BLUE -> blueValue = value;
            case NAME -> setName(value);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            setId(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            setId(0);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int red, int green, int blue) {
        setColor(new Color(red, green, blue));
    }

    public void setRed(int red) {
        this.color = new Color(red, this.color.getGreen(), this.color.getBlue());
    }

    public void setGreen(int green) {
        this.color = new Color(this.color.getRed(), green, this.color.getBlue());
    }

    public void setBlue(int blue) {
        this.color = new Color(this.color.getRed(), this.color.getGreen(), blue);
    }

    public void setColor(String red, String green, String blue) {
        setColor(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
