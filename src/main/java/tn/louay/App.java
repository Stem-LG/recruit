package tn.louay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

/**
 * JavaFX App
 */
public class App extends Application {

    private static String token;

    private static Scene scene;
    private static FXMLLoader loader;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("offers"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Object getController() {
        return loader.getController();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static String getUserName() {

        JwtConsumer consumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();

        try {

            JwtClaims claims = consumer.processToClaims(token);

            String name = claims.getClaimsMap().get("name").toString();

            return name;
        } catch (Exception e) {
            System.out.println(e);
        }

        return "username";
    }

    public static void main(String[] args) {
        launch();
    }

}