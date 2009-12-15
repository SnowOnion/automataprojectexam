package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-7
 * Time: 20:24:36
 */
public class Util {
    private static Map<Class, Logger> loggerMap;
    private static FileHandler defaultFileHandler;
    private static String home;

    static {
        String loggerPath =
                "./log/" + new Date().toString().replaceAll("[\\s:]", "_") + ".xml";
        File loggerFile;
        loggerFile = new File(loggerPath);
        try {
            loggerFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            defaultFileHandler = new FileHandler(loggerPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loggerMap = new HashMap<Class, Logger>();
        home = System.getProperty("user.dir");
    }

    public static Logger getLogger(Class c) {
        if (loggerMap.containsKey(c))
            return loggerMap.get(c);
        Logger logger = Logger.getLogger(c.getName());
        logger.addHandler(defaultFileHandler);
        logger.setUseParentHandlers(false);
        loggerMap.put(c, logger);
        return logger;
    }

    public static String getHome() {
        return home;
    }

    public static String textToUnicode(String text) {
        StringBuilder buffer = new StringBuilder();
        for (char c : text.toCharArray()) {
            String s = Integer.toHexString(c);
            if (s.length() == 1)
                buffer.append("\\u000").append(s);
            else if (s.length() == 2)
                buffer.append("\\u00").append(s);
            else if (s.length() == 3)
                buffer.append("\\u0").append(s);
            else
                buffer.append("\\u").append(s);
        }
        return buffer.toString();
    }
}