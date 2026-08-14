package savefile.objects;

public class TradeEntry {
    private double limit;
    private boolean buy;
    private boolean automateTrade;

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public boolean isAutomateTrade() {
        return automateTrade;
    }

    public void setAutomateTrade(boolean automateTrade) {
        this.automateTrade = automateTrade;
    }
}
