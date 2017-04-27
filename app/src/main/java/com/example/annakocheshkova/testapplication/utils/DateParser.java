package com.example.annakocheshkova.testapplication.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * A util class for parsing dates
 */
public class DateParser {

    /**
     * The date format we are using
     */
    private static DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 0);

    /**
     * Converts String to date
     * @param dateString string with date
     * @param dateFormat date formatter
     * @return date in Date format
     */
    public static Date parse(String dateString, DateFormat dateFormat) {
        if (dateString == null) {
            return null;
        } else {
            Date expirationDate;
            try {
                expirationDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                expirationDate = null;
            }
            return expirationDate;
        }
    }

    /**
     * Converts string to date, using a default formatter stored in this class
     * @param dateString string with date
     * @return date in Date format
     */
    public static Date parse(String dateString) {
        return parse(dateString, dateFormat);
    }

    /**
     * Converts date to string
     * @param date date in Date format
     * @param dateFormat date formatter
     * @return date in string format
     */
    public static String parse(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    /**
     * Converts date to string, using a default formatter stored in this class
     * @param date date in Date format
     * @return date in string format
     */
    public static String parse(Date date) {
        return parse(date, dateFormat);
    }
}
