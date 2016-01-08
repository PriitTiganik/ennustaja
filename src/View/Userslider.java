package View;


import javafx.scene.control.Slider;

public class Userslider extends Slider{
    public Userslider (int min, int max, int defaultVal, int xSize){ //slider min value, default value, slider max value, form width
        super(); //initiates Slider
        setMin(min);
        setMax(max);
        setValue(defaultVal);
        setShowTickLabels(true);
        setShowTickMarks(true);
        setMajorTickUnit(10);
        setMinorTickCount(5);
        setMaxWidth(xSize-100);
        createSlider();
    }

    private void createSlider() {
    }

}
