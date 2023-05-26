import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class StudentAPI {

    // Example student data
    private List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(StudentAPI.class, args);
    }

    public StudentAPI() {
        // Add sample student data
        students.add(new Student(1, "John Doe", 85));
        students.add(new Student(2, "Jane Smith", 90));
        students.add(new Student(3, "Alice Johnson", 78));
        students.add(new Student(4, "Bob Anderson", 95));
    }

    // Load Student Details API
    @GetMapping("/students")
    public List<Student> getStudents(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int pageSize) {
        int startIdx = (page - 1) * pageSize;
        int endIdx = Math.min(startIdx + pageSize, students.size());

        return students.subList(startIdx, endIdx);
    }

    // Server-side Filtering API
    @PostMapping("/students/filter")
    public List<Student> filterStudents(@RequestBody StudentFilterCriteria filterCriteria) {
        return students.stream()
                .filter(s -> (filterCriteria.getName() == null || s.getName().contains(filterCriteria.getName()))
                        && (filterCriteria.getMinTotalMarks() == null || s.getTotalMarks() >= filterCriteria.getMinTotalMarks()))
                .collect(Collectors.toList());
    }
}

class Student {
    private int id;
    private String name;
    private int totalMarks;

    public Student(int id, String name, int totalMarks) {
        this.id = id;
        this.name = name;
        this.totalMarks = totalMarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
}

class StudentFilterCriteria {
    private String name;
    private Integer minTotalMarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinTotalMarks() {
        return minTotalMarks;
    }

    public void setMinTotalMarks(Integer minTotalMarks) {
        this.minTotalMarks = minTotalMarks;
    }
}
