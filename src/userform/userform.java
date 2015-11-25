package userform;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    int ySize = 400;
    int inputHeight=170;
    int inputWeight=60;
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
            String query = new String("delete from height_weight;");
            System.out.println(query);
            sql.postgresql.execute_query(query);

            labelComments.setText("height_weight tabelis on ridasid:" + sql.postgresql.select("Select count(id) from height_weight;"));
        });

        buttonRePopulateWorkTable.setOnAction(event -> {
            String query = new String("INSERT INTO height_weight SELECT id, height, weight, username, insertdate FROM height_weight_orig;");
            System.out.println(query);
            sql.postgresql.execute_query(query);
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

        Label labelInputHeight = new Label("Sinu pikkus sentimeetrites: "+inputHeight);

        Slider sliderInputHeight = new Slider();
        sliderInputHeight.setMin(100);
        sliderInputHeight.setMax(250);
        sliderInputHeight.setValue(inputHeight);
        sliderInputHeight.setShowTickLabels(true);
        sliderInputHeight.setShowTickMarks(true);
        sliderInputHeight.setMajorTickUnit(50);
        sliderInputHeight.setMinorTickCount(5);

        Label  labelInputWeight= new Label("Sinu kaal kilogrammides: "+inputWeight);
        Slider sliderInputWeight = new Slider();
        sliderInputWeight.setMin(40);
        sliderInputWeight.setMax(140);
        sliderInputWeight.setValue(inputWeight);
        sliderInputWeight.setShowTickLabels(true);
        sliderInputWeight.setShowTickMarks(true);
        sliderInputWeight.setMajorTickUnit(20);
        sliderInputWeight.setMinorTickCount(4);

        Button buttonSave = new Button("Salvesta andmed");
        Button buttonPredict = new Button("Ennusta kaal pikkuse alusel");
        Button buttonNew = new Button("Uus sisestus");
        Label  labelComments= new Label("");
        Button buttonOpenChart = new Button("Joonista graafik");
        Button buttonCloseChart = new Button("Kustuta graafik");


        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHello,fieldInputName, labelInputHeight,sliderInputHeight
                ,labelInputWeight,sliderInputWeight,buttonSave,buttonPredict,buttonNew,labelComments, buttonOpenChart,buttonCloseChart);
        borderPane.setCenter(vbox );
        tabUser.setContent(borderPane);

        buttonSave.setOnAction(event -> {

            inputName = fieldInputName.getText();
            String query = new String("INSERT INTO height_weight (height, weight, username) VALUES ("+inputHeight + "," +inputWeight +",'" + inputName+"' ) ;");
            System.out.println(query);
            sql.postgresql.execute_query(query);

            query = "Select count(id) from height_weight;";
            System.out.println(query);
            labelComments.setText("Salvestati " + inputHeight + "cm ja " + inputWeight + "kg. " + "Andmebaasis on ridasid: " + (String) sql.postgresql.select(query).get(0).get(0));

            //andmed dbst
        });
        buttonPredict.setOnAction(event -> {
            double[] coefs = (regression.linear_regression.calc_coefs());
            int ennustus = (int) (coefs[0]+coefs[1]*inputHeight);
            System.out.println(coefs[0]+","+coefs[1]);
            labelComments.setText("Sinu ennustatav kaal on:" +String.format("%.2g", coefs[0])+"+"+String.format("%.2g", coefs[1])+"*"+inputHeight+"="+ennustus);
        });
        buttonNew.setOnAction(event -> {
            inputHeight= 170;
            inputWeight= 60;
            inputName= "";
            id= 0;
            labelHello.setText("Tere");
            fieldInputName.setText("(sisesta nimi)");

            labelInputHeight.setText("Sinu pikkus sentimeetrites: "+inputHeight);
            labelInputWeight.setText("Sinu kaal kilogrammides: "+inputWeight);
            sliderInputHeight.setValue(inputHeight);
            sliderInputWeight.setValue(inputWeight);

            labelComments.setText("");
        });
        buttonOpenChart.setOnAction(event -> {
            roomForChartAnimation(ySize+400);

        });
        buttonCloseChart.setOnAction(event -> {
            roomForChartAnimation(ySize);

        });
        sliderInputHeight.valueProperty().addListener((observable, vanaVaartus, uusVaartus) -> {
            //http://i200.itcollege.ee/javafx-Slider
            inputHeight = uusVaartus.intValue();
            labelInputHeight.setText("Sinu pikkus sentimeetrites: " +inputHeight);
        });
        sliderInputWeight.valueProperty().addListener((observable, vanaVaartus, uusVaartus) -> {
            //http://i200.itcollege.ee/javafx-Slider
            inputWeight = uusVaartus.intValue();
            labelInputWeight.setText("Sinu kaal kilogrammides: " +inputWeight);
        });

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

    private void roomForChartAnimation(int heightInput){
        //http://www.java2s.com/Tutorials/Java/JavaFX/1010__JavaFX_Timeline_Animation.htm
        //http://docs.oracle.com/javafx/2/animations/basics.htm
        Timeline timeline;
        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);

        //create a keyValue with factory: scaling the circle 2times
        KeyValue keyValueX1 = new KeyValue(stage.minHeightProperty(), heightInput, Interpolator.EASE_OUT);
        KeyValue keyValueX2 = new KeyValue(stage.maxHeightProperty(), heightInput, Interpolator.EASE_OUT);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(1000);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //reset counter
               // i = 0;
            }
        };

        KeyFrame keyFrame = new KeyFrame(duration, onFinished,keyValueX1,keyValueX2 );

        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

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
        stage.setY(10);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {System.exit(0);});//lõpetab programmi, kui sulgeme akna
    }
}
