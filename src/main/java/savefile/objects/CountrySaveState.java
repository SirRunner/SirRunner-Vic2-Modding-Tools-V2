package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CountrySaveState {
    private double taxBase;
    private Flags flags;
    private Variables variables;
    private int capital;
    private Technology technology;
    private Research research;
    private LocalDate lastReform;
    private LocalDate lastElection;
    private List<Reform> reforms;
    private UpperHouse upperHouse;
    private int rulingParty;
    private List<Integer> activeParties;
    private GoodsPool navalNeed;
    private GoodsPool landSupplyCost;
    private GoodsPool navalSupplyCost;
    private double diplomacyPoints;
    private String religion;
    private String government;
    private double plurality;
    private double revanchism;
    private List<Modifier> modifiers;
    private Tax richTax;
    private Tax middleTax;
    private Tax poorTax;
    private SpendingSaveState educationSpending;
    private SpendingSaveState crimeFighting;
    private SpendingSaveState socialSpending;
    private SpendingSaveState militarySpending;
    private double overseasPenalty;
    private double leadership;
    private boolean autoAssignLeaders;
    private boolean autoCreateLeaders;
    private List<Leader> leaders;
    private List<Army> armies;
    private List<RelationsSaveState> relations;
    private IntegerList activeInventions;
    private IntegerList possibleInventions;
    private IntegerList illegalInventions;
    private GovernmentFlag governmentFlag;
    private LocalDate lastMissionCancel;
    private AI aiHardStrategy;
    private AI ai;
    private IntegerList foreignInvestment;
    private boolean mobilize;
    private String schools;
    private String primaryCulture;
    private StringList culture;
    private double prestige;
    private Bank bank;
    private double money;
    private LocalDate lastBankrupt;
    private List<Creditor> creditors;
    private List<Movement> movements;
    private GoodsPool stockpile;
    private String nationalValue;
    private GoodsPool buyDomestic; // Assuming the type. Do not see an example of what this is
    private Trade trade;
    private boolean civilized;
    private LocalDate lastGreatnessDate;
    private List<StateSaveState> states;
    private double badboy;
    private double tradeCapLand;
    private double tradeCapNaval;
    private double tradeCapProjects;
    private double maxTariff;
    private GoodsPool domesticSupplyPool;
    private GoodsPool soldSupplyPool;
    private GoodsPool domesticDemandPool;
    private GoodsPool actualSoldDomestic;
    private GoodsPool savedCountrySupply;
    private GoodsPool maxBought;
    private NationalFocusSaveState nationalFocuses;
    private Influence influence;
    private IntegerList expenses;
    private IntegerList incomes;
    private IntegerList interestingCountries;
    private LocalDate nextQuarterlyPulse;
    private LocalDate nextYearlyPulse;
    private double suppression;
    private List<Railroads> railroads;
    private boolean isReleasableVassal;

    public CountrySaveState() {
        init();
    }

    public void init() {
        if (flags == null) {
            flags = new Flags();
        }
        if (variables == null) {
            variables = new Variables();
        }
        if (technology == null) {
            technology = new Technology();
        }
        if (research == null) {
            research = new Research();
        }
        if (reforms == null) {
            reforms = new ArrayList<>();
        }
        if (upperHouse == null) {
            upperHouse = new UpperHouse();
        }
        if (activeParties == null) {
            activeParties = new ArrayList<>();
        }
        if (navalNeed == null) {
            navalNeed = new GoodsPool();
        }
        if (landSupplyCost == null) {
            landSupplyCost = new GoodsPool();
        }
        if (navalSupplyCost == null) {
            navalSupplyCost = new GoodsPool();
        }
        if (modifiers == null) {
            modifiers = new ArrayList<>();
        }
        if (richTax == null) {
            richTax = new Tax();
        }
        if (middleTax == null) {
            middleTax = new Tax();
        }
        if (poorTax == null) {
            poorTax = new Tax();
        }
        if (educationSpending == null) {
            educationSpending = new SpendingSaveState();
        }
        if (crimeFighting == null) {
            crimeFighting = new SpendingSaveState();
        }
        if (socialSpending == null) {
            socialSpending = new SpendingSaveState();
        }
        if (militarySpending == null) {
            militarySpending = new SpendingSaveState();
        }
        if (leaders == null) {
            leaders = new ArrayList<>();
        }
        if (armies == null) {
            armies = new ArrayList<>();
        }
        if (relations == null) {
            relations = new ArrayList<>();
        }
        if (activeInventions == null) {
            activeInventions = new IntegerList();
        }
        if (possibleInventions == null) {
            possibleInventions = new IntegerList();
        }
        if (illegalInventions == null) {
            illegalInventions = new IntegerList();
        }
        if (governmentFlag == null) {
            governmentFlag = new GovernmentFlag();
        }
        if (aiHardStrategy == null) {
            aiHardStrategy = new AI();
        }
        if (ai == null) {
            ai = new AI();
        }
        if (foreignInvestment == null) {
            foreignInvestment = new IntegerList();
        }
        if (culture == null) {
            culture = new StringList();
        }
        if (bank == null) {
            bank = new Bank();
        }
        if (creditors == null) {
            creditors = new ArrayList<>();
        }
        if (movements == null) {
            movements = new ArrayList<>();
        }
        if (stockpile == null) {
            stockpile = new GoodsPool();
        }
        if (buyDomestic == null) {
            buyDomestic = new GoodsPool();
        }
        if (trade == null) {
            trade = new Trade();
        }
        if (states == null) {
            states = new ArrayList<>();
        }
        if (domesticSupplyPool == null) {
            domesticSupplyPool = new GoodsPool();
        }
        if (soldSupplyPool == null) {
            soldSupplyPool = new GoodsPool();
        }
        if (domesticDemandPool == null) {
            domesticDemandPool = new GoodsPool();
        }
        if (actualSoldDomestic == null) {
            actualSoldDomestic = new GoodsPool();
        }
        if (savedCountrySupply == null) {
            savedCountrySupply = new GoodsPool();
        }
        if (maxBought == null) {
            maxBought = new GoodsPool();
        }
        if (nationalFocuses == null) {
            nationalFocuses = new NationalFocusSaveState();
        }
        if (influence == null) {
            influence = new Influence();
        }
        if (expenses == null) {
            expenses = new IntegerList();
        }
        if (incomes == null) {
            incomes = new IntegerList();
        }
        if (interestingCountries == null) {
            interestingCountries = new IntegerList();
        }
        if (railroads == null) {
            railroads = new ArrayList<>();
        }
    }

    public double getTaxBase() {
        return taxBase;
    }

    public void setTaxBase(double taxBase) {
        this.taxBase = taxBase;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public LocalDate getLastReform() {
        return lastReform;
    }

    public void setLastReform(LocalDate lastReform) {
        this.lastReform = lastReform;
    }

    public LocalDate getLastElection() {
        return lastElection;
    }

    public void setLastElection(LocalDate lastElection) {
        this.lastElection = lastElection;
    }

    public List<Reform> getReforms() {
        return reforms;
    }

    public void setReforms(List<Reform> reforms) {
        this.reforms = reforms;
    }

    public void addReform(Reform reform) {
        this.reforms.add(reform);
    }

    public UpperHouse getUpperHouse() {
        return upperHouse;
    }

    public void setUpperHouse(UpperHouse upperHouse) {
        this.upperHouse = upperHouse;
    }

    public int getRulingParty() {
        return rulingParty;
    }

    public void setRulingParty(int rulingParty) {
        this.rulingParty = rulingParty;
    }

    public List<Integer> getActiveParties() {
        return activeParties;
    }

    public void setActiveParties(List<Integer> activeParties) {
        this.activeParties = activeParties;
    }

    public void addActiveParty(int activeParty) {
        this.activeParties.add(activeParty);
    }

    public GoodsPool getNavalNeed() {
        return navalNeed;
    }

    public void setNavalNeed(GoodsPool navalNeed) {
        this.navalNeed = navalNeed;
    }

    public GoodsPool getLandSupplyCost() {
        return landSupplyCost;
    }

    public void setLandSupplyCost(GoodsPool landSupplyCost) {
        this.landSupplyCost = landSupplyCost;
    }

    public GoodsPool getNavalSupplyCost() {
        return navalSupplyCost;
    }

    public void setNavalSupplyCost(GoodsPool navalSupplyCost) {
        this.navalSupplyCost = navalSupplyCost;
    }

    public double getDiplomacyPoints() {
        return diplomacyPoints;
    }

    public void setDiplomacyPoints(double diplomacyPoints) {
        this.diplomacyPoints = diplomacyPoints;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public double getPlurality() {
        return plurality;
    }

    public void setPlurality(double plurality) {
        this.plurality = plurality;
    }

    public double getRevanchism() {
        return revanchism;
    }

    public void setRevanchism(double revanchism) {
        this.revanchism = revanchism;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public void addModifier(Modifier modifier) {
        this.modifiers.add(modifier);
    }

    public Tax getRichTax() {
        return richTax;
    }

    public void setRichTax(Tax richTax) {
        this.richTax = richTax;
    }

    public Tax getMiddleTax() {
        return middleTax;
    }

    public void setMiddleTax(Tax middleTax) {
        this.middleTax = middleTax;
    }

    public Tax getPoorTax() {
        return poorTax;
    }

    public void setPoorTax(Tax poorTax) {
        this.poorTax = poorTax;
    }

    public SpendingSaveState getEducationSpending() {
        return educationSpending;
    }

    public void setEducationSpending(SpendingSaveState educationSpending) {
        this.educationSpending = educationSpending;
    }

    public SpendingSaveState getCrimeFighting() {
        return crimeFighting;
    }

    public void setCrimeFighting(SpendingSaveState crimeFighting) {
        this.crimeFighting = crimeFighting;
    }

    public SpendingSaveState getSocialSpending() {
        return socialSpending;
    }

    public void setSocialSpending(SpendingSaveState socialSpending) {
        this.socialSpending = socialSpending;
    }

    public SpendingSaveState getMilitarySpending() {
        return militarySpending;
    }

    public void setMilitarySpending(SpendingSaveState militarySpending) {
        this.militarySpending = militarySpending;
    }

    public double getOverseasPenalty() {
        return overseasPenalty;
    }

    public void setOverseasPenalty(double overseasPenalty) {
        this.overseasPenalty = overseasPenalty;
    }

    public double getLeadership() {
        return leadership;
    }

    public void setLeadership(double leadership) {
        this.leadership = leadership;
    }

    public boolean isAutoAssignLeaders() {
        return autoAssignLeaders;
    }

    public void setAutoAssignLeaders(boolean autoAssignLeaders) {
        this.autoAssignLeaders = autoAssignLeaders;
    }

    public boolean isAutoCreateLeaders() {
        return autoCreateLeaders;
    }

    public void setAutoCreateLeaders(boolean autoCreateLeaders) {
        this.autoCreateLeaders = autoCreateLeaders;
    }

    public List<Leader> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Leader> leaders) {
        this.leaders = leaders;
    }

    public void addLeader(Leader leader) {
        this.leaders.add(leader);
    }

    public List<Army> getArmies() {
        return armies;
    }

    public void setArmies(List<Army> armies) {
        this.armies = armies;
    }

    public void addArmy(Army army) {
        this.armies.add(army);
    }

    public List<RelationsSaveState> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationsSaveState> relations) {
        this.relations = relations;
    }

    public void addRelations(RelationsSaveState relationsSaveState) {
        this.relations.add(relationsSaveState);
    }

    public IntegerList getActiveInventions() {
        return activeInventions;
    }

    public void setActiveInventions(IntegerList activeInventions) {
        this.activeInventions = activeInventions;
    }

    public IntegerList getPossibleInventions() {
        return possibleInventions;
    }

    public void setPossibleInventions(IntegerList possibleInventions) {
        this.possibleInventions = possibleInventions;
    }

    public IntegerList getIllegalInventions() {
        return illegalInventions;
    }

    public void setIllegalInventions(IntegerList illegalInventions) {
        this.illegalInventions = illegalInventions;
    }

    public GovernmentFlag getGovernmentFlag() {
        return governmentFlag;
    }

    public void setGovernmentFlag(GovernmentFlag governmentFlag) {
        this.governmentFlag = governmentFlag;
    }

    public LocalDate getLastMissionCancel() {
        return lastMissionCancel;
    }

    public void setLastMissionCancel(LocalDate lastMissionCancel) {
        this.lastMissionCancel = lastMissionCancel;
    }

    public AI getAiHardStrategy() {
        return aiHardStrategy;
    }

    public void setAiHardStrategy(AI aiHardStrategy) {
        this.aiHardStrategy = aiHardStrategy;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public IntegerList getForeignInvestment() {
        return foreignInvestment;
    }

    public void setForeignInvestment(IntegerList foreignInvestment) {
        this.foreignInvestment = foreignInvestment;
    }

    public boolean isMobilize() {
        return mobilize;
    }

    public void setMobilize(boolean mobilize) {
        this.mobilize = mobilize;
    }

    public String getSchools() {
        return schools;
    }

    public void setSchools(String schools) {
        this.schools = schools;
    }

    public String getPrimaryCulture() {
        return primaryCulture;
    }

    public void setPrimaryCulture(String primaryCulture) {
        this.primaryCulture = primaryCulture;
    }

    public StringList getCulture() {
        return culture;
    }

    public void setCulture(StringList culture) {
        this.culture = culture;
    }

    public double getPrestige() {
        return prestige;
    }

    public void setPrestige(double prestige) {
        this.prestige = prestige;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public LocalDate getLastBankrupt() {
        return lastBankrupt;
    }

    public void setLastBankrupt(LocalDate lastBankrupt) {
        this.lastBankrupt = lastBankrupt;
    }

    public List<Creditor> getCreditors() {
        return creditors;
    }

    public void setCreditors(List<Creditor> creditors) {
        this.creditors = creditors;
    }

    public void addCreditor(Creditor creditor) {
        this.creditors.add(creditor);
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public void addMovement(Movement movement) {
        this.movements.add(movement);
    }

    public GoodsPool getStockpile() {
        return stockpile;
    }

    public void setStockpile(GoodsPool stockpile) {
        this.stockpile = stockpile;
    }

    public String getNationalValue() {
        return nationalValue;
    }

    public void setNationalValue(String nationalValue) {
        this.nationalValue = nationalValue;
    }

    public GoodsPool getBuyDomestic() {
        return buyDomestic;
    }

    public void setBuyDomestic(GoodsPool buyDomestic) {
        this.buyDomestic = buyDomestic;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public boolean isCivilized() {
        return civilized;
    }

    public void setCivilized(boolean civilized) {
        this.civilized = civilized;
    }

    public LocalDate getLastGreatnessDate() {
        return lastGreatnessDate;
    }

    public void setLastGreatnessDate(LocalDate lastGreatnessDate) {
        this.lastGreatnessDate = lastGreatnessDate;
    }

    public List<StateSaveState> getStates() {
        return states;
    }

    public void setStates(List<StateSaveState> states) {
        this.states = states;
    }

    public void addState(StateSaveState state) {
        this.states.add(state);
    }

    public double getBadboy() {
        return badboy;
    }

    public void setBadboy(double badboy) {
        this.badboy = badboy;
    }

    public double getTradeCapLand() {
        return tradeCapLand;
    }

    public void setTradeCapLand(double tradeCapLand) {
        this.tradeCapLand = tradeCapLand;
    }

    public double getTradeCapNaval() {
        return tradeCapNaval;
    }

    public void setTradeCapNaval(double tradeCapNaval) {
        this.tradeCapNaval = tradeCapNaval;
    }

    public double getTradeCapProjects() {
        return tradeCapProjects;
    }

    public void setTradeCapProjects(double tradeCapProjects) {
        this.tradeCapProjects = tradeCapProjects;
    }

    public double getMaxTariff() {
        return maxTariff;
    }

    public void setMaxTariff(double maxTariff) {
        this.maxTariff = maxTariff;
    }

    public GoodsPool getDomesticSupplyPool() {
        return domesticSupplyPool;
    }

    public void setDomesticSupplyPool(GoodsPool domesticSupplyPool) {
        this.domesticSupplyPool = domesticSupplyPool;
    }

    public GoodsPool getSoldSupplyPool() {
        return soldSupplyPool;
    }

    public void setSoldSupplyPool(GoodsPool soldSupplyPool) {
        this.soldSupplyPool = soldSupplyPool;
    }

    public GoodsPool getDomesticDemandPool() {
        return domesticDemandPool;
    }

    public void setDomesticDemandPool(GoodsPool domesticDemandPool) {
        this.domesticDemandPool = domesticDemandPool;
    }

    public GoodsPool getActualSoldDomestic() {
        return actualSoldDomestic;
    }

    public void setActualSoldDomestic(GoodsPool actualSoldDomestic) {
        this.actualSoldDomestic = actualSoldDomestic;
    }

    public GoodsPool getSavedCountrySupply() {
        return savedCountrySupply;
    }

    public void setSavedCountrySupply(GoodsPool savedCountrySupply) {
        this.savedCountrySupply = savedCountrySupply;
    }

    public GoodsPool getMaxBought() {
        return maxBought;
    }

    public void setMaxBought(GoodsPool maxBought) {
        this.maxBought = maxBought;
    }

    public NationalFocusSaveState getNationalFocuses() {
        return nationalFocuses;
    }

    public void setNationalFocuses(NationalFocusSaveState nationalFocuses) {
        this.nationalFocuses = nationalFocuses;
    }

    public Influence getInfluence() {
        return influence;
    }

    public void setInfluence(Influence influence) {
        this.influence = influence;
    }

    public IntegerList getExpenses() {
        return expenses;
    }

    public void setExpenses(IntegerList expenses) {
        this.expenses = expenses;
    }

    public IntegerList getIncomes() {
        return incomes;
    }

    public void setIncomes(IntegerList incomes) {
        this.incomes = incomes;
    }

    public IntegerList getInterestingCountries() {
        return interestingCountries;
    }

    public void setInterestingCountries(IntegerList interestingCountries) {
        this.interestingCountries = interestingCountries;
    }

    public LocalDate getNextQuarterlyPulse() {
        return nextQuarterlyPulse;
    }

    public void setNextQuarterlyPulse(LocalDate nextQuarterlyPulse) {
        this.nextQuarterlyPulse = nextQuarterlyPulse;
    }

    public LocalDate getNextYearlyPulse() {
        return nextYearlyPulse;
    }

    public void setNextYearlyPulse(LocalDate nextYearlyPulse) {
        this.nextYearlyPulse = nextYearlyPulse;
    }

    public double getSuppression() {
        return suppression;
    }

    public void setSuppression(double suppression) {
        this.suppression = suppression;
    }

    public List<Railroads> getRailroads() {
        return railroads;
    }

    public void setRailroads(List<Railroads> railroads) {
        this.railroads = railroads;
    }

    public void addRailroad(Railroads railroad) {
        this.railroads.add(railroad);
    }

    public boolean isReleasableVassal() {
        return isReleasableVassal;
    }

    public void setReleasableVassal(boolean releasableVassal) {
        isReleasableVassal = releasableVassal;
    }
}
