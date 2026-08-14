package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class Combat {
    private List<CombatEntry> entries;

    public Combat() {
        init();
    }

    public void init() {
        if (entries == null) {
            entries = new ArrayList<>();
        }
    }

    public List<CombatEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<CombatEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(CombatEntry entry) {
        this.entries.add(entry);
    }
}
