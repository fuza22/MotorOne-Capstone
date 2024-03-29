package it.epicode.Capstone.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRequest {

    @NotBlank(message = "Inserisci la vecchia password")
    private String oldPassword;

    @NotBlank(message = "Inserisci la nuova password")
    private String newPassword;


}
