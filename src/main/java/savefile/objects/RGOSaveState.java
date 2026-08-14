package savefile.objects;

public class RGOSaveState {
    private Employment employment;
    private double lastIncome;
    private String goodsType;

    public RGOSaveState() {
        init();
    }

    public void init() {
        if (employment == null) {
            employment = new Employment();
        }
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public double getLastIncome() {
        return lastIncome;
    }

    public void setLastIncome(double lastIncome) {
        this.lastIncome = lastIncome;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
