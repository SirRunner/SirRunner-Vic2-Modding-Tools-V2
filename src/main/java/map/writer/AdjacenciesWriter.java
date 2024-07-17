package map.writer;

import map.adjacencies.Adjacency;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacenciesWriter {
    protected String filename;
    protected Map<String, List<Adjacency>> typesToAdjacencies;
    protected List<String> handledTypes;
    protected static Map<String, String> TYPE_TO_HEADER = new HashMap<>();

    public AdjacenciesWriter(String filename, Map<String, List<Adjacency>> typesToAdjacencies) {
        this.filename = filename;
        this.typesToAdjacencies = typesToAdjacencies;
        this.handledTypes = new ArrayList<>();

        if (TYPE_TO_HEADER.isEmpty()) {
            TYPE_TO_HEADER.put(Adjacency.STRAIGHTS, "Straights");
            TYPE_TO_HEADER.put(Adjacency.IMPASSABLES, "Impassables");
            TYPE_TO_HEADER.put(Adjacency.CANALS, "Canals");
        }
    }

    protected String getHeader() {
        return "# From;To;Type;Through;Data;Comment\n\n";
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("windows-1252")))) {
            writer.write(getHeader());

            for (String type : Adjacency.ALL_TYPES) {
                writer.write("# " + TYPE_TO_HEADER.get(type) + "\n");

                if (typesToAdjacencies.containsKey(type)) {
                    for (Adjacency adjacency : typesToAdjacencies.get(type)) {
                        writer.write(adjacency.toString() + "\n");
                    }
                }
                writer.write("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
