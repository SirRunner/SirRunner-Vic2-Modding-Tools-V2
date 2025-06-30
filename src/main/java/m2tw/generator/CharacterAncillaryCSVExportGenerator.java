package m2tw.generator;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import m2tw.CharacterAncillary;
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

public class CharacterAncillaryCSVExportGenerator {
    public void generate(Collection<CharacterAncillary> ancillaries, Map<String, String> loc) {
        List<String[]> lines = new ArrayList<>();
        lines.add(getHeaderLine());

        for (CharacterAncillary ancillary : ancillaries) {
            try {
                lines.add(getEntry(ancillary, loc));
            } catch (Exception e) {
                e.printStackTrace();
                Logger.error("Unable to convert trait " + ancillary.getName());
            }
        }


        Path source = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "src", "main", "java", "m2tw", "output");
        String traitFilename = Paths.get(source.toString(), "Ancillaries.csv").toString();
        try {
            CSVWriterBuilder builder = new CSVWriterBuilder(new BufferedWriter(new FileWriter(traitFilename)));

            try (ICSVWriter writer = builder.build()) {
                writer.writeAll(lines);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Unable to generate ancillary file");
        }
    }

    protected String[] getHeaderLine() {
        List<String> headerLine = new ArrayList<>();
        headerLine.add("Ancillary");
        headerLine.add("Backend Ancillary Name");
        headerLine.add("Description");
        headerLine.add("In Game Effects Description");
        headerLine.add("Type");
        headerLine.add("Transferable");
        headerLine.add("Allowed Cultures");
        headerLine.add("Mutually Exclusive With");

        for (CharacterEffect effect : CharacterEffect.values()) {
            headerLine.add(effect.getLocalizedName());
        }

        return headerLine.toArray(new String[0]);
    }

    protected String[] getEntry(CharacterAncillary ancillary, Map<String, String> loc) {
        List<String> line = new ArrayList<>();

        line.add(loc.get(ancillary.getName()));
        line.add(ancillary.getName());
        line.add(loc.get(ancillary.getDescription()));
        line.add(loc.get(ancillary.getEffectsDescription()));
        line.add(ancillary.getType());
        line.add(Boolean.toString(ancillary.isTransferable()));
        line.add(getAllowedCultures(ancillary));
        line.add(getMutuallyExclusiveWith(ancillary));

        for (CharacterEffect effect : CharacterEffect.values()) {
            line.add(getEffect(ancillary, effect));
        }

        return line.toArray(new String[0]);
    }

    protected String getAllowedCultures(CharacterAncillary ancillary) {
        if (ancillary.getExcludedCultures() == null || ancillary.getExcludedCultures().isEmpty()) {
            return "All";
        }

        // List of 7. Probably more expensive to make the set rather than to make a set for the more efficient remove
        List<CharacterCulture> cultures = new ArrayList<>(Arrays.stream(CharacterCulture.values()).toList());

        for (CharacterCulture culture : ancillary.getExcludedCultures()) {
            cultures.remove(culture);
        }

        return cultures.stream().map(CharacterCulture::getLocalizedName).collect(Collectors.joining(", "));
    }

    protected String getMutuallyExclusiveWith(CharacterAncillary ancillary) {
        if (ancillary.getExcludedAncillaries() == null || ancillary.getExcludedAncillaries().isEmpty()) {
            return "";
        }

        return String.join(", ", ancillary.getExcludedAncillaries());
    }

    protected String getEffect(CharacterAncillary ancillary, CharacterEffect effect) {
        if (ancillary.getEffects().containsKey(effect)) {
            return String.format("%s%s", ancillary.getEffects().get(effect), effect.getNumberSuffix());
        }

        return "";
    }
}
