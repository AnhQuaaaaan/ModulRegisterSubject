package com.example.subjectservice.Service;

import com.example.subjectservice.Dto.SubjectDto;
import com.example.subjectservice.Model.Subject;
import com.example.subjectservice.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDto getDetail(int id){
        Subject subject=subjectRepository.findSubjectById(id);
        ModelMapper modelMapper=new ModelMapper();
        SubjectDto subjectDto=modelMapper.map(subject,SubjectDto.class);
        return subjectDto;
    }


}
