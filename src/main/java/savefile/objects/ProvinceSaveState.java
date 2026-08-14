package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProvinceSaveState {
    private String name;
    private String owner;
    private String controller;
    private List<String> cores;
    private List<BuildingLevel> buildings;
    private List<PopSaveState> pops;
    private BuildingConstruction buildingConstruction;
    private RGOSaveState rgo;
    private int lifeRating;
    private double infrastructure;
    private LocalDate lastImmigration;
    private LocalDate lastControllerChange;
    private UnitNames unitNames;
    private int crime;
    private List<PartyLoyaltySaveState> partyLoyalty;

    public ProvinceSaveState() {
        init();
    }

    public void init() {
        if (cores == null) {
            cores = new ArrayList<>();
        }
        if (buildings == null) {
            buildings = new ArrayList<>();
        }
        if (pops == null) {
            pops = new ArrayList<>();
        }

        if (buildingConstruction == null) {
            buildingConstruction = new BuildingConstruction();
        }

        if (rgo == null) {
            rgo = new RGOSaveState();
        }

        if (unitNames == null) {
            unitNames = new UnitNames();
        }

        if (partyLoyalty == null) {
            partyLoyalty = new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public List<String> getCores() {
        return cores;
    }

    public void setCores(List<String> cores) {
        this.cores = cores;
    }

    public void addCore(String core) {
        this.cores.add(core);
    }

    public List<BuildingLevel> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingLevel> buildings) {
        this.buildings = buildings;
    }

    public void addBuilding(BuildingLevel building) {
        this.buildings.add(building);
    }

    public List<PopSaveState> getPops() {
        return pops;
    }

    public void setPops(List<PopSaveState> pops) {
        this.pops = pops;
    }

    public void addPop(PopSaveState pop) {
        this.pops.add(pop);
    }

    public BuildingConstruction getBuildingConstruction() {
        return buildingConstruction;
    }

    public void setBuildingConstruction(BuildingConstruction buildingConstruction) {
        this.buildingConstruction = buildingConstruction;
    }

    public RGOSaveState getRgo() {
        return rgo;
    }

    public void setRgo(RGOSaveState rgo) {
        this.rgo = rgo;
    }

    public int getLifeRating() {
        return lifeRating;
    }

    public void setLifeRating(int lifeRating) {
        this.lifeRating = lifeRating;
    }

    public double getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(double infrastructure) {
        this.infrastructure = infrastructure;
    }

    public LocalDate getLastImmigration() {
        return lastImmigration;
    }

    public void setLastImmigration(LocalDate lastImmigration) {
        this.lastImmigration = lastImmigration;
    }

    public LocalDate getLastControllerChange() {
        return lastControllerChange;
    }

    public void setLastControllerChange(LocalDate lastControllerChange) {
        this.lastControllerChange = lastControllerChange;
    }

    public UnitNames getUnitNames() {
        return unitNames;
    }

    public void setUnitNames(UnitNames unitNames) {
        this.unitNames = unitNames;
    }

    public int getCrime() {
        return crime;
    }

    public void setCrime(int crime) {
        this.crime = crime;
    }

    public List<PartyLoyaltySaveState> getPartyLoyalty() {
        return partyLoyalty;
    }

    public void setPartyLoyalty(List<PartyLoyaltySaveState> partyLoyalty) {
        this.partyLoyalty = partyLoyalty;
    }
}
