package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.service.CallBackInterface;
import sample.service.MyClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements CallBackInterface, Initializable {

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private ListView<String> listView;
    @FXML
    private ListView<String> listViewProduce;
    @FXML
    private ListView<String> listViewConsume;
    @FXML
    private TextField textFieldH;
    @FXML
    private TextField textFieldP;
    @FXML
    private RadioButton radioButtonJSON;
    @FXML
    private RadioButton radioButtonXML;
    @FXML
    private RadioButton radioButtonDefault;

    final ToggleGroup group = new ToggleGroup();

    public MyClient myClient;
    public String dataType="1";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        myClient = new MyClient(this,"localhost",3333,"1");
        radioButtonJSON.setToggleGroup(group);
        radioButtonJSON.setUserData("1");
        radioButtonXML.setToggleGroup(group);
        radioButtonXML.setUserData("2");
        radioButtonDefault.setToggleGroup(group);
        radioButtonDefault.setUserData("3");
        radioButtonJSON.setSelected(true);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    System.out.println(group.getSelectedToggle().getUserData().toString());
                    dataType = group.getSelectedToggle().getUserData().toString();
                    myClient.setDataType(dataType);
                }
            }
        });




    }


    @FXML
    private void handleButtonAction1(ActionEvent event) {

        String host = textFieldH.getText();
        int port = Integer.parseInt(textFieldP.getText());
        myClient = new MyClient(this, host, port, dataType);
        new Thread(myClient).start();

    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        System.out.println("STOPPPPPPPPPP");
        myClient.setDataType("0");
    }


    @Override
    public void updateView(String val) {
        Platform.runLater(() -> {
            listView.getItems().add(val);
            listView.scrollTo(listView.getItems().size() - 1);
        });
    }

    @Override
    public void updateViewProduce(String val) {
        Platform.runLater(() -> {
            listViewProduce.getItems().add(val);
            listViewProduce.scrollTo(listView.getItems().size() - 1);
        });
    }

    @Override
    public void updateViewConsume(String val) {
        Platform.runLater(() -> {
            listViewConsume.getItems().add(val);
            listViewConsume.scrollTo(listView.getItems().size() - 1);
        });
    }
}
