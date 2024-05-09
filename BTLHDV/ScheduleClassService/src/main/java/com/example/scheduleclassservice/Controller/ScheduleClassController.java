package com.example.scheduleclassservice.Controller;

import com.example.scheduleclassservice.Dto.ScheduleClassDto;
import com.example.scheduleclassservice.Dto.SelectedClassDto;
import com.example.scheduleclassservice.Service.ScheduleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/scheduleclass")
public class ScheduleClassController {
    @Autowired
    private ScheduleClassService scheduleClassService;

    @GetMapping
    public ResponseEntity<List<ScheduleClassDto>> getAllClass(){
        return ResponseEntity.ok(scheduleClassService.getAllClass());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleClassDto> getClassByID(@PathVariable int id){
        return ResponseEntity.ok(scheduleClassService.getClassByID(id));
    }
    @PutMapping("/updateslot")
    public ResponseEntity<?> updateslot(@RequestBody ScheduleClassDto scheduleClassDto){
        scheduleClassService.update(scheduleClassDto);
        return ResponseEntity.ok(scheduleClassDto);
    }
    @PostMapping("/checkcapacity/{id}")
    public ResponseEntity<?> CheckCapacity(@PathVariable int id){
        return ResponseEntity.ok().body(scheduleClassService.checkCapacity(id));
    }

 }
