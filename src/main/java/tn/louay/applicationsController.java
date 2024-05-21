package tn.louay;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import kong.unirest.core.Unirest;
import tn.louay.dto.GetApplications;
import tn.louay.enums.ApplicationStatus;

public class applicationsController {

    @FXML
    private Text recruiterName;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button backBtn;

    @FXML
    private Text offerName;

    @FXML
    private TableView<GetApplications> applicantsTable;
    @FXML
    private TableColumn<GetApplications, Integer> idColumn;
    @FXML
    private TableColumn<GetApplications, String> nameColumn;
    @FXML
    private TableColumn<GetApplications, ApplicationStatus> statusColumn;
    @FXML
    private TableColumn<GetApplications, Date> createdAtColumn;

    private Integer offerId;

    public void setOffer(Integer id, String name) {
        System.out.println(id + " " + name);

        this.offerId = id;
        offerName.setText(name);

        fetchApplications();

    }

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<GetApplications, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<GetApplications, String>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<GetApplications, ApplicationStatus>("status"));
        createdAtColumn.setCellFactory(
                column -> new TableCell<GetApplications, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {

                            setText(format.format(item));
                        }
                    }
                });
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<GetApplications, Date>("createdAt"));

        applicantsTable.setOnMouseClicked(e -> {

            if (e.getClickCount() == 2) {
                GetApplications selectedApplicant = applicantsTable.getSelectionModel().getSelectedItem();

                try {
                    App.setRoot("recruiter_application");
                    recruiterApplicationController controller = (recruiterApplicationController) App.getController();
                    controller.setApplication(selectedApplicant.getId(), offerId);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

        });

        logoutBtn.setOnAction(e -> {
            try {
                App.setToken(null);
                App.setRoot("offers");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        backBtn.setOnAction(e -> {
            try {
                App.setRoot("recruiter_offer");
                recruiterOfferController controller = (recruiterOfferController) App.getController();
                controller.fetchOffer(offerId);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        recruiterName.setText(App.getUserName());
    }

    private void fetchApplications() {

        String applicationsJson = Unirest.get("http://localhost:3000/offers/" + offerId + "/applications")
                .header("Authorization", "Bearer " + App.getToken())
                .asString()
                .getBody();

        Gson gson = new Gson();

        Type applicationsListType = new TypeToken<List<GetApplications>>() {
        }.getType();
        List<GetApplications> applicationsList = gson.fromJson(applicationsJson, applicationsListType);

        applicantsTable.getItems().setAll(applicationsList);
    }

}
