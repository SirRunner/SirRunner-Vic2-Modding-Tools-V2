package map.positions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.Arrays;

public class BuildingPosition {
    PixelLocation fort;
    PixelLocation navalBase;
    PixelLocation railroad;

    public BuildingPosition(Node node) {
        parseNode(node);
    }

    public PixelLocation getFort() {
        return fort;
    }

    public void setFort(PixelLocation fort) {
        this.fort = fort;
    }

    public void setFort(Node node) {
        double x = Position.getXValueFromNode(node);
        double y = Position.getYValueFromNode(node);

        setFort(new PixelLocation(x, y));
    }

    public PixelLocation getNavalBase() {
        return navalBase;
    }

    public void setNavalBase(PixelLocation navalBase) {
        this.navalBase = navalBase;
    }

    public void setNavalBase(Node node) {
        double x = Position.getXValueFromNode(node);
        double y = Position.getYValueFromNode(node);

        setNavalBase(new PixelLocation(x, y));
    }

    public PixelLocation getRailroad() {
        return railroad;
    }

    public void setRailroad(PixelLocation railroad) {
        this.railroad = railroad;
    }

    public void setRailroad(Node node) {
        double x = Position.getXValueFromNode(node);
        double y = Position.getYValueFromNode(node);

        setRailroad(new PixelLocation(x, y));
    }

    protected void parseNode(Node node) {
        node.getNodes().forEach(childNode -> {

            VARIABLE variable = VARIABLE.getByName(childNode.getName());

            if (variable == null) {
                Logger.error("Failed to correctly handle node withing building_ration with name " + childNode.getName());
                return;
            }

            updateNode(variable, childNode);
        });
    }

    protected void updateNode(VARIABLE variable, Node node) {
        switch (variable) {
            case FORT -> setFort(node);
            case NAVAL_BASE -> setNavalBase(node);
            case RAILROAD -> setRailroad(node);
        }
    }

    protected enum VARIABLE {
        FORT,
        NAVAL_BASE,
        RAILROAD;

        protected static VARIABLE getByName(String name) {
            return Arrays.stream(VARIABLE.values()).filter(variable -> StringUtils.equalsIgnoreCase(name, variable.name())).findFirst().orElse(null);
        }
    }

    @Override
    public String toString() {

        StringBuilder data = new StringBuilder();

        data.append("    building_position = {\n");

        if (getFort() != null) {
            data.append("        fort = {\n");
            data.append(getFort().toString(2) + "\n");
        }

        if (getRailroad() != null) {
            data.append("        railroad = {\n");
            data.append(getRailroad().toString(2) + "\n");
        }

        if (getNavalBase() != null) {
            data.append("        naval_base = {\n");
            data.append(getNavalBase().toString(2) + "\n");
        }

        data.append("    }\n");

        return data.toString();
    }
}
