package events.generators;

import events.nodes.*;

import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;

/* Adds default values to an event -- this is assuming it is a triggered event */
public abstract class AbstractEventGenerator {
    protected String filename;
    protected String headerName;
    protected int lowerId;
    protected int upperId;

    public AbstractEventGenerator(String filename, String headerName, int lowerId, int upperId) {
        this.filename = filename;
        this.headerName = headerName;
        this.lowerId = lowerId;
        this.upperId = upperId;
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

    public int getLowerId() {
        return lowerId;
    }

    public void setLowerId(int lowerId) {
        this.lowerId = lowerId;
    }

    public int getUpperId() {
        return upperId;
    }

    public void setUpperId(int upperId) {
        this.upperId = upperId;
    }

    protected abstract int getId();

    protected abstract String getPicture();

    protected abstract String getTitle();

    protected String getDescription() {
        return "EVTDESC" + getId();
    }

    protected boolean getTriggeredOnly() {
        return true;
    }

    protected boolean getFireOnlyOnce() {
        return false;
    }

    protected Trigger getTrigger() {
        return null;
    }

    protected MTTH getMTTH() {
        return null;
    }

    protected Immediate getImmediate() {
        return null;
    }

    protected abstract List<Option> getOptions();

    public void generate() {

        Event event = new Event();

        event.setId(getId());
        event.setPicture(getPicture());
        event.setTitle(getTitle());
        event.setDescription(getDescription());
        event.setTriggeredOnly(getTriggeredOnly());
        event.setFireOnlyOnce(getFireOnlyOnce());
        event.setTrigger(getTrigger());
        event.setMeanTimeToHappen(getMTTH());
        event.setImmediate(getImmediate());
        event.setOptions(getOptions());

        try (FileWriter writer = new FileWriter(getFilename(), Charset.forName("windows-1252"))) {
            writer.write("# DO NOT MANUALLY UPDATE THIS FILE -- IS AUTO-GENERATED\n");
            writer.write("# Remove this line to have the validator check for strings in localisation\n");
            writer.write("# Audax Validator \"!\" Ignore_1004\n");
            writer.write("\n");
            writer.write("# " + getHeaderName() + " Events: {\n");
            writer.write("#\t" + getLowerId() + " - " + getUpperId() + " }\n");
            writer.write("\n");
            writer.write("# " + getTitle() + "\n");
            writer.write(event.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save events!");
        }

    }

}
