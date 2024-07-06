package utils.paradox.parsing;

import java.util.Arrays;
import java.util.List;

public class ParadoxParsingUtils {
    public static final String DEFINES = "=";
    public static final String OPEN_BLOCK = "{";
    public static final String CLOSE_BLOCK = "}";
    public static final String COMMENT_START = "#";
    public static final String TEXT_START = "\"";
    public static final String LOCALISATION_SPLITTER = ";";
    public static final String LOCALISATION_ENDLINE = "x";

    public static final char COMMENT_START_CHAR = '#';
    public static final char DELIMITER_CHAR = ';';

    public static final int EVENT_DESC_AVERAGE_CHARACTERS_PER_LINE = 85;

    public static final List<String> KEYWORD_CHARACTERS = Arrays.asList(DEFINES, OPEN_BLOCK, CLOSE_BLOCK, COMMENT_START);
    public static final List<String> NON_COMMENT_KEYWORDS = Arrays.asList(DEFINES, OPEN_BLOCK, CLOSE_BLOCK);
}
