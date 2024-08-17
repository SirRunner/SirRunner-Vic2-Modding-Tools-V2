package common.readers;

import common.Ideology;
import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;
import utils.paradox.parsing.ParadoxParserWithColor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IdeologyReader extends BaseReader {

    public IdeologyReader(File file) {
        super(file);
    }

    public IdeologyReader(String filename) {
        super(filename);
    }

    @Override
    public List<Ideology.IdeologyGroup> readFile() throws Exception {

        if (file == null) {
            throw new Exception("file was not defined");
        }
        ParadoxParser parser = new ParadoxParserWithColor();

        List<Node> nodes = parser.parseFile(file);

        return parseNodes(nodes);
    }

    protected List<Ideology.IdeologyGroup> parseNodes(List<Node> nodes) {

        List<Ideology.IdeologyGroup> ideologyGroups = new ArrayList<>();

        for (Node node: nodes) {
            if (node.hasComment()) {
                // TODO: Figure out something to do
                continue;
            }

            ideologyGroups.add(new Ideology.IdeologyGroup(node));

        }

        return ideologyGroups;

    }

    public static void main(String[] args) {
        try {
            IdeologyReader reader = new IdeologyReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\ideologies.txt");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
