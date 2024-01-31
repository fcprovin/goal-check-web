package com.fcprovin.goal.check.web.dto.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberPasswordForm {

    @NotEmpty
    private String password;
}
