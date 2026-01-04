package app.MyBackendApp.main.bll.impl;

import org.hibernate.validator.constraints.Length;

import app.MyBackendApp.main.api.validation.constraints.Email;
import app.MyBackendApp.main.api.validation.constraints.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AuthRequest {

        @NotBlank
        @Email
        @Length(min = 5, max = 50)
        String mail;

        @NotBlank
        @Password
        @Length(min = 5, max = 10)
        String password;

    public AuthRequest(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

}

