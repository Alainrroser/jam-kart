package ch.bbcag.jamkart.client.graphics.scenes.validation;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateIP(String ip) {
        if (ip.equals("localhost")) {
            return true;
        }else{
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
        return isNumeric(port) && Integer.parseInt(port) < 65535 && Integer.parseInt(port) > 1024;
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

    public static boolean validateName(String name){
        return name.matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$");
    }
}
