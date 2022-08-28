package historyfile.province;

import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseProvinceHistoryFile {
    protected int id;
    protected String name;
    // TODO: Make TAG/Country object
    protected String owner;
    protected String controller;
    protected List<String> cores;
    // TODO: Make rgo object
    protected String tradeGoods;
    protected int lifeRating;
    // TODO: Make terrain object
    protected String terrain;
    // TODO: Make continent object;
    protected String continent;
    // TODO: Make climate object;
    protected String climate;
    protected int railroadLevel;
    protected int fortLevel;
    protected int navalBaseLevel;
    // TODO: Make factory object
    protected List<String> factories;
    protected boolean slave;
    protected int colonial;
    protected String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String value) {
        setId(Integer.parseInt(value));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public List<String> getCores() {
        return cores;
    }

    public void setCores(List<String> cores) {
        this.cores = cores;
    }

    public void setCores(String cores) {
        String[] coreArray = StringUtils.split(cores, " ");
        this.cores = new ArrayList<>();

        for (String core : coreArray) {
            addCore(core);
        }
    }

    public void addCore(String core) {
        if (this.cores == null) {
            this.cores = new ArrayList<>();
        }
        this.cores.add(core);
    }

    public String getTradeGoods() {
        return tradeGoods;
    }

    public void setTradeGoods(String tradeGoods) {
        this.tradeGoods = tradeGoods;
    }

    public int getLifeRating() {
        return lifeRating;
    }

    public void setLifeRating(int lifeRating) {
        this.lifeRating = lifeRating;
    }

    public void setLifeRating(String lifeRating) {
        setLifeRating(Integer.parseInt(lifeRating));
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public List<String> getFactories() {
        return factories;
    }

    public void setFactories(List<String> factories) {
        this.factories = factories;
    }

    public void setFactories(String factories) {
        String[] factoryArray = StringUtils.split(factories, " ");
        this.factories = new ArrayList<>();

        for (String factory : factoryArray) {
            addFactory(factory);
        }
    }

    public void addFactory(String factory) {
        if (this.factories == null) {
            this.factories = new ArrayList<>();
        }
        this.factories.add(factory);
    }

    public int getRailroadLevel() {
        return railroadLevel;
    }

    public void setRailroadLevel(int railroadLevel) {
        this.railroadLevel = railroadLevel;
    }

    public void setRailroadLevel(String railroadLevel) {
        try {
            setRailroadLevel(Integer.parseInt(railroadLevel));
        } catch (NumberFormatException e) {
            setRailroadLevel(0);
        }
    }

    public int getFortLevel() {
        return fortLevel;
    }

    public void setFortLevel(int fortLevel) {
        this.fortLevel = fortLevel;
    }

    public void setFortLevel(String fortLevel) {
        try {
            setFortLevel(Integer.parseInt(fortLevel));
        } catch (NumberFormatException e) {
            setFortLevel(0);
        }
    }

    public int getNavalBaseLevel() {
        return navalBaseLevel;
    }

    public void setNavalBaseLevel(int navalBaseLevel) {
        this.navalBaseLevel = navalBaseLevel;
    }

    public void setNavalBaseLevel(String navalBaseLevel) {
        try {
            setNavalBaseLevel(Integer.parseInt(navalBaseLevel));
        } catch (NumberFormatException e) {
            setNavalBaseLevel(0);
        }
    }

    public boolean isSlave() {
        return slave;
    }

    public void setSlave(boolean slave) {
        this.slave = slave;
    }

    public void setSlave(String slave) {
        setSlave(Utils.isTrue(slave));
    }

    public int getColonial() {
        return colonial;
    }

    public void setColonial(int colonial) {
        this.colonial = colonial;
    }

    public void setColonial(String colonial) {
        try {
            setColonial(Integer.parseInt(colonial));
        } catch (NumberFormatException e) {
            setColonial(0);
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (!StringUtils.isEmpty(getComment())) {
            builder.append("# ").append(getComment()).append("\n");
        }

        if (!StringUtils.isEmpty(getOwner())) {
            builder.append("owner = ").append(getOwner()).append("\n");
        }

        if (!StringUtils.isEmpty(getController())) {
            builder.append("controller = ").append(getController()).append("\n");
        }

        for (String core : getCores()) {
            builder.append("add_core = ").append(core).append("\n");
        }

        if (!StringUtils.isEmpty(getTradeGoods())) {
            builder.append("trade_goods = ").append(getTradeGoods()).append("\n");
        }

        if (getLifeRating() > 0) {
            builder.append("life_rating = ").append(getLifeRating()).append("\n");
        }

        if (!StringUtils.isEmpty(getTerrain())) {
            builder.append("terrain = ").append(getTerrain()).append("\n");
        }

        if (getFortLevel() > 0) {
            builder.append("fort = ").append(getFortLevel()).append("\n");
        }

        if (getNavalBaseLevel() > 0) {
            builder.append("naval_base = ").append(getNavalBaseLevel()).append("\n");
        }

        if (getRailroadLevel() > 0) {
            builder.append("railroad = ").append(getRailroadLevel()).append("\n");
        }

        if (isSlave()) {
            builder.append("is_slave = yes\n");
        }

        if (getColonial() > 0) {
            builder.append("colonial = ").append(getColonial()).append("\n");
        }

        for (String factory : getFactories()) {
            builder.append("state_building = {\n");
            builder.append("\tlevel = 1\n");
            builder.append("\tbuilding = ").append(factory).append("\n");
            builder.append("\tupgrade = yes\n");
            builder.append("}\n");
        }

        return builder.toString();
    }
}
