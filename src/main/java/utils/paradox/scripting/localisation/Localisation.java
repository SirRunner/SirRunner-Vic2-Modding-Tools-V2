package utils.paradox.scripting.localisation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Localisation {
    protected String title;
    protected Map<Language, String> localisationForLanguage;

    public Localisation() {
        localisationForLanguage = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Language, String> getLocalisationForLanguage() {
        return localisationForLanguage;
    }

    public String getLocalisation(Language language) {
        return localisationForLanguage.getOrDefault(language, "");
    }

    public String getLocalisation() {
        return getLocalisation(Language.ENGLISH);
    }

    public void setLocalisationForLanguage(Map<Language, String> localisationForLanguage) {
        this.localisationForLanguage = localisationForLanguage;
    }

    public void setLocalisation(Language language, String text) {
        localisationForLanguage.put(language, text);
    }

    public enum Language {
        ENGLISH,
        FRENCH,
        GERMAN,
        POLISH,
        SPANISH,
        ITALIAN,
        SWEDISH,
        CZECH,
        HUNGARIAN,
        DUTCH,
        PORTUGUESE,
        RUSSIAN,
        FINNISH;

        public static Language getLanguageByOrdinal(int i) {
            return Arrays.stream(Language.values()).filter(language -> language.ordinal() == i).findFirst().orElse(null);
        }
    }
}
