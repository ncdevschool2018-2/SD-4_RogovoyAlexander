package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.LessonInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonInfoRepository extends CrudRepository<LessonInfo, Integer> {
}
