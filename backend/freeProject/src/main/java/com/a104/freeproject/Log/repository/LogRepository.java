package com.a104.freeproject.Log.repository;

import com.a104.freeproject.Log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {
}
