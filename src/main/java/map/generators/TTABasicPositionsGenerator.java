package map.generators;

import map.definitions.MapDefinitions;
import map.positions.Position;
import map.reader.MapDefinitionsReader;
import map.reader.PositionsReader;
import map.writer.PositionsWriter;
import utils.Logger;

import java.util.List;
import java.util.Map;

/* This class is to be used when adding new provinces, and there's too many to reasonably get them al in the positions file */
public class TTABasicPositionsGenerator {
    protected String definitionsFilename;
    protected String positionsFilename;

    public void setDefinitionsFilename(String filename) {
        this.definitionsFilename = filename;
    }

    public void setPositionsFilename(String filename) {
        this.positionsFilename = filename;
    }

    protected void run() throws Exception {

        /* Loads in the definitions file */
        MapDefinitionsReader definitionsReader = new MapDefinitionsReader(definitionsFilename);

        Map<Integer, MapDefinitions> mapDefinitionsEntries = definitionsReader.readFile();
        Logger.info("Read " + mapDefinitionsEntries.size() + " map definitions");

        PositionsReader positionsReader = new PositionsReader(positionsFilename);

        List<Position> positions = positionsReader.readFile();
        Logger.info("Read " + positions.size() + " positions");

        PositionsWriter writer = new PositionsWriter(positionsFilename, mapDefinitionsEntries, positions);
        writer.writeFile();

    }

    public static void main(String[] args) {
        try {
            TTABasicPositionsGenerator generator = new TTABasicPositionsGenerator();

            generator.setDefinitionsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/definition.csv");
            generator.setPositionsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/positions.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
