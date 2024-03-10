package com.example.SpringBootTestCrud.controllers;

import com.example.SpringBootTestCrud.entities.Student;
import com.example.SpringBootTestCrud.repositories.StudentRepo;
import com.example.SpringBootTestCrud.services.StudentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public @ResponseBody Student add(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @GetMapping("/getall")
    public @ResponseBody List<Student> getAll() {
        return studentRepo.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Student> getById(@PathVariable long id) {
        if (studentRepo.findById(id).isPresent()) {
            return studentRepo.findById(id);
        } else {
            return Optional.empty();
        }
    }

    @PutMapping("/update/{id}")
    public Optional<Student> updateById(@PathVariable long id, @RequestBody Student student) {
        Optional<Student> student1 = studentRepo.findById(id);
        if (student1.isPresent()) {
            student1.get().setName(student.getName());
            student1.get().setSurname(student.getSurname());
            return student1;
        } else {
            return Optional.empty();
        }
    }

    @PutMapping("/statusupdate/{id}")
    public Optional<Student> updateStatusById(@PathVariable long id, @RequestParam boolean working) {
        Optional<Student> student1 = studentRepo.findById(id);
        if (student1.isPresent()) {
            studentService.changeWorkingStatus(student1.get(), working);
            return student1;
        } else {
            return Optional.empty();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Student> deleteById (@PathVariable long id) {
        if (studentRepo.findById(id).isPresent()) {
            studentRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
