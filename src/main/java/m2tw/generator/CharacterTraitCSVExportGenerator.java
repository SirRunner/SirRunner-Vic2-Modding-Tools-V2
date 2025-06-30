package m2tw.generator;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import m2tw.CharacterCulture;
import m2tw.CharacterEffect;
import m2tw.CharacterTrait;
import utils.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CharacterTraitCSVExportGenerator {
    public void generate(Collection<CharacterTrait> traits, Map<String, String> loc) {
        List<String[]> lines = new ArrayList<>();
        lines.add(getHeaderLine());

        for (CharacterTrait trait : traits) {
            try {
                for (CharacterTrait.CharacterTraitLevel level : trait.getLevels()) {
                    try {
                        lines.add(getEntry(trait, level, loc));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.error(String.format("Unable to convert level %s for trait %s", level.getName(), trait.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.error("Unable to convert trait " + trait.getName());
            }
        }


        Path source = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "src", "main", "java", "m2tw", "output");
        String traitFilename = Paths.get(source.toString(), "Traits.csv").toString();
        try {
            CSVWriterBuilder builder = new CSVWriterBuilder(new BufferedWriter(new FileWriter(traitFilename)));

            try (ICSVWriter writer = builder.build()) {
                writer.writeAll(lines);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Unable to generate trait file");
        }
    }

    protected String[] getHeaderLine() {
        List<String> headerLine = new ArrayList<>();
        headerLine.add("Trait Group");
        headerLine.add("Trait");
        headerLine.add("Backend Trait Name");
        headerLine.add("Description");
        headerLine.add("In Game Effects Description");
        headerLine.add("Character Types");
        headerLine.add("Allowed Cultures");
        headerLine.add("Threshold Level");
        headerLine.add("Can Lose Progression");
        headerLine.add("Epithet");

        for (CharacterEffect effect : CharacterEffect.values()) {
            headerLine.add(effect.getLocalizedName());
        }

        return headerLine.toArray(new String[0]);
    }

    protected String[] getEntry(CharacterTrait trait, CharacterTrait.CharacterTraitLevel level, Map<String, String> loc) {
        List<String> line = new ArrayList<>();

        line.add(trait.getName());
        line.add(loc.get(level.getName()));
        line.add(level.getName());
        line.add(loc.get(level.getDescription()));
        line.add(loc.get(level.getEffectsDescription()));
        line.add(getCharacterTypes(trait));
        line.add(getAllowedCultures(trait));
        line.add(Integer.toString(level.getThreshholdLevel()));
        line.add(Integer.toString(trait.getNoGoingBackLevel()));
        line.add(loc.get(level.getEpithet()));

        for (CharacterEffect effect : CharacterEffect.values()) {
            line.add(getEffect(level, effect));
        }

        return line.toArray(new String[0]);
    }

    protected String getCharacterTypes(CharacterTrait trait) {
        if (trait.getCharacterTypes() == null || trait.getCharacterTypes().isEmpty()) {
            return CharacterTrait.CharacterType.ALL.name();
        }

        return trait.getCharacterTypes().stream().map(CharacterTrait.CharacterType::name).collect(Collectors.joining(", "));
    }

    protected String getAllowedCultures(CharacterTrait trait) {
        if (trait.getExcludedCultures() == null || trait.getExcludedCultures().isEmpty()) {
            return "All";
        }

        // List of 7. Probably more expensive to make the set rather than to make a set for the more efficient remove
        List<CharacterCulture> cultures = new ArrayList<>( Arrays.stream(CharacterCulture.values()).toList());

        for (CharacterCulture culture: trait.getExcludedCultures()) {
            cultures.remove(culture);
        }

        return cultures.stream().map(CharacterCulture::getLocalizedName).collect(Collectors.joining(", "));
    }

    protected String getEffect(CharacterTrait.CharacterTraitLevel level, CharacterEffect effect) {
        if (level.getEffects().containsKey(effect)) {
            return String.format("%s%s", level.getEffects().get(effect), effect.getNumberSuffix());
        }

        return "";
    }
}
