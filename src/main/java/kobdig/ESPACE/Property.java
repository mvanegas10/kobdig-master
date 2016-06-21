package kobdig.ESPACE;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Meili on 21/06/2016.
 */
public class Property {

    // CONSTANTS

    /**
     * Referente to  owner occupied
     */
    public static final String OWNER_OCCUPIED = "Owner Occupied";

    /**
     * Reference to landlord
     */
    public static final String LANDLORD = "Landlord";

    /**
     * Reference to Not for sale
     */
    public static final String NOT_FOR_SALE = "Not for sale";

    /**
     * Reference to For sale
     */
    public static final String FOR_SALE = "For sale";

    /**
     * Reference to Seeking tenants
     */
    public static final String SEEKING_TENANTS = "Seeking tenants";

    /**
     * Reference to Rented
     */
    public static final String RENTED = "Rented";


    // ATRIBUTES

    /**
     * Unique ID for the property
     */
    private String id;

    /**
     * Neighborhood to which the house belongs
     */
    private String neighborhood;

    /**
     * State of the property. Either landlord or owner occupied indicating if it is for sale, not
     * for sale, seeking tenants or rented
     */
    private String[] state;

    /**
     * Current property's price
     */
    private double price;

    /**
     * Current capitalized ground rent
     */
    private double capitalizedRent;

    /**
     * Current potential ground rent
     */
    private double potentialRent;

    /**
     * Current property's value
     */
    private double value;

    // CONSTRUCTOR

    /**
     * Constructor of the class Property
     */
    public Property(String id, String nNeighborhood, double nPrice, double nRent, double nValue){
        this.id = id;
        this.neighborhood = nNeighborhood;
        this.state = new String[2];
        this.state[0] = OWNER_OCCUPIED;
        this.state[1] = NOT_FOR_SALE;
        this.price = nPrice;
        this.capitalizedRent = nRent;
        this.potentialRent = nRent;
        this.value = nValue;
    }

    // GETTERS AND SETTERS


    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getOwnerRelation() {
        return state[0];
    }

    public void setOwnerRelation(String relation) {
        this.state[0] = relation;
    }

    public String getState() {
        return state[1];
    }

    public void setState(String state) {
        this.state[1] = state;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCapitalizedRent() {
        return capitalizedRent;
    }

    public void setCapitalizedRent(double capitalizedRent) {
        this.capitalizedRent = capitalizedRent;
    }

    public double getPotentialRent() {
        return potentialRent;
    }

    public void setPotentialRent(double potentialRent) {
        this.potentialRent = potentialRent;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    // METHODS

    public void step(int time){

        price = (price - Math.exp(time) < 0)? 0: price - Math.exp(time);
        potentialRent = (potentialRent + Math.log(time));
        capitalizedRent = (capitalizedRent - Math.log(time));
        value = (value - Math.exp(time) < 0)? 0: value - Math.exp(time);

        double random = Math.random();


    }

}
