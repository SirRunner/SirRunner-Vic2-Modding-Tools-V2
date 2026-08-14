package savefile.objects;

public class StateBuilding {
    private String building;
    private int level;
    private GoodsPool stockpile;
    private Employment employment;
    private double money;
    private double lastSpending;
    private double lastIncome;
    private double popsPaychecks;
    private double lastInvestment;
    private int unprofitableDays;
    private double leftover;
    private double injectedMoney;
    private int injectedDays;
    private double produces;
    private int profitHistoryDays;
    private int profitHistoryCurrent;
    private IntegerList profitHistoryEntry;

    public StateBuilding() {
        init();
    }

    public void init() {
        if (stockpile == null) {
            stockpile = new GoodsPool();
        }

        if (employment == null) {
            employment = new Employment();
        }

        if (profitHistoryEntry == null) {
            profitHistoryEntry = new IntegerList();
        }
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GoodsPool getStockpile() {
        return stockpile;
    }

    public void setStockpile(GoodsPool stockpile) {
        this.stockpile = stockpile;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLastSpending() {
        return lastSpending;
    }

    public void setLastSpending(double lastSpending) {
        this.lastSpending = lastSpending;
    }

    public double getLastIncome() {
        return lastIncome;
    }

    public void setLastIncome(double lastIncome) {
        this.lastIncome = lastIncome;
    }

    public double getPopsPaychecks() {
        return popsPaychecks;
    }

    public void setPopsPaychecks(double popsPaychecks) {
        this.popsPaychecks = popsPaychecks;
    }

    public double getLastInvestment() {
        return lastInvestment;
    }

    public void setLastInvestment(double lastInvestment) {
        this.lastInvestment = lastInvestment;
    }

    public int getUnprofitableDays() {
        return unprofitableDays;
    }

    public void setUnprofitableDays(int unprofitableDays) {
        this.unprofitableDays = unprofitableDays;
    }

    public double getLeftover() {
        return leftover;
    }

    public void setLeftover(double leftover) {
        this.leftover = leftover;
    }

    public double getInjectedMoney() {
        return injectedMoney;
    }

    public void setInjectedMoney(double injectedMoney) {
        this.injectedMoney = injectedMoney;
    }

    public int getInjectedDays() {
        return injectedDays;
    }

    public void setInjectedDays(int injectedDays) {
        this.injectedDays = injectedDays;
    }

    public double getProduces() {
        return produces;
    }

    public void setProduces(double produces) {
        this.produces = produces;
    }

    public int getProfitHistoryDays() {
        return profitHistoryDays;
    }

    public void setProfitHistoryDays(int profitHistoryDays) {
        this.profitHistoryDays = profitHistoryDays;
    }

    public int getProfitHistoryCurrent() {
        return profitHistoryCurrent;
    }

    public void setProfitHistoryCurrent(int profitHistoryCurrent) {
        this.profitHistoryCurrent = profitHistoryCurrent;
    }

    public IntegerList getProfitHistoryEntry() {
        return profitHistoryEntry;
    }

    public void setProfitHistoryEntry(IntegerList profitHistoryEntry) {
        this.profitHistoryEntry = profitHistoryEntry;
    }
}
