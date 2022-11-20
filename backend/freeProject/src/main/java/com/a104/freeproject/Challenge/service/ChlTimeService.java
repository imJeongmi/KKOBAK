package com.a104.freeproject.Challenge.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface ChlTimeService {

    // ROW 추가
    boolean addRow(Challenge c, Timestamp st, Timestamp ed) throws NotFoundException;

    // 스케쥴러 돌리는 코드 추가
    void changeState();

}
