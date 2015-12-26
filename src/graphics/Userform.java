package graphics;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priit on 25-Dec-15.
 */
public class Userform extends Application {
    Stage stage;
    StackPane stackPane;
    Tab tabUser = new Tab();
    Tab tabAdmin = new Tab();


    int xSize = 500;
    int ySize = 400;
    int inputHeight=170;
    int inputWeight=60;
    int ennustus =0;
    String inputName;
    int id=0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = primaryStage;
    }
/*    public Userform (String name){
        setupStage();
        setupTabUser();
        setuptabAdmin();
    }




    public void setuptabAdmin() {
        VBox vbox = new VBox();
        BorderPane borderPane = new BorderPane();

        Button buttonDeleteWorkTable = new Button("kustuta andmed tabelist height_weigth");
        Button buttonRePopulateWorkTable = new Button("lisa originaalandmed tabelisse height_weigth");
        Label labelComments= new Label("");

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

    public void setupTabUser() {
        VBox vbox = new VBox();
        BorderPane borderPane = new BorderPane();

        Label labelHello = new Label("Tere");
        TextField fieldInputName = new TextField();
        fieldInputName.setText("(sisesta nimi)");
        fieldInputName.setMaxWidth(100);

        Label labelInputHeight = new Label("Sinu pikkus sentimeetrites: "+inputHeight);

        Slider sliderInputHeight = new Slider();
        sliderInputHeight.setMin(150);
        sliderInputHeight.setMax(210);
        sliderInputHeight.setValue(inputHeight);
        sliderInputHeight.setShowTickLabels(true);
        sliderInputHeight.setShowTickMarks(true);
        sliderInputHeight.setMajorTickUnit(10);
        sliderInputHeight.setMinorTickCount(5);
        sliderInputHeight.setMaxWidth(xSize-100);

        Label  labelInputWeight= new Label("Sinu kaal kilogrammides: "+inputWeight);
        Slider sliderInputWeight = new Slider();
        sliderInputWeight.setMin(40);
        sliderInputWeight.setMax(120);
        sliderInputWeight.setValue(inputWeight);
        sliderInputWeight.setShowTickLabels(true);
        sliderInputWeight.setShowTickMarks(true);
        sliderInputWeight.setMajorTickUnit(10);
        sliderInputWeight.setMinorTickCount(5);
        sliderInputWeight.setMaxWidth(xSize-100);

        Userbutton buttonSave = new Userbutton("Salvesta andmed");
        Userbutton buttonPredict = new Userbutton("Ennusta kaal pikkuse alusel");
        Userbutton buttonNew = new Userbutton("Uus sisestus");
        Label  labelComments= new Label("");
        Userbutton buttonOpenChart = new Userbutton("Joonista graafik");
        Userbutton buttonCloseChart = new Userbutton("Kustuta graafik");

        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHello,fieldInputName, labelInputHeight,sliderInputHeight
                ,labelInputWeight,sliderInputWeight,buttonSave,buttonPredict,buttonNew,labelComments, buttonOpenChart,buttonCloseChart);
        borderPane.setCenter(vbox );
        tabUser.setContent(borderPane);

        buttonOpenChart.setClicked();
        buttonPredict.setClicked();
        buttonCloseChart.setClicked();

        buttonSave.setOnAction(event -> {
            inputName = fieldInputName.getText();
            String query = new String("INSERT INTO height_weight (height, weight, username) VALUES ("+inputHeight + "," +inputWeight +",'" + inputName+"' ) ;");
            System.out.println(query);
            sql.postgresql.execute_query(query);

            query = "Select max(id), count(id) from height_weight;";
            System.out.println(query);
            id = Integer.parseInt((String) sql.postgresql.select(query).get(0).get(0));
            int dbCount =Integer.parseInt((String) sql.postgresql.select(query).get(0).get(1));
            labelComments.setText("Salvestati " + inputHeight + "cm ja " + inputWeight + "kg. " + "Andmebaasis on ridasid: " + dbCount) ;

            buttonSave.setClicked();
            buttonPredict.setClicked();


            //andmed dbst
        });

        buttonPredict.setOnAction(event -> {
            if (id==0){
                labelComments.setText("Enne joonistamist salvesta enda andmed");
            } else {
                double[] coefs = (regression.linear_regression.calc_coefs(id));
                ennustus = (int) (coefs[0] + coefs[1] * inputHeight);
                System.out.println(coefs[0] + "," + coefs[1]);
                labelComments.setText("Sinu ennustatav kaal on:" + String.format("%.2g", coefs[0]) + "+" + String.format("%.2g", coefs[1]) + "*" + inputHeight + "=" + ennustus);
                buttonOpenChart.setClickedEnable();

            }
        });
        buttonNew.setOnAction(event -> {
            inputHeight= 170;
            inputWeight= 60;
            inputName= "";
            id= 0;
            ennustus =0;
            labelHello.setText("Tere");
            fieldInputName.setText("(sisesta nimi)");

            labelInputHeight.setText("Sinu pikkus sentimeetrites: "+inputHeight);
            labelInputWeight.setText("Sinu kaal kilogrammides: "+inputWeight);
            sliderInputHeight.setValue(inputHeight);
            sliderInputWeight.setValue(inputWeight);

            labelComments.setText("");
            buttonCloseChart.fire();
        });
        buttonOpenChart.setOnAction(event -> {
            if (id==0){
                labelComments.setText("Enne joonistamist salvesta enda andmed");
            } else {
                roomForChartAnimation(ySize + 400);
                borderPane.setBottom(drawChart());
            }
            buttonCloseChart.setClicked();
            buttonOpenChart.setClicked();
        });
        buttonCloseChart.setOnAction(event -> {
            roomForChartAnimation(ySize);
            buttonCloseChart.setClicked();
            buttonOpenChart.setClicked();
        });

        sliderInputHeight.valueProperty().addListener((observable, vanaVaartus, uusVaartus) -> {
            //http://i200.itcollege.ee/javafx-Slider
            inputHeight = uusVaartus.intValue();
            labelInputHeight.setText("Sinu pikkus sentimeetrites: " + inputHeight);
            buttonSave.setClickedEnable();
        });

        sliderInputWeight.valueProperty().addListener((observable, vanaVaartus, uusVaartus) -> {
            //http://i200.itcollege.ee/javafx-Slider
            inputWeight = uusVaartus.intValue();
            labelInputWeight.setText("Sinu kaal kilogrammides: " +inputWeight);
            buttonSave.setDisable(false);
            buttonSave.setClickedEnable();
        });

    }


    public ScatterChart<Number,Number> drawChart(){
        String query = new String("select max(height), min(height) from height_weight;");
        System.out.println(query);
        ArrayList<List> maxData = new ArrayList(sql.postgresql.select(query)) ;

        final NumberAxis xAxis = new NumberAxis(roundUpDown(Integer.parseInt((String) (maxData.get(0).get(1))), -10),
                roundUpDown(Integer.parseInt((String)(maxData.get(0).get(0)) ),10), 10);

        query = ("select max(weight), min(weight) from height_weight;");
        System.out.println(query);
        maxData = (sql.postgresql.select(query)) ;

        final NumberAxis yAxis = new NumberAxis(roundUpDown(Integer.parseInt((String)(maxData.get(0).get(1))),-10),
                roundUpDown(Integer.parseInt((String)(maxData.get(0).get(0)) ),10), 10);

        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Pikkus cm");
        yAxis.setLabel("Kaal kg");
        sc.setTitle("Pikkuse ja kaalu seos");

        query = ("Select height, weight from height_weight where id != "+id+";");
        System.out.println(query);
        ArrayList<List> chartData = new ArrayList(sql.postgresql.select(query)) ;

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("train data");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(inputName+ " tegelik");

        for (int i = 0; i < chartData.size(); i++) {
            series1.getData().add(new XYChart.Data(Integer.parseInt((String) chartData.get(i).get(0))+Math.random()/2,
                    Integer.parseInt((String) chartData.get(i).get(1))+Math.random()/2));
        }
        query = "Select height, weight from height_weight where id = "+id+";";
        chartData =sql.postgresql.select(query);
        series2.getData().add(new XYChart.Data(Integer.parseInt((String) chartData.get(0).get(0)), Integer.parseInt((String) chartData.get(0).get(1))));
        if (ennustus!=0) {
            XYChart.Series series3 = new XYChart.Series();
            series3.setName(inputName + " ennustus");
            series3.getData().add(new XYChart.Data(inputHeight, ennustus));
            sc.getData().addAll(series1, series2, series3);
        } else {
            sc.getData().addAll(series1, series2);
        }
        //Scene scene  = new Scene(sc, 500, 400);
        //stage.setScene(scene);
        //stage.show();
        return sc;
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

    public static int roundUpDown(int number, int by){
        int result=number;
        if(by>0){
            result =number+(by-number%by);
        }else if(by <0){
            if(number%by==0){
                number--;
                result = number-number%by;
            }else {
                result =number-number%by;
            }
        }else {
            return result;
        }
        return result;
    }
    public void roomForChartAnimation(int heightInput){
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

    public void setupStage() {
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
    */
}
