package m2tw.generator;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import m2tw.CharacterAncillaryTraitTrigger;
import m2tw.CharacterTrait;
import utils.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterTraitConditionsCSVExportGenerator {
    public void generate(Collection<CharacterTrait> traits) {
        List<String[]> lines = new ArrayList<>();
        lines.add(getHeaderLine());

        for (CharacterTrait trait : traits) {
            try {
                for (CharacterAncillaryTraitTrigger trigger : trait.getTriggers()) {
                    try {
                        lines.add(getEntry(trait, trigger));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.error(String.format("Unable to convert level %s for trait %s", trigger.getName(), trait.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.error("Unable to convert trait " + trait.getName());
            }
        }


        Path source = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "src", "main", "java", "m2tw", "output");
        String traitFilename = Paths.get(source.toString(), "Trait Triggers.csv").toString();
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
        headerLine.add("Trait");
        headerLine.add("Trigger Name");
        headerLine.add("Trait Amount");
        headerLine.add("When Detected");
        headerLine.add("Chance of Application");
        headerLine.add("Conditions");

        return headerLine.toArray(new String[0]);
    }

    protected String[] getEntry(CharacterTrait trait, CharacterAncillaryTraitTrigger trigger) {
        List<String> line = new ArrayList<>();

        line.add(trait.getName());
        line.add(trigger.getName());
        line.add(Integer.toString(trigger.getAmount()));
        line.add(trigger.getWhenToTest().getLocalizedName());
        line.add(String.format("%s%%", trigger.getChance()));
        line.add(getConditions(trigger));

        return line.toArray(new String[0]);
    }

    protected String getConditions(CharacterAncillaryTraitTrigger trigger) {
        if (trigger.getConditions() == null || trigger.getConditions().isEmpty()) {
            return "";
        }

        return trigger.getConditions().stream().map(CharacterAncillaryTraitTrigger::translateCondition).collect(Collectors.joining(" and "));
    }
}
