package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class WarHistory {
    private List<WarBattle> battles;
    private List<WarEvent> events;

    public WarHistory() {
        init();
    }

    public void init() {
        if (battles == null) {
            battles = new ArrayList<>();
        }
        if (events == null) {
            events = new ArrayList<>();
        }
    }

    public List<WarBattle> getBattles() {
        return battles;
    }

    public void setBattles(List<WarBattle> battles) {
        this.battles = battles;
    }

    public void addBattle(WarBattle battle) {
        this.battles.add(battle);
    }

    public List<WarEvent> getEvents() {
        return events;
    }

    public void setEvents(List<WarEvent> events) {
        this.events = events;
    }

    public void addEvent(WarEvent event) {
        this.events.add(event);
    }
}
