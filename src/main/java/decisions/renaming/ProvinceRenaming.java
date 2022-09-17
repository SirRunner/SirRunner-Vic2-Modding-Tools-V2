package decisions.renaming;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProvinceRenaming {
    // TODO: Province
    protected int provinceId;
    protected String startingName;
    // TODO: Turn into sets of CultureGroups
    protected Map<String, Set<String>> namesToCultureGroups;

    protected Map<String, String> cultureGroupToName = null;

    public static final String ID = "id";
    public static final String STARTING_NAME = "starting_name";

    public ProvinceRenaming() {
        this.namesToCultureGroups = new HashMap<>();
    }

    public static final String DEFAULT = "-";

    public ProvinceRenaming(Map<String, String> line) {
        this();

        for (String key : line.keySet()) {
            String value = StringUtils.trim(line.get(key));
            if (!StringUtils.equalsIgnoreCase(DEFAULT, value) && !StringUtils.isEmpty(value)) {
                setByName(key, StringUtils.trim(value));
            }
        }
    }

    protected void setByName(String name, String value) {
        switch(name) {
            case(ID) -> setProvinceId(value);
            case(STARTING_NAME) -> setStartingName(value);
            default -> addCultureToName(name, value);
        }
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setProvinceId(String provinceId) {
        setProvinceId(Integer.parseInt(provinceId));
    }

    public String getStartingName() {
        return startingName;
    }

    public void setStartingName(String startingName) {
        this.startingName = startingName;
    }

    protected void addCultureToName(String culture, String name) {
        if (!namesToCultureGroups.containsKey(name)) {
            namesToCultureGroups.put(name, new HashSet<>());
        }

        namesToCultureGroups.get(name).add(culture);
    }

    protected Map<String, String> getCultureGroupToName() {
        if (cultureGroupToName == null) {
            cultureGroupToName = new HashMap<>();

            for (String name : namesToCultureGroups.keySet()) {
                for (String cultureGroup : namesToCultureGroups.get(name)) {
                    cultureGroupToName.put(cultureGroup, name);
                }
            }
        }

        return cultureGroupToName;
    }

    public String getNameForCultureGroup(String cultureGroup) {
        Map<String, String> cultureGroupToName = getCultureGroupToName();

        if (cultureGroupToName.containsKey(cultureGroup)) {
            return cultureGroupToName.get(cultureGroup);
        }

        return "";
    }

    public Map<String, Set<String>> getNamesToCultureGroups() {
        return namesToCultureGroups;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(getNamesToCultureGroups());

        return string.toString();
    }
}
