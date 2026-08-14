package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class WarBattleParticipant {
    private String type;
    private String country;
    private String leader;
    private Map<String, Integer> startingUnitsToCount;
    private int losses;

    public WarBattleParticipant() {
        init();
    }

    public void init() {
        if (startingUnitsToCount == null) {
            startingUnitsToCount = new HashMap<>();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Map<String, Integer> getStartingUnitsToCount() {
        return startingUnitsToCount;
    }

    public void setStartingUnitsToCount(Map<String, Integer> startingUnitsToCount) {
        this.startingUnitsToCount = startingUnitsToCount;
    }

    public void addStartingUnitToCount(String unit, int count) {
        this.startingUnitsToCount.put(unit, count);
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
