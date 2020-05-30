package org.contatosapp.factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.contatosapp.app.Init;
import java.io.IOException;

public class FXMLFactory {

    private FXMLFactory() {

    }

    public static void createStage(String title, String fxmlPath, Boolean modal, Boolean resizable) throws IOException {

        Stage stage = new Stage();

        Parent root = FXMLLoader.load(Init.class.getResource(fxmlPath));

        stage.setTitle(title);

        stage.setScene(new Scene(root));

        stage.setResizable(resizable);

        if (modal) {

            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(Init.getCurrentStage());

            stage.showAndWait();

        } else
            stage.show();

    }
}
