package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class IntegerList {
    private List<Integer> values;

    public IntegerList() {
        init();
    }

    public void init() {
        if (values == null) {
            values = new ArrayList<>();
        }
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public void addValue(int value) {
        this.values.add(value);
    }
}
