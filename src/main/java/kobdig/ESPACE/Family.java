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
     * Purchasing power to buy home
     */
    private double purchasingPower;

    /**
     * Net monthly income to pay rent
     */
    private double netMonthlyIncome;

    // CONSTRUCTOR

    public Family(String id, String lastname, double purchasingPower, double netMonthlyIncome) {
        this.id = id;
        this.purchasingPower = purchasingPower;
        this.netMonthlyIncome = netMonthlyIncome;
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

    public double getPurchasingPower() {
        return purchasingPower;
    }

    public void setPurchasingPower(double purchasingPower) {
        this.purchasingPower = purchasingPower;
    }

    public double getNetMonthlyIncome() {
        return netMonthlyIncome;
    }

    public void setNetMonthlyIncome(double netMonthlyIncome) {
        this.netMonthlyIncome = netMonthlyIncome;
    }

    // METHODS

    public void step(int time){

    }

    public String toString(){
        return id + "," + lastname  + "," + purchasingPower  + "," + netMonthlyIncome;
    }
}
