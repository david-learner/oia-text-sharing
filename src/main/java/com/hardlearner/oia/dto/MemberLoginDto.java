package com.hardlearner.oia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 양식을 확인해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15)
    private String password;
}
