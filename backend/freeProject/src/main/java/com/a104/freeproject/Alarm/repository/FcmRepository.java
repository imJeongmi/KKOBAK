package com.a104.freeproject.Alarm.repository;

import com.a104.freeproject.Alarm.entitiy.Fcm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<Fcm,Long> {
    boolean existsByMemberId(Long id);

    Fcm findByMemberId(Long id);
}
