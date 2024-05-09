package com.example.scheduleclassservice.Service;

import com.example.scheduleclassservice.Dto.ScheduleClassDto;
import com.example.scheduleclassservice.Dto.SelectedClassDto;
import com.example.scheduleclassservice.Dto.SubjectDto;
import com.example.scheduleclassservice.Model.ScheduleClass;
import com.example.scheduleclassservice.Repository.ScheduleClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ScheduleClassService {
    @Autowired
    private WebClient webClient;
    @Value("${subject-service.url}")
    private String SubjectServiceurl;
    @Autowired
    private ScheduleClassRepository scheduleClassRepository;
    private ScheduleClassDto convertToDto(ScheduleClass scheduleClass) {
        SubjectDto subjectDto=webClient.get()
                .uri(SubjectServiceurl+"/"+scheduleClass.getSubject_id())
                .retrieve()
                .bodyToMono(SubjectDto.class)
                .block();
        ScheduleClassDto dto=new ScheduleClassDto();
        dto.setId(scheduleClass.getId());
        dto.setMamh(subjectDto.getMamh());
        dto.setTenmh(subjectDto.getTen());
        dto.setTengiangvien(scheduleClass.getTengiangvien());
        dto.setNhom(scheduleClass.getNhom());
        dto.setTc(subjectDto.getTc());
        dto.setLichhoc(scheduleClass.getLichhoc());
        dto.setSlot(scheduleClass.getSlot());
        dto.setConlai(scheduleClass.getConlai());
        dto.setSubject_id(subjectDto.getId());
        return dto;
    }

    public List<ScheduleClassDto> getAllClass(){
        List<ScheduleClass> scheduleClasses=scheduleClassRepository.findAll();
        List<ScheduleClassDto> scheduleClassDtos = scheduleClasses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return  scheduleClassDtos;
    }
    public ScheduleClassDto getClassByID(int id){
        return convertToDto(scheduleClassRepository.findScheduleClassById(id));
    }
    public ScheduleClassDto checkCapacity(int id){
        ScheduleClass scheduleClass=scheduleClassRepository.findScheduleClassById(id);
        if (scheduleClass.getConlai()<1){
            return null;
        }
        else {
            return convertToDto(scheduleClass);
        }
    }
    public void update(ScheduleClassDto scheduleClassDto){
        ModelMapper modelMapper=new ModelMapper();
        ScheduleClass scheduleClass=modelMapper.map(scheduleClassDto,ScheduleClass.class);
        scheduleClassRepository.save(scheduleClass);
    }
}
