package View;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;

public class Userbutton extends Button {

    public Userbutton (String name){
        super(); //initiates Button
        createButton(name);
    }

    private void createButton(String name) {
        setText(name);//buttan text
        setMaxWidth(200);

        setStyle(" -fx-base: #b6e7c9;"); //button colort

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
    }

    public void setClicked() { //changes disabled property to opposite
        if(isDisabled()) {
            setDisabled(false);
        } else {
            setDisabled(true);
        }
    }
    public void setClickedEnable() {
        setDisabled(false);
    } //sets not disabled
    public void setClickedDisable() {
        setDisabled(true);
    } //sets disabled
}
