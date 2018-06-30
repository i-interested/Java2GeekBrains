package Lesson1;

import Lesson1.Competitor.Cat;
import Lesson1.Competitor.Dog;
import Lesson1.Competitor.Human;
import Lesson1.Competitor.Team;
import Lesson1.Obstacle.Course;
import Lesson1.Obstacle.Cross;
import Lesson1.Obstacle.Wall;
import Lesson1.Obstacle.Water;

public class Main {
    public static void main(String[] args) {

        Team team = new Team("Rocket", new Human("Bob"), new Cat("Vaska"), new Dog("Bobik"));
        Course course = new Course(
                new Cross(80),
                new Wall(2),
                new Wall(1),
                new Cross(120),
                new Water(100));
        course.doIt(team);
        team.showResults();

    }
}