package events.generators;

public enum LeaderAgeConfiguration {
    BARD_I(33000, "bard_i_info", "bard_i_age", "DAL_bard_i_age", 1066, "Bard I", 55, 82),
    BAIN_I(33001, "bard_i_info", "bain_i_age", "DAL_bain_i_age", 1067, "Bain I", 25, 82);

    private final int id;
    private final String picture;
    private final String variable;
    private final String region;
    private final int province;
    private final String text;
    private final int startAge;
    private final int maxAge;

    LeaderAgeConfiguration(int id, String picture, String variable, String region, int province, String text, int startAge, int maxAge) {
        this.id = id;
        this.picture = picture;
        this.variable = variable;
        this.region = region;
        this.province = province;
        this.text = text;
        this.startAge = startAge;
        this.maxAge = maxAge;
    }

    public int getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getVariable() {
        return variable;
    }

    public String getRegion() {
        return region;
    }

    public int getProvince() {
        return province;
    }

    public String getText() {
        return text;
    }

    public int getStartAge() {
        return startAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
