package Lesson4.Pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMainClass {
    public static void main(String[] args) {
        String[] stringsToCheck = {"sdkjfhsd", "1.1.1.1", "10.10.10.10", "100.100.100.100",
                "256.256.256.256", "255.255.255.255", "sdg254.254.254.254sdfg", "255hkjh.255.khk.255.255"};
        for (String string : stringsToCheck)
            System.out.println(string + " has " + (hasIpAddress(string) ? "" : "no ") + "IP address");
    }

    public static boolean hasIpAddress(String input) {
        Pattern pattern = Pattern.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
