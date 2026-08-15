package savefile;

import savefile.objects.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class V2SaveFile {
    private LocalDate date;
    private String playerTag;
    private int governmentId;
    private boolean automateTrade;
    private int automateSliders;
    private int rebel;
    private int unit;
    private int state;
    private Flags globalFlags;
    private GamePlaySettings gamePlaySettings;
    private LocalDate startdate;
    private int startPopIndex;
    private WorldMarket worldMarket;
    private boolean greatWarsEnabled;
    private boolean worldWarsEnabled;
    private GoodsPool overseasPenalty;
    private GoodsPool unitCost;
    private DoubleList budgetBalance;
    private PlayerMonthlyPopGrowth playerMonthlyPopGrowth;
    private String playerMonthlyPopGrowthTag;
    private LocalDate playerMonthlyPopGrowthDate;
    private List<IdeologyEnabledDate> ideologyEnabledDates;
    private ID id; // Absolutely no idea
    private FiredEvents firedEvents;
    private List<ProvinceSaveState> provinceStates;
    private List<CountrySaveState> countryStates;
    private List<RebelFaction> rebelFactions;
    private Diplomacy activeRelationships;
    private Combat activeCombats;
    private List<War> activeWars;
    private List<War> previousWars;
    private IntegerList inventions; // Absolutely no idea
    private IntegerList greatNations;
    private IntegerList outliner;
    private NewsCollector newsCollector;
    private CrisisManager crisisManager;
    private List<RegionSaveState> regionStates;

    public V2SaveFile() {
        init();
    }

    public void init() {
        if (globalFlags == null) {
            globalFlags = new Flags();
        }
        if (gamePlaySettings == null) {
            gamePlaySettings = new GamePlaySettings();
        }
        if (worldMarket == null) {
            worldMarket = new WorldMarket();
        }
        if (overseasPenalty == null) {
            overseasPenalty = new GoodsPool();
        }
        if (unitCost == null) {
            unitCost = new GoodsPool();
        }
        if (budgetBalance == null) {
            budgetBalance = new DoubleList();
        }
        if (playerMonthlyPopGrowth == null) {
            playerMonthlyPopGrowth = new PlayerMonthlyPopGrowth();
        }
        if (ideologyEnabledDates == null) {
            ideologyEnabledDates = new ArrayList<>();
        }
        if (id == null) {
            id = new ID();
        }
        if (firedEvents == null) {
            firedEvents = new FiredEvents();
        }
        if (provinceStates == null) {
            provinceStates = new ArrayList<>();
        }
        if (countryStates == null) {
            countryStates = new ArrayList<>();
        }
        if (rebelFactions == null) {
            rebelFactions = new ArrayList<>();
        }
        if (activeRelationships == null) {
            activeRelationships = new Diplomacy();
        }
        if (activeCombats == null) {
            activeCombats = new Combat();
        }
        if (activeWars == null) {
            activeWars = new ArrayList<>();
        }
        if (previousWars == null) {
            previousWars = new ArrayList<>();
        }
        if (inventions == null) {
            inventions = new IntegerList();
        }
        if (greatNations == null) {
            greatNations = new IntegerList();
        }
        if (outliner == null) {
            outliner = new IntegerList();
        }
        if (newsCollector == null) {
            newsCollector = new NewsCollector();
        }
        if (crisisManager == null) {
            crisisManager = new CrisisManager();
        }
        if (regionStates == null) {
            regionStates = new ArrayList<>();
        }
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlayerTag() {
        return playerTag;
    }

    public void setPlayerTag(String playerTag) {
        this.playerTag = playerTag;
    }

    public int getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(int governmentId) {
        this.governmentId = governmentId;
    }

    public boolean isAutomateTrade() {
        return automateTrade;
    }

    public void setAutomateTrade(boolean automateTrade) {
        this.automateTrade = automateTrade;
    }

    public int getAutomateSliders() {
        return automateSliders;
    }

    public void setAutomateSliders(int automateSliders) {
        this.automateSliders = automateSliders;
    }

    public int getRebel() {
        return rebel;
    }

    public void setRebel(int rebel) {
        this.rebel = rebel;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Flags getGlobalFlags() {
        return globalFlags;
    }

    public void setGlobalFlags(Flags globalFlags) {
        this.globalFlags = globalFlags;
    }

    public GamePlaySettings getGamePlaySettings() {
        return gamePlaySettings;
    }

    public void setGamePlaySettings(GamePlaySettings gamePlaySettings) {
        this.gamePlaySettings = gamePlaySettings;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public int getStartPopIndex() {
        return startPopIndex;
    }

    public void setStartPopIndex(int startPopIndex) {
        this.startPopIndex = startPopIndex;
    }

    public WorldMarket getWorldMarket() {
        return worldMarket;
    }

    public void setWorldMarket(WorldMarket worldMarket) {
        this.worldMarket = worldMarket;
    }

    public boolean isGreatWarsEnabled() {
        return greatWarsEnabled;
    }

    public void setGreatWarsEnabled(boolean greatWarsEnabled) {
        this.greatWarsEnabled = greatWarsEnabled;
    }

    public boolean isWorldWarsEnabled() {
        return worldWarsEnabled;
    }

    public void setWorldWarsEnabled(boolean worldWarsEnabled) {
        this.worldWarsEnabled = worldWarsEnabled;
    }

    public GoodsPool getOverseasPenalty() {
        return overseasPenalty;
    }

    public void setOverseasPenalty(GoodsPool overseasPenalty) {
        this.overseasPenalty = overseasPenalty;
    }

    public GoodsPool getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(GoodsPool unitCost) {
        this.unitCost = unitCost;
    }

    public DoubleList getBudgetBalance() {
        return budgetBalance;
    }

    public void setBudgetBalance(DoubleList budgetBalance) {
        this.budgetBalance = budgetBalance;
    }

    public PlayerMonthlyPopGrowth getPlayerMonthlyPopGrowth() {
        return playerMonthlyPopGrowth;
    }

    public void setPlayerMonthlyPopGrowth(PlayerMonthlyPopGrowth playerMonthlyPopGrowth) {
        this.playerMonthlyPopGrowth = playerMonthlyPopGrowth;
    }

    public String getPlayerMonthlyPopGrowthTag() {
        return playerMonthlyPopGrowthTag;
    }

    public void setPlayerMonthlyPopGrowthTag(String playerMonthlyPopGrowthTag) {
        this.playerMonthlyPopGrowthTag = playerMonthlyPopGrowthTag;
    }

    public LocalDate getPlayerMonthlyPopGrowthDate() {
        return playerMonthlyPopGrowthDate;
    }

    public void setPlayerMonthlyPopGrowthDate(LocalDate playerMonthlyPopGrowthDate) {
        this.playerMonthlyPopGrowthDate = playerMonthlyPopGrowthDate;
    }

    public List<IdeologyEnabledDate> getIdeologyEnabledDates() {
        return ideologyEnabledDates;
    }

    public void setIdeologyEnabledDates(List<IdeologyEnabledDate> ideologyEnabledDates) {
        this.ideologyEnabledDates = ideologyEnabledDates;
    }

    public void addIdeologyEnabledDate(IdeologyEnabledDate ideologyEnabledDate) {
        this.ideologyEnabledDates.add(ideologyEnabledDate);
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public FiredEvents getFiredEvents() {
        return firedEvents;
    }

    public void setFiredEvents(FiredEvents firedEvents) {
        this.firedEvents = firedEvents;
    }

    public List<ProvinceSaveState> getProvinceStates() {
        return provinceStates;
    }

    public void setProvinceStates(List<ProvinceSaveState> provinceStates) {
        this.provinceStates = provinceStates;
    }

    public void addProvinceStatue(ProvinceSaveState provinceSaveState) {
        this.provinceStates.add(provinceSaveState);
    }

    public List<CountrySaveState> getCountryStates() {
        return countryStates;
    }

    public void setCountryStates(List<CountrySaveState> countryStates) {
        this.countryStates = countryStates;
    }

    public void addCountryState(CountrySaveState countrySaveState) {
        this.countryStates.add(countrySaveState);
    }

    public List<RebelFaction> getRebelFactions() {
        return rebelFactions;
    }

    public void setRebelFactions(List<RebelFaction> rebelFactions) {
        this.rebelFactions = rebelFactions;
    }

    public void addRebelFaction(RebelFaction rebelFaction) {
        this.rebelFactions.add(rebelFaction);
    }

    public Diplomacy getActiveRelationships() {
        return activeRelationships;
    }

    public void setActiveRelationships(Diplomacy activeRelationships) {
        this.activeRelationships = activeRelationships;
    }

    public Combat getActiveCombats() {
        return activeCombats;
    }

    public void setActiveCombats(Combat activeCombats) {
        this.activeCombats = activeCombats;
    }

    public List<War> getActiveWars() {
        return activeWars;
    }

    public void setActiveWars(List<War> activeWars) {
        this.activeWars = activeWars;
    }

    public void addActiveWar(War activeWar) {
        this.activeWars.add(activeWar);
    }

    public List<War> getPreviousWars() {
        return previousWars;
    }

    public void setPreviousWars(List<War> previousWars) {
        this.previousWars = previousWars;
    }

    public void addPreviousWar(War previousWar) {
        this.previousWars.add(previousWar);
    }

    public IntegerList getInventions() {
        return inventions;
    }

    public void setInventions(IntegerList inventions) {
        this.inventions = inventions;
    }

    public IntegerList getGreatNations() {
        return greatNations;
    }

    public void setGreatNations(IntegerList greatNations) {
        this.greatNations = greatNations;
    }

    public IntegerList getOutliner() {
        return outliner;
    }

    public void setOutliner(IntegerList outliner) {
        this.outliner = outliner;
    }

    public NewsCollector getNewsCollector() {
        return newsCollector;
    }

    public void setNewsCollector(NewsCollector newsCollector) {
        this.newsCollector = newsCollector;
    }

    public CrisisManager getCrisisManager() {
        return crisisManager;
    }

    public void setCrisisManager(CrisisManager crisisManager) {
        this.crisisManager = crisisManager;
    }

    public List<RegionSaveState> getRegionStates() {
        return regionStates;
    }

    public void setRegionStates(List<RegionSaveState> regionStates) {
        this.regionStates = regionStates;
    }

    public void addRegionState(RegionSaveState regionState) {
        this.regionStates.add(regionState);
    }
}
