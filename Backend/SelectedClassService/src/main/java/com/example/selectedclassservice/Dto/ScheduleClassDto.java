package com.example.selectedclassservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleClassDto {
    private int id,nhom,tc,slot,conlai,subject_id;
    private String mamh,tenmh,tengiangvien,lichhoc;
}
