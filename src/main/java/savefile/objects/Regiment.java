package savefile.objects;

public class Regiment {
    private ID id;
    private String name;
    private ID pop;
    private double organization;
    private double strength;
    private double experience;
    private int count;
    private String type;

    public Regiment() {
        init();
    }

    public void init() {
        if (id == null) {
            id = new ID();
        }

        if (pop == null) {
            pop = new ID();
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

    public ID getPop() {
        return pop;
    }

    public void setPop(ID pop) {
        this.pop = pop;
    }

    public double getOrganization() {
        return organization;
    }

    public void setOrganization(double organization) {
        this.organization = organization;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
