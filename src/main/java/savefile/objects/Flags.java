package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class Flags {
    private List<String> flags;

    public Flags() {
        init();
    }

    public void init() {
        if (flags == null) {
            flags = new ArrayList<>();
        }
    }

    public List<String> getFlags() {
        return flags;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public void addFlag(String flag) {
        this.flags.add(flag);
    }
}
