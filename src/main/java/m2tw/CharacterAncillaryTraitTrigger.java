package m2tw;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CharacterAncillaryTraitTrigger {
    String name;
    WhenToTest whenToTest;
    List<String> conditions;
    int amount;
    String associatedTraitName;
    int chance;

    public CharacterAncillaryTraitTrigger() {
        this.conditions = new ArrayList<>();
    }

    public CharacterAncillaryTraitTrigger(CharacterAncillaryTraitTrigger other) {
        this.name = other.getName();
        this.whenToTest = other.getWhenToTest();
        this.conditions = new ArrayList<>(other.getConditions());
        this.amount = other.amount;
        this.associatedTraitName = other.associatedTraitName;
        this.chance = other.chance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WhenToTest getWhenToTest() {
        return whenToTest;
    }

    public void setWhenToTest(WhenToTest whenToTest) {
        this.whenToTest = whenToTest;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(String condition) {
        this.conditions.add(condition);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAssociatedTraitName() {
        return associatedTraitName;
    }

    public void setAssociatedTraitName(String associatedTraitName) {
        this.associatedTraitName = associatedTraitName;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public enum WhenToTest {
        ACQUISITIONMISSION("On Acquisition Mission"),
        AGENTCREATED("On Agent Creation"),
        CHARACTERCOMESOFAGE("Coming of Age"),
        CHARACTERSELECTED("Character Selected"),
        CHARACTERTURNEND("Character Turn End"),
        CHARACTERTURNENDINSETTLEMENT("Character Turn End in Settlement"),
        CHARACTERTURNSTART("Character Turn Start"),
        DIPLOMACYMISSION("Diplomacy Mission"),
        EXECUTESASPYONAMISSION("Spy Mission"),
        EXTERMINATEPOPULATION("Exterminate Population"),
        GENERALCAPTURESETTLEMENT("Capture Settlement"),
        GENERALDEVASTATESTILE("Devestates Tile"),
        GENERALPRISONERSRANSOMEDCAPTIVE("Ransomed Captives"),
        GENERALPRISONERSRANSOMEDCAPTOR("Ransomed as Cative"),
        GOVERNORAGENTCREATED("Governor when Agent Created"),
        GOVERNORBUILDINGCOMPLETED("Governor when Building Completed"),
        GOVERNORCITYREBELS("Governor when City Rebels"),
        GOVERNORCITYRIOTS("Governor when City Riots"),
        HIREMERCENARIES("Hires Mercs"),
        INSURRECTION("Insurrection"),
        GOVERNORUNITTRAINED("Governor when Unit Trained"),
        LEADERORDEREDDIPLOMACY("Leader when Diplimacy Ordered"),
        LEADERORDEREDSPYINGMISSION("Leader when Spy Mission Ordered"),
        LESSERGENERALOFFEREDFORADOPTION("Lesser General Offered Optiontion"),
        OCCUPYSETTLEMENT("Occupied Settlement"),
        OFFEREDFORADOPTION("Offered for Adoption"),
        OFFEREDFORMARRIAGE("Offered for Marriage"),
        PREBATTLEWITHDRAWAL("Prebattle Withdrawal"),
        POSTBATTLE("Postbattle"),
        SACKSETTLEMENT("Sacks Settlement"),
        SPYMISSION("Spy Mission"),
        SUFFERACQUISITIONATTEMPT("Targetted by Acquisition Attempt");

        private final String localizedName;

        WhenToTest(String localizedName) {
            this.localizedName = localizedName;
        }

        public String getLocalizedName() {
            return localizedName;
        }

        public static WhenToTest getValue(String value) {
            try {
                return WhenToTest.valueOf(value.toUpperCase().trim());
            } catch (Exception e) {
                System.out.println("Unable to parse value: " + value);
                e.printStackTrace();
            }

            return null;
        }
    }

    public static String translateCondition(String condition) {
        if (StringUtils.containsIgnoreCase(condition, "Culture")) {
            for (CharacterCulture culture: CharacterCulture.values()) {
                condition = StringUtils.replace(condition, culture.name().toLowerCase(), culture.getLocalizedName());
            }
        }

        if (StringUtils.containsIgnoreCase(condition, "Religion")) {
            for (Religion religion: Religion.values()) {
                condition = StringUtils.replace(condition, religion.name().toLowerCase(), religion.getLocalizedName());
            }
        }

        if (StringUtils.containsIgnoreCase(condition, "Faction")) {
            for (Faction faction: Faction.values()) {
                condition = StringUtils.replace(condition, faction.name().toLowerCase(), faction.getLocalizedName());
            }
        }

        return condition;
    }
}
