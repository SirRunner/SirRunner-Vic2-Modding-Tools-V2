package map.reader;

import map.regions.Region;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;
import utils.paradox.parsing.ParadoxParsingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegionReader extends BaseReader {

    public static String DELIMITER = " ";

    public RegionReader(File file) {
        super(file);
    }

    public RegionReader(String filename) {
        super(filename);
    }

    public Region getRegion(String line) {
        String[] tokens = StringUtils.split(line);

        if (tokens.length == 0) {
            return null;
        }

        boolean definingProvinces = false;
        boolean commentStarted = false;
        Map<String, String> parsedLine = new HashMap<>();

        for (String token : tokens) {
            if (StringUtils.isEmpty(token) || StringUtils.equals(ParadoxParsingUtils.DEFINES, token)) {
                continue;
            }

            if (StringUtils.startsWith(token, ParadoxParsingUtils.COMMENT_START)) {
                commentStarted = true;

                if (tokens.length > 1) {
                    parsedLine.put(Region.NAME, StringUtils.removeStart(token, ParadoxParsingUtils.COMMENT_START));
                }
            } else if (commentStarted) {
                if (parsedLine.containsKey(Region.NAME)) {
                    parsedLine.put(Region.NAME, parsedLine.get(Region.NAME) + DELIMITER + token);
                } else {
                    parsedLine.put(Region.NAME, token);
                }
            } else if (definingProvinces) {
                if (StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, token)) {
                    definingProvinces = false;
                } else {
                    if (!StringUtils.isNumeric(token)) {
                        Logger.error("Province id " + token + " is not numeric");
                    } else if (parsedLine.containsKey(Region.PROVINCES)) {
                        parsedLine.put(Region.PROVINCES, parsedLine.get(Region.PROVINCES) + DELIMITER + token);
                    } else {
                        parsedLine.put(Region.PROVINCES, token);
                    }
                }
            } else if (StringUtils.equals(ParadoxParsingUtils.OPEN_BLOCK, token)) {
                definingProvinces = true;
            } else {
                if (parsedLine.containsKey(Region.CODE)) {
                    Logger.error("Line has multiple region codes: " + parsedLine.get(Region.CODE) + DELIMITER + token);
                    return null;
                } else {
                    parsedLine.put(Region.CODE, token);
                }
            }
        }

        return new Region(parsedLine);
    }

    protected void printLoggerError(String id, String codeName) {
        Logger.error("Region " + id + " has no " + codeName);
    }

    protected void checkRegion(Region region) {
        if (region.getProvinces().isEmpty()) {
            printLoggerError(region.getCode(), "assigned province");
        }

        if (StringUtils.isEmpty(region.getName())) {
            printLoggerError(region.getCode(), "name");
        }
    }

    protected void updateRegion(String line, Map<String, Region> codeToRegions, Region region) {
        String code = region.getCode();

        checkRegion(region);

        if (StringUtils.isEmpty(code)) {
            Logger.error("Region without a code:");
            Logger.error(line);
        }
        if (codeToRegions.containsKey(code)) {
            Logger.error("Code " + code + " is defined in the region file multiple times!");
        } else {
            Logger.debug("Saving province history for id " + code);

            codeToRegions.put(code, region);
        }
    }

    public Map<String, Region> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<String, Region> codeToRegions = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"))) {
            Logger.info("Reading regions from " + file.getName());

            String line = reader.readLine();
            String appendLine = "";

            while (line != null) {

                if (line.trim().startsWith("#") || line.isEmpty()) {
                    Logger.info("Ignoring line: \"" + line + "\"");

                    if (line.trim().startsWith("# SirRunner Generator Apply")) {
                        appendLine = line.split("\"")[line.split("\"").length - 1];
                    }
                } else {
                    Region region = getRegion(line + appendLine);

                    if (region != null) {
                        updateRegion(line, codeToRegions, region);
                    }
                }

                line = reader.readLine();
            }
        }

        return codeToRegions;
    }

    public static void main(String[] args) {
        try {
            RegionReader reader = new RegionReader("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/region.txt");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
