package com.example.SpringBootTestCrud;

import com.example.SpringBootTestCrud.entities.Student;
import com.example.SpringBootTestCrud.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void changestatus() {
        Student student = new Student();
        student.setWorking(false);
        studentService.changeWorkingStatus(student, true);
        assertThat(student.isWorking()).isEqualTo(true);
    }
}
