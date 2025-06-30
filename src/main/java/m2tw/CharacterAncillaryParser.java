package m2tw;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CharacterAncillaryParser {

    public Map<String, CharacterAncillary> parseFile(File file) throws Exception {
        List<String> lines = Files.readAllLines(file.toPath());

        CharacterAncillary ancillary = null;
        CharacterAncillaryTraitTrigger trigger = null;
        boolean parsingCondition = false;
        Map<String, CharacterAncillary> ancillaries = new LinkedHashMap<>();
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

                if (parts.length < 2 && !line.toUpperCase().startsWith("UNIQUE")) {
                    System.out.printf("Line %s is in an unexpected format\n", line);
                }

                switch (parts[0].toUpperCase()) {
                    case "ANCILLARY" -> {
                        if (ancillary != null) {
                            ancillaries.put(ancillary.getName(), ancillary);
                        }
                        ancillary = new CharacterAncillary();
                        trigger = null;
                        ancillary.setName(parts[1]);
                    }
                    case "TYPE" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }
                        ancillary.setType(parts[1]);
                    }
                    case "TRANSFERABLE" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }
                        ancillary.setTransferable(parts[1]);
                    }
                    case "IMAGE" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }
                        ancillary.setImageName(parts[1]);
                    }
                    case "EXCLUDEDANCILLARIES" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }

                        for (int i = 1; i < parts.length; i++) {
                            String[] types = parts[i].split(",");

                            for (String type : types) {
                                ancillary.addExcludedAncillary(type.trim());
                            }
                        }
                    }
                    case "EXCLUDECULTURES" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }

                        for (int i = 1; i < parts.length; i++) {
                            String[] types = parts[i].split(",");

                            for (String type : types) {
                                ancillary.addExcludesCultures(CharacterCulture.getValue(type));
                            }
                        }
                    }
                    case "DESCRIPTION" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }
                        ancillary.setDescription(parts[1]);
                    }
                    case "EFFECTSDESCRIPTION" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }
                        ancillary.setEffectsDescription(parts[1]);
                    }
                    case "EFFECT" -> {
                        if (ancillary == null) {
                            System.out.printf("Line %s doesn't have an associated ancillary\n", line);
                            break;
                        }
                        if (parts.length < 3) {
                            System.out.printf("Line %s is in an unexpected format\n", line);
                            break;
                        }
                        CharacterEffect effect = CharacterEffect.getValue(parts[1]);
                        ancillary.addEffect(effect, Integer.parseInt(parts[2]));
                    }
                    case "TRIGGER" -> {
                        if (trigger != null) {
                            if (ancillary != null && !StringUtils.equals(ancillary.getName(), trigger.getAssociatedTraitName())) {
                                ancillaries.putIfAbsent(ancillary.getName(), ancillary);
                                ancillary = ancillaries.get(trigger.getAssociatedTraitName());
                            }

                            if (ancillary != null) {
                                ancillary.addTrigger(trigger);
                            } else {
                                Logger.warn("Unable to find trait for trigger " + trigger.getAssociatedTraitName());
                            }
                        }
                        trigger = new CharacterAncillaryTraitTrigger();
                        trigger.setAmount(1);
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
                    case "AFFECTS", "UNIQUE" -> {
                        // Affects only used for Trigger LegolasBow_Add -- ignoring
                        // Unique only used on Ancillary galadriel_hair, Ancillary scatha_artefact, Ancillary smaug_artefact, Ancillary egg_artefact -- ignoring
                    }
                    case "ACQUIREANCILLARY" -> {
                        if (trigger == null) {
                            System.out.printf("Line %s doesn't have an associated trigger\n", line);
                            break;
                        }
                        trigger.setAssociatedTraitName(parts[1]);
                        trigger.setChance(Integer.parseInt(parts[3]));
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

        if (ancillaries != null) {
            ancillaries.put(ancillary.getName(), ancillary);
        }
        if (trigger != null) {
            ancillary = ancillaries.get(trigger.getAssociatedTraitName());

            if (ancillary != null) {
                ancillary.addTrigger(trigger);
            } else {
                Logger.warn("Unable to find trait for trigger " + trigger.getAssociatedTraitName());
            }
        }

        return ancillaries;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\export_descr_ancillaries.txt");

        CharacterAncillaryParser parser = new CharacterAncillaryParser();
        parser.parseFile(file);
    }
}
