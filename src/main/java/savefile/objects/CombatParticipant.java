package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class CombatParticipant {
    private int dice;
    private List<ID> units;
    private double losses;
    private DoubleList accumulatedLosses;
    private List<CombatUnit> combatUnits;
    private CombatLine front;
    private CombatLine back;
    private List<ID> reserves;

    public CombatParticipant() {
        init();
    }

    public void init() {
        if (units == null) {
            units = new ArrayList<>();
        }
        if (accumulatedLosses == null) {
            accumulatedLosses = new DoubleList();
        }
        if (combatUnits == null) {
            combatUnits = new ArrayList<>();
        }
        if (front == null) {
            front = new CombatLine();
        }
        if (back == null) {
            back = new CombatLine();
        }
        if (reserves == null) {
            reserves = new ArrayList<>();
        }
    }

    public int getDice() {
        return dice;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public List<ID> getUnits() {
        return units;
    }

    public void setUnits(List<ID> units) {
        this.units = units;
    }

    public void addUnit(ID unit) {
        this.units.add(unit);
    }

    public double getLosses() {
        return losses;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }

    public DoubleList getAccumulatedLosses() {
        return accumulatedLosses;
    }

    public void setAccumulatedLosses(DoubleList accumulatedLosses) {
        this.accumulatedLosses = accumulatedLosses;
    }

    public List<CombatUnit> getCombatUnits() {
        return combatUnits;
    }

    public void setCombatUnits(List<CombatUnit> combatUnits) {
        this.combatUnits = combatUnits;
    }

    public void addCombatUnit(CombatUnit combatUnit) {
        this.combatUnits.add(combatUnit);
    }

    public CombatLine getFront() {
        return front;
    }

    public void setFront(CombatLine front) {
        this.front = front;
    }

    public CombatLine getBack() {
        return back;
    }

    public void setBack(CombatLine back) {
        this.back = back;
    }

    public List<ID> getReserves() {
        return reserves;
    }

    public void setReserves(List<ID> reserves) {
        this.reserves = reserves;
    }

    public void addReserve(ID reserve) {
        this.reserves.add(reserve);
    }
}
