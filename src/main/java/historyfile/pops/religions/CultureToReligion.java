package historyfile.pops.religions;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CultureToReligion {
    DUNEDAIN("men"),
    GONDORIAN("men"),
    AMROTHIAN("men"),
    ARNORIAN("men"),
    UMBARRIM("men"),
    HARNENDAN("men"),
    HARUZANI("men"),
    PEZARSANI("men"),
    LURMSAKUNI("men"),
    ARYSIAN("men"),
    KINGSMEN("men"),
    BLACK_NUMENOREAN("men"),
    VARIAG("men"),
    NURNIAG("men"),
    CHELKIAG("men"),
    SAGATH("men"),
    SEKEL("men"),
    URGATH("men"),
    KUGA("men"),
    LOGATH("men"),
    DORWINRIM("men"),
    EHWATHRIM("men"),
    BARDING("men"),
    WOODSMEN("men"),
    BEORNING("men"),
    ANDUINMEN("men"),
    DRUEDAIN("men"),
    WILDMEN("men"),
    DUNNISH("men"),
    ENEDWAITHRIM("men"),
    ROHIRRIM("men"),
    ANGMARRIM("men"),
    HILLMEN("men"),
    LOSSOTH("men"),
    BREELANDER("men"),
    NOLDOR("elven"),
    FALATHRIM("elven"),
    SINDAR("elven"),
    SILVAN("elven"),
    GALADHRIM("elven"),
    AVARI("elven"),
    LONGBEARD("dwarven"),
    FIREBEARD("dwarven"),
    BROADBEAM("dwarven"),
    IRONFIST("dwarven"),
    URUK_HAI("orc"),
    MORDOR_ORC("orc"),
    MORDOR_URUK("orc"),
    GULDURRIM("orc"),
    MORIAN("orc"),
    GOBLIN_TOWNER("orc"),
    GUNDABAD_ORC("orc"),
    SNOW_ORC("orc"),
    ANGMAR_ORC("orc"),
    WHITE_MOUNTAIN_GOBLIN("orc"),
    SHIRE_HOBBIT("hobbit"),
    BREELAND_HOBBIT("hobbit"),
    STOOR_HOBBIT("hobbit"),
    CAVE_TROLLS("troll"),
    HILL_TROLLS("troll"),
    MOUNTAIN_TROLLS("troll"),
    SNOW_TROLLS("troll"),
    STONE_TROLLS("troll"),
    OLOG_HAI("troll"),
    ENT("ent"),
    HUORN("ent"),
    GREAT_EAGLE("eagle"),
    GREAT_SPIDER("spider"),
    WIGHT("undead");

    private String race;
    private static Map<String, String> cultureToRaceCache = new HashMap<>();

    CultureToReligion(String race) {
        this.race = race;
    }

    public static String getRaceForCulture(String culture) {
        if (cultureToRaceCache.containsKey(culture)) {
            return cultureToRaceCache.get(culture);
        }

        CultureToReligion cultureToRace = Arrays.stream(CultureToReligion.values()).filter(potentialMatch -> StringUtils.equalsIgnoreCase(culture, potentialMatch.name())).findFirst().orElse(null);
        String race = null;

        if (cultureToRace != null) {
            race = cultureToRace.race;
        }

        cultureToRaceCache.put(culture, race);
        return race;
    }
}
