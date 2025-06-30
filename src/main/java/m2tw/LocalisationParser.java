package m2tw;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalisationParser {

    public Map<String, String> parseFile(File file) throws Exception {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_16LE);
        Map<String, String> keywordToText = new HashMap<>();

        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            } else if (!line.matches("\\{.+\\}.+")) {
                Logger.debug("Skipping line " + line);
                continue;
            }

            String[] parts = line.split("\\{|\\}");

            // {Hidden_desc}Hidden gets split into ["", "Hidden_desc", "Hidden"]
            if (parts.length < 3) {
                Logger.warn("Failed to parse line " + line);
                continue;
            }

            if (keywordToText.containsKey(parts[1])) {
                Logger.warn(String.format("Line %s has a duplicate entry", line));
                continue;
            }

            keywordToText.put(parts[1], parts[2]);
        }

        return keywordToText;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Medieval II Total War\\mods\\Divide_and_Conquer\\data\\text\\export_VnVs.txt");

        LocalisationParser parser = new LocalisationParser();
        parser.parseFile(file);
    }
}
