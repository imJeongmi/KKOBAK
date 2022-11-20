package com.a104.freeproject.Category.entity;

import com.a104.freeproject.Challenge.entity.Challenge;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<DetailCategory> details = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Challenge> challenges = new LinkedList<>();

    @Column(name="imgurl")
    private String imgurl;
}
