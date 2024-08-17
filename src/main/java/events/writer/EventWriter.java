package events.writer;

import events.nodes.Event;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

public class EventWriter {
    protected String filename;
    protected List<Event> events;
    protected String name;
    protected int startingId;
    protected int endingId;

    public EventWriter(String filename, List<Event> events, String name, int startingId, int endingId) {
        this.filename = filename;
        this.events = events;
        this.name = name;
        this.startingId = startingId;
        this.endingId = endingId;
    }

    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("windows-1252")))) {
            writer.write("# Remove this line to have the validator check for strings in localisation\n");
            writer.write("# Audax Validator \"!\" Ignore_1004\n");
            writer.write("\n");
            writer.write("# " + name + ": {\n");
            writer.write("#\t" + startingId + " - " + endingId + " }\n");
            for (Event event: events) {
                writer.write(event.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
