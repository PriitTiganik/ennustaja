package userform;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class test3 extends Application{
    private Timeline timeline;
    private AnimationTimer timer;
///http://stackoverflow.com/questions/17306981/how-do-i-create-a-resize-animation-for-javafx-stage
private Integer i=0;
    @Override
    public void start(final Stage stage) throws Exception {


        stage.setTitle("Area Chart Sample");
        Group root = new Group();
        Scene scene  = new Scene(root, 250, 250);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);

//You can add a specific action when each frame is started.
        timer = new AnimationTimer() {
            //int j = 0;
            @Override
            public void handle(long l) {
                //j = (-i*i+30*i)/10;
                //stage.setWidth(stage.getWidth()+j);
                System.out.println(stage.getMinWidth());
                if(stage.getMinWidth()==800){
                    timer.stop();
                }
            }

        };

        //create a keyValue with factory: scaling the circle 2times
        KeyValue keyValueX = new KeyValue(stage.minWidthProperty(), 800, Interpolator.EASE_OUT);
       // KeyValue keyValueY = new KeyValue(stack.scaleYProperty(), 2);


        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(1000);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //reset counter
                i = 0;
            }
        };

        KeyFrame keyFrame = new KeyFrame(duration, onFinished,keyValueX );

        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);


        timeline.play();
        timer.start();
        timer.stop();


        /*
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
*/

    }


    public static void main(String[] args) {
        launch(args);
    }

}