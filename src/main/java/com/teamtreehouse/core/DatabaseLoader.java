package com.teamtreehouse.core;

import com.teamtreehouse.course.Course;
import com.teamtreehouse.course.CourseRepository;
import com.teamtreehouse.review.Review;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private final CourseRepository coursesRepo;
    private final UserRepository usersRepo;

    @Autowired
    public DatabaseLoader(CourseRepository coursesRepo, UserRepository usersRepo) {
        this.coursesRepo = coursesRepo;
        this.usersRepo = usersRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Course course = new Course("Java Basics", "http://teamtreehouse.com/library/java-basics");
        course.addReview(new Review(3, "Mediocrazy shines!!!"));
        coursesRepo.save(course);

        String[] templates = {
                "Up and running with %s",
                "How about learning about %s",
                "The very best of %s",
                "%s 101"
        };
        String[] buzzwords = {
                "Beer competition",
                "Porn film production",
                "24h rave dance moves",
                "Drug dealing",
                "Porn hands on"
        };

        List<User> students = Arrays.asList(
                new User("jacobproffer", "Jacob",  "Proffer", "password", new String[] {"ROLE_USER"}),
                new User("mlnorman", "Mike",  "Norman", "password", new String[] {"ROLE_USER"}),
                new User("k_freemansmith", "Karen",  "Freeman-Smith", "password", new String[] {"ROLE_USER"}),
                new User("seth_lk", "Seth",  "Kroger", "password", new String[] {"ROLE_USER"}),
                new User("mrstreetgrid", "Java",  "Vince", "password", new String[] {"ROLE_USER"}),
                new User("anthonymikhail", "Tony",  "Mikhail", "password", new String[] {"ROLE_USER"}),
                new User("boog690", "AJ",  "Teacher", "password", new String[] {"ROLE_USER"}),
                new User("faelor", "Erik",  "Faelor Shafer", "password", new String[] {"ROLE_USER"}),
                new User("christophernowack", "Christopher",  "Nowack", "password", new String[] {"ROLE_USER"}),
                new User("calebkleveter", "Caleb",  "Kleveter", "password", new String[] {"ROLE_USER"}),
                new User("richdonellan", "Rich",  "Donnellan", "password", new String[] {"ROLE_USER"}),
                new User("albertqerimi", "Albert",  "Qerimi", "password", new String[] {"ROLE_USER"})
        );
        usersRepo.saveAll(students);
        User adminUser = new User("bernheart","Bernhard", "Eiling", "12345",  new String[] {"ROLE_USER", "ROLE_ADMIN"});
        usersRepo.save(adminUser);

        List<Course> newCourses = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    String title = String.format(template, buzzword);
                    Course newCourse = new Course(title, "http:/example.com");
                    Review newReview = new Review((i % 5) + 1, String.format("Mooaarr %s pls senpai!", buzzword));
                    newReview.setReviewer(students.get(i % students.size()));
                    newCourse.addReview(newReview);
                    newCourses.add(newCourse);
                });
        coursesRepo.saveAll(newCourses);
    }
}
