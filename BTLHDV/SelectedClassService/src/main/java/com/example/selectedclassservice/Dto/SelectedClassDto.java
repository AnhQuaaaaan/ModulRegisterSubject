package com.example.selectedclassservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedClassDto {
    private int id,nhom,tc,scheduleclass_id;
    private String mamh,tenmh,ngaydangky,lichhoc,tengiangvien;
}
