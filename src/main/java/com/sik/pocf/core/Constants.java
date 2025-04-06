package com.sik.pocf.core;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Constants {
    /**
     * The total of the multipliers below must add up to 2 exactly.
     */
    public static final List<Double> LAST_6_MULTIPLIERS = Arrays.asList(0.04D,0.06D,0.1D,0.2D,0.6D,1.0D);

    private static final String PROPS_FILE = "/Users/sik/config/pocf.properties";
    public final Properties PROPS = this.getPropsFromFile();
    //public static final String CSV_FILE = PROPS.getProperty("csvFile");

    public static final DateTimeFormatter CSV_DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyHH:mm");
    public static final DateTimeFormatter ICS_DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    public static final int SHIP_EVT_MIN_LEN = 13;

    public static final int I_ID = 0;
    public static final int I_DAY = 1;
    public static final int I_DATE = 2;
    public static final int I_VESSEL = 3;
    public static final int I_COMPANY = 4;
    public static final int I_ARRTIM = 5;
    public static final int I_DEPTIM = 6;
    public static final int I_BERTH = 7;
    public static final int I_GTONNS = 8;
    public static final int I_LENGTH = 9;
    public static final int I_PAX = 10;
    public static final int I_CREW = 11;
    public static final int I_LANG = 12;
    public static final int I_FROM = 13;

    public static final int I1 = 1;
    public static final int I0 = 0;

    public static final Double GOALS_FOR_DELTA_DRAW_MAX = 0.9;

    private Properties getPropsFromFile() {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        try {
            props.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

}
