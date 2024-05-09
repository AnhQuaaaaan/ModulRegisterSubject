package com.example.studentservice.Service;

import com.example.studentservice.Dto.StudentDto;
import com.example.studentservice.Model.Student;
import com.example.studentservice.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentDto getDetail(int id){
        Student student=studentRepository.findStudentById(id);
        ModelMapper modelMapper=new ModelMapper();
        StudentDto studentDto=modelMapper.map(student,StudentDto.class);
        return studentDto;
    }
}
