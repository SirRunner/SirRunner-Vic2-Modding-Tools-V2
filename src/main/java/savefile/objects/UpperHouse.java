package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class UpperHouse {
    Map<String, Double> upperHouse;

    public UpperHouse() {
        init();
    }

    public void init() {
        if (upperHouse == null) {
            upperHouse = new HashMap<>();
        }
    }

    public Map<String, Double> getUpperHouse() {
        return upperHouse;
    }

    public void setUpperHouse(Map<String, Double> upperHouse) {
        this.upperHouse = upperHouse;
    }

    public void addUpperHouse(String ideology, double value) {
        this.upperHouse.put(ideology, value);
    }
}
