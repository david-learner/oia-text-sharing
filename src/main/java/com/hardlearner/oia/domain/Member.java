package com.hardlearner.oia.domain;

import com.hardlearner.oia.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {
    public static final GuestMember GUEST_MEMBER = new GuestMember();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;

    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // todo 중복 걷어내기 for Guest
        if (o.getClass() == GUEST_MEMBER.getClass()) {
            Member member = (Member) o;
            return Objects.equals(email, member.email) &&
                    Objects.equals(name, member.name);
        }
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(email, member.email) &&
                Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "Member{" +
                "email='" + email + '\'' +
                ", pwd='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }

    public MemberDto toMemberDto() {
        return new MemberDto(name, email);
    }

    public boolean isGuest() {
        return false;
    }

    public static class GuestMember extends Member {

        public GuestMember() {
            super("guest@gmail.com", "guest", "게스트");
        }

        @Override
        public boolean isGuest() {
            return true;
        }
    }
}
