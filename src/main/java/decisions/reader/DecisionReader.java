package decisions.reader;

import decisions.nodes.PoliticalDecisions;
import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DecisionReader extends BaseReader {
    public DecisionReader(File file) {
        super(file);
    }

    public DecisionReader(String filename) {
        super(filename);
    }

    @Override
    public List<PoliticalDecisions> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        ParadoxParser parser = new ParadoxParser();

        List<Node> nodes = parser.parseFile(file);

        List<PoliticalDecisions> politicalDecisions = parseNodes(nodes);

        return politicalDecisions;
    }

    protected List<PoliticalDecisions> parseNodes(List<Node> nodes) {
        List<PoliticalDecisions> politicalDecisions = new ArrayList<>();

        for (Node node : nodes) {
            if (node.hasComment()) {
                // TODO: Handle comments
            } else {
                politicalDecisions.add(new PoliticalDecisions(node));
            }


        }

        for (PoliticalDecisions pd:politicalDecisions) {
            System.out.println(pd);
        }
        return politicalDecisions;
    }

    public static void main(String[] args) {
        try {
            DecisionReader reader = new DecisionReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\decisions\\Isengard.txt");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
