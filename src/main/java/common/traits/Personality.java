package common.traits;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum Personality {
    BRASH(1, 0, 0, -0.05, 0, 0),
    GLORY_HOUND(1, 0, -0.05, 0, 0, 0),
    AGGRESSIVE(1, 0, 0, 0, 0, 0),
    PERSISTENT(1, 0, 0, 0, 0.05, 0),
    BALLSY(1, 0, 0, 0.05, 0, 0),
    INSPIRING(1, 0, 0.05, 0, 0, 0),
    RELENTLESS(2, 0, 0, 0, 0, 0),
    CAUTIOUS(0, 1, 0, 0, -0.05, 0),
    MEDIOCRE(0, 1, 0, -0.05, 0, 0),
    UNGALLANT(0, 1, -0.05, 0, 0, 0),
    DEFENSIVE(0, 1, 0, 0, 0, 0),
    DEFIANT(0, 1, 0, 0, 0.05, 0),
    RESOLUTE(0, 1, 0, 0.05, 0, 0),
    STOUT(0, 1, 0.05, 0, 0, 0),
    RESERVED(0, 2, 0, 0, 0, 0),
    DELIBERATE(1, 1, 0, 0, -0.05, 0),
    BIGOTED(1, 1, 0, -0.05, 0, 0),
    HARSH(1, 1, -0.05, 0, 0, 0),
    SMART(1, 1, 0, 0, 0, 0),
    TACTICAL(1, 1, 0, 0, 0.05, 0),
    STALWART(1, 1, 0, 0.05, 0, 0),
    HEROIC(1, 1, 0.05, 0, 0, 0);

    private final int attack;
    private final int defence;
    private final double morale;
    private final double organisation;
    private final double speed;
    private final double experience;

    Personality(int attack, int defence, double morale, double organisation, double speed, double experience) {
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

    /* Returns traits with only attack */
    public static List<Personality> getAttackOnlyTraits() {
        return Arrays.stream(Personality.values()).filter(personality -> personality.getDefence() == 0 && personality.getAttack() != 0).collect(Collectors.toList());
    }

    /* Returns traits with attack and any amount of defence */
    public static List<Personality> getAttackTraits() {
        return Arrays.stream(Personality.values()).filter(personality -> personality.getAttack() != 0).collect(Collectors.toList());
    }

    /* Returns traits with only defence */
    public static List<Personality> getDefenceOnlyTraits() {
        return Arrays.stream(Personality.values()).filter(personality -> personality.getAttack() == 0 && personality.getDefence() != 0).collect(Collectors.toList());
    }

    /* Returns traits with attack and any amount of defence */
    public static List<Personality> getDefenceTraits() {
        return Arrays.stream(Personality.values()).filter(personality -> personality.getDefence() != 0).collect(Collectors.toList());
    }

    /* Returns traits with attack and defence */
    public static List<Personality> getCombinationTraits() {
        return Arrays.stream(Personality.values()).filter(personality -> personality.getAttack() != 0 && personality.getDefence() != 0).collect(Collectors.toList());
    }

    public static Personality getRandomPersonality() {
        Random random = new Random();
        int index = random.nextInt(Personality.values().length);

        return Personality.values()[index];
    }

    public static Personality getRandomCombinationPersonality() {
        Random random = new Random();
        int index = random.nextInt(getCombinationTraits().size());

        return getCombinationTraits().get(index);
    }
}
