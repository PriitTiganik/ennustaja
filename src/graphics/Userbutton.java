package graphics;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

/**
 * Created by priit on 12-Dec-15.
 */
public class Userbutton extends Button {


    public Userbutton (String name){
        super(); //k'ivitab Buttoni
        createButton(name);
    }

    private void createButton(String name) {
        setText(name);
        setMaxWidth(200);

        setStyle(" -fx-base: #b6e7c9;");

        //http://docs.oracle.com/javafx/2/ui_controls/button.htm
        DropShadow shadow = new DropShadow();
        setOnMouseEntered(event -> {//Adding the shadow when the mouse cursor is on
            setStyle("-fx-base: #2FD5FA;");
            setEffect(shadow);
        });
        setOnMouseExited(event -> {//Removing the shadow when the mouse cursor is off
            setStyle(" -fx-base: #b6e7c9;");
            setEffect(null);
        });
        //setOnAction();


    }

    public void setClicked() {
        if(isDisabled()) {
            setDisabled(false);
        } else {
            setDisabled(true);
        }
        System.out.println(isDisabled());
    }
    public void setClickedEnable() {
        setDisabled(false);
    }
    public void setClickedDisable() {
        setDisabled(true);
    }
}
