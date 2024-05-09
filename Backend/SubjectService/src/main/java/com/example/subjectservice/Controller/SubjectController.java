package com.example.subjectservice.Controller;

import com.example.subjectservice.Dto.SubjectDto;
import com.example.subjectservice.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getDetail(@PathVariable int id){
        return ResponseEntity.ok(subjectService.getDetail(id));
    }
}
