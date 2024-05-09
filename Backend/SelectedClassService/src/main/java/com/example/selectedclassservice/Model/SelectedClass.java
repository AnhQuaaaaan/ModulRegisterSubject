package com.example.selectedclassservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "selectedclass")
@Entity
public class SelectedClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mamh")
    private String mamh;

    @Column(name = "tenmh")
    private String tenmh;

    @Column(name = "nhom")
    private int nhom;

    @Column(name = "tc")
    private int tc;

    @Column(name="scheduleclass_id")
    private int scheduleclass_id;

    @Column(name = "tengiangvien")
    private String tengiangvien;

    @Column(name = "ngaydangky")
    private String ngaydangky;

    @Column(name = "lichhoc")
    private String lichhoc;
}
