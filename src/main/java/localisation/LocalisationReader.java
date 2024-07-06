package localisation;

import utils.baseclasses.BaseReader;
import utils.paradox.parsing.localisation.ParadoxLocalisationParser;
import utils.paradox.scripting.localisation.Localisation;

import java.io.File;
import java.util.List;

public class LocalisationReader extends BaseReader {
    public LocalisationReader(File file) {
        super(file);
    }

    public LocalisationReader(String filename) {
        super(filename);
    }

    @Override
    public List<Localisation> readFile() throws Exception {

        if (file == null) {
            throw new Exception("file was not defined");
        }

        ParadoxLocalisationParser parser = new ParadoxLocalisationParser();

        return parser.parseFile(file);
    }

    public static void main(String[] args) {
        try {
            LocalisationReader reader = new LocalisationReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\localisation\\events.csv");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
