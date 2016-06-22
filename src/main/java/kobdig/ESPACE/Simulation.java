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
     * Random Number Generator.
     */
    protected static Random rnd = new Random();

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
     * @param name The name of the neighborhood
     * @return The neighborhood, null if not found
     */
    public Neighborhood getNeighborhoodFromName(String name){
        boolean found = false;
        Neighborhood neighborhood = null;
        Neighborhood current = null;
        int i = 0;
        while ((current = neighborhoods.get(i++)) != null && !found){
            if (current.getName().equals(name)){
                found = true;
                neighborhood = current;
            }
        }
        return neighborhood;
    }

    /**
     * Instantiates all the neighborhoods in the file
     * @param file The neighborhood.csv file
     * @throws FileNotFoundException If the file isn't found
     */
    public static void createNeighborhoods(File file) throws FileNotFoundException {
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
    }

    /**
     * Instantiates all the properties in the file
     * @param file The property.csv file
     * @throws FileNotFoundException If the file is not found
     */
    public static void createProperties(File file) throws FileNotFoundException {
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
                Property property = new Property(Integer.toString(id++),neighborhood, price, rent, value);
                properties.add(property);
            }
        } catch (IOException e) {
            System.out.println("Simulation.createProperties");
            e.printStackTrace();
        }
    }

    /**
     * Instantiates all the families in the file
     * @param file The family.csv file
     * @throws FileNotFoundException If the file is not found
     */
    public static void createFamilies(File file) throws FileNotFoundException {
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
    }

    public static void main(String[] args){
        System.out.println("Testing the ESPACE Simulator...");
        neighborhoods = new ArrayList<>();
        properties = new ArrayList<>();
        families = new ArrayList<>();

        // Get initializer files

        System.out.println("Please enter the path to the neighborhood.csv file:");
        Scanner scan = new Scanner(System.in);

        File neighborhood = new File(scan.next());

        System.out.println("Please enter the path to the property.csv file:");
        scan = new Scanner(System.in);

        File property = new File(scan.next());

        System.out.println("Please enter the path to the family.csv file:");
        scan = new Scanner(System.in);

        File family = new File(scan.next());

        System.out.println("Please enter the path to the familyAgent.apl file:");
        scan = new Scanner(System.in);

        File familyAgent = new File(scan.next());

        System.out.println("Please enter the number of simulations you want to generate:");
        scan = new Scanner(System.in);

        numSim = scan.nextInt();

        // Creates the report files

        BufferedWriter neigWriter = null;
        BufferedWriter propWriter = null;
        BufferedWriter famWriter = null;

        try {
            neigWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/kobdig/docs/neigoborhoodsReport.csv")));
            propWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/kobdig/docs/neigoborhoodsReport.csv")));
            famWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Meili/Desktop/ESPACE/kobdig-master/src/main/java/kobdig/docs/neigoborhoodsReport.csv")));
        } catch (FileNotFoundException e) {
            System.out.println("Simulation.main: Create reports");
            e.printStackTrace();
        }

        // Instantiates all the classes

        try {
            createNeighborhoods(neighborhood);
        } catch (FileNotFoundException e) {
            System.out.println("Simulation.main: Create neighborhoods");
            e.printStackTrace();
        }

        try {
            createProperties(property);
        } catch (FileNotFoundException e) {
            System.out.println("Simulation.main: Create properties");
            e.printStackTrace();
        }

        try {
            createFamilies(family);
        } catch (FileNotFoundException e) {
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

        // Runs the simulation

        try {

            neigWriter.write("TIME,NAME,STATUS\n");
            propWriter.write("TIME,ID,NEIGHBORHOOD,OWNER_RELATION,STATE,PRICE,CAPITALIZED_RENT,POTENTIAL_RENT,VALUE\n");
            famWriter.write("TIME,LASTNAME,PURCHASING_POWER,NET_MONTHLY_INCOME\n");

            for(time = 0; time < numSim; time++){
                System.out.println("Neighborhoods report");

                for(int i = 0; i < neighborhoods.size(); i++){
                    Neighborhood current = neighborhoods.get(i);
                    neigWriter.write(time + "," + current.toString() + "\n");
                    current.step(time);
                }

                System.out.println("Properties report");

                for(int i = 0; i < properties.size(); i++){
                    Property current = properties.get(i);
                    propWriter.write(time + "," + current.toString() + "\n");
                    current.step(time);
                }

                System.out.println("Families report");

                for(int i = 0; i < families.size(); i++){
                    Family current = families.get(i);
                    famWriter.write(time + "," + current.toString() + "\n");
                    current.step(time);
                }
            }
        } catch (IOException e) {
            System.out.println("Simulation.main: Writing files");
            e.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Simulation.main: Interruption");
            e.printStackTrace();
        }

    }

}
