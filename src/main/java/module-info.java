module tn.louay {
    requires transitive javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires unirest.java.core;
    requires com.google.gson;
    requires java.net.http;
    requires org.jose4j;

    opens tn.louay to javafx.fxml;
    // open to com.google.gson and javafx.base
    opens tn.louay.dto to com.google.gson, javafx.base;
    opens tn.louay.enums to com.google.gson, javafx.base;

    exports tn.louay;
}
