package Lesson5;

import java.util.Arrays;
import java.lang.Math;
import java.lang.System;

public class ThreadMainClass {
    final static int SIZE = 10000000;

    public static void main(String[] args) {
        testSingleThread();
        for (int i = 1; i <= 25; i++)
            testThreads(i);
    }

    public static void testSingleThread() {
        float[] array = new float[SIZE];
        Arrays.fill(array, 1f);
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++)
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void testThreads(int threadCount) {
        int THREAD_SIZE = SIZE / threadCount;
        float[] array = new float[SIZE];
        Arrays.fill(array, 1f);
        long a = System.currentTimeMillis();
        float[][] doubleArray = new float[threadCount][THREAD_SIZE];
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            System.arraycopy(array, THREAD_SIZE * i, doubleArray[i], 0, THREAD_SIZE);
            int threadNumber = i;
            threads[i] = new Thread(() -> {
                int realIdx = threadNumber * THREAD_SIZE;
                for (int j = 0; j < THREAD_SIZE; j++) {
                    doubleArray[threadNumber][j] = (float) (doubleArray[threadNumber][j]
                            * Math.sin(0.2f + realIdx / 5)
                            * Math.cos(0.2f + realIdx / 5)
                            * Math.cos(0.4f + realIdx / 2));
                    realIdx++;
                }
            });
            threads[i].start();
        }
        try {
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < threadCount; i++)
            System.arraycopy(doubleArray[i], 0, array, i * THREAD_SIZE, THREAD_SIZE);

        System.out.println("Thread count: " + threadCount + ", ms: " + String.valueOf(System.currentTimeMillis() - a));
    }
}
