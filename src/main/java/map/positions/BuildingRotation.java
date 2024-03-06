package map.positions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.Arrays;

public class BuildingRotation {
    Double fort;
    Double railroad;
    Double navalBase;
    Double aeroplaneFactory;

    public BuildingRotation(Node node) {
        parseNode(node);
    }

    public Double getFort() {
        return fort;
    }

    public void setFort(double fort) {
        this.fort = fort;
    }

    public void setFort(String fort) {
        setFort(Double.parseDouble(fort));
    }

    public void setFort(Node node) {
        setFort(node.getValue());
    }

    public Double getRailroad() {
        return railroad;
    }

    public void setRailroad(double railroad) {
        this.railroad = railroad;
    }

    public void setRailroad(String railroad) {
        setRailroad(Double.parseDouble(railroad));
    }

    public void setRailroad(Node node) {
        setRailroad(node.getValue());
    }

    public Double getNavalBase() {
        return navalBase;
    }

    public void setNavalBase(double navalBase) {
        this.navalBase = navalBase;
    }

    public void setNavalBase(String navalBase) {
        setNavalBase(Double.parseDouble(navalBase));
    }

    public void setNavalBase(Node node) {
        setNavalBase(node.getValue());
    }

    public Double getAeroplaneFactory() {
        return aeroplaneFactory;
    }

    public void setAeroplaneFactory(double aeroplaneFactory) {
        this.aeroplaneFactory = aeroplaneFactory;
    }

    public void setAeroplaneFactory(String aeroplaneFactory) {
        setAeroplaneFactory(Double.parseDouble(aeroplaneFactory));
    }

    public void setAeroplaneFactory(Node node) {
        setAeroplaneFactory(node.getValue());
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
            case RAILROAD -> setRailroad(node);
            case NAVAL_BASE -> setNavalBase(node);
            case AEROPLANE_FACTORY -> setAeroplaneFactory(node);
        }
    }

    protected enum VARIABLE {
        FORT,
        RAILROAD,
        NAVAL_BASE,
        AEROPLANE_FACTORY;

        protected static VARIABLE getByName(String name) {
            return Arrays.stream(VARIABLE.values()).filter(variable -> StringUtils.equalsIgnoreCase(name, variable.name())).findFirst().orElse(null);
        }
    }

    @Override
    public String toString() {

        StringBuilder data = new StringBuilder();

        data.append("    building_rotation = {\n");

        if (getFort() != null) {
            data.append("        fort = " + Position.getPrintableNumber(getFort()) + "\n");
        }

        if (getRailroad() != null) {
            data.append("        railroad = " + Position.getPrintableNumber(getRailroad()) + "\n");
        }

        if (getNavalBase() != null) {
            data.append("        naval_base = " + Position.getPrintableNumber(getNavalBase()) + "\n");
        }

        if (getAeroplaneFactory() != null) {
            data.append("        aeroplane_factory = " + Position.getPrintableNumber(getAeroplaneFactory()) + "\n");
        }

        data.append("    }\n");

        return data.toString();
    }
}
