package tn.louay;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import kong.unirest.core.ContentType;
import kong.unirest.core.Empty;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import tn.louay.dto.PostOffer;

public class addOfferController {

    @FXML
    private Button backBtn;
    @FXML
    private Button addOfferBtn;

    @FXML
    private TextField title;
    @FXML
    private TextField company;
    @FXML
    private TextField skills;
    @FXML
    private TextArea description;

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("recruiter_offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        addOfferBtn.setOnAction(e -> {
            try {

                if (title.getText().isEmpty() || company.getText().isEmpty() || skills.getText().isEmpty()
                        || description.getText().isEmpty()) {
                    // alert dialog
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Missing Data");
                    alert.setContentText("All fields are required");
                    alert.showAndWait();
                    return;
                }

                PostOffer postOffer = new PostOffer(
                        title.getText(),
                        company.getText(),
                        skills.getText(),
                        description.getText());

                HttpResponse<Empty> response = Unirest.post("http://localhost:3000/offers")
                        .header("Authorization", "Bearer " + App.getToken())
                        .contentType(ContentType.APPLICATION_JSON)
                        .body(postOffer)
                        .asEmpty();

                if (response.getStatus() == 200) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Offer added successfully");
                    alert.showAndWait();
                    App.setRoot("recruiter_offers");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to add offer");
                    alert.showAndWait();
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }

}
