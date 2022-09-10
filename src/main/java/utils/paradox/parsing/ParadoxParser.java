package utils.paradox.parsing;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParadoxParser {
    public List<Node> parseFile(File file) throws Exception {
        List<Node> nodes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"))) {
            List<String> words = getLinesFromFile(reader);

            nodes.addAll(getNodesFromWords(words));
        }

        return nodes;
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

    protected List<String> parseLine(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(0);
        boolean comment = false;

        for (char character : line.toCharArray()) {
            String stringCharacter = Character.toString(character);

            if (comment) {
                appendCharacter(currentWord, stringCharacter);
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
            } else if (Character.isWhitespace(character)) {
                addCurrentWord(words, currentWord);
            } else {
                appendCharacter(currentWord, stringCharacter);
            }
        }

        if (words.isEmpty() || !StringUtils.equals(words.get(words.size() - 1), currentWord.toString())) {
            addCurrentWord(words, currentWord);
        }

        return words;
    }

    public static Node addNewNode(List<Node> nodes) {
        Node node = new Node();

        nodes.add(node);

        return node;
    }

    public static Node handleNewNode(List<Node> nodes, String word) {
        if (StringUtils.startsWith(word, ParadoxParsingUtils.COMMENT_START)) {
            Node node = addNewNode(nodes);

            node.setComment(word);
        } else if (ParadoxParsingUtils.NON_COMMENT_KEYWORDS.contains(word)) {
            logExpectingNodeNameError(nodes, word);
        } else {
            Node node = addNewNode(nodes);

            node.setName(word);
            return node;
        }

        return null;
    }

    protected static void logExpectingValueError(Node node, String word) {
        Logger.error("Expecting bracket or value following \"" + node.getName() + " = \" but got " + word);
    }

    protected static void logExpectingValueError(String word) {
        Logger.error("Expecting bracket or value but got " + word);
    }

    protected static void logNotExpectingBracketError(Node node) {
        if (node == null) {
            logNotExpectingBracketError();
        } else {
            Logger.error("Received open bracket for " + node.getName() + " when the previous character was not \"=\"");
        }
    }

    protected static void logNotExpectingBracketError() {
        Logger.error("Received opening bracket but there is no existing node");
    }

    protected static void logExpectingNodeNameError(String word) {
        Logger.error("Was expecting name of new node but received " + word);
    }

    protected static void logExpectingNodeNameError(Node node, String word) {
        if (node != null) {
            Logger.error("Was expecting name of new node after closing of node named " + node.getName() + ", but received " + word);
        } else {
            logExpectingNodeNameError(word);
        }
    }

    protected static void logExpectingNodeNameError(List<Node> nodes, String word) {
        if (nodes == null) {
            logExpectingNodeNameError(word);
        } else {
            logExpectingNodeNameError(nodes.get(nodes.size() - 1), word);
        }
    }

    protected static void logClosingBracketForNonExistingNodeError() {
        Logger.info("Read closing bracket but there is no open node. This is not an error, but is bad practice");
    }

    protected List<Node> getNodesFromWords(List<String> words) {
        List<Node> nodes = new ArrayList<>();
        Node currentNode = null;
        Node parentNode = null;
        int i = 0;
        boolean expectingBracketOrValue = false;

        while (i < words.size()) {
            String word = words.get(i);

            if (parentNode == null && currentNode == null) {
                /* If we are expecting a value, we should have at least a currentNode */
                if (expectingBracketOrValue) {
                    logExpectingValueError(word);
                    continue;
                }

                currentNode = handleNewNode(nodes, word);
            } else if (StringUtils.equals(ParadoxParsingUtils.DEFINES, word)) {
                /* If we are expecting a value, "=" is not a value */
                if (expectingBracketOrValue) {
                    if (currentNode == null) {
                        logExpectingValueError(word);
                    } else {
                        logExpectingValueError(currentNode, word);
                    }

                    continue;
                }

                expectingBracketOrValue = true;
            } else if (StringUtils.equals(ParadoxParsingUtils.OPEN_BLOCK, word)) {
                if (!expectingBracketOrValue) {
                    logNotExpectingBracketError(currentNode);
                    continue;
                }

                expectingBracketOrValue = false;

                /* Get setup to make the next node */
                parentNode = currentNode;

                /* It is possible that there is nothing inside this node. If the next word is not a closing bracket, prepare for a new node */
                if (i + 1 < words.size() && !StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, words.get(i + 1))) {
                    currentNode = null;
                }
            } else if (StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, word)) {
                if (currentNode == null) {
                    logClosingBracketForNonExistingNodeError();
                    continue;
                }
                /* Close the current node */
                parentNode = currentNode.getParent();

                /* If the next word is not a closing bracket, prepare for a new node. Otherwise, update the currentNode to be the parent */
                if (i + 1 < words.size() && !StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, words.get(i + 1))) {
                    currentNode = null;
                } else {
                    currentNode = parentNode;
                }
            } else if (currentNode == null) {
                /* We are expecting a new node. Create a new one */
                currentNode = handleNewNode(parentNode.getNodes(), word);

                /* If the new node is not a comment node, make sure it gets processed correctly */
                if (currentNode != null) {
                    currentNode.setParent(parentNode);
                    currentNode.setLayer(parentNode.getLayer() + 1);
                }
            } else {
                if (!expectingBracketOrValue) {
                    logNotExpectingBracketError(currentNode);
                    continue;
                }

                expectingBracketOrValue = false;

                /* If we see a value for a node, this is the last word for this node. Update the node and then prepare for the next one */
                currentNode.setValue(word);

                /* If the next word is not a closing bracket, prepare for a new node */
                if (i + 1 < words.size() && !StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, words.get(i + 1))) {
                    currentNode = null;
                } else {
                    currentNode = currentNode.getParent();
                }
            }

            i++;
        }

        return nodes;
    }

    public static void main(String[] args) {
        try {
            ParadoxParser parser = new ParadoxParser();

            parser.parseFile(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\decisions\\Isengard.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
