package com.example.SpringBootTestCrud.services;

import com.example.SpringBootTestCrud.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    public void changeWorkingStatus(Student student, boolean b) {
        student.setWorking(b);
    }
}
