package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class War {
    private String name;
    private WarHistory history;
    private List<String> attackers;
    private List<String> defenders;
    private String originalAttacker;
    private String originalDefender;
    private WarGoal originalWargoal;
    private LocalDate action;
    private List<WarGoal> wargoals;

    public void init() {
        if (history == null) {
            history = new WarHistory();
        }
        if (attackers == null) {
            attackers = new ArrayList<>();
        }
        if (defenders == null) {
            defenders = new ArrayList<>();
        }
        if (originalWargoal == null) {
            originalWargoal = new WarGoal();
        }
        if (wargoals == null) {
            wargoals = new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WarHistory getHistory() {
        return history;
    }

    public void setHistory(WarHistory history) {
        this.history = history;
    }

    public List<String> getAttackers() {
        return attackers;
    }

    public void setAttackers(List<String> attackers) {
        this.attackers = attackers;
    }

    public void addAttacker(String attacker) {
        this.attackers.add(attacker);
    }

    public List<String> getDefenders() {
        return defenders;
    }

    public void setDefenders(List<String> defenders) {
        this.defenders = defenders;
    }

    public void addDefender(String defender) {
        this.defenders.add(defender);
    }

    public String getOriginalAttacker() {
        return originalAttacker;
    }

    public void setOriginalAttacker(String originalAttacker) {
        this.originalAttacker = originalAttacker;
    }

    public String getOriginalDefender() {
        return originalDefender;
    }

    public void setOriginalDefender(String originalDefender) {
        this.originalDefender = originalDefender;
    }

    public WarGoal getOriginalWargoal() {
        return originalWargoal;
    }

    public void setOriginalWargoal(WarGoal originalWargoal) {
        this.originalWargoal = originalWargoal;
    }

    public LocalDate getAction() {
        return action;
    }

    public void setAction(LocalDate action) {
        this.action = action;
    }

    public List<WarGoal> getWargoals() {
        return wargoals;
    }

    public void setWargoals(List<WarGoal> wargoals) {
        this.wargoals = wargoals;
    }

    public void addWargoal(WarGoal warGoal) {
        this.wargoals.add(warGoal);
    }
}
