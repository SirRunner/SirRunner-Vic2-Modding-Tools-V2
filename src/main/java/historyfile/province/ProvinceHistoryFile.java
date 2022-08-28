package historyfile.province;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.util.*;

public class ProvinceHistoryFile extends BaseProvinceHistoryFile {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String OWNER = "owner";
    public static final String CONTROLLER = "controller";
    public static final String CORES = "cores";
    public static final String TRADE_GOODS = "trade_goods";
    public static final String LIFE_RATING = "life_rating";
    public static final String TERRAIN = "terrain";
    public static final String CONTINENT = "continent";
    public static final String CLIMATE = "climate";
    public static final String RAILROAD = "railroad";
    public static final String FORT = "fort";
    public static final String NAVAL_BASE = "naval_base";
    public static final String GUILDS = "guilds";
    public static final String IS_SLAVE = "is_slave";
    public static final String COLONIAL = "colonial";
    public static final String COMMENT = "comment";

    public static final String DEFAULT = "-";

    public static Set<String> HANDLED_COLUMNS = new HashSet<>(Arrays.asList(ID, NAME, OWNER, CONTROLLER, CORES, TRADE_GOODS, LIFE_RATING, TERRAIN, CONTINENT, CLIMATE, RAILROAD, FORT, NAVAL_BASE, GUILDS, IS_SLAVE, COLONIAL, COMMENT));

    public ProvinceHistoryFile() {
        this.cores = new ArrayList<>();
        this.factories = new ArrayList<>();
    }

    public ProvinceHistoryFile(Map<String, String> line) {
        this();

        for (String key: line.keySet()) {
            if (!HANDLED_COLUMNS.contains(key)) {
                Logger.info("File contains unhandled key: " + key);
            } else {
                String value = StringUtils.trim(line.get(key));
                if (!StringUtils.equalsIgnoreCase(DEFAULT, value) && !StringUtils.isEmpty(value)) {
                    setByName(key, StringUtils.trim(value));
                }
            }
        }
    }

    protected void setByName(String name, String value) {
        switch (name) {
            case ID -> setId(value);
            case NAME -> setName(value);
            case OWNER -> setOwner(value);
            case CONTROLLER -> setController(value);
            case CORES -> setCores(value);
            case TRADE_GOODS -> setTradeGoods(value);
            case LIFE_RATING -> setLifeRating(value);
            case TERRAIN -> setTerrain(value);
            case CONTINENT -> setContinent(value);
            case CLIMATE -> setClimate(value);
            case RAILROAD -> setRailroadLevel(value);
            case FORT -> setFortLevel(value);
            case NAVAL_BASE -> setNavalBaseLevel(value);
            case GUILDS -> setFactories(value);
            case IS_SLAVE -> setSlave(value);
            case COLONIAL -> setColonial(value);
            case COMMENT -> setComment(value);
        }
    }
}
