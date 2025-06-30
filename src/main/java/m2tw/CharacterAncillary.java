package m2tw;

import utils.Logger;

import java.util.*;

public class CharacterAncillary {

    protected String name;
    protected String type;
    protected boolean transferable;
    protected String imageName;
    protected Set<String> excludedAncillaries;
    protected Set<CharacterCulture> excludedCultures;
    protected String description;
    protected String effectsDescription;
    protected Map<CharacterEffect, Integer> effects;
    protected List<CharacterAncillaryTraitTrigger> triggers;

    public CharacterAncillary() {
        this.excludedAncillaries = new HashSet<>();
        this.excludedCultures = new LinkedHashSet<>();
        this.effects = new LinkedHashMap<>();
        this.triggers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTransferable() {
        return transferable;
    }

    public void setTransferable(boolean transferable) {
        this.transferable = transferable;
    }

    public void setTransferable(String value) {
        switch (value) {
            case "0" -> setTransferable(false);
            case "1" -> setTransferable(true);
            default -> Logger.error("Unable to process transferable value " + value );
        }
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Set<String> getExcludedAncillaries() {
        return excludedAncillaries;
    }

    public void setExcludedAncillaries(Set<String> excludedAncillaries) {
        this.excludedAncillaries = excludedAncillaries;
    }

    public void addExcludedAncillary(String excludedAncillary) {
        this.excludedAncillaries.add(excludedAncillary);
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

    public Map<CharacterEffect, Integer> getEffects() {
        return effects;
    }

    public void setEffects(Map<CharacterEffect, Integer> effects) {
        this.effects = effects;
    }

    public void addEffect(CharacterEffect effect, int amount) {
        this.effects.put(effect, amount);
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
}
