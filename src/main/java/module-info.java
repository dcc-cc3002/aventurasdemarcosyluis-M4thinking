module com.example.aventurasdemarcoyluis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires org.jetbrains.annotations;

    opens com.example.aventurasdemarcoyluis.view to javafx.fxml;
    exports com.example.aventurasdemarcoyluis.view;

    exports com.example.aventurasdemarcoyluis.model.characters;
    opens com.example.aventurasdemarcoyluis.model.characters to javafx.fxml;

    exports com.example.aventurasdemarcoyluis.model.itemsconfig;
    opens com.example.aventurasdemarcoyluis.model.itemsconfig to javafx.fxml;

    exports com.example.aventurasdemarcoyluis.model.characters.players;
    opens com.example.aventurasdemarcoyluis.model.characters.players to javafx.fxml;

    exports com.example.aventurasdemarcoyluis.model.characters.enemies;
    opens com.example.aventurasdemarcoyluis.model.characters.enemies to javafx.fxml;

    exports com.example.aventurasdemarcoyluis.model.characters.attackconfig;
    opens com.example.aventurasdemarcoyluis.model.characters.attackconfig to javafx.fxml;

    exports com.example.aventurasdemarcoyluis.controller.phases;
    opens com.example.aventurasdemarcoyluis.controller.phases to javafx.fxml;
 }