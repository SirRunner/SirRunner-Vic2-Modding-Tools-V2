package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Army {
    private ID id;
    private String name;
    private ID leader;
    private int location;
    private LocalDate digInLastDate;
    private double supplies;
    private List<Regiment> regiments;
    private int base;

    public Army() {
        init();
    }

    public void init() {
        if (id == null) {
            id = new ID();
        }

        if (leader == null) {
            leader = new ID();
        }

        if (regiments == null) {
            regiments = new ArrayList<>();
        }
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ID getLeader() {
        return leader;
    }

    public void setLeader(ID leader) {
        this.leader = leader;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public LocalDate getDigInLastDate() {
        return digInLastDate;
    }

    public void setDigInLastDate(LocalDate digInLastDate) {
        this.digInLastDate = digInLastDate;
    }

    public double getSupplies() {
        return supplies;
    }

    public void setSupplies(double supplies) {
        this.supplies = supplies;
    }

    public List<Regiment> getRegiments() {
        return regiments;
    }

    public void setRegiments(List<Regiment> regiments) {
        this.regiments = regiments;
    }

    public void addRegiment(Regiment regiment) {
        this.regiments.add(regiment);
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }
}
