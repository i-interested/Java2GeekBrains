package Lesson3;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdditionalTask {
    public static void main(String[] args) {
        System.out.println("Please, Enter you password:");
        //Scanner scanner = new Scanner(System.in);
        //String password = scanner.next();
        //System.out.println(isValidPassword(password));
        String[] pass = {"12345678", "123456789", "1", "12", "1q", "1q2w3e4r", "1q2w3e4r5t", "1Q",
                "1qQ", "1Qq","q1","Q1","Qq1","qqqqqqqqqq1","qqqqqqqqqqq","QQQQQQQQQQ", "1Q2w3e4rt","QWFDJH256f"};
        for (String pas : pass) {
            System.out.println(pas + " : " + isValidPassword(pas));
        }
    }

    private static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("(?=^.{8,}$)(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}