package common;

import historyfile.countries.CountryHistoryFile;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Country {
    protected String tag;
    protected String pathToDefinition;
    protected boolean dynamic;
    protected String adjective;

    public Country(String tag, String pathToDefinition) {
        this.tag = tag;
        this.pathToDefinition = pathToDefinition;
    }

    public Country(Node node) {
        this(node, false);
    }

    public Country(Node node, boolean dynamic) {
        setTag(node.getName());
        setPathToDefinition(node.getValue());
        setDynamic(dynamic);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPathToDefinition() {
        return pathToDefinition;
    }

    public void setPathToDefinition(String pathToDefinition) {
        this.pathToDefinition = StringUtils.remove(pathToDefinition, "\"");
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getAdjective() {
        return adjective;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public static class CountryDefinition {
        protected String graphicalCulture;
        protected Color color;
        protected List<Party> parties;

        protected enum HandledColumns {
            GRAPHICAL_CULTURE,
            COLOR,
            PARTY;

            public static HandledColumns getByName(String value) {
                return Arrays.stream(HandledColumns.values()).filter(column -> StringUtils.equalsIgnoreCase(StringUtils.upperCase(value), column.name())).findFirst().orElse(null);
            }
        }

        public void addNodeByName(Node node) {

            HandledColumns column = HandledColumns.getByName(node.getName());

            if (column == null) {
                Logger.error("Cannot parse node" + node);
                return;
            }

            switch (column) {
                case GRAPHICAL_CULTURE -> setGraphicalCulture(node);
                case COLOR -> setColor(node);
                case PARTY -> addParty(node);
            }
        }

        public CountryDefinition() {
            this.parties = new ArrayList<>();
        }

        public String getGraphicalCulture() {
            return graphicalCulture;
        }

        public void setGraphicalCulture(String graphicalCulture) {
            this.graphicalCulture = graphicalCulture;
        }

        public void setGraphicalCulture(Node node) {
            setGraphicalCulture(node.getValue());
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setColor(int red, int green, int blue) {
            setColor(new Color(red, green, blue));
        }

        public void setColor(String red, String green, String blue) {
            setColor(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        }

        public void setColor(Node node) {
            String[] parts = node.getValue().split("\s");

            setColor(parts[0], parts[1], parts[2]);
        }

        public List<Party> getParties() {
            return parties;
        }

        public void setParties(List<Party> parties) {
            this.parties = parties;
        }

        public void addParty(Party party) {
            this.parties.add(party);
        }

        public void addParty(Node node) {
            addParty(new Party(node));
        }

        public boolean hasParty(String ideology) {
            return parties.stream().anyMatch(party -> StringUtils.equalsIgnoreCase(ideology, party.getIdeology()));
        }
    }

    public static class Party {
        protected String name;
        protected String startDate;
        protected String endDate;
        protected String ideology;
        protected Map<String, String> issueToPolicy;

        protected enum HandledColumns {
            NAME,
            START_DATE,
            END_DATE,
            IDEOLOGY;

            public static HandledColumns getByName(String value) {
                return Arrays.stream(HandledColumns.values()).filter(column -> StringUtils.equalsIgnoreCase(StringUtils.upperCase(value), column.name())).findFirst().orElse(null);
            }
        }

        public void addNodeByName(Node node) {

            HandledColumns column = HandledColumns.getByName(node.getName());

            if (column == null) {
                addIssue(node);
                return;
            }

            switch (column) {
                case NAME -> setName(node);
                case START_DATE -> setStartDate(node);
                case END_DATE -> setEndDate(node);
                case IDEOLOGY -> setIdeology(node);
            }
        }

        public Party() {
            this.issueToPolicy = new HashMap<>();
        }

        public Party(Node node) {
            this();

            for (Node childNode: node.getNodes()) {
                addNodeByName(childNode);
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setName(Node node) {
            setName(node.getValue());
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public void setStartDate(Node node) {
            setStartDate(node.getValue());
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public void setEndDate(Node node) {
            setEndDate(node.getValue());
        }

        public String getIdeology() {
            return ideology;
        }

        public void setIdeology(String ideology) {
            this.ideology = ideology;
        }

        public void setIdeology(Node node) {
            setIdeology(node.getValue());
        }

        public Map<String, String> getIssueToPolicy() {
            return issueToPolicy;
        }

        public void setIssueToPolicy(Map<String, String> issueToPolicy) {
            this.issueToPolicy = issueToPolicy;
        }

        public void addIssue(String issue, String policy) {
            this.issueToPolicy.put(issue, policy);
        }

        public void addIssue(Node node) {
            addIssue(node.getName(), node.getValue());
        }
    }
}
