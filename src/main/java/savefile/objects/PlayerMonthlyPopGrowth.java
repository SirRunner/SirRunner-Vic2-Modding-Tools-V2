package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class PlayerMonthlyPopGrowth {
    private List<Double> values;

    public PlayerMonthlyPopGrowth() {
        init();
    }

    public void init() {
        if (values == null) {
            values = new ArrayList<>();
        }
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public void addValue(double value) {
        this.values.add(value);
    }
}
