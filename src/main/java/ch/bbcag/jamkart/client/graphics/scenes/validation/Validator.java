package ch.bbcag.jamkart.client.graphics.scenes.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1024;

    public static boolean validateIP(String ip) {
        if (ip.equals("localhost")) {
            return true;
        } else {
            Pattern pattern;
            Matcher matcher;
            String IPADDRESS_PATTERN
                    = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
            pattern = Pattern.compile(IPADDRESS_PATTERN);
            matcher = pattern.matcher(ip);
            return matcher.matches();
        }
    }

    public static boolean validatePort(String port) {
        return isNumeric(port) && Integer.parseInt(port) < MAX_PORT && Integer.parseInt(port) > MIN_PORT;
    }

    public static boolean isNumeric(String text) {
        if (text == null) {
            return false;
        }
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean validateName(String name) {
        return name.matches("(^[a-zA-Z0-9]([. _-](?![. _-])|[a-zA-Z0-9]){0,18}[a-zA-Z0-9]$)");
    }
}
