package org.contatosapp.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.contatosapp.enumerators.Views;

public class Init extends Application{

    private static Stage currentStage = null;

    @Override
    public void start(Stage stage) throws Exception {

        currentStage = stage;

        Parent root = FXMLLoader.load(Init.class.getResource(Views.MAINVIEW.getValue()));

        stage.setTitle("Controle de contatos");

        stage.setScene(new Scene(root));

        stage.setResizable(false);

        stage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }
}
