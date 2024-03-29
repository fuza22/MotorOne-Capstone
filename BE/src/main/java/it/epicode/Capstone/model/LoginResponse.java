package it.epicode.Capstone.model;

import it.epicode.Capstone.model.entities.User;
import lombok.Data;

@Data
public class LoginResponse {

    public LoginResponse(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    private String accessToken;
    private User user;

}
