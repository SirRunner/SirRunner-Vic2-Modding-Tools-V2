package decisions.nodes;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class PoliticalDecisions {
    List<Decision> decisions;
    String comment;

    public PoliticalDecisions() {
        this.decisions = new ArrayList<>();
    }

    public PoliticalDecisions(Node node) {
        this();

        for (Node innerNode : node.getNodes()) {
            // Comments that come before a political decisions get parsed as a separate node
            if (node.hasComment() && (node.getNodes() == null || node.getNodes().isEmpty())) {
                continue;
            }
            addDecision(new Decision(innerNode));
        }
    }

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<Decision> decisions) {
        for (Decision decision : decisions) {
            addDecision(decision);
        }
    }

    public void addDecision(Decision decision) {
        this.decisions.add(decision);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        if (!StringUtils.isEmpty(getComment())) {
            string.append("# ").append(getComment()).append("\n");
        }

        string.append("political_decisions = {\n");

        for (Decision decision : getDecisions()) {
            string.append(decision.toString());
        }

        string.append("}\n");

        return string.toString();
    }
}
