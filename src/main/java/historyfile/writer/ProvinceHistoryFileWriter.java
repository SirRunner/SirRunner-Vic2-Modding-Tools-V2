package historyfile.writer;

import historyfile.province.ProvinceHistoryFile;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class ProvinceHistoryFileWriter {
    protected String provinceHistoryFolder;
    protected ProvinceHistoryFile provinceHistory;

    public ProvinceHistoryFileWriter(String provinceHistoryFolder, ProvinceHistoryFile phf) {
        this.provinceHistoryFolder = provinceHistoryFolder;
        this.provinceHistory = phf;
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFilename(provinceHistory)), Charset.forName("windows-1252")))) {
            writer.write(provinceHistory.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getFilename(ProvinceHistoryFile provinceHistory) {
        return provinceHistoryFolder + "\\" + provinceHistory.getId() + " - " + StringUtils.stripAccents(provinceHistory.getName()) + ".txt";
    }
}
