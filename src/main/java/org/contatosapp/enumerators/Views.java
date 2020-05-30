package org.contatosapp.enumerators;

public enum Views {

    MAINVIEW("../../../fxml/MainView.fxml"),
    FONEVIEW("../../../fxml/FoneDialogView.fxml"),
    EMAILVIEW("../../../fxml/EmailDialogView.fxml");

    private String value;

    Views(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
