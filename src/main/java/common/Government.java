package common;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParsingUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Government {
    protected String name;
    protected Map<Ideology, Boolean> allowedIdeologies;
    protected boolean election;
    protected int duration;
    protected boolean appointRulingParty;
    protected FlagType flagType = FlagType.STANDARD;

    public static final String HIGH_NOBILITY = "high_nobility";
    public static final String LOW_NOBILITY = "low_nobility";
    public static final String BURGHER = "burgher";
    public static final String LOREMASTER = "loremaster";
    public static final String SERVANTS = "servants";
    public static final String ELECTION = "election";
    public static final String DURATION = "duration";
    public static final String APPOINT_RULING_PARTY = "appoint_ruling_party";
    public static final String FLAG_TYPE = "flagtype";

    protected static Map<String, Ideology> ideologyMap;

    public Government() {
        this.allowedIdeologies = new HashMap<>();
    }

    public Government(Node node) {
        this();

        setName(node.getName());

        for (Node childNode : node.getNodes()) {
            if (childNode.hasComment()) {
                continue;
            }

            addNodeByName(childNode);
        }

    }

    protected void addNodeByName(Node node) {
        switch (node.getName().toLowerCase()) {
            case HIGH_NOBILITY -> addIdeology(node, getIdeology(HIGH_NOBILITY));
            case LOW_NOBILITY -> addIdeology(node, getIdeology(LOW_NOBILITY));
            case BURGHER -> addIdeology(node, getIdeology(BURGHER));
            case LOREMASTER -> addIdeology(node, getIdeology(LOREMASTER));
            case SERVANTS -> addIdeology(node, getIdeology(SERVANTS));
            case ELECTION -> setElection(node);
            case DURATION -> setDuration(node);
            case APPOINT_RULING_PARTY -> setAppointRulingParty(node);
            case FLAG_TYPE -> setFlagType(node);
        }
    }

    public static void initIdeologies(List<Ideology> ideologies) {

        ideologyMap = new HashMap<>();

        for (Ideology ideology : ideologies) {
            ideologyMap.put(ideology.getName().toLowerCase(), ideology);
        }
    }

    private static Ideology getIdeology(String ideologyName) {
        if (ideologyMap == null) {
            Logger.error("Ideology map was never configured");
        }

        return ideologyMap.get(ideologyName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Ideology, Boolean> getAllowedIdeologies() {
        return allowedIdeologies;
    }

    public void setAllowedIdeologies(Map<Ideology, Boolean> allowedIdeologies) {
        this.allowedIdeologies = allowedIdeologies;
    }

    public void addIdeology(Node node, Ideology ideology) {
        addIdeology(ParadoxParsingUtils.getBooleanFromNode(node), ideology);
    }

    public void addIdeology(boolean value, Ideology ideology) {
        allowedIdeologies.put(ideology, value);
    }

    public boolean isElection() {
        return election;
    }

    public void setElection(boolean election) {
        this.election = election;
    }

    public void setElection(Node node) {
        setElection(ParadoxParsingUtils.getBooleanFromNode(node));
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDuration(Node node) {
        if (!StringUtils.isNumeric(node.getValue())) {
            Logger.error("Non-numeric value passed into election duration: " + node.getValue());
            return;
        }

        setDuration(Integer.parseInt(node.getValue()));
    }

    public boolean isAppointRulingParty() {
        return appointRulingParty;
    }

    public void setAppointRulingParty(boolean appointRulingParty) {
        this.appointRulingParty = appointRulingParty;
    }

    public void setAppointRulingParty(Node node) {
        setAppointRulingParty(ParadoxParsingUtils.getBooleanFromNode(node));
    }

    public FlagType getFlagType() {
        return flagType;
    }

    public void setFlagType(FlagType flagType) {
        this.flagType = flagType;
    }

    public void setFlagType(Node node) {
        setFlagType(node.getValue());
    }

    public void setFlagType(String value) {
        setFlagType(FlagType.getFlagTypeFromString(value));
    }

    public enum FlagType {
        REPUBLIC,
        MONARCHY,
        STANDARD,
        COSMETIC_1,
        COSMETIC_2;

        public static FlagType getFlagTypeFromString(String flagType) {
            return Arrays.stream(FlagType.values()).filter(potentialMatch -> StringUtils.equalsIgnoreCase(flagType, potentialMatch.name())).findFirst().orElse(null);
        }
    }
}
