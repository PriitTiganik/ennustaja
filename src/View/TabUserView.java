package View;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class TabUserView extends Tab {
    public Label labelHello;
    public TextField fieldInputName;
    public Label labelInputHeight;
    public Userslider sliderInputHeight;
    public Label labelInputWeight;
    public Userslider sliderInputWeight;
    public Userbutton buttonSave;
    public Userbutton buttonPredict;
    public Userbutton buttonNew;
    public Label  labelComments;
    public Userbutton buttonOpenChart;
    public Userbutton buttonCloseChart;

    public BorderPane borderPane;

    public TabUserView (String name, int inputHeight, int inputWeight, int xSize){
        super();
        createTab(name, inputHeight,  inputWeight,  xSize);
    }

    private void createTab(String name,int inputHeight, int inputWeight, int xSize) {
        this.setText(name);
        VBox vbox = new VBox();
        borderPane = new BorderPane();

        labelHello = new Label("Tere");
        fieldInputName = new TextField();
        fieldInputName.setText("Priit");
        fieldInputName.setMaxWidth(100);

        labelInputHeight = new Label("Sinu pikkus sentimeetrites: "+inputHeight);
        sliderInputHeight = new Userslider(150,200,inputHeight,xSize);

        labelInputWeight= new Label("Sinu kaal kilogrammides: "+inputWeight);
        sliderInputWeight = new Userslider(40,120,inputWeight,xSize);

        buttonSave = new Userbutton("Salvesta andmed");
        buttonPredict = new Userbutton("Ennusta kaal pikkuse alusel");
        buttonNew = new Userbutton("Uus sisestus");
        labelComments= new Label("");
        buttonOpenChart = new Userbutton("Joonista graafik");
        buttonCloseChart = new Userbutton("Kustuta graafik");

        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHello,fieldInputName, labelInputHeight,sliderInputHeight
                ,labelInputWeight,sliderInputWeight,buttonSave,buttonPredict,buttonNew,labelComments, buttonOpenChart,buttonCloseChart);
        borderPane.setCenter(vbox );
        this.setContent(borderPane);
        //disable some buttons
        buttonOpenChart.setClickedDisable();
        buttonPredict.setClickedDisable();
        buttonCloseChart.setClickedDisable();
    }
}
