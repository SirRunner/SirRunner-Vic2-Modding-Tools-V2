package map.reader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import historyfile.province.ProvinceHistoryFile;
import map.definitions.MapDefinitions;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MapDefinitionsReader extends BaseReader {
    public MapDefinitionsReader(File file) {
        super(file);
    }

    public MapDefinitionsReader(String filename) {
        super(filename);
    }

    public MapDefinitions getMapDefinitions(Map<String, String> line) {
        if (!StringUtils.isNumeric(StringUtils.trim(line.get(MapDefinitions.ID)))) {
            Logger.error("Id is not a number!");
            Logger.error(line.toString());
            return null;
        }

        return new MapDefinitions(line);
    }

    protected void printLoggerError(int id, String codeName) {
        Logger.error("Map definitions entry " + id + " has no set " + codeName);
    }

    protected void checkMapDefinitions(MapDefinitions mapDefinitions) {
        int provinceId = mapDefinitions.getId();

        if (StringUtils.isEmpty(mapDefinitions.getName())) {
            printLoggerError(provinceId, "name");
        }
    }

    protected void updateMapDefinitions(Map<String, String> line, Map<Integer, MapDefinitions> idToMapDefinitions, MapDefinitions mapDefinitions) {
        int id = mapDefinitions.getId();

        checkMapDefinitions(mapDefinitions);

        if (id < 1) {
            Logger.error("Invalid province id!");
            Logger.error(line.toString());
        } else {
            if (idToMapDefinitions.containsKey(id)) {
                Logger.error("ID " + id + " is defined in the csv file multiple times!");
            } else {
                Logger.debug("Saving province history for id " + id);

                idToMapDefinitions.put(id, mapDefinitions);
            }
        }
    }

    public Map<Integer, MapDefinitions> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<Integer, MapDefinitions> idToMapDefinitions = new HashMap<>();

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAwareBuilder(new InputStreamReader(new FileInputStream(file), "Cp1252")).withCSVParser(parser).build()) {
            Logger.info("Reading province definitions from " + file.getName());

            Map<String, String> line = reader.readMap();

            while (line != null) {
                MapDefinitions mapDefinitions = getMapDefinitions(line);

                if (mapDefinitions != null) {
                    updateMapDefinitions(line, idToMapDefinitions, mapDefinitions);
                }

                line = reader.readMap();
            }
        }

        return idToMapDefinitions;
    }

    public static void main(String[] args) {
        try {
            MapDefinitionsReader reader = new MapDefinitionsReader("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/definition.csv");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
