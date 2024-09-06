package common;

import com.opencsv.CSVParserBuilder;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Culture {
    protected String name;
    protected Color color;
    protected List<String> firstNames;
    protected List<String> lastNames;

    public static final String COLOR = "color";
    public static final String FIRST_NAMES = "first_names";
    public static final String LAST_NAMES = "last_names";

    public Culture() {
        this.firstNames = new ArrayList<>();
        this.lastNames = new ArrayList<>();
    }

    public Culture(Node node) throws Exception {
        this();

        name = node.getName();

        for (Node childNode : node.getNodes()) {
            if (childNode.hasComment()) {
                // TODO: Figure out how to handle
                continue;
            }

            addNodeByName(childNode);
        }
    }

    public void addNodeByName(Node node) throws Exception {
        switch (node.getName().toLowerCase()) {
            case COLOR -> setColor(node);
            case FIRST_NAMES -> setFirstNames(node);
            case LAST_NAMES -> setLastNames(node);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(Node node) {
        setColor(node.getValue());
    }

    public void setColor(String value) {
        if (StringUtils.isEmpty(value)) {
            Logger.error("Cannot set color from empty value");
            return;
        }

        String[] rgbValues = value.split("\s+");

        if (rgbValues.length != 3) {
            Logger.error("Color is not in the right format: " + value);
            return;
        }

        for (String colorCode : rgbValues) {
            if (StringUtils.isNumeric(colorCode)) {
                continue;
            }

            Logger.error("Color is not numeric " + value);
            return;
        }

        setColor(Integer.parseInt(rgbValues[0]), Integer.parseInt(rgbValues[1]), Integer.parseInt(rgbValues[2]));
    }

    public void setColor(int red, int green, int blue) {

        if (red > 255 || green > 255 || blue > 255) {
            Logger.error("Color is larger than 255: " + red + " " + green + " " + blue);
            return;
        }

        setColor(new Color(red, green, blue));
    }

    public List<String> getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(List<String> firstNames) {
        this.firstNames = firstNames;
    }

    public void setFirstNames(Node node) throws Exception {
        CSVParserBuilder builder = new CSVParserBuilder();

        builder.withSeparator(' ');

        String[] names = builder.build().parseLine(node.getValue());

        for (String name: names) {
            addFirstName(name);
        }
    }

    public void addFirstName(String firstName) {
        this.firstNames.add(firstName);
    }

    public List<String> getLastNames() {
        return lastNames;
    }

    public void setLastNames(List<String> lastNames) {
        this.lastNames = lastNames;
    }

    public void setLastNames(Node node) throws Exception {
        CSVParserBuilder builder = new CSVParserBuilder();

        builder.withSeparator(' ');

        String[] names = builder.build().parseLine(node.getValue());

        for (String name: names) {
            addLastName(name);
        }
    }

    public void addLastName(String lastName) {
        this.lastNames.add(lastName);
    }

    public static class CultureGroup {
        protected String name;
        protected String leader;
        protected String unit;
        protected List<Culture> cultures;

        public static final String LEADER = "leader";
        public static final String UNIT = "unit";

        public CultureGroup() {
            this.cultures = new ArrayList<>();
        }

        public CultureGroup(Node node) throws Exception {
            this();

            name = node.getName();

            for (Node childNode : node.getNodes()) {
                if (childNode.hasComment()) {
                    // TODO: Figure out how to handle
                    continue;
                }

                addNodeByName(childNode);
            }
        }

        public void addNodeByName(Node node) throws Exception {
            switch (node.getName().toLowerCase()) {
                case LEADER -> setLeader(node);
                case UNIT -> setUnit(node);
                default -> addCulture(new Culture(node));
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public void setLeader(Node node) {
            setLeader(node.getValue());
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setUnit(Node node) {
            setUnit(node.getValue());
        }

        public List<Culture> getCultures() {
            return cultures;
        }

        public void setCultures(List<Culture> cultures) {
            this.cultures = cultures;
        }

        public void addCulture(Culture culture) {
            this.cultures.add(culture);
        }
    }
}
