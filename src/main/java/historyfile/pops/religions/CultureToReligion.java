package historyfile.pops.religions;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CultureToReligion {
    BANKER("men"),
    DUNEDAIN("men"),
    GONDORIAN("men"),
    AMROTHIAN("men"),
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
    AIVATHIUDA("men"),
    ANTHAR("men"),
    GADRAUGHT("men"),
    BARDING("men"),
    GRAMMAS("men"),
    RIVERMEN("men"),
    ADHALLING("men"),
    WOODSMEN("men"),
    BEORNING("men"),
    ANDUINMEN("men"),
    DRUEDAIN("men"),
    DUNNISH("men"),
    DUNIR("men"),
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
    WHITE_MOUNTAIN_GOBLIN("orc"),
    CAVE_TROLLS("troll"),
    HILL_TROLLS("troll"),
    MOUNTAIN_TROLLS("troll"),
    SNOW_TROLLS("troll"),
    STONE_TROLLS("troll"),
    OLOG_HAI("troll"),
    ENT("ent"),
    HUORN("ent"),
    GREAT_EAGLE("eagle"),
    RAVEN("eagle"),
    GREAT_SPIDER("spider"),
    WIGHT("undead"),
    OATHBREAKER("undead"),
    DOURHAND("dwarven"),
    BLUE_MOUNTAIN_GOBLIN("orc"),
    SIREDAIN("men"),
    MARSHMEN("men"),
    ISENMEN("men"),
    ARTHEDANI("men"),
    CARDOLANDRIM("men"),
    HAERANEDAIN("men"),
    SHORNBEARD("dwarven"),
    HOLLIN_FOLK("men"),
    SAERLANNER("men"),
    GRAM_GOBLIN("orc"),
    STOOR("hobbit"),
    HARFOOT("hobbit"),
    FALLOHIDE("hobbit"),
    BLACK_HUORN("ent"),
    BEIABARNAE("men"),
    MELBURGI("men"),
    HERIMUNDI("men");

    private final String race;
    private static final Map<String, String> cultureToRaceCache = new HashMap<>();

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
