package com.example.selectedclassservice.Repository;

import com.example.selectedclassservice.Model.SelectedClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedClassRepository extends JpaRepository<SelectedClass,Integer> {
    SelectedClass getSelectedClassById(int id);
}
