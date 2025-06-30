package m2tw;

public enum Religion {

    CATHOLIC("Melkor's Shadow"),
    DWARVEN("Dwarven"),
    ELVEN("Elven"),
    HERETIC("Others"),
    ISLAM("Dúnedain"),
    KINGS("King's Men"),
    NOMADIC("Nomadic"),
    NORTHMEN("Northmen"),
    ORTHODOX("Men of the East"),
    WILDMEN("Middlemen");

    private final String localizedName;

    Religion(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}
