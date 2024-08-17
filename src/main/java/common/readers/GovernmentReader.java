package common.readers;

import common.Government;
import common.Ideology;
import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;
import utils.paradox.parsing.ParadoxParserWithColor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GovernmentReader extends BaseReader {

    public GovernmentReader(File file) {
        super(file);
    }

    public GovernmentReader(String filename) {
        super(filename);
    }

    @Override
    public List<Government> readFile() throws Exception {

        if (file == null) {
            throw new Exception("file was not defined");
        }
        ParadoxParser parser = new ParadoxParser();

        List<Node> nodes = parser.parseFile(file);

        return parseNodes(nodes);
    }

    protected List<Government> parseNodes(List<Node> nodes) {

        List<Government> governments = new ArrayList<>();

        for (Node node: nodes) {
            if (node.hasComment()) {
                // TODO: Figure out something to do
                continue;
            }

            governments.add(new Government(node));

        }

        return governments;

    }

    public static void main(String[] args) {
        try {
            IdeologyReader ideologyReader = new IdeologyReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\ideologies.txt");

            List<Ideology.IdeologyGroup> ideologyGroups = ideologyReader.readFile();
            List<Ideology> ideologies = new ArrayList<>();

            for (Ideology.IdeologyGroup group: ideologyGroups) {
                ideologies.addAll(group.getIdeologies());
            }

            Government.initIdeologies(ideologies);

            GovernmentReader reader = new GovernmentReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\governments.txt");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
