package org.contatosapp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.contatosapp.app.Session;
import org.contatosapp.factory.RepositoryFactory;
import org.contatosapp.model.definition.CrudRepository;
import org.contatosapp.model.implementation.Contato;
import org.contatosapp.model.implementation.Email;
import org.contatosapp.model.implementation.Fone;
import org.contatosapp.util.FXUtils;
import org.contatosapp.util.PairImpl;

public class MainController implements Initializable {

    private Contato contato;

    private CrudRepository<Contato> repository;

    @FXML
    private TableView<Contato> tableContatos;

    @FXML
    private TableColumn<Contato, String> nomeColumn;

    @FXML
    private TextField txConsulta;

    @FXML
    private TextField txId;

    @FXML
    private TextField txNome;

    @FXML
    private TextField txObservacoes;

    @FXML
    private TableView<Fone> tableFones;

    @FXML
    private TableColumn<Fone, String> foneColumn;

    @FXML
    private TableColumn<Fone, String> tipoColumn;

    @FXML
    private TableView<Email> tableEmail;

    @FXML
    private TableColumn<Email, String> emailColumn;

    @FXML
    private Button btSair;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btLimpar;

    @FXML
    private ImageView imgContato;
    
    @FXML
    private Button btExcluirFone;

    @FXML
    private Button btNovoFone;

    @FXML
    private Button btExcluirEmail;

    @FXML
    private Button btNovoEmail;
    
    @FXML
    private TableColumn<Contato, Long> idColumn;
    
    @FXML
    private TableColumn<Fone, Long> idFoneColumn;
    
    @FXML
    private TableColumn<Email, Long> idEmailColumn;

    @FXML
    private Button btConsultar;

    @FXML
    private void btConsultarClick(ActionEvent event) {

        loadContactTable();

    }

    @FXML
    private void btExcluirClick(ActionEvent event) {

        try {

            FXUtils.alertDialog(Alert.AlertType.CONFIRMATION, "Controle de contatos", "Deseja mesmo exluir?",
                    result -> {

                        if (ButtonType.OK.equals(result)) {

                            this.deleteContato();

                        }
                    });

        } catch (Exception ex) {

            FXUtils.alertDialog(Alert.AlertType.ERROR, "Controle de Contatos", "Erro ao excluir!", "");

        }
    }

    @FXML
    private void btExcluirEmailClick(ActionEvent event) {

        try {

            Integer index = tableEmail.getSelectionModel().getSelectedIndex();

            Email email = tableEmail.getItems().get(index);

            if (email != null) {

                this.contato.getEmails().remove(email);

                this.loadEmailTable();

            }

        } catch (Exception ex) {

        }
    }

    @FXML
    private void btExcluirFoneClick(ActionEvent event) {

        try {

            Integer index = tableFones.getSelectionModel().getSelectedIndex();

            Fone fone = tableFones.getItems().get(index);

            if (fone != null) {

                this.contato.getFones().remove(fone);

                this.loadFoneTable();

            }

        } catch (Exception ex) {

        }

    }

    @FXML
    private void btLimparClick(ActionEvent event) {

        this.clearData();

    }

    @FXML
    private void btNovoEmailClick(ActionEvent event) throws IOException {

        this.addEmail();

    }

    private void addEmail() throws IOException {

        EmailDialogController.show();

        Object obj = Session.getInstance().getAttribute("email");

        if (obj != null) {

            Email email = (Email)obj;

            if (email.getEdicao())
                this.removeEmailToEdit(email);

            email.setContato(this.contato);

            this.contato.getEmails().add(email);

            this.loadEmailTable();

        }

        Session.getInstance().clearAttributes();
    }

    private void addFone() throws IOException {

        FoneDialogController.show();

        Object obj = Session.getInstance().getAttribute("fone");

        if (obj != null) {

            Fone fone = (Fone)obj;

            if (fone.getEdicao())
                this.removeFoneToEdit(fone);

            fone.setContato(this.contato);

            this.contato.getFones().add(fone);

            this.loadFoneTable();

        }

        Session.getInstance().clearAttributes();
    }

    private void removeEmailToEdit(Email email) {

        Integer index = tableEmail.getSelectionModel().getSelectedIndex();

        Email remover = tableEmail.getItems().get(index);

        this.contato.getEmails().remove(remover);

    }

    private void removeFoneToEdit(Fone fone) {

        Integer index = tableFones.getSelectionModel().getSelectedIndex();

        Fone remover = tableFones.getItems().get(index);

        this.contato.getFones().remove(remover);

    }

    @FXML
    private void btNovoFoneClick(ActionEvent event) throws IOException {

        this.addFone();

    }

    @FXML
    private void btSairClick(ActionEvent event) {

        FXUtils.alertDialog(Alert.AlertType.CONFIRMATION, "Controle de Contatos", "Deseja mesmo sair?",
            result -> {
                if (ButtonType.OK.equals(result))
                    System.exit(1);
            });
    }

    @FXML
    private void btSalvarClick(ActionEvent event) throws Exception {

        try {

            if (validateContato()) {

                this.fieldsForObject();

                this.tablesForObject();

                repository.save(this.contato);

                FXUtils.alertDialog(Alert.AlertType.INFORMATION, "Controle de Contatos", "Salvo com sucesso!", "");

                this.loadAllTables();

                this.txId.setText(String.valueOf(this.contato.getId()));
            }

        } catch (Exception ex) {

            FXUtils.alertDialog(Alert.AlertType.ERROR, "Controle de Contatos", "Erro ao salvar!", "");

        }
    }

    @FXML
    private void tableContatosMouseClick(MouseEvent event) {

        Contato contato = FXUtils.getObjectTableDoubleClick(tableContatos, event);

        if (contato != null) {

            this.contato = contato;

            this.objectForFields();

            loadEmailTable();

            loadFoneTable();
        }
    }

    @FXML
    private void tableEmailMouseClick(MouseEvent event) throws IOException {

        Email email = FXUtils.getObjectTableDoubleClick(tableEmail, event);

        if (email != null) {

            email.setEdicao(true);

            Session.getInstance().setAttribute("email", email);

            this.addEmail();
        }
    }

    @FXML
    private void tableFoneMouseClick(MouseEvent event) throws IOException {

        Fone fone = FXUtils.getObjectTableDoubleClick(tableFones, event);

        if (fone != null) {

            fone.setEdicao(true);

            Session.getInstance().setAttribute("fone", fone);

            this.addFone();
        }

    }

    private void objectForFields() {

        Long id = this.contato.getId();

        this.txId.setText(id == null ? "" : String.valueOf(id));

        this.txNome.setText(this.contato.getNome());

        this.txObservacoes.setText(this.contato.getObservacoes());

    }

    private void fieldsForObject() {

        this.contato.setId(txId.getText().isEmpty()
                ? null
                : Long.parseLong(String.valueOf(txId.getText())));

        this.contato.setNome(txNome.getText());

        this.contato.setObservacoes(txObservacoes.getText());

    }

    private Boolean validateContato() {

        if (txNome.getText().trim().isEmpty()) {
            FXUtils.alertDialog(Alert.AlertType.WARNING, "Aviso", "O campo nome deve ser preenchido!", "Preencha o campo nome!");

            this.txNome.requestFocus();

            return false;
        }

        return true;
    }

    private void clearData() {

        this.txConsulta.clear();

        this.contato = new Contato();

        this.objectForFields();

        this.loadAllTables();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.repository = RepositoryFactory.create(Contato.class);

        this.contato = new Contato();

        this.configureIcons();

        this.configureToolTips();

        this.configureColumns();

        this.loadContactTable();

    }

    private void loadEmailTable() {

        ObservableList<Email> emails = FXCollections.observableList(new ArrayList<>(this.contato.getEmails()));

        tableEmail.setItems(emails);

        tableEmail.refresh();

    }

    private void loadFoneTable() {

        ObservableList<Fone> fones = FXCollections.observableList(new ArrayList<>(this.contato.getFones()));

        tableFones.setItems(fones);

        tableFones.refresh();

    }

    private void loadContactTable() {

        String consulta = this.txConsulta.getText().trim();

        ObservableList<Contato> contatos = FXCollections.observableArrayList(consulta.trim().isEmpty()
                ? repository.findAll()
                : repository.executeNamedQuery("Contato.findByName", new PairImpl<>("nome", "%" + consulta + "%")));

        tableContatos.setItems(contatos);

        tableContatos.refresh();

    }

    private void configureColumns() {

        idColumn.setCellValueFactory(x -> new SimpleObjectProperty<Long>(x.getValue().getId()));
        nomeColumn.setCellValueFactory(x -> new SimpleObjectProperty<String>(x.getValue().getNome()));

        idFoneColumn.setCellValueFactory(x -> new SimpleObjectProperty<Long>(x.getValue().getId()));
        foneColumn.setCellValueFactory(x -> new SimpleObjectProperty<String>(x.getValue().getNumeroProxy()));
        tipoColumn.setCellValueFactory(x -> new SimpleObjectProperty<String>(x.getValue().getTipo()));

        idEmailColumn.setCellValueFactory(x -> new SimpleObjectProperty<Long>(x.getValue().getId()));
        emailColumn.setCellValueFactory(x -> new SimpleObjectProperty<String>(x.getValue().getEmail()));

    }

    private void configureIcons() {
        
        imgContato.setImage(new Image(this.getClass().getResource("../../../icons/address-book-new.png").toExternalForm()));
        
        FXUtils.setButtonImage(btSalvar, "./icons/emblem-default.png", "left");
        FXUtils.setButtonImage(btLimpar, "./icons/edit-clear-all.png", "left");
        FXUtils.setButtonImage(btExcluir, "./icons/edit-delete.png", "left");
        FXUtils.setButtonImage(btSair, "./icons/application-exit.png", "left");
        
        FXUtils.setButtonImage(btNovoFone, "./icons/edit-add.png", "center");
        FXUtils.setButtonImage(btExcluirFone, "./icons/edit-delete.png", "center");
        
        FXUtils.setButtonImage(btNovoEmail, "./icons/edit-add.png", "center");
        FXUtils.setButtonImage(btExcluirEmail, "./icons/edit-delete.png", "center");

        FXUtils.setButtonImage(btConsultar, "./icons/edit-find.png", "center");
        
    }
    
    private void configureToolTips() {
        
        FXUtils.setToolTip(btNovoFone, "Adicionar novo fone");
        FXUtils.setToolTip(btExcluirFone, "Excluir fone selecionado");
        
        FXUtils.setToolTip(btNovoEmail, "Adicionar novo email");
        FXUtils.setToolTip(btExcluirEmail, "Excluir email selecionado");

        FXUtils.setToolTip(btConsultar, "Pressione para consultar");
    }

    private void tablesForObject() {

        if (this.tableEmail.getItems() != null)
            this.contato.setEmails(new HashSet<>(this.tableEmail.getItems()));

        if (this.tableFones.getItems() != null)
            this.contato.setFones(new HashSet<>(this.tableFones.getItems()));
    }

    private void deleteContato() {

        if (this.contato.getId() != null) {

            repository.delete(this.contato.getId());

            this.clearData();
        }
    }

    private void loadAllTables() {

        this.loadContactTable();

        this.loadEmailTable();

        this.loadFoneTable();

    }
}
