package kobdig.ESPACE;

import kobdig.agent.Agent;
import kobdig.gui.AgentView;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by Meili on 20/06/16.
 */
public class Simulation {

    /**
     * Temporally income gap
     */
    public static final double INCOME_GAP = 0.3;

    /**
     * The properties involved in the simulation
     */
    protected static ArrayList<Property> properties;

    /**
     * The neighborhoods involved in the simulation
     */
    protected static ArrayList<Neighborhood> neighborhoods;

    /**
     * The family agents involved in the simulation
     */
    protected static ArrayList<Family> families;

    /**
     * The time reference in the simulation
     */
    protected static int time;

    /**
     * The number of simulations to generate
     */
    protected static int numSim;

    /**
     * Searches a neighborhood by name
     *
     * @param name The name of the neighborhood
     * @return The neighborhood, null if not found
     */
    public static Neighborhood getNeighborhoodFromName(String name) {
        Neighborhood neighborhood = null;
        int i = 0;
        while (i < neighborhoods.size() && (neighborhood = neighborhoods.get(i++)) != null) {
            if (neighborhood.getName().equals(name)) {
                return neighborhood;
            }
        }
        return null;
    }

    /**
     * Searches property by Id
     *
     * @param id The id of the property
     * @return The property, null if not found
     */
    public static Property getPropertyFromId(String id) {
        Property property = null;
        int i = 0;
        while (i < properties.size() && (property = properties.get(i++)) != null) {
            if (property.getId().equals(id)) {
                return property;
            }
        }
        return null;
    }

    /**
     * Generates a neighborhood step in the simulation
     *
     * @param neighborhood The neighborhood
     */
    public static void neighborhoodStep(Neighborhood neighborhood) {
        int count = 0;
        double sum = 0.0;
        for (int i = 0; i < properties.size(); i++) {
            Property current = properties.get(i);
            if (neighborhood.getName().equals(current.getNeighborhood())) {
                sum += current.getCurrentValue() - current.getPreviousValue();
                count++;
            }
        }
        double average = sum / count;
        double newStatus = neighborhood.getStatus() + 2 * average + Math.random() * Neighborhood.STATUS_VARIABILITY;
        newStatus = (newStatus < 0)? 0.0: newStatus;
        neighborhood.setStatus(newStatus);
    }

    /**
     * Generates a family step in the simulation
     *
     * @param family The family
     */
    public static void familyStep(Family family, int time) {
        double rnd1 = Math.random();

        Property property = getPropertyFromId(family.getProperty());
        Neighborhood neighborhood = (property != null) ? getNeighborhoodFromName(property.getNeighborhood()) : null;

        Property rentedProperty = getPropertyFromId(family.getRentedProperty());
        Neighborhood rentedNeighborhood = (rentedProperty != null) ? getNeighborhoodFromName(rentedProperty.getNeighborhood()) : null;

        if (property != null && neighborhood != null) {

            // The family has a property in OWNER OCCUPIED state

            if (property.getOwnerRelation().equals(Property.OWNER_OCCUPIED)){

                // The family has a property in OWNER OCCUPIED state and the property is NOT FOR SALE

                if (property.getState().equals(Property.OWNER_OCCUPIED_STATES[0])) {
                    double probability = ((1.5 - neighborhood.getStatus() + INCOME_GAP) < 1) ? 1.5 - neighborhood.getStatus() + INCOME_GAP : 0.05;
                    if (rnd1 <= probability) {
                        property.setState(Property.OWNER_OCCUPIED_STATES[1]);
                    }
                }

                // The family has a property in OWNER OCCUPIED state and the property is FOR SALE

                else if (property.getState().equals(Property.OWNER_OCCUPIED_STATES[1])) {
                    // TODO: Implement the transition from FOR SALE in OWNER OCCUPIED
                }

            }

            // The family has a property in LANDLORD state

            else if (property.getOwnerRelation().equals(Property.LANDLORD)){

                // The family has a property in LANDLORD state and the property is FOR SALE
                if (property.getState().equals(Property.LANDLORD_STATES[0])){
                    // TODO: Implement the transition from FOR SALE in LANDOWNER
                }
                // The family has a property in LANDLORD state and the property is SEEKING TENANT
                if (property.getState().equals(Property.LANDLORD_STATES[1])){
                    // TODO: Implement the transition from SEEKING TENANT in LANDOWNER
                }
                // The family has a property in LANDLORD state and the property is RENTED
                if (property.getState().equals(Property.LANDLORD_STATES[2])){
                    // TODO: Implement the transition from RENTED in LANDOWNER
                }

            }
        }

        // The family is renting home

        if (rentedProperty != null && rentedNeighborhood != null) {
            // TODO: Implement the transition for a renting family
        }

        // The family is searching for home

        if (property == null && rentedProperty == null) {
            Property current = null;
            Property best = null;
            Neighborhood ngbhdBest = null;
            Neighborhood ngbhdCurrent = null;

            for (int i = 0; i < properties.size(); i++) {
                current = properties.get(i);
                if (current.getCurrentPrice() <= family.getCurrentPurchasingPower() && current.getState().equals(Property.LANDLORD_STATES[0])) {
                    ngbhdCurrent = getNeighborhoodFromName(current.getNeighborhood());
                    if (best != null) {
                        if (ngbhdBest.getStatus() < ngbhdCurrent.getStatus()) {
                            best = current;
                            ngbhdBest = ngbhdCurrent;
                        }
                    } else {
                        best = current;
                        ngbhdBest = ngbhdCurrent;
                    }
                }
            }

            // The family can buy home

            if (best != null) {
                family.setProperty(best.getId());
                family.setCurrentPurchasingPower(family.getCurrentPurchasingPower() - best.getCurrentPrice());
                best.setOwnerRelation(Property.OWNER_OCCUPIED);
                best.setState(Property.OWNER_OCCUPIED_STATES[0]);
            }

            // The family can't buy and needs to rent

            else {
                current = null;
                best = null;
                ngbhdBest = null;
                ngbhdCurrent = null;

                for (int i = 0; i < properties.size(); i++) {
                    current = properties.get(i);
                    if (current.getCurrentCapitalizedRent() <= family.getCurrentNetMonthlyIncome() && (current.getState().equals(Property.LANDLORD_STATES[0]) || current.getState().equals(Property.LANDLORD_STATES[1]))) {
                        ngbhdCurrent = getNeighborhoodFromName(current.getNeighborhood());
                        if (best != null) {
                            if (ngbhdBest.getStatus() < ngbhdCurrent.getStatus()) {
                                best = current;
                                ngbhdBest = ngbhdCurrent;
                            }
                        } else {
                            best = current;
                            ngbhdBest = ngbhdCurrent;
                        }
                    }
                }

                // The family can rent home

                if (best != null) {
                    best.setState(Property.LANDLORD_STATES[2]);
                    family.setCurrentNetMonthlyIncome(family.getCurrentNetMonthlyIncome() - best.getCurrentCapitalizedRent());
                    family.setRentedProperty(best.getId());
                }

                // The family can't rent

                else
                    System.out.println("The family " + family.getLastname() + " in the simulation time " + time + " couldn't find a home (rent/buy) option.");

            }
        }

        // Generates a family step updating net monthly income and purchasing power

        family.step(time);
    }

    /**
     * Instantiates all the neighborhoods in the file
     *
     * @param file The neighborhood.csv file
     * @throws IOException If error I/O Error
     */
    public static void createNeighborhoods(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                double status = Double.parseDouble(data[1]);
                // TODO: Complete with attributes
                Neighborhood neighborhood = new Neighborhood(name, status);
                neighborhoods.add(neighborhood);
            }
        } catch (IOException e) {
            System.out.println("Simulation.createNeighborhoods");
            e.printStackTrace();
        }
        reader.close();
    }

    /**
     * Instantiates all the properties in the file
     *
     * @param file The property.csv file
     * @throws IOException If error I/O Error
     */
    public static void createProperties(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        try {
            int id = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String neighborhood = data[0];
                double price = Double.parseDouble(data[1]);
                double rent = Double.parseDouble(data[2]);
                double value = Double.parseDouble(data[3]);
                Property property = new Property(Integer.toString(id++), neighborhood, price, rent, value);
                properties.add(property);
            }
        } catch (IOException e) {
            System.out.println("Simulation.createProperties");
            e.printStackTrace();
        }
        reader.close();

    }

    /**
     * Instantiates all the families in the file
     *
     * @param file The family.csv file
     * @throws IOException If error I/O Error
     */
    public static void createFamilies(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        try {
            int id = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String lastname = data[0];
                double purchasingPower = Double.parseDouble(data[1]);
                double netMonthlyIncome = Double.parseDouble(data[2]);
                Family family = new Family(Integer.toString(id++), lastname, purchasingPower, netMonthlyIncome);
                families.add(family);
            }
        } catch (IOException e) {
            System.out.println("Simulation.createFamilies");
            e.printStackTrace();
        }
        reader.close();
    }

    public static void main(String[] args) {
        System.out.println("Testing the ESPACE Simulator...");
        neighborhoods = new ArrayList<>();
        properties = new ArrayList<>();
        families = new ArrayList<>();

        // Get initializer files

//        System.out.println("Please enter the path to the neighborhood.csv file:");
//        Scanner scan = new Scanner(System.in);
//
//        File neighborhood = new File(scan.next());
//
//        System.out.println("Please enter the path to the property.csv file:");
//        scan = new Scanner(System.in);
//
//        File property = new File(scan.next());
//
//        System.out.println("Please enter the path to the family.csv file:");
//        scan = new Scanner(System.in);
//
//        File family = new File(scan.next());
//
//        System.out.println("Please enter the path to the familyAgent.apl file:");
//        scan = new Scanner(System.in);
//
//        File familyAgent = new File(scan.next());
//
//        System.out.println("Please enter the number of simulations you want to generate:");
//        scan = new Scanner(System.in);
//
//        numSim = scan.nextInt();


        File neighborhood = new File("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/neighborhood.csv");

        File property = new File("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/property.csv");

        File family = new File("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/family.csv");

        File familyAgent = new File("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/familyAgent.apl");

        numSim = 50;

        // Instantiates all the classes

        try {
            createNeighborhoods(neighborhood);
        } catch (IOException e) {
            System.out.println("Simulation.main: Create neighborhoods");
            e.printStackTrace();
        }

        try {
            createProperties(property);
        } catch (IOException e) {
            System.out.println("Simulation.main: Create properties");
            e.printStackTrace();
        }

        try {
            createFamilies(family);
        } catch (IOException e) {
            System.out.println("Simulation.main: Create families");
            e.printStackTrace();
        }

        try {
            Agent agent = new Agent(new FileInputStream(familyAgent));
            AgentView agentView = new AgentView(agent);
//            agentView.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creates the report files

        BufferedWriter neigWriter = null;
        BufferedWriter propWriter = null;
        BufferedWriter famWriter = null;

        try {
            neigWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/neighborhoodsReport.csv")));
            propWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/propertiesReport.csv")));
            famWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/docs/familiesReport.csv")));
        } catch (FileNotFoundException e) {
            System.out.println("Simulation.main: Create reports");
            e.printStackTrace();
        }

        // Runs the simulation

        try {

            neigWriter.write("TIME,NAME,STATUS\n");
            propWriter.write("TIME,ID,NEIGHBORHOOD,OWNER_RELATION,STATE,PRICE,CAPITALIZED_RENT,POTENTIAL_RENT,VALUE\n");
            famWriter.write("TIME,LASTNAME,HAS_PROPERTY,PROPERTY,IS_RENTING,RENTED_PROPERTY,PURCHASING_POWER,NET_MONTHLY_INCOME\n");

            for (time = 0; time < numSim; time++) {

                for (int i = 0; i < properties.size(); i++) {
                    Property current = properties.get(i);
                    propWriter.write(time + "," + current.toString() + "\n");
                    current.step(time);
                }

                for (int i = 0; i < neighborhoods.size(); i++) {
                    Neighborhood current = neighborhoods.get(i);
                    neigWriter.write(time + "," + current.toString() + "\n");
                    neighborhoodStep(current);
                }

                for (int i = 0; i < families.size(); i++) {
                    Family current = families.get(i);
                    famWriter.write(time + "," + current.toString() + "\n");
                    familyStep(current, time);
                }

            }
        } catch (IOException e) {
            System.out.println("Simulation.main: Writing files");
            e.printStackTrace();
        }

        try {
            neigWriter.close();
            propWriter.close();
            famWriter.close();
        } catch (IOException e) {
            System.out.println("Simulation.main: Clossing writers");
            e.printStackTrace();
        }

        System.out.println("Simulation ended");

        System.exit(0);

    }
}
