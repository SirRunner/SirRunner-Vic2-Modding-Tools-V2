package savefile.objects;

public class UnitNames {
    private UnitData data;

    public UnitNames() {
        init();
    }

    public void init() {
        if (data == null) {
            data = new UnitData();
        }
    }

    public UnitData getData() {
        return data;
    }

    public void setData(UnitData data) {
        this.data = data;
    }
}
