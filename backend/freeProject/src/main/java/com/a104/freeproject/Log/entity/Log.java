package com.a104.freeproject.Log.entity;

import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="data",nullable = false)
    private LocalDate date;

    @Setter
    @Column(name="finished",nullable = false)
    private boolean isFin;

    @Setter
    @Column(name="complete_time")
    private Timestamp completeTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PrtChl prtChl;
}
