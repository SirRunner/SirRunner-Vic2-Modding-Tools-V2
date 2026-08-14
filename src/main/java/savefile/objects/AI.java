package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AI {
    private boolean initialized;
    private boolean consolidate;
    private LocalDate date;
    private boolean staticVar;
    private String personality;
    private List<ID> conquerProv;
    private List<ID> threat;
    private List<ID> antagonize;
    private List<ID> befriend;
    private List<ID> protect;
    private List<ID> rival;

    public AI() {
        init();
    }

    public void init() {
        if (conquerProv == null) {
            conquerProv = new ArrayList<>();
        }
        if (threat == null) {
            threat = new ArrayList<>();
        }
        if (antagonize == null) {
            antagonize = new ArrayList<>();
        }
        if (befriend == null) {
            befriend = new ArrayList<>();
        }
        if (protect == null) {
            protect = new ArrayList<>();
        }
        if (rival == null) {
            rival = new ArrayList<>();
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isConsolidate() {
        return consolidate;
    }

    public void setConsolidate(boolean consolidate) {
        this.consolidate = consolidate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isStaticVar() {
        return staticVar;
    }

    public void setStaticVar(boolean staticVar) {
        this.staticVar = staticVar;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public List<ID> getConquerProv() {
        return conquerProv;
    }

    public void setConquerProv(List<ID> conquerProv) {
        this.conquerProv = conquerProv;
    }

    public void addConquerProv(ID conquerProv) {
        this.conquerProv.add(conquerProv);
    }

    public List<ID> getThreat() {
        return threat;
    }

    public void setThreat(List<ID> threat) {
        this.threat = threat;
    }

    public void addThreat(ID threat) {
        this.threat.add(threat);
    }

    public List<ID> getAntagonize() {
        return antagonize;
    }

    public void setAntagonize(List<ID> antagonize) {
        this.antagonize = antagonize;
    }

    public void addAntagonize(ID antagonize) {
        this.antagonize.add(antagonize);
    }

    public List<ID> getBefriend() {
        return befriend;
    }

    public void setBefriend(List<ID> befriend) {
        this.befriend = befriend;
    }

    public void addBefriend(ID befriend) {
        this.befriend.add(befriend);
    }

    public List<ID> getProtect() {
        return protect;
    }

    public void setProtect(List<ID> protect) {
        this.protect = protect;
    }

    public void addProtect(ID protect) {
        this.protect.add(protect);
    }

    public List<ID> getRival() {
        return rival;
    }

    public void setRival(List<ID> rival) {
        this.rival = rival;
    }

    public void addRival(ID rival) {
        this.rival.add(rival);
    }
}
