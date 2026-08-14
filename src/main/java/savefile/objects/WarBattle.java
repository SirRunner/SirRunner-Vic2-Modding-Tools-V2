package savefile.objects;

public class WarBattle {
    private String name;
    private int location;
    private boolean result;
    private WarBattleParticipant attacker;
    private WarBattleParticipant defender;

    public WarBattle() {
        init();
    }

    public void init() {
        if (attacker == null) {
            attacker = new WarBattleParticipant();
        }
        if (defender == null) {
            defender = new WarBattleParticipant();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public WarBattleParticipant getAttacker() {
        return attacker;
    }

    public void setAttacker(WarBattleParticipant attacker) {
        this.attacker = attacker;
    }

    public WarBattleParticipant getDefender() {
        return defender;
    }

    public void setDefender(WarBattleParticipant defender) {
        this.defender = defender;
    }
}
