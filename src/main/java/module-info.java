module com.example.aventurasdemarcoyluis {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.aventurasdemarcoyluis to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters;
    opens com.example.aventurasdemarcoyluis.characters to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.itemsconfig;
    opens com.example.aventurasdemarcoyluis.itemsconfig to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters.playersconfig;
    opens com.example.aventurasdemarcoyluis.characters.playersconfig to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters.enemiesconfig;
    opens com.example.aventurasdemarcoyluis.characters.enemiesconfig to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters.attackconfig;
    opens com.example.aventurasdemarcoyluis.characters.attackconfig to javafx.fxml;
}