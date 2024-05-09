package com.example.studentservice.Controller;

import com.example.studentservice.Dto.StudentDto;
import com.example.studentservice.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getDetail(@PathVariable int id){
        return  ResponseEntity.ok(studentService.getDetail(id));
    }
}
