package Lesson2;

public class DayOfWeekMain {
    final static int WORK_WEEK = 5;
    final static int HOURS_IN_WORK_DAY = 8;
    final static int HOURS_IN_WORK_WEEK = WORK_WEEK * HOURS_IN_WORK_DAY;

    public static void main(String[] args) {
        for (DayOfWeek day : DayOfWeek.values())
            System.out.println(day.toString() + " - " + getWorkingHours(day));
    }

    private static String getWorkingHours(DayOfWeek day) {
        return day.ordinal() < WORK_WEEK ? (HOURS_IN_WORK_WEEK - day.ordinal() * HOURS_IN_WORK_DAY) + " Hours" : "Day off";
    }
}

enum DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
