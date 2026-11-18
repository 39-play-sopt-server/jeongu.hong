package org.sopt.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.sopt.global.auth.Role;
import org.sopt.global.domain.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    private LocalDateTime deletedAt;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public String getDisplayName() {
        return isDeleted() ? "탈퇴한 회원" : name;
    }

    public void withdraw() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
