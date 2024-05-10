package historyfile.province.continents;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RegionToContinent {
    ANDUIN_VALE(Continent.EUROPE),
    ENEDWAITH(Continent.NORTH_AMERICA),
    EREBOR(Continent.EUROPE),
    ERIADOR(Continent.NORTH_AMERICA),
    FORODWAITH(Continent.EUROPE),
    GONDOR(Continent.SOUTH_AMERICA),
    GREY_MOUNTAINS(Continent.EUROPE),
    HARAD(Continent.OCEANIA),
    KHAND(Continent.OCEANIA),
    MIRKWOOD(Continent.EUROPE),
    MISTY_MOUNTAINS(Continent.NORTH_AMERICA),
    MORDOR(Continent.AFRICA),
    RHOVANION(Continent.EUROPE),
    RHUN(Continent.ASIA),
    ROHAN(Continent.SOUTH_AMERICA);

    private final Continent continent;
    private static final Map<String, Continent> regionToContinentCache = new HashMap<>();

    RegionToContinent(Continent continent) {
        this.continent = continent;
    }

    public static Continent getRegionToContinent(String region) {
        if (regionToContinentCache.containsKey(region)) {
            return regionToContinentCache.get(region);
        }

        RegionToContinent cultureToRace = Arrays.stream(RegionToContinent.values()).filter(potentialMatch -> StringUtils.equalsIgnoreCase(region, potentialMatch.name())).findFirst().orElse(null);
        Continent continent = null;

        if (cultureToRace != null) {
            continent = cultureToRace.continent;
        }

        regionToContinentCache.put(region, continent);
        return continent;
    }

    public enum Continent {
        NORTH_AMERICA, // Eriador
        SOUTH_AMERICA, // Gondor and Rohan
        EUROPE, // Rhovanion
        AFRICA, // Mordor
        ASIA, // Rhun
        OCEANIA // Harad
    }
}
