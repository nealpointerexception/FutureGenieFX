package sample;
/**
 * CREATED BY NEAL C (nealpointerexception)
 * */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    // call all the jfx elements
    public Button fileOpenBtn, finishBtn;
    public RadioButton buy1, sell1, buy2, sell2, buy3, sell3;
    public ChoiceBox indexMenu1, indexMenu2, indexMenu3;
    public TextField shares1, shares2, shares3;
    public Label netVal, filePathLabel;

    public NumberAxis xAxis = new NumberAxis();
    public NumberAxis yAxis = new NumberAxis();
    public LineChart<Number, Number> plot = new LineChart<Number, Number>(xAxis,yAxis);
    public XYChart.Series line = new XYChart.Series();
    // file chooser
    FileChooser fileChooser = new FileChooser();
    Stock stk = new Stock();
    // buy or sell values separated by line num
    String bos1, bos2, bos3;

    //values extracted from excel file using corresponding indexes
    double val1, val2, val3, myNet;
    // amount of shares chosen by the user seperated by line number
    int shareA1, shareA2, shareA3;

    int x1, x2, x3, x4;
    double y1, y2, y3, y4;
    File file;
    Window window;
    Excel mainFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plot.getData().add(line);
        xAxis.setForceZeroInRange(false);
        xAxis.setTickUnit(50);
        //indexMenu1.setItems(FXCollections.observableArrayList(choices));
    }
    // extract index choices from excel file and fill menu fields
    public void initFields(Excel file){
        indexMenu1.setItems(FXCollections.observableArrayList(file.getIndexes()));
        indexMenu2.setItems(FXCollections.observableArrayList(file.getIndexes()));
        indexMenu3.setItems(FXCollections.observableArrayList(file.getIndexes()));
    }
    // open file button action
    public void openFile(){
        // show file opener dialog
        file = fileChooser.showOpenDialog(window);
        if(file != null){
            // load excel file and parse
            mainFile = new Excel(file.getPath(), true);
            //System.out.println(file.getPath());
            // change label text to file path
            filePathLabel.setText(file.getPath());
            // call init fields
            initFields(mainFile);
        }
        else{
            System.err.print("File Not Found");
        }
    }
    // this method extracts the values and nums the user has selected/inputted into the program
    private void extractAllSelected(){
        // check which buy or sell radio button is selected and set that to bos vals
        if(buy1.isSelected()){
            bos1 = "b";
        }
        else{
            bos1 = "s";
        }
        if(buy2.isSelected()){
            bos2 = "b";
        }
        else{
            bos2 = "s";
        }
        if(buy3.isSelected()){
            bos3 = "b";
        }
        else{
            bos3 = "s";
        }

        // get value from index value                menu.getselctionobject.getselecteditem.tostring.toint
        val1 =  mainFile.returnVal(Integer.parseInt(indexMenu1.getSelectionModel().getSelectedItem().toString()));
        val2 =  mainFile.returnVal(Integer.parseInt(indexMenu2.getSelectionModel().getSelectedItem().toString()));
        val3 =  mainFile.returnVal(Integer.parseInt(indexMenu3.getSelectionModel().getSelectedItem().toString()));

        // get amount of shares inputted
        shareA1 = Integer.parseInt(shares1.getText());
        shareA2 = Integer.parseInt(shares2.getText());
        shareA3 = Integer.parseInt(shares3.getText());


    }
    public void updatePremiumArray(){
        // add values on each line to the premium array to ultimatley find the net value
        stk.clearNetArray();
        System.out.println(stk.netArray.toString());
        stk.addToNet(val1, bos1, shareA1, 0);
        stk.addToNet(val2, bos2, shareA2, 1);
        stk.addToNet(val3, bos3, shareA3, 2);
    }
    public void calculatePoints(){
        x1 = Integer.parseInt(indexMenu1.getSelectionModel().getSelectedItem().toString());
        x2 = Integer.parseInt(indexMenu2.getSelectionModel().getSelectedItem().toString());
        x3 = Integer.parseInt(indexMenu3.getSelectionModel().getSelectedItem().toString());
        x4 = x3-50;

        y1 = myNet;

        y2 = ((x1-x2)*shareA1-(val1-val2))*50;
        //((X1-X3-Premium(x1))*Quantity(X1)*50) +( (X3-X2+Premium(X2))*Quantity(x2)*50)+Premium(X3)*50
        y3 = ((x1-x3-val1)*shareA2*50) +( (x3-x2+val2)*shareA2*50)+val3*50;
        //((X1-X4-Premium(x1))*Quantity(X1)*50) +( (X4-X2+Premium(X2))*Quantity(x2)*50)+( (X4-X3+Premium(X3))*Quantity(x3)*50)
        y4 = ((x1-x4-val1)*shareA1*50) +( (x4-x2+val2)*shareA2*50)+( (x4-x3+val3)*shareA3*50);
        System.out.println("X1: "+ x1+" Y1: "+y1);

        System.out.println("X2: "+ x2+" Y2: "+y2);

        System.out.println("X3: "+ x3+" Y3: "+y3);

        System.out.println("X4: "+ x4+" Y4: "+y4);



    }
    public void plotPoints(){

        line.getData().add(new XYChart.Data(x1, y1));
        line.getData().add(new XYChart.Data(x2, y2));
        line.getData().add(new XYChart.Data(x3, y3));
        line.getData().add(new XYChart.Data(x4, y4));


    }
    // parse values and set net amount. this is called by the doneBtn
    public void parseVals(){
        extractAllSelected();
        updatePremiumArray();
        myNet = stk.getNet();
        netVal.setText("Net Amount = "+ myNet);
        calculatePoints();
        plotPoints();


    }
// these methods make sure that only one radio btn is selected at a time;
    public void buy1Sel(){
        if(sell1.isSelected()){
            sell1.setSelected(false);
        }
    }
    public void sell1Sel(){
        if(buy1.isSelected()){
            buy1.setSelected(false);
        }
    }
    public void buy2Sel(){
        if(sell2.isSelected()){
            sell2.setSelected(false);
        }
    }
    public void sell2Sel(){
        if(buy2.isSelected()){
            buy2.setSelected(false);
        }
    }
    public void buy3Sel(){
        if(sell3.isSelected()){
            sell3.setSelected(false);
        }
    }
    public void sell3Sel(){
        if(buy3.isSelected()){
            buy3.setSelected(false);
        }
    }
    //-------------------------------------
}
