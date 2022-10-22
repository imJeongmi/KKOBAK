package com.a104.freeproject.HashTag.entity;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ChlTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name= "hashtag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;

    @JsonIgnore
    @JoinColumn(name="challenge_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;
}
