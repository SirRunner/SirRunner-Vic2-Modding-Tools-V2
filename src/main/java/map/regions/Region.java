package map.regions;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Region {
    protected String code;
    // TODO: Province objects?
    protected Set<Integer> provinces;
    protected String name;
    protected String comment;

    public static final String CODE = "code";
    public static final String PROVINCES = "provinces";
    public static final String NAME = "name";

    public static final String DEFAULT = "-";

    public static Set<String> HANDLED_COLUMNS = new HashSet<>(Arrays.asList(CODE, PROVINCES, NAME));

    public Region() {
        this.provinces = new HashSet<>();
    }

    public Region(Map<String, String> line) {
        this();

        for (String key : line.keySet()) {
            if (!HANDLED_COLUMNS.contains(key)) {
                Logger.info("File contains unhandled key: " + key);
            } else {
                String value = StringUtils.trim(line.get(key));
                if ((!StringUtils.equalsIgnoreCase(DEFAULT, value) || StringUtils.equalsIgnoreCase(line.get(CODE), "hyphen_loc")) && !StringUtils.isEmpty(value)) {
                    setByName(key, StringUtils.trim(value));
                }
            }
        }
    }

    public void setByName(String name, String value) {
        switch (name) {
            case CODE -> setCode(value);
            case PROVINCES -> setProvinces(value);
            case NAME -> setName(value);
        }
    }

    public Integer getSortingId() {
        String[] codeParts = StringUtils.split(code, "_");

        if (StringUtils.isNumeric(codeParts[1]) && !isLocRegion()) {
            return Integer.parseInt(codeParts[1]);
        } else if (!isLocRegion()) {
            Logger.error("Province id part of " + getCode() + " is not numeric");
        }

        return 0;
    }

    public boolean isLocRegion() {
        return StringUtils.endsWith(getCode(), "_loc") || StringUtils.contains(getCode(), "_loc_");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Integer> getProvinces() {
        return provinces;
    }

    public List<Integer> getProvincesInOrder() {
        return provinces.stream().sorted().collect(Collectors.toList());
    }

    public void setProvinces(Set<Integer> provinces) {
        this.provinces = provinces;
    }

    public void setProvinces(String provinces) {
        String[] provincesArray = StringUtils.split(provinces, " ");
        this.provinces = new HashSet<>();

        for (String province : provincesArray) {
            addProvince(province);
        }
    }

    public void addProvince(String province) {
        addProvince(Integer.parseInt(province));
    }

    public void addProvince(int provinceId) {
        if (this.provinces == null) {
            this.provinces = new HashSet<>();
        }

        if (this.provinces.contains(provinceId)) {
            Logger.error("Region " + code + " has province " + provinceId + "multiple times");
        }

        provinces.add(provinceId);
    }

    public int getLowestProvinceId() {
        if (this.provinces == null) {
            Logger.error("Region.getLowestProvinceId() called before provinces were defined");
            return 0;
        }

        return Collections.min(this.provinces);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void appendComment(String comment) {
        if (StringUtils.isEmpty(comment)) {
            return;
        }

        if (StringUtils.isEmpty(this.comment)) {
            setComment(comment);
            return;
        }

        this.comment += comment;
    }

    @Override
    public java.lang.String toString() {
        return getCode() + " = { " + StringUtils.join(getProvincesInOrder().toArray(), " ") + " }";
    }
}
