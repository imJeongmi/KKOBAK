package com.a104.freeproject.Log.entity;

import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @Setter
    @NotNull
    private boolean isFin;

    @Setter
    private Timestamp completeTime;

    @Setter
    @NotNull
    @Builder.Default
    private int cnt = 0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PrtChl prtChl;
}
