package decisions.reader;

import com.opencsv.CSVReaderHeaderAware;
import decisions.renaming.ProvinceRenaming;
import historyfile.province.ProvinceHistoryFile;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ProvinceRenamingCSVReader extends BaseReader {
    public ProvinceRenamingCSVReader(File file) {
        super(file);
    }

    public ProvinceRenamingCSVReader(String filename) {
        super(filename);
    }

    protected ProvinceRenaming getProvinceRenaming(Map<String, String> line) {
        if (!StringUtils.isNumeric(StringUtils.trim(line.get(ProvinceHistoryFile.ID)))) {
            Logger.error("Id is not a number!");
            Logger.error(line.toString());
            return null;
        }

        return new ProvinceRenaming(line);
    }

    @Override
    public Map<Integer, ProvinceRenaming> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<Integer, ProvinceRenaming> idToProvinceRenaming = new HashMap<>();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Logger.info("Reading province renaming data from " + file.getName());

            Map<String, String> line = reader.readMap();

            while (line != null) {
                ProvinceRenaming provinceRenaming = getProvinceRenaming(line);

                if (provinceRenaming != null) {
                    idToProvinceRenaming.put(provinceRenaming.getProvinceId(), provinceRenaming);
                }

                line = reader.readMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idToProvinceRenaming;
    }
}
