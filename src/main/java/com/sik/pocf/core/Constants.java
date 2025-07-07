package com.sik.pocf.core;

import java.time.format.DateTimeFormatter;

public class Constants {


    public static final DateTimeFormatter CSV_DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyHH:mm");
    public static final DateTimeFormatter ICS_DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    public static final int SHIP_EVT_MIN_LEN = 13;

    public static final int I_ID = 0;
    public static final int I_DAY = 1;
    public static final int I_DATE = 2;
    public static final int I_VESSEL = 3;
    public static final int I_COMPANY = 4;
    public static final int I_ARRTIM = 5;
    public static final int I_POB = 6;
    public static final int I_DEPTIM = 7;
    public static final int I_BERTH = 8;
    public static final int I_GTONNS = 9;
    public static final int I_LENGTH = 10;
    public static final int I_PAX = 11;
    public static final int I_CREW = 12;
    public static final int I_LANG = 13;
    //public static final int I_FROM = 14;

}
