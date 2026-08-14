package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class IdeologySaveState {
    Map<Integer, Double> ideologies;

    public IdeologySaveState() {
        init();
    }

    private void init() {
        if (ideologies == null) {
            ideologies = new HashMap<>();
        }
    }

    public Map<Integer, Double> getIdeologies() {
        return ideologies;
    }

    public void setIdeologies(Map<Integer, Double> ideologies) {
        this.ideologies = ideologies;
    }

    public void addIdeology(int ideology, double value) {
        this.ideologies.put(ideology, value);
    }
}
