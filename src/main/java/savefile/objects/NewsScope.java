package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsScope {
    private String type;
    private List<StringList> tags;
    private List<IntegerList> values;
    private List<StringList> strings;
    private List<LocalDate> dates;
    private String name;
    private double freshness;

    public NewsScope() {
        init();
    }

    public void init() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        if (values == null) {
            values = new ArrayList<>();
        }
        if (strings == null) {
            strings = new ArrayList<>();
        }
        if (dates == null) {
            dates = new ArrayList<>();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<StringList> getTags() {
        return tags;
    }

    public void setTags(List<StringList> tags) {
        this.tags = tags;
    }

    public void addTags(StringList tags) {
        this.tags.add(tags);
    }

    public List<IntegerList> getValues() {
        return values;
    }

    public void setValues(List<IntegerList> values) {
        this.values = values;
    }

    public void addValues(IntegerList values) {
        this.values.add(values);
    }

    public List<StringList> getStrings() {
        return strings;
    }

    public void setStrings(List<StringList> strings) {
        this.strings = strings;
    }

    public void addStrings(StringList strings) {
        this.strings.add(strings);
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public void addDate(LocalDate date) {
        this.dates.add(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFreshness() {
        return freshness;
    }

    public void setFreshness(double freshness) {
        this.freshness = freshness;
    }
}
