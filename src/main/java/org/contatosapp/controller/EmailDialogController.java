package org.contatosapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.contatosapp.app.Session;
import org.contatosapp.enumerators.Views;
import org.contatosapp.factory.FXMLFactory;
import org.contatosapp.model.implementation.Email;
import org.contatosapp.util.FXUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDialogController implements Initializable {

    private Email email;

    @FXML
    private ImageView imgEmail;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btLimpar;

    @FXML
    private Button btSalvar;

    @FXML
    private TextField txId;

    @FXML
    private TextField txEmail;

    @FXML
    private void btCancelarClick(ActionEvent event) {

        Session.getInstance().clearAttributes();

        ((Stage)this.btCancelar.getScene().getWindow()).close();

    }

    @FXML
    void btLimparClick(ActionEvent event) {

        this.txEmail.clear();

    }

    private void fieldsForObject() {

        Long id = txId.getText().isEmpty()
                ? null
                : Long.parseLong(String.valueOf(txId.getText()));

        this.email.setId(id);

        this.email.setEmail(txEmail.getText());

    }

    private void objectForFields() {

        Long id = this.email.getId();

        txId.setText(id == null ? "" : String.valueOf(id));

        txEmail.setText(this.email.getEmail());

    }

    private Boolean validateEmail() {

        String email = txEmail.getText().trim();

        if (email.isEmpty() || !email.contains("@")) {

            String message = email.isEmpty()
                    ? "Necessário informar email!"
                    : (!email.contains("@") ? "Email inválido!" : "");

            FXUtils.alertDialog(Alert.AlertType.WARNING, "Cadastro de Email", message, "");

            txEmail.requestFocus();

            return false;
        }

        return true;
    }

    @FXML
    private void btSalvarClick(ActionEvent event) {

        if (this.validateEmail()) {

            this.fieldsForObject();

            Session.getInstance().setAttribute("email", this.email);

            ((Stage) btSalvar.getScene().getWindow()).close();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Object obj = Session.getInstance().getAttribute("email");

        if (obj != null) {

            this.email = (Email)obj;

            this.objectForFields();

        } else
            this.email = new Email();

        this.setImages();

    }

    public void setImages() {

        imgEmail.setImage(new Image(this.getClass().getResource("../../../icons/mail-mark-unread.png").toExternalForm()));

        FXUtils.setButtonImage(btSalvar, "./icons/emblem-default.png", "left");
        FXUtils.setButtonImage(btLimpar, "./icons/edit-clear-all.png", "left");
        FXUtils.setButtonImage(btCancelar, "./icons/edit-undo-rtl.png", "left");

    }

    public static void show() throws IOException {

        FXMLFactory.createStage("Cadastro de Email", Views.EMAILVIEW.getValue(), true, false);

    }
}
