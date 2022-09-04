package map.generators;

import map.adjacencies.Adjacency;
import map.reader.AdjacenciesCSVReader;
import map.writer.AdjacenciesWriter;
import org.apache.commons.lang3.tuple.Pair;
import utils.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class AdjacenciesGenerator {
    protected String csvFilename;
    protected String adjacenciesFilename;

    public void setCsvFilename(String filename) {
        this.csvFilename = filename;
    }

    public void setAdjacenciesFilename(String filename) {
        this.adjacenciesFilename = filename;
    }

    protected Map<String, List<Adjacency>> getAdjacencyGroups(Map<Pair<Integer, Integer>, Adjacency> adjacenciesMap) {
        Map<String, List<Adjacency>> adjacencyMapGroups = new HashMap<>();

        for (Adjacency adjacency:adjacenciesMap.values()) {
            String type = adjacency.getType();

            if (!Adjacency.ALL_TYPES.contains(type)) {
                Logger.error("Adjacency contains invalid type " + type);
                continue;
            }

            if (!adjacencyMapGroups.containsKey(type)) {
                adjacencyMapGroups.put(type, new ArrayList<>());
            }

            adjacencyMapGroups.get(type).add(adjacency);
        }

        for (String type: adjacencyMapGroups.keySet()) {
            List<Adjacency> adjacencies = adjacencyMapGroups.get(type);

            adjacencies.sort(Comparator.comparing(Adjacency::getComment).thenComparing(Adjacency::getFromProvince));
        }

        return adjacencyMapGroups;
    }

    protected void run() throws Exception {
        /* Loads in the adjacencies files */
        AdjacenciesCSVReader reader = new AdjacenciesCSVReader(csvFilename);

        Map<Pair<Integer, Integer>, Adjacency> adjacenciesMap = reader.readFile();
        Logger.info("Read " + adjacenciesMap.size() + " adjacencies");

        /* Group them by the type of adjacency */
        Map<String, List<Adjacency>> adjacencyMapGroups = getAdjacencyGroups(adjacenciesMap);

        AdjacenciesWriter writer = new AdjacenciesWriter(adjacenciesFilename, adjacencyMapGroups);
        writer.writeFile();
    }

    public static void main(String[] args) {
        try {
            AdjacenciesGenerator generator = new AdjacenciesGenerator();

            generator.setCsvFilename(System.getProperty("user.home") + "/Downloads/Provinces - Adjacencies.csv");
            generator.setAdjacenciesFilename("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\map\\adjacencies.csv");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
