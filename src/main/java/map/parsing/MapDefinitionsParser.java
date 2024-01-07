package map.parsing;

import map.definitions.MapDefinitions;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.*;

public class MapDefinitionsParser extends BaseReader {

    public static final char DELIMITER = ';';
    public static final char COMMENT_START = '#';

    protected Set<Color> usedColors;

    public MapDefinitionsParser(File file) {
        super(file);
        usedColors = new HashSet<>();
    }

    public Set<Color> getUsedColors() {
        return usedColors;
    }

    @Override
    public Map<Integer, MapDefinitions> readFile() throws Exception {
        if (getFile() == null) {
            throw new Exception("No definitions file set");
        }

        Map<Integer, MapDefinitions> idToMapDefinitions = new HashMap<>();

        List<String> lines = Files.readAllLines(getFile().toPath());

        for (String line : lines) {
            parseLine(line, idToMapDefinitions);
        }

        return idToMapDefinitions;
    }

    protected void parseLine(String line, Map<Integer, MapDefinitions> idToMapDefinitions) {

        List<String> tokens = getTokens(line);

        List<String> errors = getErrors(tokens, idToMapDefinitions);

        if (!errors.isEmpty()) {
            Logger.error(line + " has errors:");
            errors.forEach(error -> System.out.println("\t" + error));
            return;
        }

        MapDefinitions definitions = getDefinitions(tokens);

        idToMapDefinitions.put(definitions.getId(), definitions);
        usedColors.add(definitions.getColor());
    }

    protected List<String> getTokens(String line) {

        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inComment = false;

        for (Character character : line.toCharArray()) {
            // When we are in a comment, the game will not parse anything else for that line
            if (inComment) {
                currentToken.append(character);
                continue;
            }

            // Without this line, we will parse the space I generally put between the ;x; and the comment as a separate token
            // e.g. [4, 178, 201, 174, Bouekihou, x,  ,  Strandholm = Empire Name]
            if (Character.isWhitespace(character) && currentToken.isEmpty()) {
                continue;
            }

            switch (character) {
                case DELIMITER -> {
                    if (!currentToken.isEmpty()) {
                        tokens.add(currentToken.toString());
                    }

                    currentToken = new StringBuilder();
                }
                case COMMENT_START -> {
                    if (!currentToken.isEmpty()) {
                        tokens.add(currentToken.toString());
                    }

                    currentToken = new StringBuilder();
                    inComment = true;
                }
                default -> currentToken.append(character);
            }
        }

        if (!currentToken.isEmpty()) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    protected List<String> getErrors(List<String> tokens, Map<Integer, MapDefinitions> idToMapDefinitions) {

        List<String> errors = new ArrayList<>();

        if (4 > tokens.size()) {
            errors.add("Line does not have enough tokens");
        } else if (tokens.size() > 7) {
            errors.add("Line has too many tokens");
        }

        // Note that these are already trimmed -- there is no validation on the name, x or comment
        String id = getPart(PART.ID, tokens);
        String red = getPart(PART.RED, tokens);
        String green = getPart(PART.GREEN, tokens);
        String blue = getPart(PART.BLUE, tokens);

        if (StringUtils.isEmpty(id)) {
            errors.add("Line is missing an id");
        } else if (!StringUtils.isNumeric(id)) {
            errors.add("Province id " + id + " has more characters than just numbers");
        } else if (idToMapDefinitions.containsKey(Integer.parseInt(id))) {
            errors.add("Province id " + id + " is defined more than once");
        }

        boolean checkColors = true;

        if (StringUtils.isEmpty(red)) {
            errors.add("Line is missing a red color input");
            checkColors = false;
        } else if (!StringUtils.isNumeric(red)) {
            errors.add("Red color input " + red + " has more characters than just numbers");
            checkColors = false;
        } else if (Integer.parseInt(red) > 255) {
            errors.add("Red color " + red + " exceeds the maximum allow value (255)");
            checkColors = false;
        }

        if (StringUtils.isEmpty(green)) {
            errors.add("Line is missing an green color input");
            checkColors = false;
        } else if (!StringUtils.isNumeric(green)) {
            errors.add("Green color input " + green + " has more characters than just numbers");
            checkColors = false;
        } else if (Integer.parseInt(green) > 255) {
            errors.add("Green color " + green + " exceeds the maximum allow value (255)");
            checkColors = false;
        }

        if (StringUtils.isEmpty(blue)) {
            errors.add("Line is missing an blue color input");
            checkColors = false;
        } else if (!StringUtils.isNumeric(blue)) {
            errors.add("Blue color input " + blue + " has more characters than just numbers");
            checkColors = false;
        } else if (Integer.parseInt(blue) > 255) {
            errors.add("Blue color " + blue + " exceeds the maximum allow value (255)");
            checkColors = false;
        }

        if (checkColors && usedColors.contains(new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue)))) {
            errors.add("Color " + red + ";" + green + ";" + blue + " is used more than once");
        }

        return errors;
    }

    protected MapDefinitions getDefinitions(List<String> tokens) {

        MapDefinitions definitions = new MapDefinitions();

        // Note that these are already trimmed, and validation has already taken place, so we can make assumptions that the colors and ids can be converted into ints without issue
        // The x field is not stored
        String id = getPart(PART.ID, tokens);
        String red = getPart(PART.RED, tokens);
        String green = getPart(PART.GREEN, tokens);
        String blue = getPart(PART.BLUE, tokens);
        String name = getPart(PART.NAME, tokens);
        String comment = getPart(PART.COMMENT, tokens);

        definitions.setId(Integer.parseInt(id));
        definitions.setColor(red, green, blue);
        definitions.setName(name);
        definitions.setComment(comment);

        return definitions;
    }

    protected String getPart(PART part, List<String> tokens) {
        return PART.getPart(part, tokens);
    }

    protected enum PART {
        ID,
        RED,
        GREEN,
        BLUE,
        NAME,
        X,
        COMMENT;

        protected static String getPart(PART part, List<String> tokens) {
            if (part.ordinal() >= tokens.size()) {
                return null;
            }

            return StringUtils.trim(tokens.get(part.ordinal()));
        }
    }
}
