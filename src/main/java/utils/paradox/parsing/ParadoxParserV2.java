package utils.paradox.parsing;

import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.nodes.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParadoxParserV2 extends ParadoxParser {

    private final List<String> LOOKBEHIND_UNNAMED_NODE_WORDS = Arrays.asList(ParadoxParsingUtils.OPEN_BLOCK, ParadoxParsingUtils.CLOSE_BLOCK);

    protected List<Node> getNodesFromWords(List<String> words) {

        List<Node> nodes = new ArrayList<>();
        Node current = null;
        Node parent = null;

        boolean seenDefines = false;

        for (int i = 0; i < words.size(); i++) {

            String word = words.get(i);
            String lookahead = null;
            String lookbehind = null;

            if (i + 1 < words.size()) {
                lookahead = words.get(i + 1);
            }

            if (i - 1 >= 0) {
                lookbehind = words.get(i - 1);
            }

            if (ParadoxParsingUtils.CLOSE_BLOCK.equals(word)) {

                if (parent == null) {

                    // The save file ends in an extra } for some reason. We're going to be ignoring this
                    if (i == words.size() - 1 && ParadoxParsingUtils.CLOSE_BLOCK.equals(lookbehind)) {
                        continue;
                    }

                    Logger.error("Attempting to close undefined node");
                    printCurrentState(parent, current, word, i, nodes);
                    return null;
                }

                if (parent.getParent() == null) {
                    nodes.add(parent);
                }

                parent = parent.getParent();
                continue;
            }

            // We have finished up the previous node and gone to the next in the list or this is the first node
            if (current == null) {

                // Consider employees = { { province_pop_id = ... count = 55328 } }. This is a valid set of nodes in the save file despite not having a name for the inner node
                // In this scenario, we should create a new node on the second open brace and also follow the standard protocol for an open brace
                if (lookbehind != null && LOOKBEHIND_UNNAMED_NODE_WORDS.contains(lookbehind) && ParadoxParsingUtils.OPEN_BLOCK.equals(word) && parent != null) {
                    // New Node actions
                    current = new Node();
                    current.setName("");

                    parent.addNodes(current);
                    current.setParent(parent);

                    // Open Block actions
                    seenDefines = false;
                    parent = current;
                    current = null;
                    continue;
                }

                // Consider setgameplayoptions = { 2 }. It's a valid node in the save game despite the node having both brackets and a value and no child nodes
                // In this scenario, we don't want to create a new node but instead update the parent node
                // TODO: Need to figure out how to best represent this in the node so that the toString matches. Right now it incorrectly shows setgameplayoptions = 2
                if (lookahead != null && !ParadoxParsingUtils.DEFINES.equals(lookahead) && parent != null) {
                    String value = StringUtils.defaultString(parent.getValue()) + " " + word;
                    parent.setValue(value);
                    continue;
                }

                current = new Node();
                current.setName(word);

                if (parent != null) {
                    parent.addNodes(current);
                    current.setParent(parent);
                }
                continue;
            }

            // Node definition has just started -- we only have the name and now see "="
            if (ParadoxParsingUtils.DEFINES.equals(word)) {
                seenDefines = true;
                continue;
            }

            // After "=" we see an open block. We need to be ready to create child node(s)
            if (ParadoxParsingUtils.OPEN_BLOCK.equals(word)) {
                seenDefines = false;
                parent = current;
                current = null;
                continue;
            }

            // Default handling for what we do after we see "="
            if (seenDefines) {
                current.setValue(word);
                seenDefines = false;

                // If we have a parent, the child node is stored in the parent upon creation
                if (parent == null) {
                    nodes.add(current);
                }

                current = null;
            }
        }

        return nodes;
    }

    private void printCurrentState(Node parent, Node current, String word, int i, List<Node> nodes) {
        Logger.info("Parent: " + (parent == null ? "undefined" : parent.toString()));
        Logger.info("Current: " + (current == null ? "undefined" : current.toString()));
        Logger.info("Parent: " + word);
        Logger.info("i: " + i);
        Logger.info("Node List Size: " + nodes.size());
        if (!nodes.isEmpty()) {
            Logger.info("Last Node: " + nodes.get(nodes.size() - 1).toString());
        }
    }
}
