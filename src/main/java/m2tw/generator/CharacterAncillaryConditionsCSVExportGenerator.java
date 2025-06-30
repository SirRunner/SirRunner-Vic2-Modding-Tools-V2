package m2tw.generator;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import m2tw.CharacterAncillary;
import m2tw.CharacterAncillaryTraitTrigger;
import utils.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterAncillaryConditionsCSVExportGenerator {
    public void generate(Collection<CharacterAncillary> ancillaries) {
        List<String[]> lines = new ArrayList<>();
        lines.add(getHeaderLine());

        for (CharacterAncillary ancillary : ancillaries) {
            try {
                for (CharacterAncillaryTraitTrigger trigger : ancillary.getTriggers()) {
                    try {
                        lines.add(getEntry(ancillary, trigger));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.error(String.format("Unable to convert level %s for ancillary %s", trigger.getName(), ancillary.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.error("Unable to convert ancillary " + ancillary.getName());
            }
        }


        Path source = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "src", "main", "java", "m2tw", "output");
        String ancillaryFilename = Paths.get(source.toString(), "Ancillary Triggers.csv").toString();
        try {
            CSVWriterBuilder builder = new CSVWriterBuilder(new BufferedWriter(new FileWriter(ancillaryFilename)));

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
        headerLine.add("Trigger Name");
        headerLine.add("Ancillary Amount");
        headerLine.add("When Detected");
        headerLine.add("Chance of Application");
        headerLine.add("Conditions");

        return headerLine.toArray(new String[0]);
    }

    protected String[] getEntry(CharacterAncillary ancillary, CharacterAncillaryTraitTrigger trigger) {
        List<String> line = new ArrayList<>();

        line.add(ancillary.getName());
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
