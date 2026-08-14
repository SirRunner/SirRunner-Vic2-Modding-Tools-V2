package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class StateSaveState {
    private ID id;
    private IntegerList stateProvince;
    private List<StateBuilding> buildings;
    private boolean isColonial;
    private double savings;
    private double interest;
    private boolean flashpoint;
    private String crisis;

    public StateSaveState() {
        init();
    }

    public void init() {
        if (id == null) {
            id = new ID();
        }

        if (stateProvince == null) {
            stateProvince = new IntegerList();
        }

        if (buildings == null) {
            buildings = new ArrayList<>();
        }
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public IntegerList getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(IntegerList stateProvince) {
        this.stateProvince = stateProvince;
    }

    public List<StateBuilding> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<StateBuilding> buildings) {
        this.buildings = buildings;
    }

    public void addBuilding(StateBuilding building) {
        this.buildings.add(building);
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}
