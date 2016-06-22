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
     * Referente to owner occupied
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
    private double currentPrice;

    /**
     * Current capitalized ground rent
     */
    private double currentCapitalizedRent;

    /**
     * Current potential ground rent
     */
    private double currentPotentialRent;

    /**
     * Current property's value
     */
    private double currentValue;

    /**
     * Current property's price
     */
    private double previousPrice;

    /**
     * Current capitalized ground rent
     */
    private double previousCapitalizedRent;

    /**
     * Current potential ground rent
     */
    private double previousPotentialRent;

    /**
     * Current property's value
     */
    private double previousValue;

    // CONSTRUCTOR

    /**
     * Constructor of the class Property
     */
    public Property(String id, String nNeighborhood, double nPrice, double nRent, double nValue){
        this.id = id;
        this.neighborhood = nNeighborhood;
        this.ownerRelation = LANDLORD;
        this.state = LANDLORD_STATES[0];
        this.currentPrice = nPrice;
        this.currentCapitalizedRent = nRent;
        this.currentPotentialRent = nRent;
        this.currentValue = nValue;
        this.previousPrice = nPrice;
        this.previousCapitalizedRent = nRent;
        this.previousPotentialRent = nRent;
        this.previousValue = nValue;
    }

    // GETTERS AND SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getOwnerRelation() {
        return ownerRelation;
    }

    public void setOwnerRelation(String ownerRelation) {
        this.ownerRelation = ownerRelation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentCapitalizedRent() {
        return currentCapitalizedRent;
    }

    public void setCurrentCapitalizedRent(double currentCapitalizedRent) {
        this.currentCapitalizedRent = currentCapitalizedRent;
    }

    public double getCurrentPotentialRent() {
        return currentPotentialRent;
    }

    public void setCurrentPotentialRent(double currentPotentialRent) {
        this.currentPotentialRent = currentPotentialRent;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    public double getPreviousCapitalizedRent() {
        return previousCapitalizedRent;
    }

    public void setPreviousCapitalizedRent(double previousCapitalizedRent) {
        this.previousCapitalizedRent = previousCapitalizedRent;
    }

    public double getPreviousPotentialRent() {
        return previousPotentialRent;
    }

    public void setPreviousPotentialRent(double previousPotentialRent) {
        this.previousPotentialRent = previousPotentialRent;
    }

    public double getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(double previousValue) {
        this.previousValue = previousValue;
    }


    // METHODS

    public void step(int time){

        previousPrice = currentPrice;
        previousCapitalizedRent = currentCapitalizedRent;
        previousPotentialRent = currentPotentialRent;
        previousValue = currentValue;

        currentPrice = (previousPrice - Math.exp(time) < 0)? 0: previousPrice - Math.exp(time);
        currentPotentialRent = (previousPotentialRent + Math.log(time));
        currentCapitalizedRent = (previousCapitalizedRent - Math.log(time));
        currentValue = (previousValue - Math.exp(time) < 0)? 0: previousValue - Math.exp(time);



    }

    public String toString(){
        return id  + "," + neighborhood + "," + ownerRelation + "," + state +  "," + currentPrice +  "," + currentCapitalizedRent +  "," + currentPotentialRent +  "," + currentValue;
    }

}
