package scrumselector;

import java.util.ArrayList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;


class UserSelectionBox extends VBox
{
    private PieChart My_pieChart = new PieChart();  // The Pie Chart that will display the names
    private TextArea My_textArea = new TextArea();  // The text area where the user will enter the names
    private VBox MasterEntryUI = new VBox();              // This is the VBox that will hold the add or subtract entries
    private ArrayList<String> My_UserEntries = new ArrayList<>(); // The list of names that will be displayed in the pie chart


    public UserSelectionBox(TextArea textArea, PieChart pieChart)
    {
        super();
        this.My_textArea = textArea; // Aassign the text area to the class variable
        this.My_pieChart = pieChart; // Assign the pie chart to the class variable

        this.My_textArea.setOnKeyReleased(KeyReleased ->
                                                {
                                                    if (KeyReleased.getCode().toString().equals("ENTER")) // Only process the user input if the user presses the enter key
                                                    {
                                                        ProcessUserInput(); // Process the user input
                                                    }
                                                }
                                        );
        this.getChildren().addAll(My_textArea,MasterEntryUI); // Add the text area and the VBox to the UserSelectionBox for initializtion
    }

    private void ProcessUserInput()
    {
        String name = this.My_textArea.getText().trim();
        if (!name.isEmpty())
        {
            if(!My_UserEntries.contains(name))
            {
                My_UserEntries.add(name);
                AddNewEntry(name);
            }
            else
            {
                My_UserEntries.add(name);
                AddAdditionalEntry(name);
            }
        }
        this.My_textArea.clear();
    }


    // This section handles the Processing of the User Input
    private void AddNewEntry(String pEntryName)
    {

        HBox hbox = new HBox(); // Create the UI for increasing or decreasing the number of entries
        Button SubButton = new Button("-"); // Create a button to subtract the entry one time
        Button AddButton = new Button("+"); // Create a button to increase the entry one time
        Label label = new Label(pEntryName + " (" + My_UserEntries.stream().filter(name -> name.equals(pEntryName)).count() + ")"); // Create a label with the name and show it's count of 1


        SubButton.setOnAction(e -> {RemoveEntry(label, pEntryName);}); // Set the action for the subtract button
        AddButton.setOnAction(e -> {   AddEntry(label, pEntryName);}); // Set the action for the add button

        hbox.getChildren().addAll(SubButton,label, AddButton); // Create the UI for increasing or decreasing the number of entries
        MasterEntryUI.getChildren().add(hbox); // Add this entry's UI to the VBox (Overall Entry UI)

        updatePieChart();  // Update the Pie Chart with the new data
    }


    private void AddAdditionalEntry(String pEntryName)
    {

        HBox hbox = (HBox) MasterEntryUI.getChildren().stream() .filter(VBoxOneKids -> Checklabel(VBoxOneKids, pEntryName)).findFirst() .get(); // cycles through the children of the MasterEntryUI and Find the correct HBox
        Label plabel = (Label) hbox.getChildren().get(1); // Since I know the second child in the HBox is a label I can cast it to a label
        long count = My_UserEntries.stream().filter(name -> name.equals(pEntryName)).count(); // Count the number of times the name appears in the list
        plabel.setText(pEntryName + " (" + count + ")"); // Update the label with the new count
        updatePieChart(); // Update the Pie Chart with the new data
    }

    private boolean Checklabel(Node pNode, String pEntryName) // Check if the label contains the name
    {
        HBox lhbox   = (HBox) pNode;                             // Since I know each child in this parent VBox is an HBox I can cast it to an HBox
        Label llabel = (Label) lhbox.getChildren().get(1); // Since I know the second child in the HBox is a label I can cast it to a label
        return llabel.getText().contains(pEntryName);            // Check if the label contains the name
    }



    // This section handles the selection of + or - buttons which will update the number of entries to be selected
    private void AddEntry(Label plabel, String pEntryName)
    {
        My_UserEntries.add(pEntryName);
        long count = My_UserEntries.stream().filter(name -> name.equals(pEntryName)).count();
        plabel.setText(pEntryName + " (" + count + ")");
        updatePieChart();
    }

    private void RemoveEntry(Label plabel, String pEntryName)
    {
        if(My_UserEntries.contains(pEntryName))
        {
            My_UserEntries.remove(pEntryName);
            long count = My_UserEntries.stream().filter(name -> name.equals(pEntryName)).count();
            plabel.setText(pEntryName + " (" + count + ")");
            updatePieChart();
        }

    }

    public void updatePieChart()
    {
        My_pieChart.getData().clear();
        My_UserEntries.forEach(name -> My_pieChart.getData().add(new PieChart.Data(name, 1)));
    }
}