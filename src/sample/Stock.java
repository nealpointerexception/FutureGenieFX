package sample;import java.util.ArrayList;

/**
 * CREATED BY NEAL C (nealpointerexception)
 * */
public class Stock {
    // store premium values
    ArrayList<Double> netArray = new ArrayList<>(3);
    private double net = 0 ;
    // add values to net depending on values etc..
    public void addToNet(double val, String buyOrSell, int amt, int line){
        int factor = -1;
        if(buyOrSell.toLowerCase().charAt(0) == 's'){
            factor = 1;
        }

        netArray.add(line, (val*factor*amt));

    }
    // get net by adding all values in array and * by 50
    public double getNet(){
        for(int i = 0;i <= netArray.size()-1; i++){
            net += netArray.get(i);

        }
        return net*50;
    }
    public void clearNetArray(){
        netArray.clear();
    }
}
