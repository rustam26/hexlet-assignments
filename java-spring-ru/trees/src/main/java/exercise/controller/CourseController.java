package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping(path = "{id}/previous")
    public Iterable<Course> getAllCoursesById(@PathVariable("id") long id) {
        Course course = courseRepository.findById(id);
        String path = course.getPath();
        if (path != null && !path.isEmpty()) {
            List<Long> ids = Arrays.stream(path.split("\\."))
                    .map(Long::valueOf)
                    .toList();
            return courseRepository.findAllById(ids);

        }
        return null;
    }
    // END

}
