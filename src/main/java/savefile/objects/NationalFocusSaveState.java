package savefile.objects;


import java.util.HashMap;
import java.util.Map;

public class NationalFocusSaveState {
    private Map<Integer, String> focuses;

    public NationalFocusSaveState() {
        init();
    }

    public void init() {
        if (focuses == null) {
            focuses = new HashMap<>();
        }
    }

    public Map<Integer, String> getFocuses() {
        return focuses;
    }

    public void setFocuses(Map<Integer, String> focuses) {
        this.focuses = focuses;
    }

    public void addFocus(int id, String focus) {
        this.focuses.put(id, focus);
    }
}
