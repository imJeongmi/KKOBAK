package com.a104.freeproject.Log.repository;

import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {
    boolean existsByPrtChlAndDate(PrtChl p, LocalDate today);
    Log findByPrtChlAndDate(PrtChl p, LocalDate today);
}
