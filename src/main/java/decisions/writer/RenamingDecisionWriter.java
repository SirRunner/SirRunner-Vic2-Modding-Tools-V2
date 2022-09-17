package decisions.writer;

import decisions.nodes.PoliticalDecisions;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RenamingDecisionWriter {
    protected String filename;
    protected Map<String, PoliticalDecisions> pdRegionToPoliticalDecisions;

    public RenamingDecisionWriter(String filename, Map<String, PoliticalDecisions> pdRegionToPoliticalDecisions) {
        this.filename = filename;
        this.pdRegionToPoliticalDecisions = pdRegionToPoliticalDecisions;
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            for (String name : pdRegionToPoliticalDecisions.keySet()) {
                writer.write(pdRegionToPoliticalDecisions.get(name).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
