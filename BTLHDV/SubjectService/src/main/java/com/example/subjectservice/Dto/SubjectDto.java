package com.example.subjectservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectDto {
    private int id,tc;
    private String ten,mamh,mota;
}
