package events.generators;

import events.nodes.*;

import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMultiEventGenerator extends AbstractEventGenerator {
    public AbstractMultiEventGenerator(String filename, String headerName, int lowerId, int upperId) {
        super(filename, headerName, lowerId, upperId);
    }

    protected List<Event> getEvents() throws Exception {

        List<Event> events = new ArrayList<>();

        for (int id = getLowerId(); id <= getUpperId(); id++) {
            Event event = getEvent(id);

            if (event != null) {
                events.add(event);
            }
        }

        return events;
    }

    protected Event getEvent(int id) throws Exception {

        Event event = new Event();

        event.setId(id);
        event.setPicture(getPicture(id));
        event.setTitle(getTitle(id));
        event.setDescription(getDescription(id));
        event.setTriggeredOnly(getTriggeredOnly(id));
        event.setFireOnlyOnce(getFireOnlyOnce(id));
        event.setTrigger(getTrigger(id));
        event.setMeanTimeToHappen(getMTTH(id));
        event.setImmediate(getImmediate(id));
        event.setOptions(getOptions(id));

        return event;

    }

    /* This function should not get called */
    protected int getId() {
        return 0;
    }

    /* This function should not get called */
    protected String getTitle() {
        return "";
    }

    /* This function should not get called */
    protected List<Option> getOptions() {
        return Collections.emptyList();
    }

    protected String getPicture(int id) {
        return getPicture();
    }

    protected abstract String getTitle(int id);

    protected String getDescription(int id) {
        return "EVTDESC" + id;
    }

    protected boolean getTriggeredOnly(int id) {
        return getTriggeredOnly();
    }

    protected boolean getFireOnlyOnce(int id) {
        return getFireOnlyOnce();
    }

    protected Trigger getTrigger(int id) {
        return getTrigger();
    }

    protected MTTH getMTTH(int id) {
        return getMTTH();
    }

    protected Immediate getImmediate(int id) {
        return getImmediate();
    }

    protected abstract List<Option> getOptions(int id) throws Exception;

    public void generate() {

        try (FileWriter writer = new FileWriter(getFilename(), Charset.forName("windows-1252"))) {
            writer.write("# DO NOT MANUALLY UPDATE THIS FILE -- IS AUTO-GENERATED\n");
            writer.write("# Remove this line to have the validator check for strings in localisation\n");
            writer.write("# Audax Validator \"!\" Ignore_1004\n");
            writer.write("\n");
            writer.write("# " + getHeaderName() + " Events: {\n");
            writer.write("#\t" + getLowerId() + " - " + getUpperId() + " }\n");

            for (Event event : getEvents()) {
                writer.write("\n");
                writer.write("# " + event.getTitle() + "\n");
                writer.write(event.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save events!");
        }

    }
}
