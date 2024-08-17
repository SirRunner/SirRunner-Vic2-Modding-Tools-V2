package utils.paradox.parsing;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.nodes.Node;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParadoxParserWithColor extends ParadoxParser {

    protected List<String> getLinesFromFile(BufferedReader reader) throws Exception {
        List<String> originallyParsedWords = super.getLinesFromFile(reader);
        List<String> words = new ArrayList<>();

        for (int i = 0; i < originallyParsedWords.size(); i++) {

            String word = originallyParsedWords.get(i);

            if (!StringUtils.equalsIgnoreCase("color", word)) {
                words.add(word);
                continue;
            }

            /* Add color = { */
            words.add(word);
            words.add(originallyParsedWords.get(++i));
            words.add(originallyParsedWords.get(++i));
            words.add(StringUtils.join(Arrays.asList(originallyParsedWords.get(++i), originallyParsedWords.get(++i), originallyParsedWords.get(++i)), " "));
        }

        return words;
    }

    protected List<Node> getNodesFromWords(List<String> words) {
        List<Node> nodes = new ArrayList<>();
        Node currentNode = null;
        Node parentNode = null;
        int i = 0;
        boolean expectingBracketOrValue = false;
        boolean colorShenanigans = false;

        while (i < words.size()) {
            String word = words.get(i);

            if (parentNode != null && StringUtils.equalsIgnoreCase(parentNode.getName(), "color") && currentNode == null) {
                currentNode = parentNode;
                parentNode = currentNode.getParent();
                expectingBracketOrValue = true;
                colorShenanigans = true;
            }

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

                /* It is possible that there is nothing inside this node. If the next non-comment word is not a closing bracket, prepare for a new node */
                if (i + 1 < words.size() && !StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, getNextNonCommentString(words, i + 1))) {
                    currentNode = null;
                }
            } else if (StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, word)) {
                if (currentNode == null) {
                    logClosingBracketForNonExistingNodeError();
                    continue;
                }
                /* Close the current node */
                parentNode = currentNode.getParent();

                /* If the next non-comment word is not a closing bracket, prepare for a new node. Otherwise, update the currentNode to be the parent */
                if (i + 1 < words.size() && !StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, getNextNonCommentString(words, i + 1))) {
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
            } else if (StringUtils.startsWith(words.get(i), ParadoxParsingUtils.COMMENT_START)) {
                /* If we see a comment in the middle of the code, put it in it's own node, but don't change any of the existing pointers to the current or parent node */
                handleNewNode(currentNode.getNodes(), word);
            } else {
                if (!expectingBracketOrValue) {
                    logNotExpectingBracketError(currentNode);
                    continue;
                }

                expectingBracketOrValue = false;

                /* If we see a value for a node, this is the last word for this node. Update the node and then prepare for the next one */
                currentNode.setValue(word);

                /* If the next non-comment word is not a closing bracket, prepare for a new node */
                if (i + 1 < words.size() && !StringUtils.equals(ParadoxParsingUtils.CLOSE_BLOCK, getNextNonCommentString(words, i + 1))) {
                    currentNode = null;
                } else {
                    if (!colorShenanigans) {
                        currentNode = currentNode.getParent();
                    }

                    colorShenanigans = false;
                }
            }

            i++;
        }

        return nodes;
    }
}
