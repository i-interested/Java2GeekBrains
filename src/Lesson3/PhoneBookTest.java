package Lesson3;

public class PhoneBookTest {
    public static void main(String[] args) {
        PhoneBook book = new PhoneBook();
        book.add("Ivanov", "2431234");
        book.add("Ivanov", "1223434");
        book.add("Ivanov", "12534535");
        book.add("Petrov", "4345344");
        book.add("Petrov", "43452545");
        book.add("Petrov", "43452346");

        book.get("Ivanov");
        book.get("Petrov");
        book.get("Sidoriv");

    }
}
