package main;

import graphics.Userform;
import javafx.application.Application;
import javafx.stage.Stage;



public class main  extends Application{
    Stage stage;

    int xSize = 500;
    int ySize = 400;
    int inputHeight=170;
    int inputWeight=60;
    int ennustus =0;
    String inputName;
    int id=0;

    //Postgresql sql = new Postgresql();
    //LinearRegression linearRegression = new LinearRegression();


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Userform minuUserform= new Userform(stage,"Ennustaja");
    }

    /*
    public static void main(String[] args) {
        Userform minuUserform= new Userform("aa");
        //minuUserform.setupStage();
        //minuUserform.setupTabUser();
        //minuUserform.setuptabAdmin();
    }
    */
}
