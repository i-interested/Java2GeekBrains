package Lesson2;

import java.util.Random;


public class MainTask {
    public static void main(String[] args) {
        String[][] stringArray = new String[4][4];
        fillArray(stringArray);
        printArray(stringArray);
        int sum = 0;
        try {
            sum = sumArray(stringArray);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
        System.out.println(sum);

    }

    private static void fillArray(String[][] stringArray) {
        Random random = new Random();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (random.nextInt(100) > 5)
                    stringArray[i][j] = String.valueOf(random.nextInt(100));
                else
                    stringArray[i][j] = "bad";
    }

    private static void printArray(String [][] stringArray){
        for (int i = 0; i < stringArray.length; i++) {
            for (int j = 0; j < stringArray[i].length; j++)
                System.out.print(stringArray[i][j]+" ");
            System.out.println();
        }
    }

    private static int sumArray(String[][] stringArray) throws MyArraySizeException, MyArrayDataException {
        if (stringArray.length != 4) throw new MyArraySizeException();
        int sum = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].length != 4) throw new MyArraySizeException();
            for (int j = 0; j < stringArray[i].length; j++)
                try {
                    sum += Integer.parseInt(stringArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
        }
        return sum;
    }
}

