package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends CrudRepository<Day, Integer> {
}