package utils.paradox.parsing.localisation;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.parsing.ParadoxParsingUtils;
import utils.paradox.scripting.localisation.Localisation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParadoxLocalisationParser {
    public List<Localisation> parseFile(File file) throws Exception {
        List<Localisation> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"))) {
            String line = reader.readLine();

            while (line != null) {
                Localisation record = parseLine(line);

                if (record != null) {
                    records.add(record);
                }

                line = reader.readLine();
            }
        }

        return records;
    }

    protected Localisation parseLine(String line) {

        if (line.startsWith(ParadoxParsingUtils.COMMENT_START)) {
            return null;
        }

        List<String> parts = Arrays.asList(line.split(ParadoxParsingUtils.LOCALISATION_SPLITTER));
        Localisation localisation = new Localisation();

        if (parts.isEmpty()) {
            Logger.error("Unable to parse: " + line);
            return null;
        }

        localisation.setTitle(StringUtils.trim(parts.get(0)));

        for (int i = 1; i < parts.size(); i++) {
            /* i starts at 1, while ordinal starts at 0, so we need to adjust accordingly */
            Localisation.Language language = Localisation.Language.getLanguageByOrdinal(i - 1);

            if (language == null) {
                Logger.error("Unable to parse: " + line);
                Logger.error("Unable to find language for section " + i);
                return null;
            }

            String text = StringUtils.trim(parts.get(i));

            if (StringUtils.equalsIgnoreCase(text, ParadoxParsingUtils.LOCALISATION_ENDLINE)) {
                continue;
            }

            localisation.setLocalisation(language, text);
        }

        return localisation;

    }
}
