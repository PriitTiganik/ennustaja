package userform;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;

/**
 * Created by priit on 06-Nov-15.
 */
public class userform extends Application {
    Stage stage;
    StackPane stackPane;
    Tab tabUser = new Tab();
    Tab tabAdmin = new Tab();
    int xSize = 500;
    int ySize = 500;
    int inputHeight;
    int inputWeight;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        setupStage();
        setupTabUser();
        //checkInputs();
        //setuptabAdmin();

    }

    private void setupTabUser() {
        VBox vbox = new VBox();
        BorderPane borderPane = new BorderPane();

        Label labelHello = new Label("Tere");
        Label labelInputHeight = new Label("Sisesta enda pikkus sentimeetrites");
        TextField fieldInputHeight = new TextField();
        fieldInputHeight.setMaxWidth(100);
        Label  labelInputWeight= new Label("Sisesta enda kaal kilogrammides");
        TextField fieldInputWeight = new TextField();
        fieldInputWeight.setMaxWidth(100);
        Button buttonCheck = new Button("Kontrolli andmed");
        Button buttonSave = new Button("Salvesta andmed");
        Button buttonPredict = new Button("Ennusta kaal pikkuse alusel");
        Label  labelComments= new Label("");


        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHello, labelInputHeight,fieldInputHeight
                ,labelInputWeight,fieldInputWeight,buttonCheck,buttonSave,buttonPredict,labelComments);
        borderPane.setCenter(vbox );
        tabUser.setContent(borderPane);
        buttonCheck.setOnAction(event -> { //kriips ja nool värk. Kui nupule vajutati, siis mis toimub
            String sInputHeight = fieldInputHeight.getText();
            String sInputWeight = fieldInputWeight.getText();
            String checkResult = checkInputs(sInputHeight, sInputWeight);

            labelComments.setText(checkResult);
        });
        buttonSave.setOnAction(event -> {
            labelComments.setText("");
        });
        buttonPredict.setOnAction(event -> {
            labelComments.setText("");
        });

    }

    private String checkInputs(String sInputHeight, String sInputWeight) {
//kontrollib, kas sisesestus on normaalses vahemikus,
// kas on v]imalik intiks konvertida
        //ja tagastab tulemuse voi veateate tekstina
        if (isInteger(sInputHeight)) {
            inputHeight = Integer.parseInt(sInputHeight);
        } else {
            return "Pikkus pole taisarv";
        }

        if (isInteger(sInputWeight)) {
            inputWeight = Integer.parseInt(sInputWeight);
        } else {
            return "Kaal pole taisarv";
        }

        if (inputHeight>250 | inputHeight < 130 | inputHeight>300 | inputWeight <30){
            inputHeight = 0;
            inputHeight = 0;
            return "Pikkus ja kaal peavad olema taiskasvanud inimese omad";
        }

        return "Sisestasid  \n"+sInputHeight+ "\n" + sInputWeight + "\nSalvesta andmed voi vaata, kui tapselt ennustaja su kaalu pikkuse alusel ara arvab" ;
    }
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private void setupStage() {
        stackPane = new StackPane();
        TabPane tabPane = new TabPane();
        tabUser.setText("User");
        tabAdmin.setText("Admin");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(tabUser);
        tabPane.getTabs().add(tabAdmin);

        Scene scene= new Scene(tabPane, xSize, ySize);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {System.exit(0);});//lõpetab programmi, kui sulgeme akna
    }
}
