package decisions.renaming;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class RegionRenaming {
    // TODO: Region
    protected String regionCode;
    protected String startingName;
    protected String decisionRegion;
    protected String politicalDecisionsRegion;
    // TODO: Turn into sets of CultureGroups
    protected Map<String, Set<String>> namesToCultureGroups;
    protected String startingCulture;

    protected Map<String, String> cultureGroupToName = null;

    protected String localizationName = "";

    public static final String ID = "id";
    public static final String STARTING_NAME = "starting_name";
    public static final String DECISION_REGION = "decision_region";
    public static final String POLITICAL_DECISIONS_REGION = "pd_region";
    public static final String STARTING_CULTURE = "starting_culture";

    public RegionRenaming() {
        this.namesToCultureGroups = new HashMap<>();
    }

    public static final String DEFAULT = "-";

    public RegionRenaming(Map<String, String> line) {
        this();

        for (String key : line.keySet()) {
            String value = StringUtils.trim(line.get(key));
            if (!StringUtils.equalsIgnoreCase(DEFAULT, value) && !StringUtils.isEmpty(value)) {
                setByName(key, StringUtils.trim(value));
            }
        }
    }

    protected void setByName(String name, String value) {
        switch (name) {
            case (ID) -> setRegionCode(value);
            case (STARTING_NAME) -> setStartingName(value);
            case (DECISION_REGION) -> setDecisionRegion(value);
            case (POLITICAL_DECISIONS_REGION) -> setPoliticalDecisionsRegion(value);
            case (STARTING_CULTURE) -> setStartingCulture(value);
            default -> addCultureToName(name, value);
        }
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getStartingName() {
        return startingName;
    }

    public String getDecisionRegion() {
        return decisionRegion;
    }

    public void setDecisionRegion(String decisionRegion) {
        this.decisionRegion = decisionRegion;
    }

    public String getPoliticalDecisionsRegion() {
        return politicalDecisionsRegion;
    }

    public void setPoliticalDecisionsRegion(String politicalDecisionsRegion) {
        this.politicalDecisionsRegion = politicalDecisionsRegion;
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

    public Map<String, Set<String>> getNamesToCultureGroups() {
        return namesToCultureGroups;
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

    public String getStartingCulture() {
        return startingCulture;
    }

    public void setStartingCulture(String startingCulture) {
        this.startingCulture = startingCulture;
    }

    public String getNameForCultureGroup(String cultureGroup) {
        Map<String, String> cultureGroupToName = getCultureGroupToName();

        if (cultureGroupToName.containsKey(cultureGroup)) {
            return cultureGroupToName.get(cultureGroup);
        }

        return "";
    }

    public String getDecisionName() {
        return "rename_" + getDecisionRegion();
    }

    public String getVariableName() {
        return StringUtils.remove(StringUtils.stripAccents(StringUtils.replace(StringUtils.lowerCase(getStartingName()), " ", "_") + "_name"), "’");
    }

    public String getLocalizationName() {
        return getDecisionName() + "_title;Rename " + getDecisionRegionLocalization() + ";x\n";
    }

    protected String getDecisionRegionLocalization() {
        if (StringUtils.isEmpty(localizationName)) {
            localizationName = Arrays.stream(getDecisionRegion().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        }

        return localizationName;
    }

    public String getLocalizationDesc() {
        return getDecisionName() + "_desc;" + "A section of " + getDecisionRegionLocalization() + " has fallen under our control. We have alternative names for select provinces and states in the region. Let's use them.;x\n";
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(getNamesToCultureGroups());

        return string.toString();
    }
}
