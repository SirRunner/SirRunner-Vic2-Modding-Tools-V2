package savefile.objects;

public class Research {
    private String technologyName;
    private double cost;
    private double maxProducing;
    private double lastSpending;
    private boolean active;

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getMaxProducing() {
        return maxProducing;
    }

    public void setMaxProducing(double maxProducing) {
        this.maxProducing = maxProducing;
    }

    public double getLastSpending() {
        return lastSpending;
    }

    public void setLastSpending(double lastSpending) {
        this.lastSpending = lastSpending;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
