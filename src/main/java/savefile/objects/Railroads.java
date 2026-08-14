package savefile.objects;

public class Railroads {
    private IntegerList path;

    public Railroads() {
        init();
    }

    public void init() {
        if (path == null) {
            path = new IntegerList();
        }
    }

    public IntegerList getPath() {
        return path;
    }

    public void setPath(IntegerList path) {
        this.path = path;
    }
}
