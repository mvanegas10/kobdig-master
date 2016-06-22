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
     * Landlord states
     */
    public static final String[] LANDLORD_STATES = {"For sale", "Seeking tenant", "Rented"};

    /**
     * Owner occupied states
     */
    public static final String[] OWNER_OCCUPIED_STATES = {"Not for sale", "For sale"};

    /**
     * Probability at any particular time step that an owner-occupier household
     * will choose to move out
     */
    public final static double ABANDONMENT_FACTOR = 0.0125;

    /**
     * Probability that a tenant household will move out in a particular time step
     */
    public final static double TENANT_MOBILITY = 0.0561;

    /**
     * Loss in value applied to the physical condition variable of every location every time ste
     */
    public final static double DEPRECIATION_RATE = 0.0028;

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
     * Either landlord or owner occupied
     */
    private String ownerRelation;

    /**
     * Indicates if it is for sale, not for sale, seeking tenants or rented
     */
    private String state;

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
        this.ownerRelation = OWNER_OCCUPIED;
        this.state = OWNER_OCCUPIED_STATES[1];
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
        return ownerRelation;
    }

    public void setOwnerRelation(String relation) {
        this.ownerRelation = relation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

        double rnd1 = Math.random();
        double rnd2 = Math.random();
        double rnd3 = Math.random();
        double rnd4 = Math.random();
        double rnd5 = Math.random();

        if (ownerRelation.equals(OWNER_OCCUPIED)){
            if (state.equals(OWNER_OCCUPIED_STATES[0])){

            }
        }

    }

    public String toString(){
        return id  + "," + neighborhood + "," + ownerRelation + "," + state +  "," + price +  "," + capitalizedRent +  "," + potentialRent +  "," + value;
    }

}
