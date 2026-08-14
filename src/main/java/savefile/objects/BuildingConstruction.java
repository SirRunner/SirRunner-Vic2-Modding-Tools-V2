package savefile.objects;

import java.time.LocalDate;

public class BuildingConstruction {
    private ID id;
    private LocalDate startDate;
    private LocalDate date;
    private int location;
    private String country;
    private int building;

    public BuildingConstruction() {
        init();
    }

    public void init() {
        if (id == null) {
            id = new ID();
        }
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }
}
