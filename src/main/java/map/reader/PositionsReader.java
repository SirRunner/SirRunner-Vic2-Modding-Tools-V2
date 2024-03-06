package map.reader;

import decisions.nodes.PoliticalDecisions;
import map.positions.Position;
import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PositionsReader extends BaseReader {
    public PositionsReader(File file) {
        super(file);
    }

    public PositionsReader(String filename) {
        super(filename);
    }

    @Override
    public List<Position> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        ParadoxParser parser = new ParadoxParser();

        List<Node> nodes = parser.parseFile(file);

        List<Position> positions = parseNodes(nodes);

        return positions;
    }

    protected List<Position> parseNodes(List<Node> nodes) {
        List<Position> positions = new ArrayList<>();

        for (Node node : nodes) {
            if (node.hasComment()) {
                // TODO: Handle comments
            } else {
                positions.add(new Position(node));
            }


        }

        return positions;
    }

    public static void main(String[] args) {
        try {
            PositionsReader reader = new PositionsReader("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/positions.txt");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
