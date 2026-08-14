package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class Diplomacy {
    public List<DiplomacyEntry> entries;

    public Diplomacy() {
        init();
    }

    public void init() {
        if (entries == null) {
            entries = new ArrayList<>();
        }
    }

    public List<DiplomacyEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DiplomacyEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(DiplomacyEntry entry) {
        this.entries.add(entry);
    }
}
