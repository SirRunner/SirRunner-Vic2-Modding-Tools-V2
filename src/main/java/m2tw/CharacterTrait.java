package m2tw;

import java.util.*;

public class CharacterTrait {

    protected String name;
    protected List<CharacterType> characterTypes;
    protected Set<CharacterCulture> excludedCultures;
    protected int noGoingBackLevel;
    protected boolean hidden;
    List<CharacterTraitLevel> levels;
    protected List<CharacterAncillaryTraitTrigger> triggers;

    public CharacterTrait() {
        this.characterTypes = new ArrayList<>();
        this.excludedCultures = new LinkedHashSet<>();
        this.levels = new ArrayList<>();
        this.triggers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CharacterType> getCharacterTypes() {
        return characterTypes;
    }

    public void setCharacterTypes(List<CharacterType> characterTypes) {
        this.characterTypes = characterTypes;
    }

    public void addCharacterType(CharacterType characterType) {
        this.characterTypes.add(characterType);
    }

    public Set<CharacterCulture> getExcludedCultures() {
        return excludedCultures;
    }

    public void setExcludedCultures(Set<CharacterCulture> excludedCultures) {
        this.excludedCultures = excludedCultures;
    }

    public void addExcludesCultures(CharacterCulture excludedCulture) {
        this.excludedCultures.add(excludedCulture);
    }

    public int getNoGoingBackLevel() {
        return noGoingBackLevel;
    }

    public void setNoGoingBackLevel(int noGoingBackLevel) {
        this.noGoingBackLevel = noGoingBackLevel;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public List<CharacterTraitLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<CharacterTraitLevel> levels) {
        this.levels = levels;
    }

    public void addLevel(CharacterTraitLevel level) {
        this.levels.add(level);
    }

    public List<CharacterAncillaryTraitTrigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<CharacterAncillaryTraitTrigger> triggers) {
        this.triggers = triggers;
    }

    public void addTrigger(CharacterAncillaryTraitTrigger trigger) {
        this.triggers.add(trigger);
    }

    public enum CharacterType {
        ALL,
        ADMIRAL,
        DIPLOMAT,
        FAMILY,
        MERCHANT,
        PRIEST,
        SPY;

        public static CharacterType getValue(String value) {
            try {
                return CharacterType.valueOf(value.toUpperCase().trim());
            } catch (Exception e) {
                System.out.println("Unable to parse value: " + value);
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class CharacterTraitLevel {
        protected String name;
        protected String description;
        protected String effectsDescription;
        protected String epithet;
        protected String gainMessage;
        protected String loseMessage;
        protected int threshholdLevel;
        protected Map<CharacterEffect, Integer> effects;

        public CharacterTraitLevel() {
            this.effects = new LinkedHashMap<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEffectsDescription() {
            return effectsDescription;
        }

        public void setEffectsDescription(String effectsDescription) {
            this.effectsDescription = effectsDescription;
        }

        public String getEpithet() {
            return epithet;
        }

        public void setEpithet(String epithet) {
            this.epithet = epithet;
        }

        public String getGainMessage() {
            return gainMessage;
        }

        public void setGainMessage(String gainMessage) {
            this.gainMessage = gainMessage;
        }

        public String getLoseMessage() {
            return loseMessage;
        }

        public void setLoseMessage(String loseMessage) {
            this.loseMessage = loseMessage;
        }

        public int getThreshholdLevel() {
            return threshholdLevel;
        }

        public void setThreshholdLevel(int threshholdLevel) {
            this.threshholdLevel = threshholdLevel;
        }

        public Map<CharacterEffect, Integer> getEffects() {
            return effects;
        }

        public void setEffects(Map<CharacterEffect, Integer> effects) {
            this.effects = effects;
        }

        public void addEffect(CharacterEffect effect, int amount) {
            this.effects.put(effect, amount);
        }
    }
}
