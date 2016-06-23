package kobdig.ESPACE;

/**
 * Created by Meili on 21/06/2016.
 */
public class Family {

    // ATRIBUTES

    /**
     * Unique ID for the family
     */
    private String id;

    /**
     * Family's lastname
     */
    private String lastname;

    /**
     * ID of the family's property
     */
    private String property;

    /**
     * ID of the family's rented property
     */
    private String rentedProperty;

    /**
     * Purchasing power to buy home
     */
    private double previousPurchasingPower;

    /**
     * Net monthly income to pay rent
     */
    private double previousNetMonthlyIncome;

    /**
     * Purchasing power to buy home
     */
    private double currentPurchasingPower;

    /**
     * Net monthly income to pay rent
     */
    private double currentNetMonthlyIncome;

    // CONSTRUCTOR

    public Family(String id, String lastname, double purchasingPower, double netMonthlyIncome) {
        this.id = id;
        this.property = null;
        this.rentedProperty = null;
        this.lastname = lastname;
        this.previousPurchasingPower = purchasingPower;
        this.previousNetMonthlyIncome = netMonthlyIncome;
        this.currentPurchasingPower = purchasingPower;
        this.currentNetMonthlyIncome = netMonthlyIncome;
    }

    // GETTERS AND SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRentedProperty() {
        return rentedProperty;
    }

    public void setRentedProperty(String rentedProperty) {
        this.rentedProperty = rentedProperty;
    }

    public double getPreviousPurchasingPower() {
        return previousPurchasingPower;
    }

    public void setPreviousPurchasingPower(double previousPurchasingPower) {
        this.previousPurchasingPower = previousPurchasingPower;
    }

    public double getPreviousNetMonthlyIncome() {
        return previousNetMonthlyIncome;
    }

    public void setPreviousNetMonthlyIncome(double previousNetMonthlyIncome) {
        this.previousNetMonthlyIncome = previousNetMonthlyIncome;
    }

    public double getCurrentPurchasingPower() {
        return currentPurchasingPower;
    }

    public void setCurrentPurchasingPower(double currentPurchasingPower) {
        this.currentPurchasingPower = currentPurchasingPower;
    }

    public double getCurrentNetMonthlyIncome() {
        return currentNetMonthlyIncome;
    }

    public void setCurrentNetMonthlyIncome(double currentNetMonthlyIncome) {
        this.currentNetMonthlyIncome = currentNetMonthlyIncome;
    }

// METHODS

    /**
     * Returns YES if the family is landlord, otherwise, returns NO
     * @return YES if the family is landlord, otherwise, returns NO
     */
    public String hasProperty(){
        if (property != null) return "YES";
        return "NO";
    }

    /**
     * Returns YES if the family is renting, otherwise, returns NO
     * @return YES if the family is renting, otherwise, returns NO
     */
    public String isRenting(){
        if (rentedProperty != null) return "YES";
        return "NO";
    }

    /**
     * Generates a step in the simulation
     * @param time The time in the simulation
     */
    public void step(int time){
        previousPurchasingPower = currentPurchasingPower;
        previousNetMonthlyIncome = currentNetMonthlyIncome;
        currentPurchasingPower = previousPurchasingPower + (previousPurchasingPower*Math.log(time + 1))/20;
        currentNetMonthlyIncome = previousNetMonthlyIncome + (previousPurchasingPower*(0.001));
    }


    public String toString(){
        if (property != null)
            return lastname  + "," + hasProperty() + "," + property + "," + isRenting() + ",," + currentPurchasingPower  + "," + currentNetMonthlyIncome;
        if (rentedProperty != null)
            return lastname  + "," + hasProperty() + ",," + isRenting() + "," + rentedProperty + "," + currentPurchasingPower  + "," + currentNetMonthlyIncome;
        else
            return lastname  + "," + hasProperty() + ",,"  + isRenting() + ",," + currentPurchasingPower  + "," + currentNetMonthlyIncome;
    }
}
