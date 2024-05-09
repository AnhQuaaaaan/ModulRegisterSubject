package com.example.scheduleclassservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedClassDto {
    private int id,nhom,tc,scheduleclass_id,subjectId;
    private String mamh,tenmh,ngaydangky,lichhoc,tengiangvien;
}
