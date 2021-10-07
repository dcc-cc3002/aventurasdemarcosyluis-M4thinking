module com.example.aventurasdemarcoyluis {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.aventurasdemarcoyluis to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters;
    opens com.example.aventurasdemarcoyluis.characters to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.itemsconfig;
    opens com.example.aventurasdemarcoyluis.itemsconfig to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters.players;
    opens com.example.aventurasdemarcoyluis.characters.players to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters.enemies;
    opens com.example.aventurasdemarcoyluis.characters.enemies to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.characters.attackconfig;
    opens com.example.aventurasdemarcoyluis.characters.attackconfig to javafx.fxml;
}