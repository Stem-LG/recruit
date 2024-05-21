package tn.louay;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import kong.unirest.core.Empty;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;

public class applicationController {

    @FXML
    private Button backBtn;
    @FXML
    private Button applyBtn;

    @FXML
    private Text offerName;

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private Button resumeBtn;
    @FXML
    private TextArea motivation;

    private Integer offerId;
    private File selectedResume;

    public void setOffer(Integer id, String name) {
        this.offerId = id;
        this.offerName.setText(name);
    }

    public void initialize() {

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("offer");
                offerController controller = (offerController) App.getController();
                controller.fetchOffer(offerId);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        resumeBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Resume");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            selectedResume = fileChooser.showOpenDialog(null);
            resumeBtn.setText(selectedResume.getName());
        });

        applyBtn.setOnAction(e -> {
            try {
                if (name.getText().isEmpty() || email.getText().isEmpty() || selectedResume == null
                        || motivation.getText().isEmpty()) {
                    // alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Missing Data");
                    alert.setContentText("All fields are required");
                    alert.showAndWait();
                    return;
                }

                HttpResponse<Empty> response = Unirest.post("http://localhost:3000/offers/" + offerId + "/applications")
                        .field("name", name.getText())
                        .field("email", email.getText())
                        .field("resume", selectedResume)
                        .field("motivation", motivation.getText())
                        .asEmpty();

                if (response.getStatus() == 200) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Application Sent");
                    alert.setContentText("Your application has been sent successfully");
                    alert.showAndWait();
                    App.setRoot("offer");
                    offerController controller = (offerController) App.getController();
                    controller.fetchOffer(offerId);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Application Failed");
                    alert.setContentText("Failed to send your application");
                    alert.showAndWait();
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

    }

}
