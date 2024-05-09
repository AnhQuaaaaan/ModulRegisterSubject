package com.example.selectedclassservice.Controller;

import com.example.selectedclassservice.Dto.ScheduleClassDto;
import com.example.selectedclassservice.Dto.SelectedClassDto;
import com.example.selectedclassservice.Service.SelectedClassService;
import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/selectedclass")
public class SelectedClassController {
    @Autowired
    private SelectedClassService selectedClassService;
    @GetMapping
    public  ResponseEntity<List<SelectedClassDto>> GetAllSelectedClass(){
        return ResponseEntity.ok(selectedClassService.getAll());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<SelectedClassDto> getSelectedClassById(@PathVariable int id){
        return ResponseEntity.ok(selectedClassService.getSelectedClassById(id));
    }
    @PostMapping("/save")
    public ResponseEntity<SelectedClassDto> saveSelectedClass(@RequestBody SelectedClassDto selectedClassDto){
        selectedClassService.save(selectedClassDto);
        return ResponseEntity.ok(selectedClassDto);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateSelectedClass(@RequestBody SelectedClassDto selectedClassDto,@PathVariable int id){
        selectedClassDto.setId(id);
        selectedClassService.update(selectedClassDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSelectedClass(@PathVariable int id){
        selectedClassService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/checkduplicate")
    public ResponseEntity<?> CheckDuplicate(@RequestBody ScheduleClassDto scheduleClassDto) throws ParseException {
        try {
            return ResponseEntity.ok(selectedClassService.checkDuplicate(scheduleClassDto));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/checkregister")
    public ResponseEntity<?> CheckRegister(@RequestBody ScheduleClassDto scheduleClassDto)  {
        return ResponseEntity.ok(selectedClassService.checkRegister(scheduleClassDto));
    }
}
