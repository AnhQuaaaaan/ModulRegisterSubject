package com.example.subjectservice.Repository;

import com.example.subjectservice.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    Subject findSubjectById(int id);
}
