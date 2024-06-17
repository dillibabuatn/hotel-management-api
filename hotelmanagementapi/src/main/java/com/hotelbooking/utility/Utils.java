package com.hotelbooking.utility;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;

/**
 * Utility class providing common methods for parsing dates, extracting parameter values,
 * and sending HTTP responses.
 */
public class Utils {

    /**
     * Parses a date string into a Date object using the format "yyyy-MM-dd".
     *
     * @param dateString The date string to parse.
     * @return The parsed Date object.
     * @throws RuntimeException If parsing fails due to invalid format.
     */
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format, expected yyyy-MM-dd", e);
        }
    }

    /**
     * Retrieves the value associated with a given key from an array of parameter strings.
     *
     * @param params The array of parameter strings in the format "key=value".
     * @param key    The key to search for.
     * @return The value associated with the key.
     * @throws IllegalArgumentException If the key is not found in the params array.
     */
    public static String getValue(String[] params, String key) {
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(key)) {
                return keyValue[1];
            }
        }
        throw new IllegalArgumentException("Missing parameter: " + key);
    }

    /**
     * Sends an HTTP response with the specified status code and response body.
     *
     * @param exchange   The HttpExchange object representing the HTTP request and response.
     * @param statusCode The HTTP status code to send.
     * @param response   The response body to send.
     * @throws IOException If an I/O error occurs while sending the response.
     */
    public static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
