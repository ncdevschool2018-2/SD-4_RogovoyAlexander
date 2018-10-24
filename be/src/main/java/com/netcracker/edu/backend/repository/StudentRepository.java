package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {
}
