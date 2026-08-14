package savefile.objects;

public class SpendingSaveState {
    private double settings;
    private double tempSettings;
    private double factor;
    private double reserve;
    private double maxValue;
    private double rangeLimitMax;
    private double rangeLimitMin;
    private int maxTax;
    private int minTax;

    public double getSettings() {
        return settings;
    }

    public void setSettings(double settings) {
        this.settings = settings;
    }

    public double getTempSettings() {
        return tempSettings;
    }

    public void setTempSettings(double tempSettings) {
        this.tempSettings = tempSettings;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double getReserve() {
        return reserve;
    }

    public void setReserve(double reserve) {
        this.reserve = reserve;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getRangeLimitMax() {
        return rangeLimitMax;
    }

    public void setRangeLimitMax(double rangeLimitMax) {
        this.rangeLimitMax = rangeLimitMax;
    }

    public double getRangeLimitMin() {
        return rangeLimitMin;
    }

    public void setRangeLimitMin(double rangeLimitMin) {
        this.rangeLimitMin = rangeLimitMin;
    }

    public int getMaxTax() {
        return maxTax;
    }

    public void setMaxTax(int maxTax) {
        this.maxTax = maxTax;
    }

    public int getMinTax() {
        return minTax;
    }

    public void setMinTax(int minTax) {
        this.minTax = minTax;
    }
}
