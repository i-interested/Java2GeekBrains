package Lesson2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BodyMassIndex {
    public static void main(String[] args) {
        String inputData = "118 2.05\n" +
                "106 1.77\n" +
                "87 1.83\n" +
                "45 1.12\n" +
                "70 1.87\n" +
                "54 1.57\n" +
                "105 1.76\n" +
                "50 1.96\n" +
                "114 1.76\n" +
                "72 2.45\n" +
                "53 2.10\n" +
                "66 2.25\n" +
                "54 1.50\n" +
                "95 1.62\n" +
                "86 1.72\n" +
                "62 1.57\n" +
                "65 2.24\n" +
                "72 1.43\n" +
                "93 2.01\n" +
                "109 3.01\n" +
                "106 2.97\n" +
                "77 1.69\n" +
                "114 2.09\n" +
                "98 1.72\n" +
                "85 2.46\n" +
                "113 1.94\n" +
                "53 1.77\n" +
                "106 2.30\n";
        splitMethod(inputData);
        regularMethod(inputData);
    }

    private static void splitMethod(String inputData) {
        for (String line : inputData.split("\n")) {
            String[] bmiArgs = line.split(" ");
            double bmi = getBmi(bmiArgs);
            printBmi(bmi);
        }
        System.out.println();
    }

    private static void regularMethod(String inputData) {
        Pattern pattern = Pattern.compile("\\d+[ ]\\d+[.]\\d+\n*");
        Matcher matcher = pattern.matcher(inputData);
        while (matcher.find()) {
            String [] bmiArgs = matcher.group().split(" ");
            double bmi = getBmi(bmiArgs);
            printBmi(bmi);
        }
        System.out.println();
    }

    private static double getBmi(String[] bmiArgs) {
        double height = Double.parseDouble(bmiArgs[0]);
        double mass = Double.parseDouble(bmiArgs[1]);
        return height / (mass * mass);
    }

    private static void printBmi(double bmi) {
        if (bmi < 18.5)
            System.out.print("under, ");
        else if (18.5 <= bmi && bmi < 25.0)
            System.out.print("normal, ");
        else if (25.0 <= bmi && bmi < 30.0)
            System.out.print("over, ");
        else if (30.0 <= bmi) {
            System.out.print("obese, ");
        }
    }
}
