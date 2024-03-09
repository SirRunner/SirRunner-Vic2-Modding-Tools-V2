package map.validation;

import map.definitions.MapDefinitions;
import map.parsing.MapDefinitionsParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefinitionsValidator {

    protected String modDirectory;
    protected File definitionsFile;
    protected File provinceMapFile;

    public String getModDirectory() {
        return modDirectory;
    }

    public void setModDirectory(String modDirectory) {
        this.modDirectory = modDirectory;
    }

    public File getDefinitionsFile() {

        if (definitionsFile == null) {
            definitionsFile = new File(getDefinitionsFileLocation());
        }

        return definitionsFile;
    }

    public File getProvinceMapFile() {

        if (provinceMapFile == null) {
            provinceMapFile = new File(getProvinceMapFileLocation());
        }

        return provinceMapFile;
    }

    protected String getDefinitionsFileLocation() {
        return getModDirectory() + "\\map\\definition.csv";
    }

    protected String getProvinceMapFileLocation() {
        return getModDirectory() + "\\map\\provinces.bmp";
    }

    protected void run() throws Exception {

        if (StringUtils.isEmpty(getModDirectory())) {
            throw new Exception("Mod not defined");
        }

        // Loading the definitions file
        MapDefinitionsParser definitionsParser = new MapDefinitionsParser(getDefinitionsFile());
        Map<Integer, MapDefinitions> idToMapDefinitions = definitionsParser.readFile();

        // Validating that all expected province ids are used, and there are no extraneous ones getting used
        Set<Integer> expectedProvinceIds = new HashSet<>();

        for (int i = 1; i < idToMapDefinitions.size() + 1; i++) {
            expectedProvinceIds.add(i);
        }

        Collection<Integer> extraProvinceIds = CollectionUtils.removeAll(idToMapDefinitions.keySet(), expectedProvinceIds);
        Collection<Integer> missingProvinceIds = CollectionUtils.removeAll(expectedProvinceIds, idToMapDefinitions.keySet());

        if (!extraProvinceIds.isEmpty()) {
            Logger.error("The following ids are defined, but are larger than the number of provinces defined (" + idToMapDefinitions.size() + "):");
            extraProvinceIds.forEach(id -> System.out.println("\t" + id));
        }

        if (!missingProvinceIds.isEmpty()) {
            Logger.error("The following ids are not defined, despite being equal to or less than the number of provinces defined (" + idToMapDefinitions.size() + "):");
            missingProvinceIds.forEach(id -> System.out.println("\t" + id));
        }

        // Validating that all defined provinces have at leats one pixel on the province map, and that all colors on the province map have a defined province
        BufferedImage img = ImageIO.read(getProvinceMapFile());
        Set<Color> colors = new HashSet<>();

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                colors.add(new Color(img.getRGB(x, y)));
            }
        }

        Collection<Color> unusedDefinitionsColors = CollectionUtils.removeAll(definitionsParser.getUsedColors(), colors);
        Collection<Color> extraMapColors = CollectionUtils.removeAll(colors, definitionsParser.getUsedColors());

        if (!unusedDefinitionsColors.isEmpty()) {
            Logger.error("The following colors are defined, but not on the province map:");
            unusedDefinitionsColors.forEach(color -> System.out.println("\t" + color));
        }

        if (!extraMapColors.isEmpty()) {
            Logger.error("The following colors are not defined, but on the province map:");
            extraMapColors.forEach(color -> System.out.println("\t" + color));
        }
    }

    public static void main(String[] args) throws Exception {
        DefinitionsValidator validator = new DefinitionsValidator();

        validator.setModDirectory("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA");

        validator.run();
    }
}
