/*
 * Copyright (c) 2009.
 * Author by HUANGCD.
 * All right reserved.
 */

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
    private static boolean logable;
    public final static String STATE_ID;
    public final static String FA_NAME;
    public static final String FA_TYPE;
    public static final String DFA_NAME;
    public static final String NFA_NAME;
    public static final String STATE_TYPE_ATTR;
    public static final String STATE_TYPES;
    public static final String SYMBOL;
    public static final String FINAL_STATE;
    public static final String INITIAL_STATE;
    public static final String FROM_STATE;
    public static final String CONDITIONS;
    public static final String TO_STATES;
    public static final String STATES;
    public static final String STATE;
    public static final String TRANSITIONS;
    public static final String TRANSITION;
    public static final String INPUT_SYMBOLS;
    public static final String INPUT_SYMBOL;
    public static final String DOCUMENT_TYPE;
    public static final String SYSTEM_ID;
    public static final String CONDITION;
    public static final String STATE_TYPE;
    public static final String TO_STATE;

    // assign constant
    static {
        SYSTEM_ID = "FA.dtd";
        DOCUMENT_TYPE = "FiniteAutomaton";
        FA_NAME = "name";
        DFA_NAME = "DFA";
        NFA_NAME = "NFA";
        FA_TYPE = "type";

        STATE = "State";
        STATES = "States";
        STATE_ID = "stateID";
        STATE_TYPE_ATTR = "type";
        STATE_TYPES = "StateTypes";
        STATE_TYPE = "StateType";
        INITIAL_STATE = "INITIAL";
        FINAL_STATE = "FINAL";

        INPUT_SYMBOLS = "InputSymbols";
        INPUT_SYMBOL = "InputSymbol";
        SYMBOL = "symbol";

        TRANSITION = "Transition";
        TRANSITIONS = "Transitions";
        FROM_STATE = "FromState";
        CONDITIONS = "Conditions";
        CONDITION = "Condition";
        TO_STATES = "ToStates";
        TO_STATE = "ToState";
    }


    static {
        logable = true;
        String loggerPath =
                "./log/" + new Date().toString().replaceAll("[\\s:]", "_") + ".xml";
        File loggerFile;
        loggerFile = new File(loggerPath);
        try {
            loggerFile.getParentFile().mkdirs();
            loggerFile.createNewFile();
        } catch (IOException ignored) {
        }
        try {
            defaultFileHandler = new FileHandler(loggerPath);
        } catch (IOException e) {
            logable = false;
            e.printStackTrace();
        }
        loggerMap = new HashMap<Class, Logger>();
        home = System.getProperty("user.dir");
    }

    public static Logger getLogger(Class c) {
        if (loggerMap.containsKey(c))
            return loggerMap.get(c);
        Logger logger = Logger.getLogger(c.getName());
        if (logable)
            logger.addHandler(defaultFileHandler);
        logger.setUseParentHandlers(false);
        loggerMap.put(c, logger);
        return logger;
    }

    public static String getHome() {
        return home;
    }

// --Commented out by Inspection START (09-12-31 下午1:18):
//    public static String textToUnicode(String text) {
//        StringBuilder buffer = new StringBuilder();
//        for (char c : text.toCharArray()) {
//            String s = Integer.toHexString(c);
//            if (s.length() == 1)
//                buffer.append("\\u000").append(s);
//            else if (s.length() == 2)
//                buffer.append("\\u00").append(s);
//            else if (s.length() == 3)
//                buffer.append("\\u0").append(s);
//            else
//                buffer.append("\\u").append(s);
//        }
//        return buffer.toString();
//    }
// --Commented out by Inspection STOP (09-12-31 下午1:18)
}