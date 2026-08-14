package savefile.objects;

import java.time.LocalDate;

public class IdeologyEnabledDate {
    private String ideology;
    private LocalDate enabledDate;

    public String getIdeology() {
        return ideology;
    }

    public void setIdeology(String ideology) {
        this.ideology = ideology;
    }

    public LocalDate getEnabledDate() {
        return enabledDate;
    }

    public void setEnabledDate(LocalDate enabledDate) {
        this.enabledDate = enabledDate;
    }
}
