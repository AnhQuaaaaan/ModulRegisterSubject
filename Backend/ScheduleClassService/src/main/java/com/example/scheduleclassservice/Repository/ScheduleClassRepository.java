package com.example.scheduleclassservice.Repository;

import com.example.scheduleclassservice.Model.ScheduleClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleClassRepository extends JpaRepository<ScheduleClass,Integer> {
    ScheduleClass findScheduleClassById(int id);
}
