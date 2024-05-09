package com.example.studentservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    private int id;
    private String ten,ngaysinh,sdt,email;
}
