package map.writer;

import map.adjacencies.Adjacency;
import map.definitions.MapDefinitions;
import map.positions.Position;
import map.reader.PositionsReader;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PositionsWriter {
    protected String filename;
    protected Map<Integer, MapDefinitions> mapDefinitionsMap;
    protected List<Position> positions;

    public PositionsWriter(String filename, Map<Integer, MapDefinitions> mapDefinitionsEntries, List<Position> positions) {
        this.filename = filename;
        this.mapDefinitionsMap = mapDefinitionsEntries;
        this.positions = positions;
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {

            for (int i = 0; i < mapDefinitionsMap.size(); i++) {
                Position position;
                MapDefinitions mapDefinitions = mapDefinitionsMap.get(i + 1); // mapDefinitionsMap is stored id --> map definitions entry. And the id is always 1 higher than the index

                if (i < positions.size()) {
                    position = positions.get(i);
                } else {
                    position = new Position();
                    position.setProvinceId(i + 1);
                }

                writer.write("# " + mapDefinitions.getName() + "\n");
                writer.write(position.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
