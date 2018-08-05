package sample;
/**
 * CREATED BY NEAL C (nealpointerexception)
 * */


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;

public class Excel {
    private ArrayList<Integer> indexArray = new ArrayList<>(100);
    private ArrayList<Double> premium = new ArrayList<>(100);
    private XSSFWorkbook wkbk;
    private XSSFSheet mainSheet;
    private XSSFRow row;
    private XSSFCell cell;
    private Stock stock = new Stock();
    private String myFilePath;
    public Excel(String filePath, boolean parse){
        myFilePath = filePath;
        if(parse){
            parseFile(myFilePath);
        }
    }
    public void parseFile(String filePath){

        try {

            wkbk = new XSSFWorkbook(filePath);
            mainSheet = wkbk.getSheetAt(0);

            for(int r = 0; r <= mainSheet.getPhysicalNumberOfRows()-1; r++){
                row = mainSheet.getRow(r);
                for(int c = 0; c <= row.getPhysicalNumberOfCells()-1; c++){
                    cell = row.getCell(c);
                    if(cell.getColumnIndex() == 0){
                        indexArray.add((int)cell.getNumericCellValue());
                    }
                    else if(cell.getColumnIndex() == 1){premium.add(cell.getNumericCellValue());
                    }
                    //System.out.println(cell);

                }
            }


        }
        catch(Exception e){
            System.out.print(e);
        }


    }

    public ArrayList getPremiums(){
        return premium;
    }
    public ArrayList getIndexes(){
        return indexArray;
    }
    public double returnVal(int indexNum){
        if (indexArray.contains(indexNum)){
            for(int i = 0; i <= indexArray.size()-1; i++){
                if(indexArray.get(i)==indexNum){
                    return premium.get(i);
                }
            }
        }
        return -1.0;

    }



}
