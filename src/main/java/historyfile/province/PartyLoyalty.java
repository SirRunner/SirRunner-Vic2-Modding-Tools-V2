package historyfile.province;

public class PartyLoyalty {
    private String ideology;
    private double value;

    public PartyLoyalty(String ideology, double value) {
        this.ideology = ideology;
        this.value = value;
    }

    public String getIdeology() {
        return ideology;
    }

    public void setIdeology(String ideology) {
        this.ideology = ideology;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
