package com.example.subjectservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table (name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "mamh")
    private String mamh;

    @Column(name = "tc")
    private int tc;

    @Column(name = "mota")
    private String mota;
}
