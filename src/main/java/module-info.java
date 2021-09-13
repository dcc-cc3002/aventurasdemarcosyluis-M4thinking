module com.aventurasdemarcoyluis {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.aventurasdemarcosyluis to javafx.fxml;
    exports com.aventurasdemarcosyluis.types;
    opens com.aventurasdemarcosyluis.types to javafx.fxml;
    exports com.aventurasdemarcosyluis.characterstats;
    opens com.aventurasdemarcosyluis.characterstats to javafx.fxml;
}