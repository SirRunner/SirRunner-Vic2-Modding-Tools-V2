package test;

import map.definitions.MapDefinitions;
import map.reader.MapDefinitionsReader;
import map.reader.RegionReader;
import map.regions.Region;
import utils.Logger;

import java.util.List;
import java.util.Map;

public class Testing {
    public static void main(String[] args) throws Exception {
        MapDefinitionsReader definitionsReader = new MapDefinitionsReader("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/definition.csv");
        RegionReader regionReader = new RegionReader("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/region.txt");

        Map<Integer, MapDefinitions> mapDefinitionsEntrys = definitionsReader.readFile();
        Logger.info("Read " + mapDefinitionsEntrys.size() + " map definitions");

        Map<String, Region> codeToRegions = regionReader.readFile();
        Logger.info("Read " + codeToRegions.size() + " regions");

        List<Region> regions = codeToRegions.values().stream().filter(region -> region.getSortingId() != 0).sorted((o1, o2) -> {
            if (o1.getSortingId() > o2.getSortingId()) {
                return 1;
            } else if (o2.getSortingId() > o1.getSortingId()) {
                return -1;
            }

            return 0;
        }).toList();

        for (Region region : regions) {
            System.out.println("add_claim_" + region.getCode() + "_loc = { } # §WWe will gain claims over §!§Y" + region.getName() + "§!");
            System.out.println("remove_claim_" + region.getCode() + "_loc = { } # §WWe will lose claims over §!§Y" + region.getName() + "§!");
            System.out.println("claim_status_owns_" + region.getCode() + "_loc = { } # §Y" + region.getName() + "§!: §GOwned§!");
            System.out.println("claim_status_doesnt_own_" + region.getCode() + "_loc = { } # §Y" + region.getName() + "§!: §RUnowned§!");
        }
    }
}
