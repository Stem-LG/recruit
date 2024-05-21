package tn.louay;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kong.unirest.core.ContentType;
import kong.unirest.core.Empty;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import tn.louay.dto.PostOffer;

public class editOfferController {

    @FXML
    private Button backBtn;
    @FXML
    private Button editOfferBtn;

    @FXML
    private TextField title;
    @FXML
    private TextField company;
    @FXML
    private TextField skills;
    @FXML
    private TextArea description;

    public Integer offerId;

    public void setOffer(Integer id, String title, String company, String skills, String description) {
        this.offerId = id;
        this.title.setText(title);
        this.company.setText(company);
        this.skills.setText(skills);
        this.description.setText(description);
    }

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("recruiter_offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        editOfferBtn.setOnAction(e -> {
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

                HttpResponse<Empty> response = Unirest.put("http://localhost:3000/offers/" + offerId)
                        .header("Authorization", "Bearer " + App.getToken())
                        .contentType(ContentType.APPLICATION_JSON)
                        .body(postOffer)
                        .asEmpty();

                if (response.getStatus() == 200) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Offer edited successfully");
                    alert.showAndWait();
                    App.setRoot("recruiter_offer");
                    recruiterOfferController controller = (recruiterOfferController) App.getController();
                    controller.fetchOffer(offerId);
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
