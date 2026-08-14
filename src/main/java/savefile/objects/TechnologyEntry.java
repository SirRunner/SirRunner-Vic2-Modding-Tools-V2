package savefile.objects;

public class TechnologyEntry {
    private String name;
    private int researched;
    private double amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResearched() {
        return researched;
    }

    public void setResearched(int researched) {
        this.researched = researched;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
