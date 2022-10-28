package com.a104.freeproject.PrtChl.entity;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.Member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class PrtChl implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name="is_fin",nullable = false)
    private boolean is_fin;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @NotNull
    private LocalDate startDate;

    @Setter
    @NotNull
    private LocalDate endDate;

    @Setter
    @NotNull
    private int alarmDir;

    @Setter
    @NotNull
    @Builder.Default
    @ColumnDefault("0")
    private int sucDay = 0;

    @Setter
    @NotNull
    @Builder.Default
    @ColumnDefault("1")
    private int failDay = 1;

    @Builder.Default
    @OneToMany(mappedBy = "prtChl", cascade = CascadeType.ALL)
    private List<Log> logs = new LinkedList<>();

}