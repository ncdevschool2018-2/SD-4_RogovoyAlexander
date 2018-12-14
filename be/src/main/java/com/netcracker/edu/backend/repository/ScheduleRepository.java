package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Lesson, Integer> {
    Iterable<Lesson> getLessonsByDay_DayName(String dayName);
}
