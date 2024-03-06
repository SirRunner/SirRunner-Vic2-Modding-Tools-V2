package map.positions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.Arrays;

public class SpawnRailwayTrack {
    PixelLocation empty;

    public SpawnRailwayTrack(Node node) {
        parseNode(node);
    }

    public PixelLocation getEmpty() {
        return empty;
    }

    public void setEmpty(PixelLocation fort) {
        this.empty = fort;
    }

    public void setEmpty(Node node) {
        double x = Position.getXValueFromNode(node);
        double y = Position.getYValueFromNode(node);

        setEmpty(new PixelLocation(x, y));
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
            case EMPTY -> setEmpty(node);
        }
    }

    protected enum VARIABLE {
        EMPTY;

        protected static VARIABLE getByName(String name) {
            return Arrays.stream(values()).filter(variable -> StringUtils.equalsIgnoreCase(name, variable.name())).findFirst().orElse(null);
        }
    }

    @Override
    public String toString() {

        StringBuilder data = new StringBuilder();

        data.append("    spawn_railway_track = {\n");

        if (getEmpty() != null) {
            data.append("        empty = {\n");
            data.append(getEmpty().toString(2) + "\n");
        }

        data.append("    }\n");

        return data.toString();
    }
}