package common.readers;

import common.Country;
import decisions.nodes.PoliticalDecisions;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;
import utils.paradox.nodes.Node;
import utils.paradox.parsing.ParadoxParser;
import utils.paradox.parsing.ParadoxParsingUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryListReader extends BaseReader {

    boolean dynamic = false;

    public CountryListReader(File file) {
        super(file);
    }

    public CountryListReader(String filename) {
        super(filename);
    }

    @Override
    public List<Country> readFile() throws Exception {

        if (file == null) {
            throw new Exception("file was not defined");
        }
        ParadoxParser parser = new ParadoxParser();

        List<Node> nodes = parser.parseFile(file);

        return parseNodes(nodes);
    }

    protected List<Country> parseNodes(List<Node> nodes) {

        List<Country> countries = new ArrayList<>();

        for (Node node : nodes) {

            if (StringUtils.equalsIgnoreCase(StringUtils.trim(node.getName()), "dynamic_tags")) {
                dynamic = ParadoxParsingUtils.getBooleanFromNode(node);
                continue;
            }

            if (node.hasComment()) {
                // TODO: Handle comments
            } else if (!node.getNodes().isEmpty()) {
                Logger.error("Node somehow has children: " + node);
            } else {
                countries.add(new Country(node, dynamic));
            }
        }

        return countries;
    }

    protected Country getCountry(String line) {
        if (StringUtils.isEmpty(StringUtils.trim(line)) || StringUtils.startsWith(line, ParadoxParsingUtils.COMMENT_START)) {
            return null;
        }

        String standardizedLine = StringUtils.trim(line);

        if (StringUtils.startsWith(standardizedLine, "dynamic_tags")) {

        }

        /* The game assumes the tag is the first 3 characters once leading whitespace is removed */
        String tag = StringUtils.substring(line, 0, 3);

        String[] parts = line.split("\"");

        /* Should never happen, but just in case */
        if (parts.length < 3) {
            Logger.error("Unparsable Line: " + line);
            return null;
        }

        /* There should be 3 parts created, at a minimum -- the first that encompasses `tag = `, the second that has the path, and a third to handles any text after the closing quote */
        String path = parts[1];

        return new Country("", "");
    }

    public static void main(String[] args) {
        try {
            CountryListReader reader = new CountryListReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\countries.txt");

            reader.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
