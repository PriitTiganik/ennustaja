package userform;

import javafx.application.Application;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import regression.linear_regression;

import java.util.Arrays;


/**
 * Created by priit on 06-Nov-15.
 */
public class test extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        double[] aa = linear_regression.test();
        System.out.println(Arrays.toString(aa));
    }
}
