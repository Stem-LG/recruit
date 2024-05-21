package tn.louay;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import kong.unirest.core.Unirest;
import tn.louay.dto.GetOffer;

public class offerController {

    @FXML
    private Button loginBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button applyBtn;

    // id, title, createdAt, companyName, skills, description
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
                App.setRoot("offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        applyBtn.setOnAction(e -> {
            try {
                App.setRoot("application");
                applicationController controller = (applicationController) App.getController();
                controller.setOffer(Integer.parseInt(id.getText().substring(2)), title.getText());
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
