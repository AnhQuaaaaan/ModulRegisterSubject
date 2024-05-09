package com.example.studentservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table (name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngaysinh")
    private String ngaysinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;
}
