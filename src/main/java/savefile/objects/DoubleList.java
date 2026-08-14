package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class DoubleList {
    private List<Double> values;

    public DoubleList() {
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
