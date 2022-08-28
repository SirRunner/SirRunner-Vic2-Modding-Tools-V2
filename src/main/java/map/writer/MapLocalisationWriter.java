package map.writer;

import map.definitions.MapDefinitions;
import map.regions.Region;
import org.apache.commons.lang3.StringUtils;
import utils.ParadoxParsingUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MapLocalisationWriter {
    protected String filename;
    protected List<MapDefinitions> mapDefinitions;
    protected List<Region> regions;

    public MapLocalisationWriter(String filename, List<MapDefinitions> mapDefinitions, List<Region> regions) {
        this.filename = filename;
        this.mapDefinitions = mapDefinitions;
        this.regions = regions;
    }

    public String getProvincesHeader() {
        return """
                #####################;x
                ##### PROVINCES #####;x {
                #####################;x
                """;
    }

    public String getProvinceLocalisation(MapDefinitions definition) {
        return StringUtils.joinWith(ParadoxParsingUtils.LOCALISATION_SPLITTER, "PROV" + definition.getId(), definition.getName(), ParadoxParsingUtils.LOCALISATION_ENDLINE);
    }

    public String getRegionHeader() {
        return """
                ###################;x }
                ##### REGIONS #####;x {
                ###################;x
                """;
    }

    public String getRegionLocalisation(Region region) {
        return StringUtils.joinWith(ParadoxParsingUtils.LOCALISATION_SPLITTER, region.getCode(), region.getName(), ParadoxParsingUtils.LOCALISATION_ENDLINE);
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "Cp1252"))) {
            writer.write(getProvincesHeader());

            for (MapDefinitions definitions : mapDefinitions) {
                writer.write(getProvinceLocalisation(definitions) + "\n");
            }

            writer.write(getRegionHeader());

            for (Region region : regions) {
                writer.write(getRegionLocalisation(region) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilename() {
        return filename;
    }
}
