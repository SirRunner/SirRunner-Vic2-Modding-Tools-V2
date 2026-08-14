package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class IssueSaveState {
    Map<Integer, Double> issues;

    public IssueSaveState() {
        init();
    }

    private void init() {
        if (issues == null) {
            issues = new HashMap<>();
        }
    }

    public Map<Integer, Double> getIssues() {
        return issues;
    }

    public void setIssues(Map<Integer, Double> issues) {
        this.issues = issues;
    }

    public void addIssue(int ideology, double value) {
        this.issues.put(ideology, value);
    }
}
