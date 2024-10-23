package scrumselector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application 
{ 
    VBox VBoxOne = new VBox(); // UI for adding and removing entries which is replaced with the results after the Scrum Master is selected

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

        BorderPane borderPane = new BorderPane(){{ setPrefSize(400, 400);}};
        PieChart pieChart = new PieChart();
        TextArea textArea = new TextArea(){{ setPrefSize(50, 100);}};
        UserSelectionBox userSelectionBox = new UserSelectionBox(textArea, pieChart);

        userSelectionBox.setSpacing(10);


        Label label = new Label("Input Names for Scrum Master");
        Button button = new Button("Select Scrum Master");

        button.setOnAction(ButtonClickedEvent -> {SelectUser(pieChart); });


        VBoxOne.getChildren().addAll(label, userSelectionBox, button);
        VBoxOne.setPadding(new javafx.geometry.Insets(10));


        borderPane.setRight(VBoxOne);
        borderPane.setCenter(pieChart);
        borderPane.setTop((new Label("Wheel of Names"){ { setStyle("-fx-alignment: center;-fx-font-size: 20px;"); setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  }}));


        primaryStage.setTitle("Final Project");
        primaryStage.setScene(new Scene(borderPane, 500, 400));
        primaryStage.show();
    }


    public void SelectUser(PieChart My_pieChart)
    {
        int NumOfUsers = My_pieChart.getData().size();



        int numofCyles = (int) (Math.random() *17)+5;
        int WinnerIndx = 0;

        for (int i = 0; i < numofCyles; i++)
        {
            WinnerIndx = (int) (Math.random() *NumOfUsers);
        }
        
        ShowWinner(My_pieChart,WinnerIndx);
    }

    private void ShowWinner(PieChart My_pieChart, int WinnerIndx)
    {
        String ScrumMasteString = My_pieChart.getData().get(WinnerIndx).getName();
        My_pieChart.getData().get(WinnerIndx).getNode().setStyle("-fx-border-color: Black; -fx-border-width: 15px;");
        System.out.println("Scrum Master is: " + ScrumMasteString);
        VBoxOne.getChildren().clear();
        VBoxOne.getChildren().addAll(new Label("Scrum Master is: "), new Label(ScrumMasteString){ { setStyle("-fx-font-size: 20px;"); } });
    }




    
}