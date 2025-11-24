package historyfile.province;

import historyfile.province.continents.RegionToContinent;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
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
    protected int barracksLevel;
    protected int scriptoriumLevel;
    protected int marketSquareLevel;
    protected int publicHouseLevel;
    protected List<Factory> factories;
    protected boolean slave;
    protected int colonial;
    protected List<PartyLoyalty> partyLoyalties;
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

    public void setContinent(String continent, boolean convertToStandard) {

        if (convertToStandard) {
            RegionToContinent.Continent convertedContinent = RegionToContinent.getRegionToContinent(continent);

            if (convertedContinent == null) {
                Logger.error("Unable to find mapping for continent: " + continent);
                return;
            }

            setContinent(StringUtils.toRootLowerCase(convertedContinent.name()));

            return;
        }

        setContinent(continent);

    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }

    public void setFactories(String factories) {
        String[] factoryArray = StringUtils.split(factories, " ");
        this.factories = new ArrayList<>();

        int count = 1;

        for (String factory : factoryArray) {
            if (StringUtils.isNumeric(factory)) {
                count = Integer.parseInt(factory);
                continue;
            }

            addFactory(factory, count);
            count = 1;
        }
    }

    public void addFactory(String name, int count) {
        addFactory(new Factory(name, count));
    }

    public void addFactory(Factory factory) {
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

    public int getBarracksLevel() {
        return barracksLevel;
    }

    public void setBarracksLevel(int barracksLevel) {
        this.barracksLevel = barracksLevel;
    }

    public void setBarracksLevel(String barracksLevel) {
        try {
            setBarracksLevel(Integer.parseInt(barracksLevel));
        } catch (NumberFormatException e) {
            setBarracksLevel(0);
        }
    }

    public int getScriptoriumLevel() {
        return scriptoriumLevel;
    }

    public void setScriptoriumLevel(int scriptoriumLevel) {
        this.scriptoriumLevel = scriptoriumLevel;
    }

    public void setScriptoriumLevel(String scriptoriumLevel) {
        try {
            setScriptoriumLevel(Integer.parseInt(scriptoriumLevel));
        } catch (NumberFormatException e) {
            setScriptoriumLevel(0);
        }
    }

    public int getMarketSquareLevel() {
        return marketSquareLevel;
    }

    public void setMarketSquareLevel(int marketSquareLevel) {
        this.marketSquareLevel = marketSquareLevel;
    }

    public void setMarketSquareLevel(String marketSquareLevel) {
        try {
            setMarketSquareLevel(Integer.parseInt(marketSquareLevel));
        } catch (NumberFormatException e) {
            setMarketSquareLevel(0);
        }
    }

    public int getPublicHouseLevel() {
        return publicHouseLevel;
    }

    public void setPublicHouseLevel(int publicHouseLevel) {
        this.publicHouseLevel = publicHouseLevel;
    }

    public void setPublicHouseLevel(String publicHouseLevel) {
        try {
            setPublicHouseLevel(Integer.parseInt(publicHouseLevel));
        } catch (NumberFormatException e) {
            setPublicHouseLevel(0);
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

    public List<PartyLoyalty> getPartyLoyalties() {
        return partyLoyalties;
    }

    public void setPartyLoyalties(List<PartyLoyalty> partyLoyalties) {
        this.partyLoyalties = partyLoyalties;
    }

    public void setPartyLoyalties(String partyLoyalties) {
        String[] loyaltyArray = StringUtils.split(partyLoyalties, " ");
        this.partyLoyalties = new ArrayList<>();

        double count = 1;

        for (String partyLoyalty : loyaltyArray) {
            if (StringUtils.isNumeric(StringUtils.remove(partyLoyalty, "."))) {
                count = Double.parseDouble(partyLoyalty);
                continue;
            }

            addPartyLoyalty(partyLoyalty, count);
            count = 1;
        }
    }

    public void addPartyLoyalty(String ideologyName, double total) {
        addPartyLoyalty(new PartyLoyalty(ideologyName, total));
    }

    public void addPartyLoyalty(PartyLoyalty partyLoyalty) {
        if (this.partyLoyalties == null) {
            this.partyLoyalties = new ArrayList<>();
        }
        this.partyLoyalties.add(partyLoyalty);
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

        if (getBarracksLevel() > 0) {
            builder.append("barracks = ").append(getBarracksLevel()).append("\n");
        }

        if (getScriptoriumLevel() > 0) {
            builder.append("scriptorium = ").append(getScriptoriumLevel()).append("\n");
        }

        if (getMarketSquareLevel() > 0) {
            builder.append("market_square = ").append(getMarketSquareLevel()).append("\n");
        }

        if (getPublicHouseLevel() > 0) {
            builder.append("public_house = ").append(getPublicHouseLevel()).append("\n");
        }

        if (isSlave()) {
            builder.append("is_slave = yes\n");
        }

        if (getColonial() > 0) {
            builder.append("colonial = ").append(getColonial()).append("\n");
        }

        for (Factory factory : getFactories()) {
            builder.append("state_building = {\n");
            builder.append("\tlevel = ").append(factory.getLevel()).append("\n");
            builder.append("\tbuilding = ").append(factory.getType()).append("\n");
            builder.append("\tupgrade = yes\n");
            builder.append("}\n");
        }

        for (PartyLoyalty partyLoyalty : getPartyLoyalties()) {
            builder.append("\n");
            builder.append("party_loyalty = {\n");
            builder.append("\tideology = ").append(partyLoyalty.getIdeology()).append("\n");
            builder.append("\tloyalty_value = ").append(partyLoyalty.getValue()).append("\n");
            builder.append("}\n");
        }

        return builder.toString();
    }
}
