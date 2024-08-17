package common;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParsingUtils;
import utils.paradox.scripting.conditions.ChanceGroup;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ideology {
    protected String name;
    protected Color color;
    protected boolean canReduceMilitancy;
    protected Map<REFORM_CHANGE, ReformSupport> reformSupportMap;

    public static final String COLOR = "color";
    public static final String CAN_REDUCE_MILITANCY = "can_reduce_militancy";
    public static final String ADD_POLITICAL_REFORM = "add_political_reform";
    public static final String REMOVE_POLITICAL_REFORM = "remove_political_reform";
    public static final String ADD_SOCIAL_REFORM = "add_social_reform";
    public static final String REMOVE_SOCIAL_REFORM = "remove_social_reform";
    public static final String ADD_MILITARY_REFORM = "add_military_reform";
    public static final String ADD_ECONOMIC_REFORM = "add_economic_reform";

    public Ideology() {
        this.reformSupportMap = new HashMap<>();
    }

    public Ideology(Node node) {
        this();

        name = node.getName();

        for (Node childNode : node.getNodes()) {
            if (childNode.hasComment()) {
                continue;
            }

            addNodeByName(childNode);
        }

    }

    protected void addNodeByName(Node node) {
        switch (node.getName().toLowerCase()) {
            case COLOR -> setColor(node);
            case CAN_REDUCE_MILITANCY -> setCanReduceMilitancy(node);
            case ADD_POLITICAL_REFORM -> addReformSupport(node, REFORM_CHANGE.ADD_POLITICAL_REFORM);
            case REMOVE_POLITICAL_REFORM -> addReformSupport(node, REFORM_CHANGE.REMOVE_POLITICAL_REFORM);
            case ADD_SOCIAL_REFORM -> addReformSupport(node, REFORM_CHANGE.ADD_SOCIAL_REFORM);
            case REMOVE_SOCIAL_REFORM -> addReformSupport(node, REFORM_CHANGE.REMOVE_SOCIAL_REFORM);
            case ADD_MILITARY_REFORM -> addReformSupport(node, REFORM_CHANGE.ADD_MILITARY_REFORM);
            case ADD_ECONOMIC_REFORM -> addReformSupport(node, REFORM_CHANGE.ADD_ECONOMIC_REFORM);
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

    public boolean isCanReduceMilitancy() {
        return canReduceMilitancy;
    }

    public void setCanReduceMilitancy(boolean canReduceMilitancy) {
        this.canReduceMilitancy = canReduceMilitancy;
    }

    public void setCanReduceMilitancy(Node node) {
        setCanReduceMilitancy(StringUtils.equalsIgnoreCase(node.getValue(), ParadoxParsingUtils.TRUE));
    }

    public Map<REFORM_CHANGE, ReformSupport> getReformSupportMap() {
        return reformSupportMap;
    }

    public void setReformSupportMap(Map<REFORM_CHANGE, ReformSupport> reformSupportMap) {
        this.reformSupportMap = reformSupportMap;
    }

    public void addReformSupport(Node node, REFORM_CHANGE type) {
        addReformSupport(new ReformSupport(node), type);
    }

    public void addReformSupport(ReformSupport reformSupport, REFORM_CHANGE type) {
        reformSupportMap.put(type, reformSupport);
    }

    public static class ReformSupport {
        double baseChance;
        List<ChanceGroup> modifierGroups;

        public static final String BASE = "base";
        public static final String GROUP = "group";
        public static final String MODIFIER = "modifier";

        public ReformSupport() {
            modifierGroups = new ArrayList<>();
        }

        public ReformSupport(Node node) {
            this();

            for (Node childNode : node.getNodes()) {
                if (childNode.hasComment()) {
                    continue;
                }

                addNodeByName(childNode);
            }

        }

        protected void addNodeByName(Node node) {
            switch (node.getName().toLowerCase()) {
                case BASE -> setBaseChance(node);
                case GROUP -> addModifierGroup(node);
                case MODIFIER -> addSingletonModifierGroup(node);
            }
        }

        public double getBaseChance() {
            return baseChance;
        }

        public void setBaseChance(double baseChance) {
            this.baseChance = baseChance;
        }

        public void setBaseChance(Node node) {
            setBaseChance(node.getValue());
        }

        public void setBaseChance(String value) {
            setBaseChance(Double.parseDouble(value));
        }

        public List<ChanceGroup> getModifierGroups() {
            return modifierGroups;
        }

        public void setModifierGroups(List<ChanceGroup> modifierGroups) {
            this.modifierGroups = modifierGroups;
        }

        public void addModifierGroup(Node node) {
            addModifierGroup(new ChanceGroup(node));
        }

        public void addModifierGroup(ChanceGroup group) {
            modifierGroups.add(group);
        }

        public void addSingletonModifierGroup(Node node) {
            ChanceGroup group = new ChanceGroup();

            group.addModifier(node);

            addModifierGroup(group);
        }
    }

    public static class IdeologyGroup {
        String name;
        List<Ideology> ideologies;

        public IdeologyGroup() {
            ideologies = new ArrayList<>();
        }

        public IdeologyGroup(Node node) {
            this();

            name = node.getName();

            for (Node childNode : node.getNodes()) {
                if (childNode.hasComment()) {
                    continue;
                }

                addIdeology(new Ideology(childNode));
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Ideology> getIdeologies() {
            return ideologies;
        }

        public void setIdeologies(List<Ideology> ideologies) {
            this.ideologies = ideologies;
        }

        public void addIdeology(Ideology ideology) {
            this.ideologies.add(ideology);
        }
    }

    public enum REFORM_CHANGE {
        ADD_POLITICAL_REFORM,
        REMOVE_POLITICAL_REFORM,
        ADD_SOCIAL_REFORM,
        REMOVE_SOCIAL_REFORM,
        ADD_MILITARY_REFORM,
        ADD_ECONOMIC_REFORM
    }
}
