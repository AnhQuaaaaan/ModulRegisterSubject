package com.example.taskservice.Controller;

import com.example.taskservice.Dto.ScheduleClassDto;
import com.example.taskservice.Dto.SelectedClassDto;
import com.example.taskservice.Model.NotificationRequest;
import com.example.taskservice.Service.TASKSERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/task")
public class taskcontroller {
    @Autowired
    private TASKSERVICE taskservice;

    @GetMapping("/getscheduleclass")
    public Mono<ResponseEntity<List<ScheduleClassDto>>> getAllClass(){
        return taskservice.getAllschedule()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/getselectedclass")
    public Mono<ResponseEntity<List<SelectedClassDto>>> getAllSelectedClass(){
        return taskservice.getAllSelectedClass()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping("/registerSubject/{id}")
    public Mono<ResponseEntity<Object>> registerSubject(@PathVariable int id) {
        return taskservice.RegisterSubject(id)
                .map(ignored -> ResponseEntity.ok().build())
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()));
                });
    }
    @DeleteMapping("/deleteselectedclass/{id}")
    public Mono<ResponseEntity<Object>> DeleteSelectedClass(@PathVariable int id){
        return taskservice.DeleteSelectedClass(id)
                .map(ignored -> ResponseEntity.ok().build())
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()));
                });
    }


}
