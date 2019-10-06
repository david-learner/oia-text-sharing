package com.hardlearner.oia.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class JoinDto {
    private String name;
    private String email;
    private String password;
    private String passwordConfirmed;

    public JoinDto() {
    }

    public JoinDto(String name, String email, String password, String passwordConfirmed) {
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
