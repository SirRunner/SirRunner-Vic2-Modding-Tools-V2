package historyfile.generators;

import historyfile.pops.PopHistoryFile;
import historyfile.reader.PopHistoryFileCSVReader;
import historyfile.writer.PopHistoryFileWriter;
import utils.Utils;

import java.io.File;
import java.util.Map;

public class PopHistoryFileGenerator {
    protected String popHistoryFolder;
    protected String csvFilename;

    public void setPopHistoryFolder(String popHistoryFolder) {
        this.popHistoryFolder = popHistoryFolder;
    }

    public void setCsvFilename(String csvFilename) {
        this.csvFilename = csvFilename;
    }

    protected void run() throws Exception {
        PopHistoryFileCSVReader reader = new PopHistoryFileCSVReader(csvFilename);

        Map<String, Map<Integer, PopHistoryFile>> popHistoryFiles = reader.readFile();

        /* Deletes all old pop history files */
        File outputFolder = new File(popHistoryFolder);
        Utils.clearFolder(outputFolder);

        for (String filenamePrefix: popHistoryFiles.keySet()) {
            String filename = filenamePrefix + ".txt";

            PopHistoryFileWriter writer = new PopHistoryFileWriter(filename, popHistoryFolder, popHistoryFiles.get(filenamePrefix));
            writer.write();
        }
    }

    public static void main(String[] args) {
        try {
            PopHistoryFileGenerator generator = new PopHistoryFileGenerator();

            generator.setPopHistoryFolder("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/history/pops/2954.1.1");
            generator.setCsvFilename(System.getProperty("user.home") + "/Downloads/Provinces - Pops.csv");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
