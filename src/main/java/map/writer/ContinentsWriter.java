package map.writer;

import historyfile.province.ProvinceHistoryFile;
import map.definitions.MapDefinitions;
import map.regions.Region;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContinentsWriter {

    protected String filename;
    protected List<ProvinceHistoryFile> provinceHistoryFiles;

    public ContinentsWriter(String filename, List<ProvinceHistoryFile> provinceHistoryFiles) {
        this.filename = filename;
        this.provinceHistoryFiles = provinceHistoryFiles;
    }

    protected Map<String, List<Integer>> groupHistoryFiles() {

        Map<String, List<Integer>> groups = new HashMap<>();

        for (ProvinceHistoryFile province: provinceHistoryFiles) {

            if (!groups.containsKey(province.getContinent())) {
                groups.put(province.getContinent(), new ArrayList<>());
            }

            groups.get(province.getContinent()).add(province.getId());

        }

        return groups;

    }

    protected String getIds(List<Integer> ids) {

        StringBuilder text = new StringBuilder();
        List<List<Integer>> groups = ListUtils.partition(ids, 30);

        for (List<Integer> group: groups) {
            text.append("\t\t").append(StringUtils.join(group, " ")).append("\n");
        }

        return text.toString();
    }

    public void writeFile() {

        Map<String, List<Integer>> groups = groupHistoryFiles();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "Cp1252"))) {

            for (String continent: groups.keySet()) {
                writer.write(continent + " = {\n");
                writer.write("\tprovinces = {\n");
                writer.write(getIds(groups.get(continent)));
                writer.write("\t}\n");
                writer.write("}\n");
                writer.write("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
