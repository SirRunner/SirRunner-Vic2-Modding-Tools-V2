package common.readers;

import common.Culture;
import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;
import utils.paradox.parsing.ParadoxParserWithColorAndNames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CultureReader extends BaseReader {

    public CultureReader(File file) {
        super(file);
    }

    public CultureReader(String filename) {
        super(filename);
    }

    @Override
    public List<Culture.CultureGroup> readFile() throws Exception {

        if (file == null) {
            throw new Exception("file was not defined");
        }
        ParadoxParser parser = new ParadoxParserWithColorAndNames();

        List<Node> nodes = parser.parseFile(file);

        return parseNodes(nodes);
    }

    protected List<Culture.CultureGroup> parseNodes(List<Node> nodes) throws Exception {

        List<Culture.CultureGroup> cultureGroups = new ArrayList<>();

        for (Node node : nodes) {
            if (node.hasComment()) {
                // TODO: Figure out something to do
                continue;
            }

            cultureGroups.add(new Culture.CultureGroup(node));

        }

        return cultureGroups;

    }
}
