package decisions.writer;

import decisions.nodes.PoliticalDecisions;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

public class RenamingDecisionWriter {
    protected String filename;
    protected Map<String, PoliticalDecisions> pdRegionToPoliticalDecisions;

    public RenamingDecisionWriter(String filename, Map<String, PoliticalDecisions> pdRegionToPoliticalDecisions) {
        this.filename = filename;
        this.pdRegionToPoliticalDecisions = pdRegionToPoliticalDecisions;
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("windows-1252")))) {
            writer.write("# Remove this line to have the validator check that ORs have more than one condition and that interior arguments are correct\n");
            writer.write("# Audax Validator \"!\" Ignore_1002\n");
            writer.write("# Audax Validator \"!\" Ignore_1009\n");
            for (String name : pdRegionToPoliticalDecisions.keySet()) {
                writer.write(pdRegionToPoliticalDecisions.get(name).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
