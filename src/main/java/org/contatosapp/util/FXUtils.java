package org.contatosapp.util;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class FXUtils {

    private FXUtils() {
    
    }
    
    public static void setButtonImage(Button button, String url, String position) {
        button.setStyle("-fx-background-image: url('" + url + "'); -fx-background-repeat: no-repeat; -fx-background-position: " + position + " center");
    }
    
    public static void setToolTip(Control control, String text) {
        Tooltip toolTip = new Tooltip(text);
        control.setTooltip(toolTip);
    }

    public static void alertDialog(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void alertDialog(Alert.AlertType type, String title, String headerText, Consumer<ButtonType> consumer) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait().ifPresent(consumer);
    }

    public static <T> T getObjectTableDoubleClick(TableView<T> table, MouseEvent event) {

        if (event.getClickCount() == 2 && !event.isConsumed()) {

            Integer index = table.getSelectionModel().getSelectedIndex();

            return index != -1 ? table.getItems().get(index) : null;
        }

        return null;
    }

    public static void setTextFieldNumeric(TextField textField) {

        UnaryOperator<TextFormatter.Change> numericFilter = (change) -> {

            String text = change.getText();

            for (int i = 0; i < text.length(); i++) {
                if (!Character.isDigit(text.charAt(i)))
                    return null;
            }

            return change;
        };

        textField.setTextFormatter(new TextFormatter<String>(numericFilter));
    }
}
