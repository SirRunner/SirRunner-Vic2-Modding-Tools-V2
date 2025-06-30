package m2tw;

public enum CharacterCulture {

    EASTERN_EUROPEAN("Orcish"),
    GONDOR("Gondor"),
    GREEK("Elven"),
    MIDDLE_EASTERN("Men of the East"),
    MESOAMERICAN("Wildmen"),
    NORTHERN_EUROPEAN("Men of the West"),
    SOUTHERN_EUROPEAN("Dwarven");

    private final String localizedName;

    CharacterCulture(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public static CharacterCulture getValue(String value) {
        try {
            return CharacterCulture.valueOf(value.toUpperCase().trim());
        } catch (Exception e) {
            System.out.println("Unable to parse value: " + value);
            e.printStackTrace();
        }

        return null;
    }
}
