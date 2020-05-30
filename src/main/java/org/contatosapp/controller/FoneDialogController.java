package org.contatosapp.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.contatosapp.app.Session;
import org.contatosapp.enumerators.Views;
import org.contatosapp.factory.FXMLFactory;
import org.contatosapp.model.implementation.Fone;
import org.contatosapp.util.FXUtils;
import org.contatosapp.util.TextUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FoneDialogController implements Initializable {

    private Fone fone;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField txFone;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btLimpar;

    @FXML
    private TextField txId;

    @FXML
    private void btCancelarClick(ActionEvent event) {

        Session.getInstance().clearAttributes();

        ((Stage)this.btCancelar.getScene().getWindow()).close();

    }

    @FXML
    private void btLimparClick(ActionEvent event) {

        this.txFone.clear();

        this.cbTipo.setValue("");

        this.cbTipo.setPromptText("Tipo");
    }

    @FXML
    private void btSalvarClick(ActionEvent event) {

        if (this.validateFone()) {

            this.fieldsForObject();

            Session.getInstance().setAttribute("fone", this.fone);

            ((Stage) btSalvar.getScene().getWindow()).close();
        }

    }

    private Boolean validateFone() {

        String fone = this.txFone.getText();

        if (fone.trim().isEmpty()) {

            FXUtils.alertDialog(Alert.AlertType.WARNING, "Cadastro de Telefone", "Necessário informar telefone!", "Preencha o campo Fone.");

            txFone.requestFocus();

            return false;
        }

        if (fone.length() != 14 && fone.length() != 15) {

            FXUtils.alertDialog(Alert.AlertType.WARNING, "Cadastro de Telefone", "Número inválido!", "Informe um número válido.");

            txFone.requestFocus();

            return false;
        }

        if (cbTipo.getValue() == null || cbTipo.getValue().isEmpty()) {

            FXUtils.alertDialog(Alert.AlertType.WARNING, "Cadastro de Telefone", "Necessário informar tipo!", "");

            cbTipo.requestFocus();

            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Object obj = Session.getInstance().getAttribute("fone");

        if (obj != null) {

            this.fone = (Fone)obj;

            this.objectForFields();

        } else
            this.fone = new Fone();

        this.setImages();

        this.loadCbTipoFone();

        this.setEvents();

    }

    public void setImages() {

        imgView.setImage(new Image(this.getClass().getResource("../../../icons/call-start.png").toExternalForm()));

        FXUtils.setButtonImage(btSalvar, "./icons/emblem-default.png", "left");
        FXUtils.setButtonImage(btLimpar, "./icons/edit-clear-all.png", "left");
        FXUtils.setButtonImage(btCancelar, "./icons/edit-undo-rtl.png", "left");

    }

    public static void show() throws IOException {

        FXMLFactory.createStage("Cadastro de Telefone", Views.FONEVIEW.getValue(), true, false);

    }

    private void loadCbTipoFone() {

        this.cbTipo.getItems().clear();

        String[] types = { "", "Residencial", "Celular", "Comercial", "Outros" };

        cbTipo.setItems(FXCollections.observableArrayList(types));

    }

    private void fieldsForObject() {

        Long id = txId.getText().isEmpty()
                ? null
                : Long.parseLong(String.valueOf(txId.getText()));

        this.fone.setId(id);

        this.fone.setNumeroProxy(txFone.getText());

        this.fone.setTipo(cbTipo.getValue());

    }

    private void objectForFields() {

        Long id = this.fone.getId();

        txId.setText(id == null ? "" : String.valueOf(id));

        txFone.setText(this.fone.getNumeroProxy());

        cbTipo.setValue(this.fone.getTipo());

    }

    private void setEvents() {

        txFone.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue) {

                txFone.setText(TextUtils.getOnlyNumbers(txFone.getText()));

                FXUtils.setTextFieldNumeric(txFone);

            } else {

                txFone.setTextFormatter(null);

                this.txFone.setText(TextUtils.maskFone(txFone.getText().trim()));

            }
        });


    }
}
