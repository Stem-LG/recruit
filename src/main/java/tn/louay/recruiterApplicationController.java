package tn.louay;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import kong.unirest.core.Empty;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import tn.louay.dto.GetApplication;

public class recruiterApplicationController {

    @FXML
    private Button logoutBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button acceptBtn;
    @FXML
    private Button rejectBtn;

    @FXML
    private Text recruiterName;

    @FXML
    private Text id;
    @FXML
    private Text offerTitle;
    @FXML
    private Text createdAt;
    @FXML
    private Text status;
    @FXML
    private Text name;
    @FXML
    private Text email;
    @FXML
    private Text motivation;
    @FXML
    private Text resumeSummary;

    private Integer offerId;

    public void setApplication(Integer applicationId, Integer offerId) {
        this.offerId = offerId;

        String applicationJson = Unirest.get("http://localhost:3000/applications/" + applicationId)
                .header("Authorization", "Bearer " + App.getToken())
                .asString()
                .getBody();

        Gson gson = new Gson();

        GetApplication application = gson.fromJson(applicationJson, GetApplication.class);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        id.setText("N°" + String.valueOf(application.getId()));
        createdAt.setText(format.format(application.getCreatedAt()));
        status.setText(application.getStatus().toString());
        offerTitle.setText(application.getOfferTitle());
        name.setText(application.getName());
        email.setText(application.getEmail());
        motivation.setText(application.getMotivation());
        resumeSummary.setText(application.getResumeSummary());
    }

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("applications");
                applicationsController controller = (applicationsController) App.getController();
                controller.setOffer(offerId, offerTitle.getText());
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

        acceptBtn.setOnAction(e -> {

            System.out.println("Accepting application...");
            try {

                HttpResponse<Empty> response = Unirest
                        .put("http://localhost:3000/applications/" + id.getText().replace("N°", "") + "/accept")
                        .header("Authorization", "Bearer " + App.getToken())
                        .header("Content-Type", "application/json")
                        .asEmpty();

                if (response.getStatus() == 200) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Application accepted");
                    alert.setHeaderText(null);
                    alert.setContentText("The application has been accepted successfully.");
                    alert.showAndWait();
                    setApplication(Integer.parseInt(id.getText().replace("N°", "")), offerId);

                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Application not accepted");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        rejectBtn.setOnAction(e -> {
            System.out.println("Rejecting application...");
            try {
                HttpResponse<Empty> response = Unirest
                        .put("http://localhost:3000/applications/" + id.getText().replace("N°", "") + "/reject")
                        .header("Authorization", "Bearer " + App.getToken())
                        .header("Content-Type", "application/json")
                        .asEmpty();

                if (response.getStatus() == 200) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Application rejected");
                    alert.setHeaderText(null);
                    alert.setContentText("The application has been rejected successfully.");
                    alert.showAndWait();
                    setApplication(Integer.parseInt(id.getText().replace("N°", "")), offerId);

                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Application not rejected");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }
}
