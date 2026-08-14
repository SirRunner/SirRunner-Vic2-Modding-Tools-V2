package savefile.objects;

public class Employee {
    private ProvincePopId provincePopId;
    private int count;

    public Employee() {
        init();
    }

    private void init() {
        if (provincePopId == null) {
            provincePopId = new ProvincePopId();
        }
    }

    public ProvincePopId getProvincePopId() {
        return provincePopId;
    }

    public void setProvincePopId(ProvincePopId provincePopId) {
        this.provincePopId = provincePopId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
