package savefile.objects;

public class Tax {
    private double current;
    private IntegerList taxIncome;
    private IntegerList taxEfficiency;
    private double total;
    private double rangeLimitMax;
    private double rangeLimitMin;
    private int maxTax;
    private int minTax;

    public Tax() {
        init();
    }

    public void init() {
        if (taxIncome == null) {
            taxIncome = new IntegerList();
        }
        if (taxEfficiency == null) {
            taxEfficiency = new IntegerList();
        }
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public IntegerList getTaxIncome() {
        return taxIncome;
    }

    public void setTaxIncome(IntegerList taxIncome) {
        this.taxIncome = taxIncome;
    }

    public IntegerList getTaxEfficiency() {
        return taxEfficiency;
    }

    public void setTaxEfficiency(IntegerList taxEfficiency) {
        this.taxEfficiency = taxEfficiency;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
