package com.example.SpringBootTestCrud.repositories;

import com.example.SpringBootTestCrud.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
}
