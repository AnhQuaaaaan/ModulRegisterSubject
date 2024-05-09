package com.example.scheduleclassservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(name = "scheduleclass")

public class ScheduleClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subject_id")
    private int subject_id;

    @Column(name = "tengiangvien")
    private String tengiangvien;

    @Column(name = "nhom")
    private int nhom;

    @Column(name = "slot")
    private int slot;

    @Column(name = "conlai")
    private int conlai;

    @Column(name = "lichhoc")
    private String lichhoc;
}
