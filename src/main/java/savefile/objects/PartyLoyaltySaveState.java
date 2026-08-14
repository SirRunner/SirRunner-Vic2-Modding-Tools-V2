package savefile.objects;

public class PartyLoyaltySaveState {
    private String ideology;
    private double loyaltyValue;

    public String getIdeology() {
        return ideology;
    }

    public void setIdeology(String ideology) {
        this.ideology = ideology;
    }

    public double getLoyaltyValue() {
        return loyaltyValue;
    }

    public void setLoyaltyValue(double loyaltyValue) {
        this.loyaltyValue = loyaltyValue;
    }
}
