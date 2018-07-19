package Lesson6;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] mass = {5, 7, 9, 1};
        sort(mass, true);
        System.out.println("asc: " + Arrays.toString(mass));

        sort(mass, false);
        System.out.println("desc: " + Arrays.toString(mass));
    }

    public static void sort(int[] arr, Boolean asc) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                swap(arr, i, j, asc);
            }
        }
    }

    private static void swap(int[] arr, int i, int j, Boolean asc) {
        if ((arr[i] > arr[j]) ^ asc) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

}

