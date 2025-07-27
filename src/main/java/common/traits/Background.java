package common.traits;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum Background {
    NOBLEMAN_BAD(-1, -1, 0, -0.05, 0, 0),
    NOBLEMAN(0, 0, 0, 0, 0, 0),
    NOBLEMAN_GOOD(1, 1, 0.05, 0.05, 0, 0),
    OFFICER(1, 1, 0.1, 0.1, 0, 0.25);

    private final int attack;
    private final int defence;
    private final double morale;
    private final double organisation;
    private final double speed;
    private final double experience;

    Background(int attack, int defence, double morale, double organisation, double speed, double experience) {
        this.attack = attack;
        this.defence = defence;
        this.morale = morale;
        this.organisation = organisation;
        this.speed = speed;
        this.experience = experience;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public double getMorale() {
        return morale;
    }

    public double getOrganisation() {
        return organisation;
    }

    public double getSpeed() {
        return speed;
    }

    public double getExperience() {
        return experience;
    }

    /* Returns traits with positive attack and defence */
    public static List<Background> getGoodTraits() {
        return Arrays.stream(Background.values()).filter(personality -> personality.getAttack() > 0 && personality.getDefence() > 0).collect(Collectors.toList());
    }

    /* Returns traits with 0 attack and defence */
    public static List<Background> getNeutralTraits() {
        return Arrays.stream(Background.values()).filter(personality -> personality.getAttack() == 0 && personality.getDefence() == 0).collect(Collectors.toList());
    }

    /* Returns traits with negative attack and defence */
    public static List<Background> getBadTraits() {
        return Arrays.stream(Background.values()).filter(personality -> personality.getAttack() < 0 && personality.getDefence() < 0).collect(Collectors.toList());
    }

    public static Background getRandomBackground() {
        Random random = new Random();
        int index = random.nextInt(Background.values().length);

        return Background.values()[index];
    }
}
