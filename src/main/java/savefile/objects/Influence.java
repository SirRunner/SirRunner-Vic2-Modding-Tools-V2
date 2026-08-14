package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class Influence {
    private Map<String, Integer> tagToValue;

    public Influence() {
        init();
    }

    public void init() {
        if (tagToValue == null) {
            tagToValue = new HashMap<>();
        }
    }

    public Map<String, Integer> getTagToValue() {
        return tagToValue;
    }

    public void setTagToValue(Map<String, Integer> tagToValue) {
        this.tagToValue = tagToValue;
    }

    public void addTagToValue(String tag, int value) {
        this.tagToValue.put(tag,value);
    }
}
