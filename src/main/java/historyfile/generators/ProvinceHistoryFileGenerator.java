package historyfile.generators;

import decisions.generators.ClaimsCBViewerGenerator;
import events.generators.ClaimsCBGenerator;
import historyfile.province.BaseProvinceHistoryFile;
import historyfile.province.ProvinceHistoryFile;
import historyfile.reader.ProvinceHistoryFileCSVReader;
import historyfile.writer.ProvinceHistoryFileWriter;
import map.definitions.MapDefinitions;
import map.reader.MapDefinitionsReader;
import map.reader.RegionReader;
import map.regions.Region;
import map.writer.ClimatesWriter;
import map.writer.ContinentsWriter;
import map.writer.MapLocalisationWriter;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.Utils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ProvinceHistoryFileGenerator {
    protected String provinceHistoryFolder;
    protected String mapLocalisationFilename;
    protected String csvFilename;
    protected String definitionsFilename;
    protected String regionsFilename;
    protected String continentsFilename;
    protected String climatesFilename;
    protected String claimsEventFilename;
    protected String claimsDecisionFilename;

    public void setProvinceHistoryFolder(String provinceHistoryFolder) {
        this.provinceHistoryFolder = provinceHistoryFolder;
    }

    public void setMapLocalisationFilename(String mapLocalisationFilename) {
        this.mapLocalisationFilename = mapLocalisationFilename;
    }

    public void setCsvFilename(String csvFilename) {
        this.csvFilename = csvFilename;
    }

    public void setDefinitionsFilename(String filename) {
        this.definitionsFilename = filename;
    }

    public void setRegionsFilename(String filename) {
        this.regionsFilename = filename;
    }

    public void setContinentsFilename(String continentsFilename) {
        this.continentsFilename = continentsFilename;
    }

    public void setClimatesFilename(String climatesFilename) {
        this.climatesFilename = climatesFilename;
    }

    public void setClaimsEventFilename(String claimsEventFilename) {
        this.claimsEventFilename = claimsEventFilename;
    }

    public void setClaimsDecisionFilename(String claimsDecisionFilename) {
        this.claimsDecisionFilename = claimsDecisionFilename;
    }

    protected void compareDefinitionsAndCSVEntryNames(MapDefinitions definitionsEntry, ProvinceHistoryFile historyFile, int id) {
        boolean missingEntry = false;

        if (definitionsEntry == null) {
            Logger.error("Definitions entry for id " + id + " is missing");
            missingEntry = true;
        }

        if (historyFile == null) {
            Logger.error("History file entry for id " + id + " is missing");
            missingEntry = true;
        }

        if (missingEntry) {
            return;
        }

        if (!StringUtils.equals(definitionsEntry.getName(), historyFile.getName())) {
            Logger.error("Province name is different in the definitions file and history file for id " + id +
                    "\n\tDefinitions File: " + definitionsEntry.getName() + "\n\tHistory File: " + historyFile.getName());
        }
    }

    protected void mapProvinceIdsToRegion(Map<Integer, Region> provinceIdToRegion, Map<String, Region> regions) {
        for (String code : regions.keySet()) {
            Region region = regions.get(code);

            for (int provinceId : region.getProvinces()) {
                if (provinceIdToRegion.containsKey(provinceId)) {
                    Logger.error("Province id " + provinceId + " is defined in multiple regions");
                } else {
                    provinceIdToRegion.put(provinceId, region);
                }
            }
        }
    }

    protected void run() throws Exception {
        // TODO: Handle verifying that the other provinces in mapDefinitionEntrys are indeed sea provinces (default.map)
        /* Loads in the province history csv, definitions and region files */
        ProvinceHistoryFileCSVReader reader = new ProvinceHistoryFileCSVReader(csvFilename);
        MapDefinitionsReader definitionsReader = new MapDefinitionsReader(definitionsFilename);
        RegionReader regionReader = new RegionReader(regionsFilename);

        Map<Integer, ProvinceHistoryFile> provinceHistoryFiles = reader.readFile();
        Logger.info("Read " + provinceHistoryFiles.size() + " province history files");

        Map<Integer, MapDefinitions> mapDefinitionsEntrys = definitionsReader.readFile();
        Logger.info("Read " + mapDefinitionsEntrys.size() + " map definitions");

        Map<String, Region> codeToRegions = regionReader.readFile();
        Logger.info("Read " + codeToRegions.size() + " regions");

        /* Creates the provinceIdToRegion map and ensure that each province is only in one region */
        Map<Integer, Region> provinceIdToRegion = new HashMap<>();
        mapProvinceIdsToRegion(provinceIdToRegion, codeToRegions);

        /* Deletes all old province history files */
        File outputFolder = new File(provinceHistoryFolder);
        Utils.clearFolder(outputFolder);

        /* Compares the names of provinces in the definitions file and the history csv and alerts of any differences,
           or if either of the entries is missing */
        for (int i : provinceHistoryFiles.keySet()) {
            compareDefinitionsAndCSVEntryNames(mapDefinitionsEntrys.get(i), provinceHistoryFiles.get(i), i);

            if (!provinceIdToRegion.containsKey(i)) {
                Logger.error("Province id " + i + " is not in a region");
            }

            ProvinceHistoryFileWriter writer = new ProvinceHistoryFileWriter(provinceHistoryFolder, provinceHistoryFiles.get(i));
            writer.writeFile();
        }

        /* Orders the mapDefinitions and regions by province id and writes the localisation file */
        List<MapDefinitions> mapDefinitions = new ArrayList<>(mapDefinitionsEntrys.values());
        mapDefinitions.sort(Comparator.comparing(MapDefinitions::getId));

        List<Region> regions = new ArrayList<>(codeToRegions.values());
        regions.sort(Comparator.comparing(Region::getSortingId));

        MapLocalisationWriter mapLocalisationWriter = new MapLocalisationWriter(mapLocalisationFilename, mapDefinitions, regions);
        mapLocalisationWriter.writeFile();

        /* Gets just the province history file information in id order */
        List<ProvinceHistoryFile> orderedProvinceHistoryFiles = provinceHistoryFiles.keySet().stream().map(provinceHistoryFiles::get).collect(Collectors.toList());
        orderedProvinceHistoryFiles.sort(Comparator.comparingInt(BaseProvinceHistoryFile::getId));

        ContinentsWriter continentsWriter = new ContinentsWriter(continentsFilename, orderedProvinceHistoryFiles);
        continentsWriter.writeFile();

        ClimatesWriter climatesWriter = new ClimatesWriter(climatesFilename, orderedProvinceHistoryFiles);
        climatesWriter.writeFile();

        ClaimsCBGenerator claimsCBGenerator = new ClaimsCBGenerator(claimsEventFilename, "CB and Cores", 2900, 2999, regions);
        claimsCBGenerator.generate();

        ClaimsCBViewerGenerator claimsCBViewerGenerator = new ClaimsCBViewerGenerator(claimsDecisionFilename, "Quality of Life Information", regions);
        claimsCBViewerGenerator.generate();
    }

    public static void main(String[] args) {
        try {
            ProvinceHistoryFileGenerator generator = new ProvinceHistoryFileGenerator();

            generator.setProvinceHistoryFolder("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/history/provinces/middle earth");
            generator.setMapLocalisationFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/localisation/map.csv");
            generator.setCsvFilename(System.getProperty("user.home") + "/Downloads/Provinces - Provinces.csv");
            generator.setDefinitionsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/definition.csv");
            generator.setRegionsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/region.txt");
            generator.setContinentsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/continent.txt");
            generator.setClimatesFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/climate.txt");
            generator.setClaimsEventFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/events/Claim CBs.txt");
            generator.setClaimsDecisionFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/decisions/_QOL Information.txt");

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
