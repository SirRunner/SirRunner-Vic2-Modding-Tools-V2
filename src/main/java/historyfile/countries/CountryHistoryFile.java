package historyfile.countries;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParsingUtils;

import java.util.*;

public class CountryHistoryFile {
    protected int capitalProvinceId;
    protected String primaryCulture;
    protected List<String> acceptedCultures;
    protected String religion;
    protected String government;
    protected String nationalValue;
    protected boolean civilized;
    protected int prestige;
    protected String rulingParty;
    protected Map<String, Integer> upperHouse;
    protected String techSchool;

    enum HandledColumns {
        CAPITAL,
        PRIMARY_CULTURE,
        CULTURE,
        RELIGION,
        GOVERNMENT,
        NATIONALVALUE,
        CIVILIZED,
        PRESTIGE,
        RULING_PARTY,
        UPPER_HOUSE,
        SCHOOLS;

        public static HandledColumns getByName(String value) {
            return Arrays.stream(HandledColumns.values()).filter(column -> StringUtils.equalsIgnoreCase(StringUtils.upperCase(value), column.name())).findFirst().orElse(null);
        }
    }

    public void addNodeByName(Node node) throws Exception {

        HandledColumns column = HandledColumns.getByName(node.getName());

        if (column == null) {
            Logger.error("Cannot parse node" + node);
            return;
        }

        switch (column) {
            case CAPITAL -> setCapitalProvinceId(node);
            case PRIMARY_CULTURE -> setPrimaryCulture(node);
            case CULTURE -> addAcceptedCulture(node);
            case RELIGION -> setReligion(node);
            case GOVERNMENT -> setGovernment(node);
            case NATIONALVALUE -> setNationalValue(node);
            case CIVILIZED -> setCivilized(node);
            case PRESTIGE -> setPrestige(node);
            case RULING_PARTY -> setRulingParty(node);
            case UPPER_HOUSE -> addUpperHouse(node);
            case SCHOOLS -> setTechSchool(node);
        }
    }

    public CountryHistoryFile() {
        this.acceptedCultures = new ArrayList<>();
        this.upperHouse = new HashMap<>();
    }

    public int getCapitalProvinceId() {
        return capitalProvinceId;
    }

    public void setCapitalProvinceId(int capitalProvinceId) {
        this.capitalProvinceId = capitalProvinceId;
    }

    public void setCapitalProvinceId(String capitalProvinceId) {
        if (StringUtils.isNumeric(capitalProvinceId)) {
            setCapitalProvinceId(Integer.parseInt(capitalProvinceId));
        } else {
            Logger.error("Capital province not numeric: " + capitalProvinceId);
        }
    }

    public void setCapitalProvinceId(Node node) {
        setCapitalProvinceId(node.getValue());
    }

    public String getPrimaryCulture() {
        return primaryCulture;
    }

    public void setPrimaryCulture(String primaryCulture) {
        this.primaryCulture = primaryCulture;
    }

    public void setPrimaryCulture(Node node) {
        setPrimaryCulture(node.getValue());
    }

    public List<String> getAcceptedCultures() {
        return acceptedCultures;
    }

    public void setAcceptedCultures(List<String> acceptedCultures) {
        this.acceptedCultures = acceptedCultures;
    }

    public void addAcceptedCulture(String acceptedCulture) {
        this.acceptedCultures.add(acceptedCulture);
    }

    public void addAcceptedCulture(Node node) {
        addAcceptedCulture(node.getValue());
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setReligion(Node node) {
        setReligion(node.getValue());
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public void setGovernment(Node node) {
        setGovernment(node.getValue());
    }

    public String getNationalValue() {
        return nationalValue;
    }

    public void setNationalValue(String nationalValue) {
        this.nationalValue = nationalValue;
    }

    public void setNationalValue(Node node) {
        setNationalValue(node.getValue());
    }

    public boolean isCivilized() {
        return civilized;
    }

    public void setCivilized(boolean civilized) {
        this.civilized = civilized;
    }

    public void setCivilized(String civilized) {
        setCivilized(StringUtils.equalsIgnoreCase(civilized, ParadoxParsingUtils.TRUE));
    }

    public void setCivilized(Node node) {
        setCivilized(node.getValue());
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public void setPrestige(String prestige) {
        if (StringUtils.isNumeric(prestige)) {
            setCapitalProvinceId(Integer.parseInt(prestige));
        } else {
            Logger.error("Capital province not numeric: " + prestige);
        }
    }

    public void setPrestige(Node node) {
        setPrestige(node.getValue());
    }

    public String getRulingParty() {
        return rulingParty;
    }

    public void setRulingParty(String rulingParty) {
        this.rulingParty = rulingParty;
    }

    public void setRulingParty(Node node) {
        setRulingParty(node.getValue());
    }

    public Map<String, Integer> getUpperHouse() {
        return upperHouse;
    }

    public void setUpperHouse(Map<String, Integer> upperHouse) {
        this.upperHouse = upperHouse;
    }

    public void addUpperHouse(String ideology, int value) {
        this.upperHouse.put(ideology, value);
    }

    public void addUpperHouse(String ideology, String value) {
        if (StringUtils.isNumeric(value)) {
            addUpperHouse(ideology, Integer.parseInt(value));
        } else {
            Logger.error("Ideology value is not an integer: " + ideology + " " + value);
        }
    }

    public void addUpperHouse(Node node) {
        for (Node childNode : node.getNodes()) {
            addUpperHouse(childNode.getName(), childNode.getValue());
        }
    }

    public String getTechSchool() {
        return techSchool;
    }

    public void setTechSchool(String techSchool) {
        this.techSchool = techSchool;
    }

    public void setTechSchool(Node node) {
        setTechSchool(node.getValue());
    }
}
