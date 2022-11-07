package com.a104.freeproject.Statgps.repository;

import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.Statgps.entity.Statgps;
import com.a104.freeproject.Statgps.response.GpsResultResponse;
import com.a104.freeproject.Todolist.entity.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StatgpsRepository extends JpaRepository<Statgps,Long> {
    @Query(value="select * from statgps where prt_chl_id=:p and date_format(chk, '%Y-%m-%d')=:date group by chk order by chk", nativeQuery = true)
    List<Statgps> findByChkAndPrtChl(@Param("date") String date, @Param("p") PrtChl p);

    @Query(value="select count(*) from statgps where prt_chl_id=:p and date_format(chk, '%Y-%m-%d')=:date group by chk order by chk", nativeQuery = true)
    boolean existsByChkAndPrtChl(@Param("p") PrtChl p, @Param("date") String date);

    @Query(value="select * from statgps where prt_chl_id=:p and chk=:date", nativeQuery = true)
    List<Statgps> findByChkTimeAndPrtChl(@Param("p") PrtChl p, @Param("date") LocalDateTime date);
}
