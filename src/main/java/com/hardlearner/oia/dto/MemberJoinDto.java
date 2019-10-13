package com.hardlearner.oia.dto;

import com.hardlearner.oia.domain.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.*;

@Builder
public class MemberJoinDto {
    @NotBlank(message = "이름을 작성해주세요.")
    @Size(min = 2, max = 10)
    private String name;

    @NotBlank(message = "이메일을 작성해주세요.")
    @Email(message = "이메일 양식을 지켜주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 작성해주세요.")
    @Size(min = 5, max = 20)
    private String password;

    @NotBlank(message = "비밀번호를 작성해주세요.")
    private String passwordConfirmed;

    public MemberJoinDto() {
    }

    public MemberJoinDto(String name, String email, String password, String passwordConfirmed) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirmed = passwordConfirmed;
    }

    public Member toMember() {
        if (!password.equals(passwordConfirmed)) {
            throw new IllegalStateException("password is not correct");
        }
        return new Member(email, password, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    @Override
    public String toString() {
        return "JoinDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirmed='" + passwordConfirmed + '\'' +
                '}';
    }
}
