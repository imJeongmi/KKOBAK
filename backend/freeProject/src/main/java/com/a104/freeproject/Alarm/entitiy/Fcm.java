package com.a104.freeproject.Alarm.entitiy;

import com.a104.freeproject.Member.entity.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Fcm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    private String phoneToken;

    private String watchToken;


}
