package com.hardlearner.oia.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {
    public static Member guest = new Member("guest@gmail.com", "guestPwd", "게스트");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String pwd;
    String name;

    public Member(String email, String pwd, String name) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
    }

    public Member(Long id, String email, String pwd, String name) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Member{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
