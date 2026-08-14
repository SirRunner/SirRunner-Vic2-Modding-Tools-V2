package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class Trade {
    private List<TradeEntry> entries;

    public Trade() {
        init();
    }

    public void init() {
        if (entries == null) {
            this.entries = new ArrayList<>();
        }
    }

    public List<TradeEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TradeEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(TradeEntry entry) {
        this.entries.add(entry);
    }
}
