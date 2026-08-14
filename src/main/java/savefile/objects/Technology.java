package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class Technology {
    List<TechnologyEntry> entries;

    public Technology() {
        init();
    }

    public void init() {
        if (entries == null) {
            entries = new ArrayList<>();
        }
    }

    public List<TechnologyEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TechnologyEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(TechnologyEntry entry) {
        this.entries.add(entry);
    }
}
