package View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class TabAdminView extends Tab {

    public Label labelComments;
    public Button buttonDeleteWorkTable;
    public Button buttonRePopulateWorkTable;

    public TabAdminView(String name){
        super();
        createTab(name);
    }
    public  void createTab(String name){
        this.setText(name);
        VBox vbox = new VBox();
        BorderPane borderPane = new BorderPane();

        buttonDeleteWorkTable = new Button("kustuta andmed tabelist height_weigth");
        buttonRePopulateWorkTable = new Button("lisa originaalandmed tabelisse height_weigth");
        labelComments= new Label("");

        vbox.setSpacing(3);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(buttonDeleteWorkTable,buttonRePopulateWorkTable,labelComments );
        borderPane.setCenter(vbox);
        this.setContent(borderPane);
    }
 }
