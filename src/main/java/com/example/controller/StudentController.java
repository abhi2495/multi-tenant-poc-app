package com.example.controller;

import com.example.jpa.Student;
import com.example.jpa.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/{tenantId}/api/student")
  public Student getStudent(@RequestParam("name") String name) {
    return studentRepository.findByName(name);
  }

  @PostMapping("/{tenantId}/api/student")
  public ResponseEntity postStudent(@RequestBody StudentContract studentContract) {
    Student student = new Student();
    student.setName(studentContract.getName());
    student.setEmail(studentContract.getEmail());
    studentRepository.save(student);
    return ResponseEntity.ok().build();
  }
}
