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
        Label labelInputHeight = new Label("Sisesta enda pikkus");
        TextField fieldInputHeight = new TextField();
        fieldInputHeight.setMaxWidth(100);
        Label  labelInputWeight= new Label("Sisesta enda kaal");
        TextField fieldInputWeight = new TextField();
        fieldInputHeight.setMaxWidth(100);
        Button buttonCheck = new Button("Kontrolli andmed");
        Button buttonSave = new Button("Salvesta andmed");
        Button buttonPredict = new Button("Ennusta kaal pikkuse alusel");


        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHello, labelInputHeight,fieldInputHeight
                ,labelInputWeight,fieldInputWeight,buttonCheck,buttonSave,buttonPredict);
        borderPane.setCenter(vbox );
        tabUser.setContent(borderPane);
        buttonCheck.setOnAction(event -> { //kriips ja nool värk. Kui nupule vajutati, siis mis toimub

            String sInputHeight = fieldInputHeight.getText();
            String sInputWeight = fieldInputWeight.getText();
            String checkResult = checkInputs(sInputHeight, sInputWeight);

            Label  labelcheckResult= new Label(checkResult);
            vbox.getChildren().add(labelcheckResult);
        });


    }

    private String checkInputs(String sInputHeight, String sInputWeight) {
//kontrollib, kas sisesestus on normaalses vahemikus,
// kas on v]imalik intiks konvertida
        //ja tagastab tulemuse voi veateate tekstina

        return "pole errorit \n "+sInputHeight+ "\n" + sInputWeight;
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
