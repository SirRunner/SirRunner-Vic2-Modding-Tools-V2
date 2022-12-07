package historyfile.pops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PopHistoryFile extends BasePopHistoryFile {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TOTAL_POP = "total_pop";
    public static final String FILENAME = "filename";
    public static final String BREAKDOWN_SUFFIX = "_breakdown";

    public static final String DEFAULT = "-";
    public static final String REST = "rest";

    public static Set<String> HANDLED_COLUMNS = new HashSet<>(Arrays.asList(ID, NAME));

    public PopHistoryFile() {
        this.pops = new ArrayList<>();
    }

    public void setByName(String key, String value) {
        switch (key) {
            case ID -> setProvinceId(value);
            case NAME -> setProvinceName(value);
        }
    }
}
