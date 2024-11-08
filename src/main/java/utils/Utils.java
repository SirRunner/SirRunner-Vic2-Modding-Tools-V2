package utils;

import org.apache.commons.lang3.StringUtils;
import utils.paradox.parsing.ParadoxParsingUtils;

import java.io.File;
import java.util.*;

public class Utils {
    protected static String[] STRING_TRUE_VALUES = {"YES", "TRUE"};

    public static boolean isTrue(String value) {
        return containsIgnoreCase(value, STRING_TRUE_VALUES);
    }

    public static boolean containsIgnoreCase(String value, String[] list) {
        return containsIgnoreCase(value, Arrays.asList(list));
    }

    public static boolean containsIgnoreCase(String value, Iterable<String> list) {
        for (String object : list) {
            if (StringUtils.equalsIgnoreCase(value, object)) {
                return true;
            }
        }

        return false;
    }

    public static boolean mapContainsAll(Map<String, String> map, Iterable<String> list) {
        for (String item : list) {
            if (!map.containsKey(item)) {
                return false;
            }
        }

        return true;
    }

    public static void clearFolder(File folder) {
        clearFolder(folder, new HashSet<>());
    }

    public static void clearFolder(File folder, Set<String> exceptions) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    if (file.isDirectory()) {
                        Logger.info("File " + file.getAbsolutePath() + " is a directory");
                    } else if (exceptions.contains(file.getName().toUpperCase())) {
                        Logger.info("File " + file.getAbsolutePath() + " is on exceptions list -- NOT DELETING");
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    public static String getCSV(List<String> strings) {
        return getCSV(strings, ParadoxParsingUtils.LOCALISATION_SPLITTER);
    }

    public static String getCSV(List<String> strings, String delimiter) {
        return StringUtils.join(strings, delimiter);
    }
}
