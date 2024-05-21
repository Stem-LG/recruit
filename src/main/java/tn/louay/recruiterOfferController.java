package tn.louay;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import kong.unirest.core.Empty;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import tn.louay.dto.GetOffer;

public class recruiterOfferController {

    @FXML
    private Button logoutBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button applicationsBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    @FXML
    private Text recruiterName;

    @FXML
    private Text id;
    @FXML
    private Text title;
    @FXML
    private Text createdAt;
    @FXML
    private Text companyName;
    @FXML
    private Text skills;
    @FXML
    private Text description;

    public void fetchOffer(Integer offerId) {

        String offerJson = Unirest.get("http://localhost:3000/offers/" + offerId)
                .asString()
                .getBody();

        Gson gson = new Gson();

        GetOffer offer = gson.fromJson(offerJson, GetOffer.class);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        id.setText("N°" + String.valueOf(offer.getId()));
        title.setText(offer.getTitle());
        createdAt.setText(format.format(offer.getCreatedAt()));
        companyName.setText(offer.getCompany());
        skills.setText(offer.getSkills());
        description.setText(offer.getDescription());
    }

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("recruiter_offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        logoutBtn.setOnAction(e -> {
            try {
                App.setRoot("offers");
                App.setToken(null);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        applicationsBtn.setOnAction(e -> {
            try {
                
                App.setRoot("applications");
                applicationsController controller = (applicationsController) App.getController();
                controller.setOffer(Integer.parseInt(id.getText().replace("N°", "")), title.getText());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        editBtn.setOnAction(e -> {
            try {
                App.setRoot("edit_offer");
                editOfferController controller = (editOfferController) App.getController();
                controller.setOffer(
                        Integer.parseInt(id.getText().replace("N°", "")),
                        title.getText(),
                        companyName.getText(),
                        skills.getText(),
                        description.getText());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Offer");
                alert.setContentText("Are you sure you want to delete this offer?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    HttpResponse<Empty> response = Unirest
                            .delete("http://localhost:3000/offers/" + id.getText().replace("N°", ""))
                            .header("Authorization", "Bearer " + App.getToken())
                            .asEmpty();
                    if (response.getStatus() == 200) {
                        App.setRoot("recruiter_offers");
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("Success");
                        alert2.setContentText("Offer deleted successfully");
                        alert2.showAndWait();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }

}
