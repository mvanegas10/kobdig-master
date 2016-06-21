package kobdig.ESPACE;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
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
     * Creates all the neighborhoods
     * @param file The file with the neighborhoods data
     * @throws FileNotFoundException If the file isn't found
     */
    public static void createNeighborhoods(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                // TODO: Complete with attributes
                Neighborhood neighborhood = new Neighborhood(name);
                neighborhoods.add(neighborhood);
            }
        } catch (IOException e) {
            System.out.println("Simulation.createNeighborhoods");
            e.printStackTrace();
        }
    }

    /**
     * Creates all the properties
     * @param file The file with the properties data
     * @throws FileNotFoundException If the file isn't found
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

    public static void createFamilies(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String neighborhood = data[0];
                Family family = new Family();
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

        File neighborhood = new File("");
        File property = new File("");
        File family = new File("");

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
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Simulation.main: Interruption");
            e.printStackTrace();
        }

    }

}
