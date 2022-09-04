package map.reader;

import com.opencsv.CSVReaderHeaderAware;
import map.adjacencies.Adjacency;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utils.Logger;
import utils.baseclasses.BaseReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AdjacenciesCSVReader extends BaseReader {
    public AdjacenciesCSVReader(File file) {
        super(file);
    }

    public AdjacenciesCSVReader(String filename) {
        super(filename);
    }

    protected Adjacency getAdjacencies(Map<String, String> line) {
        return new Adjacency(line);
    }

    protected void updateAdjacenciesMap(Map<String, String> line, Map<Pair<Integer, Integer>, Adjacency> provinceIdsToAdjacencies, Adjacency adjacency) {
        Pair<Integer, Integer> provincePair = new ImmutablePair<>(adjacency.getFromProvince(), adjacency.getToProvince());
        Pair<Integer, Integer> reversedProvincePair = new ImmutablePair<>(adjacency.getToProvince(), adjacency.getFromProvince());

        if (provinceIdsToAdjacencies.containsKey(provincePair) || provinceIdsToAdjacencies.containsKey(reversedProvincePair)) {
            Logger.error("Pair " + provincePair.getLeft() + " " + provincePair.getRight() + " is in the map multiple times!");
        } else {
            Logger.debug("Saving province history for par " + provincePair.getLeft() + " " + provincePair.getRight());

            provinceIdsToAdjacencies.put(provincePair, adjacency);
        }
    }

    public Map<Pair<Integer, Integer>, Adjacency> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<Pair<Integer, Integer>, Adjacency> provinceIdsToAdjacencies = new HashMap<>();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Logger.info("Reading adjacencies from " + file.getName());

            Map<String, String> line = reader.readMap();

            while (line != null) {
                Adjacency adjacency = getAdjacencies(line);

                if (adjacency != null) {
                    updateAdjacenciesMap(line, provinceIdsToAdjacencies, adjacency);
                }

                line = reader.readMap();
            }
        }

        return provinceIdsToAdjacencies;
    }
}
