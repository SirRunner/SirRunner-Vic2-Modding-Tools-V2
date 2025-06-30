package utils;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

public class Logger {

    protected static AnsiFormat infoFormat = new AnsiFormat(Attribute.WHITE_TEXT());
    protected static AnsiFormat warnFormat = new AnsiFormat(Attribute.YELLOW_TEXT());
    protected static AnsiFormat errorFormat = new AnsiFormat(Attribute.RED_TEXT());
    protected static AnsiFormat debugFormat = new AnsiFormat(Attribute.GREEN_TEXT());

    public static boolean debug = false;

    public static void info(String str) {
        print("[INFO] " + str, infoFormat);
    }

    public static void warn(String str) {
        print("[WARN] " + str, infoFormat);
    }

    public static void debug(String str) {
        if (debug) {
            print("[DEBUG] " + str, debugFormat);
        }
    }

    public static void error(String str) {
        print("[ERROR] " + str, errorFormat);
    }

    public static void print(String str) {
        print(str, null);
    }

    public static void print(String str, AnsiFormat format) {
        if (format == null) {
            System.out.println(str);
        } else {
            System.out.println(format.format(str));
        }
    }
}
