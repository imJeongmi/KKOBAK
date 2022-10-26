package com.a104.freeproject.Challenge.entity;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class Challenge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Column(name = "contents", nullable = false)
    private String contents;

    @Setter
    @Column(name = "imgurl", nullable = false)
    private String imgurl;

    @Setter
    @Column(name = "is_watch", nullable = false)
    @ColumnDefault("false")
    private boolean isWatch;

    @Setter
    @Column(name = "password")
    private String password;

    @Setter
    @Column(name= "roomtype")
    private int roomtype;

    @Setter
    @Column(name= "writer", nullable = false)
    private Long writer;

    @Setter
    @Column(name= "limit_people", nullable = false)
    private int limitPeople;

    @Setter
    @Column(name= "current_num", nullable = false)
    @ColumnDefault("1")
    private int currentNum;

    @Setter
    @Column(name= "alarm")
    private String alarm;

    @Setter
    @Column(name= "status", nullable = false)
    @ColumnDefault("0")
    private int status;

    @Setter
    @Column(name="goal",nullable =false)
    private int goal;

    @Setter
    @Column(name="unit", nullable = false)
    private String unit;

    @Setter
    @NotNull
    @Builder.Default
    @ColumnDefault("false")
    private boolean isFin = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="detail_category_id")
    private DetailCategory detailCategory;

    @Builder.Default
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<ChlTag> tagList = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<PrtChl> chlList = new LinkedList<>();
}
