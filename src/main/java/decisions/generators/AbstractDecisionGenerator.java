package decisions.generators;

import decisions.nodes.*;

import java.io.FileWriter;
import java.nio.charset.Charset;

/* Adds default values to an event -- this is assuming it is a triggered event */
public abstract class AbstractDecisionGenerator {
    protected String filename;
    protected String headerName;

    public AbstractDecisionGenerator(String filename, String headerName) {
        this.filename = filename;
        this.headerName = headerName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    protected abstract String getSectionName();

    protected abstract String getName();

    protected abstract Picture getPicture();

    protected abstract Potential getPotential();

    protected abstract Allow getAllow();

    protected abstract DecisionEffect getEffect();

    protected AIWillDo getAiWillDo() {
        return null;
    }

    public void generate() {

        PoliticalDecisions politicalDecisions = new PoliticalDecisions();
        politicalDecisions.setComment(getSectionName());

        Decision decision = new Decision();

        decision.setComment(getName());
        decision.setName(Decision.standardizeName(getName()));
        decision.setPicture(getPicture());
        decision.setPotential(getPotential());
        decision.setAllow(getAllow());
        decision.setDecisionEffect(getEffect());
        decision.setAiWillDo(getAiWillDo());

        politicalDecisions.addDecision(decision);

        try (FileWriter writer = new FileWriter(getFilename(), Charset.forName("windows-1252"))) {
            writer.write("# DO NOT MANUALLY UPDATE THIS FILE -- IS AUTO-GENERATED\n");
            writer.write("# Remove this line to have the claim get that interior arguments are correct\n");
            writer.write("# Audax Validator \"!\" Ignore_1009\n");
            writer.write("\n");
            writer.write("# " + getHeaderName() + ": {\n");
            writer.write("#\t" + getSectionName() + " }\n");
            writer.write("\n");
            writer.write(politicalDecisions.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save events!");
        }

    }

}
