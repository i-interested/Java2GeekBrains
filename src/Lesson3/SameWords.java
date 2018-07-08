package Lesson3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SameWords {
    public static void main(String[] args) {
        String[] words = {"Banana", "Apple", "Fruit", "Juice", "Laptop", "Fun", "Phone", "Browser", "Cup", "Mouse",
                "Door", "Table", "Banana", "Apple", "Fruit", "Juice", "Banana", "Apple", "Banana", "Tea", "Coffee", "Sugar"};
        HashMap<String, Integer> wordsMap = new HashMap<>(30);
        for (String word : words) {
            int count = wordsMap.getOrDefault(word, 0);
            wordsMap.put(word, count + 1);
        }
        printUnique(wordsMap);
        printCount(wordsMap);

    }

    private static void printUnique(HashMap<String, Integer> map) {
        System.out.println("Unique words:");
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        for (Map.Entry<String, Integer> pair : set)
            if (pair.getValue() == 1)
                System.out.println(pair.getKey());
    }

    private static void printCount(HashMap<String, Integer> map) {
        System.out.println("Count each word:");
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        for (Map.Entry<String, Integer> pair : set)
            System.out.println(pair.getKey() + ": " + pair.getValue());
    }
}
