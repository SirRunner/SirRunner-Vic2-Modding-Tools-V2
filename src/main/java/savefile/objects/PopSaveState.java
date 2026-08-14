package savefile.objects;

public class PopSaveState {
    private int id;
    private int size;
    private String culture;
    private String religion;
    private double money;
    private IdeologySaveState ideology;
    private IssueSaveState issues;
    private double militancy;
    private double literacy;
    private double bank;
    private String productionType;
    private GoodsPool stockpile;
    private GoodsPool need;
    private double lastSpending;
    private double currentProducing;
    private double percentAfforded;
    private double percentSoldDomestic;
    private double percentSoldExport;
    private double leftover;
    private double throttle;
    private double needsCost;
    private double productionIncome;
    private double consciousness;
    private int demoted;
    private double everydayNeeds;
    private double luxuryNeeds;
    private ID rebelFaction;
    private int random;
    private IntegerList sizeChanges;

    public PopSaveState() {
        init();
    }

    public void init() {
        if (ideology == null) {
            ideology = new IdeologySaveState();
        }

        if (issues == null) {
            issues = new IssueSaveState();
        }

        if (stockpile == null) {
            stockpile = new GoodsPool();
        }

        if (need == null) {
            need = new GoodsPool();
        }

        if (rebelFaction == null) {
            rebelFaction = new ID();
        }

        if (sizeChanges == null) {
            sizeChanges = new IntegerList();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public IdeologySaveState getIdeology() {
        return ideology;
    }

    public void setIdeology(IdeologySaveState ideology) {
        this.ideology = ideology;
    }

    public IssueSaveState getIssues() {
        return issues;
    }

    public void setIssues(IssueSaveState issues) {
        this.issues = issues;
    }

    public double getMilitancy() {
        return militancy;
    }

    public void setMilitancy(double militancy) {
        this.militancy = militancy;
    }

    public double getLiteracy() {
        return literacy;
    }

    public void setLiteracy(double literacy) {
        this.literacy = literacy;
    }

    public double getBank() {
        return bank;
    }

    public void setBank(double bank) {
        this.bank = bank;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public GoodsPool getStockpile() {
        return stockpile;
    }

    public void setStockpile(GoodsPool stockpile) {
        this.stockpile = stockpile;
    }

    public GoodsPool getNeed() {
        return need;
    }

    public void setNeed(GoodsPool need) {
        this.need = need;
    }

    public double getLastSpending() {
        return lastSpending;
    }

    public void setLastSpending(double lastSpending) {
        this.lastSpending = lastSpending;
    }

    public double getCurrentProducing() {
        return currentProducing;
    }

    public void setCurrentProducing(double currentProducing) {
        this.currentProducing = currentProducing;
    }

    public double getPercentAfforded() {
        return percentAfforded;
    }

    public void setPercentAfforded(double percentAfforded) {
        this.percentAfforded = percentAfforded;
    }

    public double getPercentSoldDomestic() {
        return percentSoldDomestic;
    }

    public void setPercentSoldDomestic(double percentSoldDomestic) {
        this.percentSoldDomestic = percentSoldDomestic;
    }

    public double getPercentSoldExport() {
        return percentSoldExport;
    }

    public void setPercentSoldExport(double percentSoldExport) {
        this.percentSoldExport = percentSoldExport;
    }

    public double getLeftover() {
        return leftover;
    }

    public void setLeftover(double leftover) {
        this.leftover = leftover;
    }

    public double getThrottle() {
        return throttle;
    }

    public void setThrottle(double throttle) {
        this.throttle = throttle;
    }

    public double getNeedsCost() {
        return needsCost;
    }

    public void setNeedsCost(double needsCost) {
        this.needsCost = needsCost;
    }

    public double getProductionIncome() {
        return productionIncome;
    }

    public void setProductionIncome(double productionIncome) {
        this.productionIncome = productionIncome;
    }

    public double getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(double consciousness) {
        this.consciousness = consciousness;
    }

    public int getDemoted() {
        return demoted;
    }

    public void setDemoted(int demoted) {
        this.demoted = demoted;
    }

    public double getEverydayNeeds() {
        return everydayNeeds;
    }

    public void setEverydayNeeds(double everydayNeeds) {
        this.everydayNeeds = everydayNeeds;
    }

    public double getLuxuryNeeds() {
        return luxuryNeeds;
    }

    public void setLuxuryNeeds(double luxuryNeeds) {
        this.luxuryNeeds = luxuryNeeds;
    }

    public ID getRebelFaction() {
        return rebelFaction;
    }

    public void setRebelFaction(ID rebelFaction) {
        this.rebelFaction = rebelFaction;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public IntegerList getSizeChanges() {
        return sizeChanges;
    }

    public void setSizeChanges(IntegerList sizeChanges) {
        this.sizeChanges = sizeChanges;
    }
}
