package com.a104.freeproject.Statbpm.repository;

import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.Statbpm.entity.Statbpm;
import com.a104.freeproject.Statbpm.response.BpmMiddleInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatbpmRepository extends JpaRepository<Statbpm,Long> {
    @Query(value="select chk, success from statbpm where prt_chl_id=:p and date_format(chk, '%Y-%m-%d')=:date group by chk, success order by chk", nativeQuery = true)
    List<BpmMiddleInterface> findByChkAndPrtChlJPQL(@Param("date") String date, @Param("p") PrtChl p);

    @Query(value="select count(*) from statbpm where prt_chl_id=:p and date_format(chk, '%Y-%m-%d')=:date group by chk order by chk", nativeQuery = true)
    boolean existsByChkAndPrtChl(@Param("p") PrtChl p, @Param("date") String date);

    @Query(value="select * from statbpm where prt_chl_id=:p and chk=:date order by time", nativeQuery = true)
    List<Statbpm> findByChkTimeAndPrtChl(@Param("p") PrtChl p, @Param("date") LocalDateTime date);
}
