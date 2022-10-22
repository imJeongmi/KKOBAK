package com.a104.freeproject.Category.entity;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class DetailCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "detailCategory",cascade = CascadeType.ALL)
    private List<Challenge> challenges = new LinkedList<>();
}
