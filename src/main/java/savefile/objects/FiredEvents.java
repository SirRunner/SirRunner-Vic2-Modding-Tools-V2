package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class FiredEvents {
    List<FiredEvent> firedEvents;

    public FiredEvents() {
        init();
    }

    public void init() {
        if (firedEvents == null) {
            firedEvents = new ArrayList<>();
        }
    }

    public List<FiredEvent> getFiredEvents() {
        return firedEvents;
    }

    public void setFiredEvents(List<FiredEvent> firedEvents) {
        this.firedEvents = firedEvents;
    }

    public void addFiredEvent(FiredEvent firedEvent) {
        this.firedEvents.add(firedEvent);
    }
}
