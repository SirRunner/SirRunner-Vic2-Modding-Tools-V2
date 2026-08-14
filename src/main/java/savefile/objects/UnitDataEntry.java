package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class UnitDataEntry {
    private int count;
    private IntegerList ids;

    public UnitDataEntry() {
        init();
    }

    public void init() {
        if (ids == null) {
            ids = new IntegerList();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public IntegerList getIds() {
        return ids;
    }

    public void setIds(IntegerList ids) {
        this.ids = ids;
    }
}
