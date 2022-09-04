package map.adjacencies;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.ParadoxParsingUtils;
import utils.Utils;

import java.util.*;

public class Adjacency {
    // Todo: Province objects?
    protected int fromProvince;
    // Todo: Province objects?
    protected int toProvince;
    protected String type;
    // Todo: Province objects?
    protected int through;
    protected int data;
    protected String comment;

    public static final String FROM_PROVINCE = "prov1";
    public static final String TO_PROVINCE = "prov2";
    public static final String TYPE = "type";
    public static final String THROUGH = "through";
    public static final String DATA = "data";
    public static final String COMMENT = "comment";

    public static final String STRAIGHTS = "sea";
    public static final String IMPASSABLES = "impassable";
    public static final String CANALS = "canal";

    public static final String DEFAULT = "-";

    public static Set<String> HANDLED_COLUMNS = new HashSet<>(Arrays.asList(FROM_PROVINCE, TO_PROVINCE, TYPE, THROUGH, DATA, COMMENT));
    public static Set<String> ALL_TYPES = new LinkedHashSet<>(Arrays.asList(STRAIGHTS, IMPASSABLES, CANALS));

    public Adjacency() {
    }

    public Adjacency(Map<String, String> line) {
        this();

        for (String key : line.keySet()) {
            if (!HANDLED_COLUMNS.contains(key)) {
                Logger.info("File contains unhandled key: " + key);
            } else {
                String value = StringUtils.trim(line.get(key));
                if (!StringUtils.equalsIgnoreCase(DEFAULT, value) && !StringUtils.isEmpty(value)) {
                    setByName(key, StringUtils.trim(value));
                }
            }
        }
    }

    public void setByName(String name, String value) {
        switch (name) {
            case FROM_PROVINCE -> setFromProvince(value);
            case TO_PROVINCE -> setToProvince(value);
            case TYPE -> setType(value);
            case THROUGH -> setThrough(value);
            case DATA -> setData(value);
            case COMMENT -> setComment(value);
        }
    }

    public int getFromProvince() {
        return fromProvince;
    }

    public void setFromProvince(int fromProvince) {
        this.fromProvince = fromProvince;
    }

    public void setFromProvince(String fromProvince) {
        try {
            setFromProvince(Integer.parseInt(fromProvince));
        } catch (NumberFormatException e) {
            setFromProvince(0);
        }
    }

    public int getToProvince() {
        return toProvince;
    }

    public void setToProvince(int toProvince) {
        this.toProvince = toProvince;
    }

    public void setToProvince(String toProvince) {
        try {
            setToProvince(Integer.parseInt(toProvince));
        } catch (NumberFormatException e) {
            setToProvince(0);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getThrough() {
        return through;
    }

    public void setThrough(int through) {
        this.through = through;
    }

    public void setThrough(String through) {
        try {
            setThrough(Integer.parseInt(through));
        } catch (NumberFormatException e) {
            setThrough(0);
        }
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setData(String data) {
        try {
            setData(Integer.parseInt(data));
        } catch (NumberFormatException e) {
            setData(0);
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        List<String> fields = Arrays.asList(String.valueOf(getFromProvince()), String.valueOf(getToProvince()), getType(), String.valueOf(getThrough()), String.valueOf(getData()), getComment());

        return Utils.getCSV(fields);
    }
}
