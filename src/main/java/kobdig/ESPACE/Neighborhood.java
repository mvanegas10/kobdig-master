package kobdig.ESPACE;

import java.util.ArrayList;

/**
 * Created by Meili on 21/06/2016.
 */
public class Neighborhood {

    // CONSTANTS

    /**
     * Factor affecting variability in the neighborhood status
     */
    public static final double STATUS_VARIABILITY = 0.025;

    // ATRIBUTES

    /**
     * Neighborhoods name
     */
    private String name;

    /**
     * Current standing of the whole neighborhood represented in the model
     */
    private double status;


    // CONSTRUCTOR

    public Neighborhood (String nName, double nStatus){
        this.name = nName;
        this.status = nStatus;
    }

    // GETTERS AND SETTERS

    public String getName(){

        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getStatus(){
        return status;
    }

    public void setStatus(double status){
        this.status = status;
    }

    // METHODS

    public String toString(){
        return name +  "," + status;
    }

}
