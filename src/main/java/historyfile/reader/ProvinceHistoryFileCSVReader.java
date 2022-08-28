package historyfile.reader;

import com.opencsv.CSVReaderHeaderAware;
import historyfile.province.ProvinceHistoryFile;
import org.apache.commons.lang3.StringUtils;
import utils.baseclasses.BaseReader;
import utils.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProvinceHistoryFileCSVReader extends BaseReader {
    public ProvinceHistoryFileCSVReader(File file) {
        super(file);
    }

    public ProvinceHistoryFileCSVReader(String filename) {
        super(filename);
    }

    protected ProvinceHistoryFile getProvinceHistory(Map<String, String> line) {
        if (!StringUtils.isNumeric(StringUtils.trim(line.get(ProvinceHistoryFile.ID)))) {
            Logger.error("Id is not a number!");
            Logger.error(line.toString());
            return null;
        }

        return new ProvinceHistoryFile(line);
    }

    protected void printLoggerError(int provinceId, String codeName) {
        Logger.error("Province " + provinceId + " has no set " + codeName);
    }

    protected void checkProvinceHistoryFile(ProvinceHistoryFile historyFile) {
        int provinceId = historyFile.getId();

        if (StringUtils.isEmpty(historyFile.getName())) {
            printLoggerError(provinceId, "name");
        }

        if (StringUtils.isEmpty(historyFile.getTradeGoods())) {
            printLoggerError(provinceId, "trade_goods");
        }

        if (historyFile.getLifeRating() < 1) {
            printLoggerError(provinceId, "life_rating");
        }

        if (StringUtils.isEmpty(historyFile.getContinent())) {
            printLoggerError(provinceId, "continent");
        }

        if (StringUtils.isEmpty(historyFile.getClimate())) {
            printLoggerError(provinceId, "climate");
        }
    }

    protected void updateProvinceHistoryMap(Map<String, String> line, Map<Integer, ProvinceHistoryFile> idToProvinceHistory, ProvinceHistoryFile provinceHistory) {
        int id = provinceHistory.getId();

        checkProvinceHistoryFile(provinceHistory);

        if (id < 1) {
            Logger.error("Invalid province id!");
            Logger.error(line.toString());
        } else {
            if (idToProvinceHistory.containsKey(id)) {
                Logger.error("ID " + id + " is defined in the csv file multiple times!");
            } else {
                Logger.debug("Saving province history for id " + id);

                idToProvinceHistory.put(id, provinceHistory);
            }
        }
    }

    public Map<Integer, ProvinceHistoryFile> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<Integer, ProvinceHistoryFile> idToProvinceHistory = new HashMap<>();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Logger.info("Reading province history from " + file.getName());

            Map<String, String> line = reader.readMap();

            while (line != null) {
                ProvinceHistoryFile provinceHistory = getProvinceHistory(line);

                if (provinceHistory != null) {
                    updateProvinceHistoryMap(line, idToProvinceHistory, provinceHistory);
                }

                line = reader.readMap();
            }
        }

        return idToProvinceHistory;
    }
}
