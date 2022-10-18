package com.a104.freeproject.Member.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @Setter
    @NotNull
    private String password;

    @Setter
    @NotNull
    @Column(length = 20)
    private String nickname;

    @Setter
    @NotNull
    private String hp;

    @Setter
    @NotNull
    private String watchInfo;

    @Setter
    @NotNull
    @Builder.Default
    @ColumnDefault("false")
    private boolean isDelete = false;

    @Setter
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @NotNull
    @CreationTimestamp
    private Timestamp joinDate;

    public Member(String email, String password, String nickname, String hp) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.hp = hp;
    }
}
