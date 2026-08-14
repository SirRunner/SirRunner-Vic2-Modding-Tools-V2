package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class RebelFaction {
    private ID id;
    private String type;
    private String name;
    private String country;
    private String independence;
    private String culture;
    private String religion;
    private String government;
    private int province;
    private ID leader;
    private double organization;
    private List<ID> armies;
    private List<ID> pops;
    private IntegerList provinces;
    private UnitNames unitNames;
    private int nextUnit;

    public RebelFaction() {
        init();
    }

    public void init() {
        if (id == null) {
            id = new ID();
        }
        if (leader == null) {
            leader = new ID();
        }
        if (armies == null) {
            armies = new ArrayList<>();
        }
        if (pops == null) {
            pops = new ArrayList<>();
        }
        if (provinces == null) {
            provinces = new IntegerList();
        }
        if (unitNames == null) {
            unitNames = new UnitNames();
        }
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndependence() {
        return independence;
    }

    public void setIndependence(String independence) {
        this.independence = independence;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public ID getLeader() {
        return leader;
    }

    public void setLeader(ID leader) {
        this.leader = leader;
    }

    public double getOrganization() {
        return organization;
    }

    public void setOrganization(double organization) {
        this.organization = organization;
    }

    public List<ID> getArmies() {
        return armies;
    }

    public void setArmies(List<ID> armies) {
        this.armies = armies;
    }

    public void addArmy(ID army) {
        this.armies.add(army);
    }

    public List<ID> getPops() {
        return pops;
    }

    public void setPops(List<ID> pops) {
        this.pops = pops;
    }

    public void addPop(ID pop) {
        this.pops.add(pop);
    }

    public IntegerList getProvinces() {
        return provinces;
    }

    public void setProvinces(IntegerList provinces) {
        this.provinces = provinces;
    }

    public UnitNames getUnitNames() {
        return unitNames;
    }

    public void setUnitNames(UnitNames unitNames) {
        this.unitNames = unitNames;
    }

    public int getNextUnit() {
        return nextUnit;
    }

    public void setNextUnit(int nextUnit) {
        this.nextUnit = nextUnit;
    }
}
