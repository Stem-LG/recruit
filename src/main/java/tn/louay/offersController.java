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
import kong.unirest.core.Unirest;
import tn.louay.dto.GetOffers;

public class offersController {

    @FXML
    private Button loginBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private TableView<GetOffers> offersTable;
    @FXML
    private TableColumn<GetOffers, Integer> idColumn;
    @FXML
    private TableColumn<GetOffers, String> titleColumn;
    @FXML
    private TableColumn<GetOffers, Date> createdAtColumn;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<GetOffers, Integer>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<GetOffers, String>("title"));
        createdAtColumn.setCellFactory(
                column -> new TableCell<GetOffers, Date>() {
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
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<GetOffers, Date>("createdAt"));
        fetchOffers();

        refreshBtn.setOnAction(e -> {
            System.out.println("Refreshing offers...");
            fetchOffers();
        });

        offersTable.setOnMouseClicked(e -> {

            if (e.getClickCount() == 2) {
                GetOffers selectedOffer = offersTable.getSelectionModel().getSelectedItem();
                System.out.println("Selected offer: " + selectedOffer);
                try {
                    App.setRoot("offer");
                    offerController controller = (offerController) App.getController();
                    controller.fetchOffer(selectedOffer.getId());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

        });
    }

    private void fetchOffers() {

        String offersJson = Unirest.get("http://localhost:3000/offers")
                .asString()
                .getBody();

        Gson gson = new Gson();

        Type offersListType = new TypeToken<List<GetOffers>>() {
        }.getType();
        List<GetOffers> offersList = gson.fromJson(offersJson, offersListType);

        offersTable.getItems().setAll(offersList);
    }

}
