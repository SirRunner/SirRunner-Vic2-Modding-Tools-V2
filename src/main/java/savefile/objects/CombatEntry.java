package savefile.objects;

public class CombatEntry {
    private int location;
    private int day;
    private int duration;
    private CombatParticipant attacker;
    private CombatParticipant defender;
    private int total;

    public CombatEntry() {
        init();
    }

    public void init() {
        if (attacker == null) {
            attacker = new CombatParticipant();
        }
        if (defender == null) {
            defender = new CombatParticipant();
        }
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CombatParticipant getAttacker() {
        return attacker;
    }

    public void setAttacker(CombatParticipant attacker) {
        this.attacker = attacker;
    }

    public CombatParticipant getDefender() {
        return defender;
    }

    public void setDefender(CombatParticipant defender) {
        this.defender = defender;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
