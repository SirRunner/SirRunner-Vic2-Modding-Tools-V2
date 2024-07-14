package historyfile.province;

public class Factory {
    private int level;
    private String type;

    public Factory(String type, int level) {
        this.level = level;
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
