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
import tn.louay.dto.RegisterRequest;
import tn.louay.dto.TokenDto;

public class registerController {

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    private Button backBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private Hyperlink loginBtn;

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        registerBtn.setOnAction(e -> {

            System.out.println("Registering...");
            try {

                if (name.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty()) {
                    // alert dialog
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Missing Data");
                    alert.setContentText("All fields are required");
                    alert.showAndWait();
                    return;
                }

                // post to /auth/register and recieve a {token}

                RegisterRequest registerRequest = new RegisterRequest(name.getText(), email.getText(),
                        password.getText());

                String registerJson = Unirest.post("http://localhost:3000/auth/register")
                        .contentType(ContentType.APPLICATION_JSON)
                        .body(registerRequest)
                        .asString()
                        .getBody();

                Gson gson = new Gson();

                TokenDto tokenDto = gson.fromJson(registerJson, TokenDto.class);

                try {

                    App.setToken(tokenDto.getToken());
                    App.setRoot("recruiter_offers");

                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Registration Failed");
                    alert.setContentText("An error occurred during registration. Please try again.");
                    alert.showAndWait();
                    return;
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        loginBtn.setOnAction(e -> {
            try {
                App.setRoot("login");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }

}
