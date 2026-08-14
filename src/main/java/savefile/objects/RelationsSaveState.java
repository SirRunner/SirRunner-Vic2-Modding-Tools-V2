package savefile.objects;

import java.time.LocalDate;

public class RelationsSaveState {
    private int value;
    private boolean militaryAccess;
    private LocalDate lastSendDiplomat;
    private LocalDate lastWar;
    private LocalDate truceUntil;
    private int level;
    private LocalDate levelChangedDate;
    private double influenceValue;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isMilitaryAccess() {
        return militaryAccess;
    }

    public void setMilitaryAccess(boolean militaryAccess) {
        this.militaryAccess = militaryAccess;
    }

    public LocalDate getLastSendDiplomat() {
        return lastSendDiplomat;
    }

    public void setLastSendDiplomat(LocalDate lastSendDiplomat) {
        this.lastSendDiplomat = lastSendDiplomat;
    }

    public LocalDate getLastWar() {
        return lastWar;
    }

    public void setLastWar(LocalDate lastWar) {
        this.lastWar = lastWar;
    }

    public LocalDate getTruceUntil() {
        return truceUntil;
    }

    public void setTruceUntil(LocalDate truceUntil) {
        this.truceUntil = truceUntil;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LocalDate getLevelChangedDate() {
        return levelChangedDate;
    }

    public void setLevelChangedDate(LocalDate levelChangedDate) {
        this.levelChangedDate = levelChangedDate;
    }

    public double getInfluenceValue() {
        return influenceValue;
    }

    public void setInfluenceValue(double influenceValue) {
        this.influenceValue = influenceValue;
    }
}
