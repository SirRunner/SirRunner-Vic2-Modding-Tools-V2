package m2tw;

public enum Faction {
    AZTECS("Dunlendings"),
    BYZANTIUM("Vale of Dorwinion"),
    DENMARK("Principality of Dol Amroth"),
    ENGLAND("Mordor"),
    FRANCE("Dominion of Isengard"),
    GUNDABAD("Orcs of Gundabad"),
    HRE("Orcs of Moria"),
    HUNGARY("Dwarves of Ered Luin"),
    IRELAND("Realm of Lothlórien"),
    KHAND("Variags of Khand"),
    MILAN("Kingdom of Rohan"),
    MONGOLS("Woodland Realm"),
    MOORS("Dwarves of Erebor"),
    NORMANS("Bree-land"),
    NORWAY("Dwarves of Khazad-dûm"),
    POLAND("Dol Guldur"),
    PORTUGAL("Remnants of Angmar"),
    RUSSIA("Ar-Adunaim"),
    SAXONS("High Elves"),
    SCOTLAND("Kingdom of Dale"),
    SICILY("Kingdom of Gondor"),
    SPAIN("Haradrim Tribes"),
    TEUTONIC_ORDER("Clans ofEnedwaith"),
    TIMURIDS("Anduin Vale"),
    TURKS("Northern Dúnedain"),
    VENICE("Easterlings of Rhûn");

    private final String localizedName;

    Faction(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}
