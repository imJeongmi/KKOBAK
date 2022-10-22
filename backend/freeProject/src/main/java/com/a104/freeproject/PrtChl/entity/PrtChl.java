package com.a104.freeproject.PrtChl.entity;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class PrtChl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="is_fin",nullable = false)
    private boolean is_fin;

    @ManyToOne
    @JoinColumn(name="challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}