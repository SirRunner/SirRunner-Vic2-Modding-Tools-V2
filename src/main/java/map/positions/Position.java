package map.positions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Position {
    int provinceId;
    PixelLocation unit;
    PixelLocation textPosition;
    Double textScale;
    Double textRotation;
    PixelLocation buildingConstruction;
    PixelLocation militaryConstruction;
    PixelLocation factory;
    BuildingPosition buildingPosition;
    BuildingRotation buildingRotation;
    PixelLocation city;
    BuildingNudge buildingNudge;
    SpawnRailwayTrack spawnRailwayTrack;

    public Position() {
        this.textScale = 3.0;
    }

    public Position(Node node) {
        parseNode(node);
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setProvinceId(String provinceId) {
        setProvinceId(Integer.parseInt(provinceId));
    }

    public PixelLocation getUnit() {
        return unit;
    }

    public void setUnit(PixelLocation unit) {
        this.unit = unit;
    }

    public void setUnit(Node node) {
        double x = getXValueFromNode(node);
        double y = getYValueFromNode(node);

        setUnit(new PixelLocation(x, y));
    }

    public PixelLocation getTextPosition() {
        return textPosition;
    }

    public void setTextPosition(PixelLocation textPosition) {
        this.textPosition = textPosition;
    }

    public void setTextPosition(Node node) {
        double x = getXValueFromNode(node);
        double y = getYValueFromNode(node);

        setTextPosition(new PixelLocation(x, y));
    }

    public Double getTextScale() {
        return textScale;
    }

    public void setTextScale(double textScale) {
        this.textScale = textScale;
    }

    public void setTextScale(String textScale) {
        setTextScale(Double.parseDouble(textScale));
    }

    public void setTextScale(Node node) {
        setTextScale(node.getValue());
    }

    public Double getTextRotation() {
        return textRotation;
    }

    public void setTextRotation(double textRotation) {
        this.textRotation = textRotation;
    }

    public void setTextRotation(String textRotation) {
        setTextRotation(Double.parseDouble(textRotation));
    }

    public void setTextRotation(Node node) {
        setTextRotation(node.getValue());
    }

    public PixelLocation getBuildingConstruction() {
        return buildingConstruction;
    }

    public void setBuildingConstruction(PixelLocation buildingConstruction) {
        this.buildingConstruction = buildingConstruction;
    }

    public void setBuildingConstruction(Node node) {
        double x = getXValueFromNode(node);
        double y = getYValueFromNode(node);

        setBuildingConstruction(new PixelLocation(x, y));
    }

    public PixelLocation getMilitaryConstruction() {
        return militaryConstruction;
    }

    public void setMilitaryConstruction(PixelLocation military_construction) {
        this.militaryConstruction = military_construction;
    }

    public void setMilitaryConstruction(Node node) {
        double x = getXValueFromNode(node);
        double y = getYValueFromNode(node);

        setMilitaryConstruction(new PixelLocation(x, y));
    }

    public PixelLocation getFactory() {
        return factory;
    }

    public void setFactory(PixelLocation factory) {
        this.factory = factory;
    }

    public void setFactory(Node node) {
        double x = getXValueFromNode(node);
        double y = getYValueFromNode(node);

        setFactory(new PixelLocation(x, y));
    }

    public BuildingPosition getBuildingPosition() {
        return buildingPosition;
    }

    public void setBuildingPosition(BuildingPosition buildingPosition) {
        this.buildingPosition = buildingPosition;
    }

    public void setBuildingPosition(Node node) {
        setBuildingPosition(new BuildingPosition(node));
    }

    public BuildingRotation getBuildingRotation() {
        return buildingRotation;
    }

    public void setBuildingRotation(BuildingRotation buildingRotation) {
        this.buildingRotation = buildingRotation;
    }

    public void setBuildingRotation(Node node) {
        setBuildingRotation(new BuildingRotation(node));
    }

    public PixelLocation getCity() {
        return city;
    }

    public void setCity(PixelLocation city) {
        this.city = city;
    }

    public void setCity(Node node) {
        double x = getXValueFromNode(node);
        double y = getYValueFromNode(node);

        setCity(new PixelLocation(x, y));
    }

    public BuildingNudge getBuildingNudge() {
        return buildingNudge;
    }

    public void setBuildingNudge(BuildingNudge buildingNudge) {
        this.buildingNudge = buildingNudge;
    }

    public void setBuildingNudge(Node node) {
        setBuildingNudge(new BuildingNudge(node));
    }

    public SpawnRailwayTrack getSpawnRailwayTrack() {
        return spawnRailwayTrack;
    }

    public void setSpawnRailwayTrack(SpawnRailwayTrack spawnRailwayTrack) {
        this.spawnRailwayTrack = spawnRailwayTrack;
    }

    public void setSpawnRailwayTrack(Node node) {
        setSpawnRailwayTrack(new SpawnRailwayTrack(node));
    }

    protected void parseNode(Node node) {

        setProvinceId(node.getName());

        node.getNodes().forEach(childNode -> {

            VARIABLE variable = VARIABLE.getByName(childNode.getName());

            if (variable == null) {
                Logger.error("Failed to correctly handle node with name " + childNode.getName());
                return;
            }

            updateNode(variable, childNode);
        });
    }

    protected void updateNode(VARIABLE variable, Node node) {
        switch (variable) {
            case UNIT -> setUnit(node);
            case TEXT_POSITION -> setTextPosition(node);
            case TEXT_SCALE -> setTextScale(node);
            case TEXT_ROTATION -> setTextRotation(node);
            case BUILDING_CONSTRUCTION -> setBuildingConstruction(node);
            case MILITARY_CONSTRUCTION -> setMilitaryConstruction(node);
            case FACTORY -> setFactory(node);
            case BUILDING_POSITION -> setBuildingPosition(node);
            case BUILDING_ROTATION -> setBuildingRotation(node);
            case CITY -> setCity(node);
            case BUILDING_NUDGE -> setBuildingNudge(node);
            case SPAWN_RAILWAY_TRACK -> setSpawnRailwayTrack(node);
        }
    }

    protected static double getXValueFromNode(Node node) {

        String value = getValueFromChildNode(node, "X");

        return Double.parseDouble(value);
    }

    protected static double getYValueFromNode(Node node) {

        String value = getValueFromChildNode(node, "Y");

        return Double.parseDouble(value);
    }

    protected static String getValueFromChildNode(Node node, String nodeName) {

        Node nodeWithName = node.getNodes().stream().filter(childNode -> StringUtils.equalsIgnoreCase(nodeName, childNode.getName())).findFirst().orElse(null);

        return nodeWithName == null ? "" : nodeWithName.getValue();
    }

    /* For whatever reason, the game uses doubles to a precision of 6 decimal digits */
    protected static String getPrintableNumber(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        DecimalFormat dc = new DecimalFormat("0.000000");

        bd = bd.setScale(6, RoundingMode.HALF_UP);

        return dc.format(bd.doubleValue());
    }

    protected enum VARIABLE {
        UNIT,
        TEXT_POSITION,
        TEXT_SCALE,
        TEXT_ROTATION,
        BUILDING_CONSTRUCTION,
        MILITARY_CONSTRUCTION,
        FACTORY,
        BUILDING_POSITION,
        BUILDING_ROTATION,
        CITY,
        BUILDING_NUDGE,
        SPAWN_RAILWAY_TRACK;

        protected static VARIABLE getByName(String name) {
            return Arrays.stream(VARIABLE.values()).filter(variable -> StringUtils.equalsIgnoreCase(name, variable.name())).findFirst().orElse(null);
        }
    }

    /*
     * Mapping out the data in the same order as the positions editor. Using spaces instead of tabs to mimic the positions editor
     *
     * Note that for the purposes of TTA, the text scale is being forced to be an int (is always 3)
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append(getProvinceId() + " = {\n");

        if (getTextPosition() != null) {
            builder.append("    text_position = {\n");
            builder.append(getTextPosition().toString(1) + "\n");
        }

        if (getTextRotation() != null) {
            builder.append("    text_rotation = " + getPrintableNumber(getTextRotation()) + "\n");
        }

        if (getTextScale() != null) {
            builder.append("    text_scale = " + (int) getTextScale().doubleValue() + "\n");
        }

        if (getUnit() != null) {
            builder.append("    unit = {\n");
            builder.append(getUnit().toString(1) + "\n");
        }

        if (getCity() != null) {
            builder.append("    city = {\n");
            builder.append(getCity().toString(1) + "\n");
        }

        if (getFactory() != null) {
            builder.append("    factory = {\n");
            builder.append(getFactory().toString(1) + "\n");
        }

        if (getBuildingConstruction() != null) {
            builder.append("    building_construction = {\n");
            builder.append(getBuildingConstruction().toString(1) + "\n");
        }

        if (getMilitaryConstruction() != null) {
            builder.append("    military_construction = {\n");
            builder.append(getMilitaryConstruction().toString(1) + "\n");
        }

        if (getBuildingPosition() != null) {
            builder.append(getBuildingPosition().toString() + "\n");
        }

        if (getBuildingRotation() != null) {
            builder.append(getBuildingRotation().toString() + "\n");
        }

        if (getBuildingNudge() != null) {
            builder.append(getBuildingNudge().toString() + "\n");
        }

        if (getSpawnRailwayTrack() != null) {
            builder.append(getSpawnRailwayTrack().toString() + "\n");
        }

        builder.append("}\n\n");

        return builder.toString();
    }
}
