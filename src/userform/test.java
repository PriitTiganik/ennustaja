package userform;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class test extends Application{

///http://stackoverflow.com/questions/17306981/how-do-i-create-a-resize-animation-for-javafx-stage
    @Override
    public void start(final Stage stage) throws Exception {

        stage.setTitle("Area Chart Sample");
        Group root = new Group();
        Scene scene  = new Scene(root, 250, 250);
        stage.setResizable(false);


        Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {

            int i=0;
            int j = 0;
            @Override
            public void run() {
                if (i<100 && j <300 &&j>=0 ){
                    System.out.println(j);
                    //(-A4*A4+30*A4)/10
                    j = (-i*i+30*i)/10;
                    stage.setWidth(stage.getWidth()+j);
                    //stage.setHeight(stage.getHeight()+j);
                }
                else {
                    this.cancel();
                }

                i++;
            }
        }, 2000, 25);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}