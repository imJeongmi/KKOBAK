package com.a104.freeproject.Statbpm.entity;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Statbpm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PrtChl prtChl;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private int bpm;

    @NotNull
    @ColumnDefault("false")
    private boolean success = false;

    @NotNull
    private LocalDateTime chk;

}
