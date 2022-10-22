package com.a104.freeproject.Log.entity;

import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.joda.time.DateTime;

import javax.persistence.*;
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
    private Date date;

    @Column(name="finished",nullable = false)
    private boolean isFin;

    @Column(name="complete_time")
    private DateTime completeTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="prt_chl_id")
    private PrtChl prtChl;
}
