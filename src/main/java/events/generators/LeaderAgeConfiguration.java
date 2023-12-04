package events.generators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LeaderAgeConfiguration {
    BARD_I(33000, "bard_i_info", "bard_i_age", "DAL_bard_i_age", 1066, "Bard I", 55, 82, ConfigurationType.AGE), // 2898 - 2980
    BAIN_I(33001, "bard_i_info", "bain_i_age", "DAL_bain_i_age", 1067, "Bain I", 25, 82, ConfigurationType.AGE), // 2928 - 3010
    BRAND_I(33002, "bard_i_info", "brand_i_age", "DAL_brand_i_age", 1069, "Brand I", -1, 79, ConfigurationType.AGE), // 2954 - 3033
    BARD_II(33003, "bard_i_info", "bard_ii_age", "DAL_bard_ii_age", 1070, "Bard II", -1, 80, ConfigurationType.AGE), // 2982 - 3062
    BRAND_I_BIRTH(33004, "bard_i_info", "DAL_brand_i_born", "DAL_brand_i_age", 1071, "Brand I", 2954, 2960, ConfigurationType.BIRTH), // Official birth in 2957
    BARD_II_BIRTH(33005, "bard_i_info", "DAL_bard_ii_born", "DAL_bard_ii_age", 1072, "Bard II", 2982, 2988, ConfigurationType.BIRTH), // Official birth in 2985
    BARD_I_DEATH(33006, "bard_i_info", "bard_i_age", "DAL_bard_i_age", 1066, "Bard I", 2974, 2980, ConfigurationType.DEATH), // Official death in 2977
    BAIN_I_DEATH(33007, "bard_i_info", "bain_i_age", "DAL_bain_i_age", 1067, "Bain I", 3004, 3010, ConfigurationType.DEATH), // Official death in 3007
    BRAND_I_DEATH(33008, "bard_i_info", "brand_i_age", "DAL_brand_i_age", 1069, "Brand I", 3018, 3033, ConfigurationType.DEATH), // Official death in 3019
    BARD_II_DEATH(33009, "bard_i_info", "bard_ii_age", "DAL_bard_ii_age", 1070, "Bard II", 3056, 3062, ConfigurationType.DEATH); // Official death in FA 40 (TA 3059)

    private final int id;
    private final String picture;
    private final String variable;
    private final String region;
    private final int province;
    private final String text;
    private final int startAge;
    private final int maxAge;
    private final ConfigurationType type;

    LeaderAgeConfiguration(int id, String picture, String variable, String region, int province, String text, int startAge, int maxAge, ConfigurationType type) {
        this.id = id;
        this.picture = picture;
        this.variable = variable;
        this.region = region;
        this.province = province;
        this.text = text;
        this.startAge = startAge;
        this.maxAge = maxAge;
        this.type = type;
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

    public ConfigurationType getType() {
        return type;
    }

    public static List<LeaderAgeConfiguration> getConfigurationsByType(ConfigurationType type) {
        return Arrays.stream(values()).filter(config -> type == config.getType()).collect(Collectors.toList());
    }

    public enum ConfigurationType {
        AGE,
        BIRTH,
        DEATH
    }
}
