package savefile.objects;

import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;
import utils.paradox.parsing.ParadoxParserV2;

import java.util.List;

public class SaveFileReader extends BaseReader {

    public SaveFileReader(String filename) {
        super(filename);
    }

    @Override
    public Object readFile() throws Exception {

        if (file == null) {
            throw new Exception("file was not defined");
        }
        ParadoxParser parser = new ParadoxParserV2();

        List<Node> nodes = parser.parseFile(file);

        return null;// TODO
    }

    public static void main(String[] args) {
        try {
            SaveFileReader reader = new SaveFileReader(System.getProperty("user.home") + "\\Documents\\Paradox Interactive\\Victoria II\\GGG\\save games\\Sublime Veyethu.v2");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
