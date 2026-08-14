package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class UnitData {
    List<UnitDataEntry> entries;

    public UnitData() {
        init();
    }

    public void init() {
        if (entries == null) {
            entries = new ArrayList<>();
        }
    }

    public List<UnitDataEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<UnitDataEntry> entries) {
        this.entries = entries;
    }
}
