package com.example.annakocheshkova.testapplication.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * A util class for parsing dates
 */
public class DateParser {

    /**
     * Instance of dateParser
     */
    private final static DateParser dateParser  = new DateParser();

    /**
     * The date format we are using
     */
    private DateFormat dateFormat;

    /**
     * Creates the instance of date parser
     */
    private DateParser() {
        dateFormat = DateFormat.getDateTimeInstance(3, 0); // dd/MM/yyyy hh:mm g
    }

    /**
     * Gets the instance of date parser
     * @return instance of date parser
     */
    public static DateParser getInstance() {
        return dateParser;
    }

    /**
     * Converts String to date
     * @param dateString string with date
     * @return date in Date format
     */
    public Date parse(String dateString) {
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
     * Converts date to string
     * @param date date in Date format
     * @return date in string format
     */
    public String parse(Date date) {
        return dateFormat.format(date);
    }
}
