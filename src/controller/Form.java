package controller;

import View.MyStage;
import View.TabAdminView;
import View.TabUserView;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.LinearRegression;
import model.Postgresql;

import java.util.ArrayList;
import java.util.List;

public class Form {

    StackPane stackPane;
    TabUserView tabUser;
    TabAdminView tabAdmin;
    public MyStage stage;
    int xSize = 500;
    int ySize = 400;
    int inputHeight=170;
    int inputWeight=60;
    int ennustus =0;
    String inputName;
    int id=0;

    Postgresql sql = new Postgresql();
    LinearRegression linearRegression = new LinearRegression();

    public Form() {
        tabAdmin = new TabAdminView("Admin");//create admin tab
        tabUser = new TabUserView("User",inputHeight,  inputWeight,  xSize);//create user tab
        stage = new MyStage( tabUser, tabAdmin,  xSize,  ySize);//put tabs together into stage
        addListeners(); //add listeners to form objects
    }

    public void addListeners(){ //events and listeners for buttons and sliders with corresponding functions
        tabAdmin.buttonDeleteWorkTable.setOnAction(event -> {
            adminButtonDeleteWorkTable();
        });
        tabAdmin.buttonRePopulateWorkTable.setOnAction(event -> {
            adminButtonRePopulateWorkTable();
        });
        tabUser.buttonSave.setOnAction(event -> {
            userButtonSave();
        });
        tabUser.buttonPredict.setOnAction(event -> {
            userButtonPredict();
        });
        tabUser.buttonNew.setOnAction(event -> {
            userButtonNew();
         });
        tabUser.buttonOpenChart.setOnAction(event -> {
            userButtonOpenChart();
        });
        tabUser.buttonCloseChart.setOnAction(event -> {
            userButtonCloseChart();
        });

        tabUser.sliderInputHeight.valueProperty().addListener((observable, vanaVaartus, uusVaartus) -> {
            //http://i200.itcollege.ee/javafx-Slider
            inputHeight = uusVaartus.intValue();
            tabUser.labelInputHeight.setText("Sinu pikkus sentimeetrites: " + inputHeight);
            tabUser.buttonSave.setClickedEnable();
        });

        tabUser.sliderInputWeight.valueProperty().addListener((observable, vanaVaartus, uusVaartus) -> {
            //http://i200.itcollege.ee/javafx-Slider
            inputWeight = uusVaartus.intValue();
            tabUser.labelInputWeight.setText("Sinu kaal kilogrammides: " +inputWeight);
            tabUser.buttonSave.setDisable(false);
            tabUser.buttonSave.setClickedEnable();
        });
   }

    //button functions
    private void adminButtonDeleteWorkTable(){
        String query = new String("delete from height_weight;");
        System.out.println(query);
        sql.execute_query(query);
        tabAdmin.labelComments.setText("height_weight tabelis on ridasid:" + sql.select("Select count(id) from height_weight;"));
    }
    private void adminButtonRePopulateWorkTable() {
        String query = new String("INSERT INTO height_weight SELECT id, height, weight, username, insertdate FROM height_weight_orig;");
        System.out.println(query);
        sql.execute_query(query);
        tabAdmin.labelComments.setText("height_weight tabelis on ridasid:" + sql.select("Select count(id) from height_weight;"));
    }

    private void userButtonSave(){
        inputName = tabUser.fieldInputName.getText();
        String query = new String("INSERT INTO height_weight (height, weight, username) VALUES ("+inputHeight + "," +inputWeight +",'" + inputName+"' ) ;");
        System.out.println(query);
        sql.execute_query(query);

        query = "Select max(id), count(id) from height_weight;";
        System.out.println(query);
        id = Integer.parseInt((String) sql.select(query).get(0).get(0));
        int dbCount =Integer.parseInt((String) sql.select(query).get(0).get(1));
        tabUser.labelComments.setText("Salvestati " + inputHeight + "cm ja " + inputWeight + "kg. " + "Andmebaasis on ridasid: " + dbCount) ;

        tabUser.buttonSave.setClickedDisable();
        tabUser.buttonPredict.setClickedEnable();
    }

    private void userButtonPredict(){
        if (id==0){
            tabUser.labelComments.setText("Enne joonistamist salvesta enda andmed");
        } else {
            double[] coefs = (linearRegression.calc_coefs(id));
            ennustus = (int) (coefs[0] + coefs[1] * inputHeight);
            System.out.println(coefs[0] + "," + coefs[1]);
            tabUser.labelComments.setText("Sinu ennustatav kaal on:" + String.format("%.2g", coefs[0]) + "+" + String.format("%.2g", coefs[1]) + "*" + inputHeight + "=" + ennustus);
            tabUser.buttonOpenChart.setClickedEnable();
            tabUser.buttonPredict.setClickedDisable();
        }
    }
    private void userButtonNew() {
        inputHeight= 170;
        inputWeight= 60;
        inputName= "";
        id= 0;
        ennustus =0;
        tabUser.labelHello.setText("Tere");
        tabUser.fieldInputName.setText("Priit");

        tabUser.labelInputHeight.setText("Sinu pikkus sentimeetrites: "+inputHeight);
        tabUser.labelInputWeight.setText("Sinu kaal kilogrammides: "+inputWeight);
        tabUser.sliderInputHeight.setValue(inputHeight);
        tabUser.sliderInputWeight.setValue(inputWeight);

        tabUser.labelComments.setText("");
        tabUser.buttonCloseChart.fire(); //sulgeb joonise

        tabUser.buttonSave.setClickedEnable();
        tabUser.buttonOpenChart.setClickedDisable();
        tabUser.buttonPredict.setClickedDisable();
    }
    private void userButtonOpenChart(){
            if (id==0){
                tabUser.labelComments.setText("Enne joonistamist salvesta enda andmed");
            } else {
                roomForChartAnimation(ySize + 400);
                tabUser.borderPane.setBottom(drawChart());
            }
        tabUser.buttonCloseChart.setClicked();
        tabUser.buttonOpenChart.setClicked();
    }
    private void userButtonCloseChart() {
        roomForChartAnimation(ySize);
        tabUser.buttonCloseChart.setClicked();
        tabUser.buttonOpenChart.setClicked();
    }

    //build scatterchart
    public ScatterChart<Number,Number> drawChart(){
        //get max and min for x axis length
        String query = new String("select max(height), min(height) from height_weight;");
        System.out.println(query);
        ArrayList<List> maxData = new ArrayList(sql.select(query)) ;

        int minAxisValue = model.helpers.roundUpDown(Integer.parseInt((String) (maxData.get(0).get(1))), -10);
        int maxAxisValue = model.helpers.roundUpDown(Integer.parseInt((String) (maxData.get(0).get(0))), 10);
        final NumberAxis xAxis = new NumberAxis(minAxisValue, maxAxisValue, 10);
        //get max and min for y axis lengths
        query = ("select max(weight), min(weight) from height_weight;");
        System.out.println(query);
        maxData = (sql.select(query)) ;

        minAxisValue = model.helpers.roundUpDown(Integer.parseInt((String) (maxData.get(0).get(1))), -10);
        maxAxisValue = model.helpers.roundUpDown(Integer.parseInt((String)(maxData.get(0).get(0)) ),10);
        final NumberAxis yAxis = new NumberAxis(minAxisValue, maxAxisValue, 10); //add maxmin to axis

        //format scatter chart
        xAxis.setLabel("Pikkus cm");
        yAxis.setLabel("Kaal kg");

        final ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
        sc.setTitle("Pikkuse ja kaalu seos");

        //get train data and add to Series1
        query = ("Select height, weight from height_weight where id != "+id+";");
        System.out.println(query);
        ArrayList<List> chartData = new ArrayList(sql.select(query)) ;

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("train data");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(inputName+ " tegelik");

        for (int i = 0; i < chartData.size(); i++) {
            series1.getData().add(new XYChart.Data(Integer.parseInt((String) chartData.get(i).get(0))+Math.random()/2,
                    Integer.parseInt((String) chartData.get(i).get(1))+Math.random()/2));
        }
        //get real height and weight data and add to series2
        query = "Select height, weight from height_weight where id = "+id+";";
        chartData =sql.select(query);
        series2.getData().add(new XYChart.Data(Integer.parseInt((String) chartData.get(0).get(0)), Integer.parseInt((String) chartData.get(0).get(1))));

        //add prediction to series3
        if (ennustus!=0) {
            XYChart.Series series3 = new XYChart.Series();
            series3.setName(inputName + " ennustus");
            series3.getData().add(new XYChart.Data(inputHeight, ennustus));
            sc.getData().addAll(series1, series2, series3);
        } else {
            sc.getData().addAll(series1, series2);
        }

        return sc;
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

}
