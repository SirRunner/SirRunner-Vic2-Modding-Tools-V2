package m2tw;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CharacterTraitParser {

    public Map<String, CharacterTrait> parseFile(File file) throws Exception {
        List<String> lines = Files.readAllLines(file.toPath());

        CharacterTrait trait = null;
        CharacterTrait.CharacterTraitLevel level = null;
        CharacterAncillaryTraitTrigger trigger = null;
        boolean parsingCondition = false;
        Map<String, CharacterTrait> traits = new LinkedHashMap<>();
        for (String line : lines) {
            try {
                line = StringUtils.trim(line);

                String[] commentParts = line.split(";");

                if (StringUtils.isEmpty(line) || commentParts.length == 0) {
                    continue;
                }

                String nonComment = commentParts[0];

                if (StringUtils.isEmpty(nonComment)) {
                    Logger.debug(String.format("Line %s appears to be entirely comment\n", line));
                    continue;
                }

                String[] parts = line.split(" +");

                if (parts.length < 2 && !line.toUpperCase().startsWith("HIDDEN")) {
                    System.out.printf("Line %s is in an unexpected format\n", line);
                }

                switch (parts[0].toUpperCase()) {
                    case "TRAIT" -> {
                        if (trait != null) {
                            traits.put(trait.getName(), trait);
                        }
                        if (level != null) {
                            trait.addLevel(level);
                        }
                        trait = new CharacterTrait();
                        level = null;
                        trigger = null;
                        trait.setName(parts[1]);
                    }
                    case "CHARACTERS" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }

                        for (int i = 1; i < parts.length; i++) {
                            String[] types = parts[i].split(",");

                            for (String type : types) {
                                trait.addCharacterType(CharacterTrait.CharacterType.getValue(type));
                            }
                        }
                    }
                    case "EXCLUDECULTURES" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }

                        for (int i = 1; i < parts.length; i++) {
                            String[] types = parts[i].split(",");

                            for (String type : types) {
                                trait.addExcludesCultures(CharacterCulture.getValue(type));
                            }
                        }
                    }
                    case "NOGOINGBACKLEVEL" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        trait.setNoGoingBackLevel(Integer.parseInt(parts[1]));
                    }
                    case "HIDDEN" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        trait.setHidden(true);
                    }
                    case "LEVEL" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level != null) {
                            trait.addLevel(level);
                        }
                        level = new CharacterTrait.CharacterTraitLevel();
                        level.setName(parts[1]);
                    }
                    case "DESCRIPTION" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        level.setDescription(parts[1]);
                    }
                    case "EFFECTSDESCRIPTION" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        level.setEffectsDescription(parts[1]);
                    }
                    case "EPITHET" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        level.setEpithet(parts[1]);
                    }
                    case "GAINMESSAGE" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        level.setGainMessage(parts[1]);
                    }
                    case "LOSEMESSAGE" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        level.setLoseMessage(parts[1]);
                    }
                    case "THRESHOLD" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        level.setThreshholdLevel(Integer.parseInt(parts[1]));
                    }
                    case "EFFECT" -> {
                        if (trait == null) {
                            System.out.printf("Line %s doesn't have an associated trait\n", line);
                            break;
                        }
                        if (level == null) {
                            System.out.printf("Line %s doesn't have an associated level\n", line);
                            break;
                        }
                        if (parts.length < 3) {
                            System.out.printf("Line %s is in an unexpected format\n", line);
                            break;
                        }
                        CharacterEffect effect = CharacterEffect.getValue(parts[1]);
                        level.addEffect(effect, Integer.parseInt(parts[2]));
                    }
                    case "TRIGGER" -> {
                        if (trigger != null) {
                            if (trait != null && !StringUtils.equals(trait.getName(), trigger.getAssociatedTraitName())) {
                                traits.putIfAbsent(trait.getName(), trait);
                                trait = traits.get(trigger.getAssociatedTraitName());
                            }

                            if (trait != null) {
                                trait.addTrigger(trigger);
                            } else {
                                Logger.warn("Unable to find trait for trigger " + trigger.getAssociatedTraitName());
                            }
                        }
                        trigger = new CharacterAncillaryTraitTrigger();
                        trigger.setName(parts[1]);
                        parsingCondition = false;
                    }
                    case "WHENTOTEST" -> {
                        if (trigger == null) {
                            System.out.printf("Line %s doesn't have an associated trigger\n", line);
                            break;
                        }
                        trigger.setWhenToTest(CharacterAncillaryTraitTrigger.WhenToTest.getValue(parts[1]));
                        parsingCondition = false;
                    }
                    case "CONDITION" -> {
                        if (trigger == null) {
                            System.out.printf("Line %s doesn't have an associated trigger\n", line);
                            break;
                        }
                        String condition = "";

                        for (int i = 1; i < parts.length; i++) {
                            condition += parts[i] + " ";
                        }

                        trigger.addCondition(condition.trim());
                        parsingCondition = true;
                    }
                    case "AFFECTS" -> {
                        if (trigger == null) {
                            System.out.printf("Line %s doesn't have an associated trigger\n", line);
                            break;
                        }
                        if (StringUtils.isNotEmpty(trigger.getAssociatedTraitName())) {
                            if (trait != null && !StringUtils.equals(trait.getName(), trigger.getAssociatedTraitName())) {
                                traits.putIfAbsent(trait.getName(), trait);
                                trait = traits.get(trigger.getAssociatedTraitName());
                            }

                            if (trait != null) {
                                trait.addTrigger(trigger);
                                trigger = new CharacterAncillaryTraitTrigger(trigger);
                            } else {
                                Logger.warn("Unable to find trait for trigger " + trigger.getAssociatedTraitName());
                            }
                        }
                        trigger.setAssociatedTraitName(parts[1]);
                        trigger.setAmount(Integer.parseInt(parts[2]));
                        trigger.setChance(Integer.parseInt(parts[4]));
                        parsingCondition = false;
                    }
                    default -> {
                        if (parsingCondition) {
                            if (trigger == null) {
                                System.out.printf("Line %s doesn't have an associated trigger\n", line);
                                break;
                            }
                            String condition = "";

                            for (int i = 1; i < parts.length; i++) {
                                condition += parts[i] + " ";
                            }

                            trigger.addCondition(condition.trim());
                            break;
                        }
                        System.out.printf("Not parsing line %s correctly\n", line);
                    }
                }
            } catch (Exception e) {
                System.out.printf("Errored on line %s\n", line);
                throw e;
            }
        }

        if (trait != null) {
            if (level != null) {
                trait.addLevel(level);
            }
            traits.put(trait.getName(), trait);
        }
        if (trigger != null) {
            trait = traits.get(trigger.getAssociatedTraitName());

            if (trait != null) {
                trait.addTrigger(trigger);
            } else {
                Logger.warn("Unable to find trait for trigger " + trigger.getAssociatedTraitName());
            }
        }

        return traits;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\export_descr_character_traits.txt");

        CharacterTraitParser parser = new CharacterTraitParser();
        parser.parseFile(file);
    }
}
