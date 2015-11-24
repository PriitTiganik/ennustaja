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
    String inputName;
    int id;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        setupStage();
        setupTabUser();
        //checkInputs();
        setuptabAdmin();

    }

    private void setuptabAdmin() {
        VBox vbox = new VBox();
        BorderPane borderPane = new BorderPane();

        Button buttonDeleteWorkTable = new Button("kustuta andmed tabelist height_weigth");
        Button buttonRePopulateWorkTable = new Button("lisa originaalandmed tabelisse height_weigth");
        Label  labelComments= new Label("");

        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(buttonDeleteWorkTable,buttonRePopulateWorkTable,labelComments );
        borderPane.setCenter(vbox );
        tabAdmin.setContent(borderPane);

        buttonDeleteWorkTable.setOnAction(event -> {
            sql.postgresql.execute_query("delete from height_weight;");
            labelComments.setText("height_weight tabelis on ridasid:" + sql.postgresql.select("Select count(id) from height_weight;"));
        });

        buttonRePopulateWorkTable.setOnAction(event -> {
            sql.postgresql.execute_query("INSERT INTO height_weight SELECT height, weight, name FROM height_weight_orig;");
            labelComments.setText("height_weight tabelis on ridasid:" + sql.postgresql.select("Select count(id) from height_weight;"));
        });

    }

    private void setupTabUser() {
        VBox vbox = new VBox();
        BorderPane borderPane = new BorderPane();

        Label labelHello = new Label("Tere");
        TextField fieldInputName = new TextField();
        fieldInputName.setText("(sisesta nimi)");
        fieldInputName.setMaxWidth(100);

        Label labelInputHeight = new Label("Sisesta enda pikkus sentimeetrites");
        TextField fieldInputHeight = new TextField();
        fieldInputHeight.setMaxWidth(100);
        Label  labelInputWeight= new Label("Sisesta enda kaal kilogrammides");
        TextField fieldInputWeight = new TextField();
        fieldInputWeight.setMaxWidth(100);
        Button buttonSave = new Button("Salvesta andmed");
        Button buttonPredict = new Button("Ennusta kaal pikkuse alusel");
        Button buttonNew = new Button("uus sisestus");
        Label  labelComments= new Label("");


        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHello,fieldInputName, labelInputHeight,fieldInputHeight
                ,labelInputWeight,fieldInputWeight,buttonSave,buttonPredict,buttonNew,labelComments);
        borderPane.setCenter(vbox );
        tabUser.setContent(borderPane);

        buttonSave.setOnAction(event -> {
            String sInputHeight = fieldInputHeight.getText();
            String sInputWeight = fieldInputWeight.getText();
            String checkResult = checkInputs(sInputHeight, sInputWeight);
            inputName = fieldInputName.getText();


            System.out.println(checkResult.substring(0, 10));
            if (checkResult.substring(0, 10).equals("Sisestasid")){

                sql.postgresql.execute_query("INSERT INTO height_weight (height, weight, name) VALUES ("+inputHeight + "," +inputWeight +",'" + inputName+"' ) ;");
                labelComments.setText(checkResult + " \n" + "Andmebaasis on ridasid: " + (String)sql.postgresql.select("Select count(id) from height_weight;").get(0).get(0));
            } else {
                labelComments.setText(checkResult);
            }
            //andmed dbst
        });
        buttonPredict.setOnAction(event -> {
            double[] coefs = (regression.linear_regression.calc_coefs());
            int ennustus = (int) (coefs[0]+coefs[1]*inputHeight);
            System.out.println(coefs[0]+","+coefs[1]);
            labelComments.setText("Sinu ennustatav kaal on:" +String.format("%.2g", coefs[0])+"+"+String.format("%.2g", coefs[1])+"*"+inputHeight+"="+ennustus);
        });
        buttonNew.setOnAction(event -> {
            inputHeight= 0;
            inputWeight= 0;
            inputName= "";
            id= 0;
            labelHello.setText("Tere");
            fieldInputName.setText("(sisesta nimi)");
            fieldInputHeight.setText("");
            fieldInputWeight.setText("");
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

        return "Sisestasid  \n"+sInputHeight+ "\n" + sInputWeight + "\nVaata, kui tapselt ennustaja su kaalu pikkuse alusel ara arvab.  \n Ennustuses sinu enda kaalu ei arvestata" ;
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
