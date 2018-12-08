package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.Lesson;
import com.netcracker.edu.backend.entity.LessonDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface LessonDateRepository extends CrudRepository<LessonDate, Integer> {

    @Query("SELECT ld.lesson FROM LessonDate ld " +
            "INNER JOIN ld.lesson ls " +
            "WHERE ls.professor.id = :professorId AND ld.date BETWEEN :dateFrom AND :dateTo " +
            "ORDER BY ld.date ASC")
    Iterable<Lesson> getProfessorLessonsBetween(
            @Param("professorId") Integer professorId,
            @Param("dateFrom") Date from,
            @Param("dateTo") Date to);
}
