package com.a104.freeproject.Challenge.entity;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "imgurl", nullable = false)
    private String imgurl;

    @Column(name = "is_watch", nullable = false)
    @ColumnDefault("false")
    private boolean isWatch;

    @Column(name = "password")
    private String password;

    @Column(name= "roomtype")
    private int roomtype;

    @Column(name= "writer", nullable = false)
    private Long writer;

    @Column(name= "limit_people", nullable = false)
    private int limitPeople;

    @Column(name= "current_num", nullable = false)
    @ColumnDefault("1")
    private int currentNum;

    @Column(name= "alarm")
    private String alarm;

    @Column(name= "status", nullable = false)
    @ColumnDefault("0")
    private int status;

    @Column(name="goal",nullable =false)
    private int goal;

    @Column(name="unit", nullable = false)
    private String unit;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="detail_category_id")
    private DetailCategory detailCategory;

    @Builder.Default
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<ChlTag> tagList = new LinkedList<>();
}
