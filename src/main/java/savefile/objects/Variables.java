package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private Map<String, Double> variables;

    public Variables() {
        init();
    }

    public void init() {
        if (variables == null) {
            variables = new HashMap<>();
        }
    }

    public Map<String, Double> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Double> variables) {
        this.variables = variables;
    }

    public void addVariable(String variable, double value) {
        this.variables.put(variable, value);
    }
}
