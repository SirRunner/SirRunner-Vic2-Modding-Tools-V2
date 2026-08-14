package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class StringList {
    private List<String> values;

    public StringList() {
        init();
    }

    public void init() {
        if (values == null) {
            values = new ArrayList<>();
        }
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        this.values.add(value);
    }
}
