package decisions.reader;

import com.opencsv.CSVReaderHeaderAware;
import decisions.renaming.ProvinceRenaming;
import decisions.renaming.RegionRenaming;
import historyfile.province.ProvinceHistoryFile;
import map.regions.Region;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionRenamingCSVReader extends BaseReader {
    public RegionRenamingCSVReader(File file) {
        super(file);
    }

    public RegionRenamingCSVReader(String filename) {
        super(filename);
    }

    protected RegionRenaming getRegionRenaming(Map<String, String> line) {
        return new RegionRenaming(line);
    }

    @Override
    public Map<String, RegionRenaming> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<String, RegionRenaming> codeToRegionRenamingList = new HashMap<>();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Logger.info("Reading region renaming data from " + file.getName());

            Map<String, String> line = reader.readMap();

            while (line != null) {
                RegionRenaming regionRenaming = getRegionRenaming(line);

                if (regionRenaming != null) {
                    if (codeToRegionRenamingList.containsKey(regionRenaming.getRegionCode())) {
                        Logger.error("Region " + regionRenaming.getRegionCode() + " is defined multiple times in the renaming sheet");
                    }

                    codeToRegionRenamingList.put(regionRenaming.getRegionCode(), regionRenaming);
                }

                line = reader.readMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return codeToRegionRenamingList;
    }
}
