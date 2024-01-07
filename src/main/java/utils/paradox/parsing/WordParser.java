package utils.paradox.parsing;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordParser {

    public List<String> getWords(File file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"))) {
            return getLinesFromFile(reader);
        }
    }

    protected List<String> getLinesFromFile(BufferedReader reader) throws Exception {
        List<String> words = new ArrayList<>();
        String line = reader.readLine();

        while (line != null) {
            words.addAll(parseLine(line));

            line = reader.readLine();
        }

        return words;
    }

    protected List<String> parseLine(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(0);
        boolean comment = false;
        boolean isQuote = false;

        for (char character : line.toCharArray()) {
            String stringCharacter = Character.toString(character);

            if (comment) {
                appendCharacter(currentWord, stringCharacter);
            } else if (isQuote) {
                appendCharacter(currentWord, stringCharacter);

                isQuote = !StringUtils.equals(ParadoxParsingUtils.TEXT_START, stringCharacter);
            } else if (ParadoxParsingUtils.KEYWORD_CHARACTERS.contains(stringCharacter)) {
                addCurrentWord(words, currentWord);

                if (StringUtils.equals(ParadoxParsingUtils.COMMENT_START, stringCharacter)) {
                    comment = true;
                    appendCharacter(currentWord, stringCharacter);
                } else {
                    StringBuilder currentCharacter = new StringBuilder(0);
                    appendCharacter(currentCharacter, stringCharacter);

                    addCurrentWord(words, currentCharacter);
                }
            } else if (StringUtils.equals(ParadoxParsingUtils.TEXT_START, stringCharacter)) {
                isQuote = true;
                appendCharacter(currentWord, stringCharacter);
            } else if (Character.isWhitespace(character)) {
                addCurrentWord(words, currentWord);
            } else {
                appendCharacter(currentWord, stringCharacter);
            }
        }

        if (words.isEmpty() || !StringUtils.equals(words.get(words.size() - 1), currentWord.toString())) {
            addCurrentWord(words, currentWord);
        }

        if (isQuote) {
            logOpenQuoteError(currentWord.toString());
        }

        return words;
    }

    protected void addCurrentWord(List<String> words, StringBuilder currentWordBuilder) {
        String currentWord = currentWordBuilder.toString();

        if (StringUtils.isNotEmpty(currentWord)) {
            words.add(currentWord);
        }

        currentWordBuilder.setLength(0);
    }

    protected void appendCharacter(StringBuilder builder, String character) {
        builder.setLength(builder.length());

        builder.append(character);
    }

    protected static void logOpenQuoteError(String word) {
        Logger.error("Quote was not closed for " + word);
    }
}
