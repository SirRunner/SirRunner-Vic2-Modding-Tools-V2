package historyfile.writer;

import historyfile.pops.PopHistoryFile;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopHistoryFileWriter {
    protected String filename;
    protected String foldername;
    protected Map<Integer, PopHistoryFile> idToPopHistory;

    public PopHistoryFileWriter(String filename, String foldername, Map<Integer, PopHistoryFile> idToPopHistory) {
        this.filename = filename;
        this.foldername = foldername;
        this.idToPopHistory = idToPopHistory;
    }

    protected String getFilePath() {
        return foldername + "/" + filename;
    }

    public void write() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFilePath()), Charset.forName("windows-1252")))) {
            writer.write("""
                    # "religions": men, elven, dwarven, orc, hobbit, ent, troll, eagle, spider, undead
                                        
                    ### - Population Research
                    # - Based on medieval populations, especially from the late ERE
                    # - Reference materiel: https://medium.com/migration-issues/notes-on-medieval-population-geography-fd062449364f

                    ### - Rough outline of population composition:
                    # ~2-4% nobles ( aristocrats, soldiers, loremasters )
                    # ~10-20% urban population ( partly artisans, craftsmen, clerks, soldiers )
                    # ~90% rural population ( ~10-30% free peasants, ~30-50% serfs, possibly also slaves )
                                        
                    """);

            List<Integer> ids = idToPopHistory.keySet().stream().sorted().collect(Collectors.toList());

            for (Integer id : ids) {
                writer.write(idToPopHistory.get(id).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
