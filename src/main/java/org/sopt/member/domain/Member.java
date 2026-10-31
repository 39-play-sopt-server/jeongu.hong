package org.sopt.member.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseEntity;

import java.time.LocalDateTime;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDateTime deletedAt;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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
