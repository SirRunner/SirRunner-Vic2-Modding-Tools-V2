package m2tw;

public enum CharacterEffect {
    // General stats
    COMMAND("Command"),
    ATTACK("Command (Attacking)"),
    DEFENCE("Command (Defending)"),
    CAVALRYCOMMAND("Command (Commanding Cavalry)"),
    INFANTRYCOMMAND("Command (Commanding Infantry)"),
    SIEGEATTACK("Command (Attacking Walls)"),
    SIEGEDEFENCE("Command (Defending Walls)"),
    AMBUSH("Command (Ambushing)"),
    NIGHTBATTLE("Command (At Night)"),
    NAVALCOMMAND("Command At Sea"),
    CHIVALRY("Renown"),
    LOYALTY("Loyalty"),
    PIETY("Acumen"),
    AUTHORITY("Authority"),

    // Agent stats
    CHARM("Charm"),
    FINANCE("Finance"),
    INFLUENCE("Influence"),
    SUBTERFUGE("to Agent's Skill"),

    // Combat stats
    BODYGUARDSIZE("Bodyguard Size"),
    BODYGUARDVALOUR("Bodyguard Experience"),
    BATTLESURGERY("Casualties Recovering"),
    HITPOINTS("Hitpoints"),
    TROOPMORALE("Troop Morale"),

    // Campaign Map stats,
    LEVEL("Character Model Used"),
    LOOTING("Looting Gain", "%"),
    MOVEMENTPOINTS("Movement Points", "%"),
    SIEGEENGINEERING("Siege Build Points"),
    TRAININGUNITS("Unit Training Costs", "%"),
    LINEOFSIGHT("Line of Sight"),
    PERSONALSECURITY("Personal Security"),
    FERTILITY("Fertility"),

    // Economy Stats
    FARMING("Farming"),
    MINING("Mining Income", "%"),
    TAXCOLLECTION("Taxes Income", "%"),
    TRADING("Trade", "%"),
    CONSTRUCTION("Construction costs", "%"),
    TRAININGAGENTS("Agent Training Cost"),

    // City Stats
    LAW("Public Order"),
    HEALTH("Public health"),
    LOCALPOPULARITY("Public Happiness"),
    PUBLICSECURITY("Public Security"),
    SQUALOR("Squalor"),
    UNREST("Unrest"),

    // ???,
    HERESYIMMUNITY("HeresyImmunity"),
    MANAGEMENT("Management"),
    UNORTHODOXY("Unorthodoxy"),

    // Attack bonuses. AI suggests it's in factors of 5
    COMBAT_V_FACTION_AZTECS("Attack Against " + Faction.AZTECS.getLocalizedName()),
    COMBAT_V_FACTION_BYZANTIUM("Attack Against " + Faction.BYZANTIUM.getLocalizedName()),
    COMBAT_V_FACTION_DENMARK("Attack Against " + Faction.DENMARK.getLocalizedName()),
    COMBAT_V_FACTION_ENGLAND("Attack Against " + Faction.ENGLAND.getLocalizedName()),
    COMBAT_V_FACTION_FRANCE("Attack Against " + Faction.FRANCE.getLocalizedName()),
    COMBAT_V_FACTION_GUNDABAD("Attack Against " + Faction.GUNDABAD.getLocalizedName()),
    COMBAT_V_FACTION_HRE("Attack Against " + Faction.HRE.getLocalizedName()),
    COMBAT_V_FACTION_IRELAND("Attack Against " + Faction.IRELAND.getLocalizedName()),
    COMBAT_V_FACTION_KHAND("Attack Against " + Faction.KHAND.getLocalizedName()),
    COMBAT_V_FACTION_MILAN("Attack Against " + Faction.MILAN.getLocalizedName()),
    COMBAT_V_FACTION_MONGOLS("Attack Against " + Faction.MONGOLS.getLocalizedName()),
    COMBAT_V_FACTION_NORMANS("Attack Against " + Faction.NORMANS.getLocalizedName()),
    COMBAT_V_FACTION_POLAND("Attack Against " + Faction.POLAND.getLocalizedName()),
    COMBAT_V_FACTION_PORTUGAL("Attack Against " + Faction.PORTUGAL.getLocalizedName()),
    COMBAT_V_FACTION_RUSSIA("Attack Against " + Faction.RUSSIA.getLocalizedName()),
    COMBAT_V_FACTION_SAXONS("Attack Against " + Faction.SAXONS.getLocalizedName()),
    COMBAT_V_FACTION_SCOTLAND("Attack Against " + Faction.SCOTLAND.getLocalizedName()),
    COMBAT_V_FACTION_SICILY("Attack Against " + Faction.SICILY.getLocalizedName()),
    COMBAT_V_FACTION_SPAIN("Attack Against " + Faction.SPAIN.getLocalizedName()),
    COMBAT_V_FACTION_TEUTONIC_ORDER("Attack Against " + Faction.TEUTONIC_ORDER.getLocalizedName()),
    COMBAT_V_FACTION_TIMURIDS("Attack Against " + Faction.TIMURIDS.getLocalizedName()),
    COMBAT_V_FACTION_TURKS("Attack Against " + Faction.TURKS.getLocalizedName()),
    COMBAT_V_FACTION_VENICE("Attack Against " + Faction.VENICE.getLocalizedName()),
    COMBAT_V_RELIGION_CATHOLIC("Attack Against " + Religion.CATHOLIC.getLocalizedName()),
    COMBAT_V_RELIGION_DWARVEN("Attack Against " + Religion.DWARVEN.getLocalizedName()),
    COMBAT_V_RELIGION_ELVEN("Attack Against " + Religion.ELVEN.getLocalizedName()),
    COMBAT_V_RELIGION_ISLAM("Attack Against " + Religion.ISLAM.getLocalizedName()),
    COMBAT_V_RELIGION_NORTHMEN("Attack Against " + Religion.NORTHMEN.getLocalizedName()),
    COMBAT_V_RELIGION_ORTHODOX("Attack Against " + Religion.ORTHODOX.getLocalizedName());

    private final String localizedName;
    private final String numberSuffix;

    CharacterEffect(String localizedName) {
        this(localizedName, "");
    }

    CharacterEffect(String localizedName, String numberSuffix) {
        this.localizedName = localizedName;
        this.numberSuffix = numberSuffix;

    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getNumberSuffix() {
        return numberSuffix;
    }

    public static CharacterEffect getValue(String value) {
        try {
            return CharacterEffect.valueOf(value.toUpperCase().trim());
        } catch (Exception e) {
            System.out.println("Unable to parse value: " + value);
            e.printStackTrace();
        }

        return null;
    }
}
