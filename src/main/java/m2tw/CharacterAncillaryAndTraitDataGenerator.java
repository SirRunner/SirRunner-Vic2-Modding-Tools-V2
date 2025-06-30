package m2tw;

import m2tw.generator.CharacterAncillaryCSVExportGenerator;
import m2tw.generator.CharacterAncillaryConditionsCSVExportGenerator;
import m2tw.generator.CharacterTraitCSVExportGenerator;
import m2tw.generator.CharacterTraitConditionsCSVExportGenerator;

import java.io.File;
import java.util.Map;

public class CharacterAncillaryAndTraitDataGenerator {

    public void run() throws Exception {
        CharacterAncillaryParser ancillaryParser = new CharacterAncillaryParser();
        Map<String, CharacterAncillary> ancillaries = ancillaryParser.parseFile(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\export_descr_ancillaries.txt"));

        CharacterTraitParser traitParser = new CharacterTraitParser();
        Map<String, CharacterTrait> traits = traitParser.parseFile(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\export_descr_character_traits.txt"));

        LocalisationParser localisationParser = new LocalisationParser();
        Map<String, String> ancillaryLoc = localisationParser.parseFile(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\text\\export_ancillaries.txt"));
        Map<String, String> traitLoc = localisationParser.parseFile(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\text\\export_VnVs.txt"));

        CharacterTraitCSVExportGenerator traitCSVGenerator = new CharacterTraitCSVExportGenerator();
        traitCSVGenerator.generate(traits.values(), traitLoc);

        CharacterTraitConditionsCSVExportGenerator traitConditionsCSVGenerator = new CharacterTraitConditionsCSVExportGenerator();
        traitConditionsCSVGenerator.generate(traits.values());

        CharacterAncillaryCSVExportGenerator ancillaryCSVGenerator = new CharacterAncillaryCSVExportGenerator();
        ancillaryCSVGenerator.generate(ancillaries.values(), ancillaryLoc);

        CharacterAncillaryConditionsCSVExportGenerator ancillaryConditionsCsvGenerator = new CharacterAncillaryConditionsCSVExportGenerator();
        ancillaryConditionsCsvGenerator.generate(ancillaries.values());
    }

    public static void main(String[] args) throws Exception {
        CharacterAncillaryAndTraitDataGenerator generator = new CharacterAncillaryAndTraitDataGenerator();
        generator.run();

    }
}
