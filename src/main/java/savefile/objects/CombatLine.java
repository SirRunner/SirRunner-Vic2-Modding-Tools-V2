package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class CombatLine {
    private Map<Integer, ID> positionToUnit;

    public CombatLine() {
        init();
    }

    public void init() {
        if (positionToUnit == null) {
            positionToUnit = new HashMap<>();
        }
    }

    public Map<Integer, ID> getPositionToUnit() {
        return positionToUnit;
    }

    public void setPositionToUnit(Map<Integer, ID> positionToUnit) {
        this.positionToUnit = positionToUnit;
    }

    public void addPositionToUnit(int position, ID unit) {
        this.positionToUnit.put(position, unit);
    }
}
