package kobdig.ESPACE;

import java.util.ArrayList;

/**
 * Created by Meili on 21/06/2016.
 */
public class Neighborhood {

    // ATRIBUTES

    /**
     * Neighborhoods name
     */
    private String name;

    /**
     * Properties in the neighborhood
     */
    private ArrayList<Property> properties;


    // CONSTRUCTOR

    public Neighborhood (String nName){

        this.name = nName;
        this.properties = new ArrayList<>();
    }

    // GETTERS AND SETTERS

    public String getName(){

        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Property> getProperties(){
        return properties;
    }

    public void addProperty(Property property){
        this.properties.add(property);
    }

}
