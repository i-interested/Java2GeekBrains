package Lesson3;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {
    private HashMap<String, HashSet<String>> book;

    public PhoneBook() {
        book = new HashMap<>();
    }

    public void add(String Surname, String phone) {
        HashSet<String> phones = book.getOrDefault(Surname, new HashSet<>());
        phones.add(phone);
        book.put(Surname, phones);
    }

    public void get(String Surname) {
        System.out.println(book.containsKey(Surname) ? Surname + ": " + book.get(Surname) : "Sorry, don't have number " + Surname);
    }

}
