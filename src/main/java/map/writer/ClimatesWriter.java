package map.writer;

import historyfile.province.ProvinceHistoryFile;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClimatesWriter {

    protected String filename;
    protected List<ProvinceHistoryFile> provinceHistoryFiles;

    public ClimatesWriter(String filename, List<ProvinceHistoryFile> provinceHistoryFiles) {
        this.filename = filename;
        this.provinceHistoryFiles = provinceHistoryFiles;
    }

    protected Map<String, List<Integer>> groupHistoryFiles() {

        Map<String, List<Integer>> groups = new HashMap<>();

        for (ProvinceHistoryFile province: provinceHistoryFiles) {

            if (!groups.containsKey(province.getClimate())) {
                groups.put(province.getClimate(), new ArrayList<>());
            }

            groups.get(province.getClimate()).add(province.getId());

        }

        return groups;

    }

    protected String getIds(List<Integer> ids) {

        StringBuilder text = new StringBuilder();
        List<List<Integer>> groups = ListUtils.partition(ids, 30);

        for (List<Integer> group: groups) {
            text.append("\t").append(StringUtils.join(group, " ")).append("\n");
        }

        return text.toString();
    }

    public void writeFile() {

        Map<String, List<Integer>> groups = groupHistoryFiles();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "Cp1252"))) {

            for (String climate: groups.keySet()) {
                writer.write(climate + " = {\n");
                writer.write(getIds(groups.get(climate)));
                writer.write("}\n");
                writer.write("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
