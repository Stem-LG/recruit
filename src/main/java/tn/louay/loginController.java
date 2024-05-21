package tn.louay;

import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import kong.unirest.core.ContentType;
import kong.unirest.core.Unirest;
import tn.louay.dto.LoginRequest;
import tn.louay.dto.TokenDto;

public class loginController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    private Button backBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Hyperlink registerBtn;

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        loginBtn.setOnAction(e -> {

            System.out.println("Logging in...");
            try {

                if (email.getText().isEmpty() || password.getText().isEmpty()) {
                    // alert dialog
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Missing Data");
                    alert.setContentText("All fields are required");
                    alert.showAndWait();
                    return;
                }

                // post to /auth/login and recieve a {token}

                LoginRequest loginRequest = new LoginRequest(email.getText(), password.getText());

                String loginJson = Unirest.post("http://localhost:3000/auth/login")
                        .contentType(ContentType.APPLICATION_JSON)
                        .body(loginRequest)
                        .asString()
                        .getBody();

                Gson gson = new Gson();

                TokenDto token = gson.fromJson(loginJson, TokenDto.class);

                try {

                    App.setToken(token.getToken());

                    App.setRoot("recruiter_offers");

                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setContentText("Invalid email or password");
                    alert.showAndWait();
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        registerBtn.setOnAction(e -> {
            try {
                App.setRoot("register");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }

}
