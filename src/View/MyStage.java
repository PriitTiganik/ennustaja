package View;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MyStage extends Stage {
    public StackPane stackPane;
    public MyStage(TabUserView tabUser,TabAdminView tabAdmin, int xSize, int ySize){
        super();
        stackPane = new StackPane();
        TabPane tabPane = new TabPane();

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(tabUser);
        tabPane.getTabs().add(tabAdmin);

        Scene scene= new Scene(tabPane, xSize, ySize);
        this.setY(10);
        this.setScene(scene);
        this.show();
        this.setOnCloseRequest(event -> {
            System.exit(0);});//lõpetab programmi, kui sulgeme akna
    }
}
