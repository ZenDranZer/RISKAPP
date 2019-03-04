package controllers;
import models.GameContinent;
import models.GameCountry;
import utils.GraphUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

/** This class hosts the data of countries and contients. It also hosts the method definitions with
 * which any map related operations are to be performed in the game.
 *
 */
public class MapGenerator {
    ArrayList<GameContinent> continentList;
   public static HashMap<String,GameCountry> countryHashMap;
   public static HashMap<String,GameContinent> continentHashMap;
    ArrayList<GameCountry> countryList;
    static HashMap <String,String>guiHashMap;
    GraphUtil graphUtilObject ;
    BufferedReader inputReader;

    /** Class constructor that initializes the ubiquitious countryHashMap and ContinentHashMap
     *
     */
    public MapGenerator() {
        countryHashMap = new HashMap<>();
        continentHashMap = new HashMap<>();
        continentList = new ArrayList<>();
        countryList = new ArrayList<>();
        guiHashMap = new HashMap<>();
    }

    /**Reads continents from a file and store them in required structure.
     *
     * @param inputReader
     * @return object of buffered reader to continue reading after continents are read.
     * @throws IOException
     */
    public String readContinentList(BufferedReader inputReader) throws IOException {
        try {
            String inputLine;

            inputLine = inputReader.readLine();
            while(inputLine.equals("")){
                inputLine = inputReader.readLine();
            }
            while (!inputLine.equals("[Territories]")) {
                GameContinent continent = new GameContinent();
                String name;
                try {
                    name = inputLine.substring(0, inputLine.indexOf("="));
                } catch (Exception e) {
                    return "INVALID MAP FORMAT";
                }
                int value = Integer.parseInt(inputLine.substring(inputLine.indexOf("=") + 1));
                if (continentHashMap.containsKey(name)) {


                    return "ONE OR MORE DUPLICATE CONTINENTS ENCOUNTERED.";
                }
                continent.setContinentName(name);
                continent.setContinentValue(value);
                continentHashMap.put(name, continent);
                //  continentList.add(continent);
                inputLine = inputReader.readLine();
            }


            return "SUCCESS";          //Return the current position of reader file so that countries can be loaded.
        }catch (IOException e){
            return null;
        }catch (NullPointerException e){
            return null;
        }
    }

    /**Reads countries from the file and stores them in the required structure.
     *
     * @param inputReader
     * @return object of buffered reader to continue reading after continents are read.
     * @throws IOException
     */
    public String readCountryList(BufferedReader inputReader) throws IOException {
        try{
        String inputLine;

        inputLine = inputReader.readLine();

        while(!inputLine.equals("")){

            String[] inpList = inputLine.split(",");
            GameCountry currentCountry;
            String continentName;

            if(countryExists(inpList[0])==null) {

                currentCountry = new GameCountry();
             /*   countryList.add(currentCountry);*/
                currentCountry.setCountryName(inpList[0]);
                countryHashMap.put(inpList[0],currentCountry);
            }
            else{
                currentCountry = countryExists(inpList[0]);
                if(currentCountry.getNeighbouringCountries().size()!=0){
                    String returnString =  "ONE OR MORE COUNTRIES ARE DUPLICATE";
                    return returnString;
                }
            }

            currentCountry.setCoordinateX(Integer.parseInt(inpList[1]));
            currentCountry.setCoordinateY(Integer.parseInt(inpList[2]));
            continentName = inpList[3];
/*
            for(GameContinent continent : continentList){
                if(continent.getContinentName().equals(continentName)){
                    currentCountry.setContinent(continent);
                    continent.setCountries(currentCountry);
                }
            }*/
                    //make a method to set continent for each country for testing purposes
            if(continentHashMap.containsKey(continentName)){
                currentCountry.setContinent(continentHashMap.get(continentName));
                continentHashMap.get(continentName).setCountries(currentCountry);
            }
            for(int iterator=4;iterator<inpList.length; iterator++){
                GameCountry neighborCountry;
                if(countryExists(inpList[iterator])==null){
                    neighborCountry = new GameCountry();
                 /*   countryList.add(neighborCountry);*/

                    neighborCountry.setCountryName(inpList[iterator]);
                    countryHashMap.put(inpList[iterator],neighborCountry);
                }
                else{
                    neighborCountry = countryExists(inpList[iterator]);
                }
                currentCountry.addNeighbouringCountry(neighborCountry);
            }
            inputLine = inputReader.readLine();
        }
        return "SUCCESS";
        }catch (IOException e){
            return "IOException";
        }catch (NullPointerException e){
            return "NullPointerException";
        }
    }

    /**Checks if a country already exist using the country name.
     *
     * @param countryname
     * @return null if the country does not exist and the country object if the country already exists.
     */
    public GameCountry countryExists(String  countryname){
        if(countryHashMap.containsKey(countryname.toUpperCase()) ){
            return countryHashMap.get(countryname);
        }
        return null;
    }
    public String getGuiParameters(BufferedReader inputReader){
        try {
            String inputLine = inputReader.readLine();
            inputLine = inputReader.readLine();

            System.out.println(inputLine);
            while (!inputLine.equals("")){
                String[] inpList = inputLine.split("=");
                guiHashMap.put(inpList[0],inpList[1]);
                inputLine = inputReader.readLine();
            }
            return "SUCCESS";
        }catch(Exception e){
            return "THE INITIAL PARAMETERS HAVE WRONG FORMAT";
        }
    }
    /**This method is called when a user wants to load the file from the system.
     *
     * @param filePath
     * @return status of the operation
     * @throws IOException
     */
    public String readConquestFile(String filePath) throws IOException {
        try {
            File inputMap = new File(filePath);
            String returnString;
            inputReader = new BufferedReader(new FileReader(inputMap));

            returnString=this.getGuiParameters(inputReader);
            if(!returnString.equals("SUCCESS")){
                return returnString;
            }


            String inputLine;
            int lineCounter = 6;
            while ((inputLine = inputReader.readLine()) != null) {
            /*[Map]  //check if the first line equals [map]
                image=world.bmp
                wrap=yes
                scroll=horizontal           Read the first six lines here, increment the line counter and then continue.
                author=Your name
                warn=yes*/
                if (lineCounter == 6) {
                    if (inputLine.equals("[Continents]")) {
                        returnString= this.readContinentList(inputReader);//reader is returned as we need to have a single reader reading different parts of the map file.
                        if (!returnString.equals("SUCCESS")) {
                            return "ONE OR MORE DUPLICATE CONTINENTS ENCOUNTERED";
                        }
                    } else {

                        return "THE MAP FORMAT IS WRONG";
                    }
                    lineCounter++;
                }
                returnString = this.readCountryList(inputReader);

              if (!returnString.equals("SUCCESS")){
                   return returnString;
                 }
                lineCounter++;
            }

            return "SUCCESS";
        }catch (FileNotFoundException e){
            return "THE FILE NOT FOUND";
        }catch (IOException e){
            return "ERROR IN READING THE SPECIFIED FILE";
        }
    }

    /**Writes the map into a text file following the Conquest File Format.
     *
     * @return status of the operation
     */
    public String writeConquestFile(){

        // this class creates a conquest File from existing map - we read contries and continents from global arraylists

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("ConquestMap.txt"), "utf-8"));
            writer.write("[map]");
            ((BufferedWriter) writer).newLine();
            //here we most add information regarding GUI
            writer.write("[Continents]");
            ((BufferedWriter) writer).newLine();
            for (GameContinent continent: continentHashMap.values()) { //check if for each works with collections or not
                writer.write(continent.getContinentName() + "=" + continent.getContinentValue());
            }
            ((BufferedWriter) writer).newLine();
            writer.write("[Territories]");
            ((BufferedWriter) writer).newLine();
            for (GameCountry country : countryHashMap.values()) {
                String neighbours = "";
                for (GameCountry neighbour: country.getNeighbouringCountries().values()) {
                    neighbours += ","+neighbour.getCountryName();
                }
                writer.write(country.getCountryName()+","+country.getCoordinateX()+","+country.getCoordinateY()+","
                        + neighbours);
            }
        } catch (IOException ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
        return "SUCCESS";

    }

    public String GenerateMap(){
        return null;
    }

   /* public void getCountriesFromUser(){
        for( GameContinent continent : continentList){
            System.out.println("Enter the countries for  "+ continent.getContinentName());
            getContriesForEachContinent(continent);
        }
    }*/

    /**
     * in the GUI the view can directly call this method after appropriate options are selected by the user
     *
     */
 /*   public void getContriesForEachContinent(GameContinent continent){

        int numberOfCountries = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("How many countries do you want to add? ");
        numberOfCountries = in.nextInt();
        for(int i = 0 ; i< numberOfCountries ; i++){
            GameCountry newCountry;
            System.out.println("Enter Name of "+i+"th Country: ");
            String countryName = in.next();
            if (countryExists(countryName) == null){
                 newCountry= new GameCountry();
                newCountry.setCountryName(countryName);
            }
            else{
              newCountry = countryExists(countryName);
            }
            newCountry.setContinent(continent);
            System.out.println("Enter Neighbors or enter 0 to exit\n");
            while (!in.next().equals("0")){
                System.out.println("Enter Name of Neighbor:");
                String neighborName = in.next();
                if (countryExists(neighborName) == null) {
                    GameCountry newNeighbor = new GameCountry();
                    newNeighbor.setCountryName(in.next());
                    newCountry.addNeighbouringCountry(newNeighbor);
                }else {
                    newCountry.addNeighbouringCountry(countryExists(neighborName));
                }

            }
            countryList.add(newCountry);
        }


    }*/

    /**Method builds a graph from the list of continents and country data acquired.
     *
     * @return returns the graph.
     */
    public GraphUtil buildGraph(){
        try {

            GraphUtil graphUtilObject = new GraphUtil();
            graphUtilObject.setCountryGraph(countryHashMap);

            return graphUtilObject;
        }catch(Exception e){
            return null;
        }
    }
    public GraphUtil getGraphUtilObject(){
        return graphUtilObject;
    }
    public void editMap(){

    }

    /**This method adds a continent in the map.
     *
     * @param continentName
     * @param continentValue
     * @return returns the status of the method execution
     */
    public String addContinent(String continentName, int continentValue){
        try {
            if (!continentHashMap.containsKey(continentName)) {
                GameContinent newContinent = new GameContinent();
                newContinent.setContinentName(continentName);
                newContinent.setContinentValue(continentValue);
                continentHashMap.put(continentName,newContinent);
                return "CONTINENT ADDED SUCCESSFULLY";
            }
            return "THE CONTINENT ALREADY EXISTS";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
        }
    }

    /**This method adds a country to the map.
     *
     * @param continentname
     * @param countryName
     * @param neighbours
     * @return returns the status of the method execution
     */
    public String addCountry(String continentname, String countryName, ArrayList<String> neighbours){
        String returnString;
        try {
            if ((!countryHashMap.containsKey(countryName)) || (countryHashMap.get(countryName).getNeighbouringCountries().size()==0)) {
                GameCountry newCountry = new GameCountry();
                newCountry.setContinent(continentHashMap.get(continentname));
                newCountry.setCountryName(countryName);
                continentHashMap.get(continentname).setCountries(newCountry);

                for (String tempNeighbourName : neighbours) {
                    GameCountry newNeighbour;
                    if (countryExists(tempNeighbourName) == null) {
                        newNeighbour = new GameCountry();
                        newNeighbour.setCountryName(tempNeighbourName);
                        countryHashMap.put(tempNeighbourName,newNeighbour);
                        newCountry.addNeighbouringCountry(newNeighbour);

                        countryHashMap.put(tempNeighbourName,newNeighbour);
                        System.out.println(countryHashMap.keySet());
                    } else

                    newCountry.setNeighbouringCountries(countryExists(tempNeighbourName));

                }
                countryHashMap.put(countryName,newCountry);
                returnString = "SUCCESS";


                return returnString;
            }


            return "THE COUNTRY ALREADY EXISTS";
        }catch(NullPointerException e){
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
        }
    }

    public String validateMap(){
        MapValidator validator = new MapValidator();
        String returnString = "SUCCESS";

        for(GameCountry country : countryHashMap.values()){

            if(!validator.hasNeighbor(country)){
                returnString = "ONE OF THE COUNTRY HAS NO NEIGHBOURS";
                break;
            }

            if(!validator.hasValidNumberOfNeighbors(country)){
                returnString = "INVALID NUMBER OF NEIGHBOURS";
                break;
            }
        }

        if(!validator.hasValidNumberOfContinents(new ArrayList<>(continentHashMap.values()))){
            returnString =  "NUMBER OF CONTINENTS IS NOT VALID";
        }
        if(!validator.hasValidNumberOfCountries(new ArrayList<>(countryHashMap.values()))){
            returnString = "INVALID NUMBER OF COUNTRIES";
        }

        graphUtilObject = this.buildGraph();
        if(!new MapValidator().isWholeMapConnected(graphUtilObject,new ArrayList<>(countryHashMap.values()))){
            returnString = "THE WHOLE MAP IS NOT CONNECTED";
        }

        return returnString;
    }

    /**Method gives a list of Continents of the map.
     *
     * @return arraylist of objects of each continent
     */
    public static ArrayList<String> getListOfContinents(){
        return  new ArrayList<>(continentHashMap.keySet());

    }



    /**Method gets the list of countries of the map.
     *
     * @return arraylist of objects of each country
     */
    public static ArrayList<String> getListOfCountries(){
        ArrayList<String> countries = new ArrayList<>(countryHashMap.keySet());
        return countries;
    }

    /**This method removes a neighbour as per the player request.
     *
     * @param countryName
     * @param neighborName
     * @return status of the method execcution
     */
    public String removeNeighbor(String countryName , String neighborName) {
        try {

            for (GameCountry neighbor :  countryHashMap.get(countryName).getNeighbouringCountries().values()) {
                if (neighbor.getCountryName().equals(neighborName)) {
                    countryHashMap.get(countryName).getNeighbouringCountries().remove(neighborName);
                }
            }
            //  countryHashMap.get(countryName).setNeighbouringCountries(neighbors); This is not needed.
            return "SUCCESS";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method adds a neighbour for a specified country on user demand.
     *
     * @param countryName
     * @param neighborName
     * @return status of the method execcution
     */
    public String addNeighbor(String countryName , String neighborName){
        try {
            HashMap <String,GameCountry> neighbors = countryHashMap.get(countryName).getNeighbouringCountries();
            if (countryHashMap.containsKey(neighborName)) {
                neighbors.put(neighborName,countryHashMap.get(neighborName));
            } else {
                return "NEIGHBOR DOES NOT EXIST";
            }
        /*for(GameCountry neighbor : neighbors){ This should not be needed.
            countryHashMap.get(neighborName).setNeighbouringCountries(neighbor);
        }*/
            return "SUCCESS";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method changes the continent of a specified country on user demand.
     *
     * @param countryName
     * @param continentName
     * @return status of the method execcution
     */
    public String changeCountryContinent(String countryName, String continentName){
        try {
            String oldContinentName = countryHashMap.get(countryName).getContinent().getContinentName();
            continentHashMap.get(oldContinentName).getCountries().remove(countryHashMap.get(countryName));
            if (continentHashMap.containsKey(continentName)) {
                countryHashMap.get(countryName).setContinent(continentHashMap.get(continentName));
                continentHashMap.get(continentName).setCountries(countryHashMap.get(countryName));
            } else {
                return "CONTINENT DOES NOT EXIST";
            }

            return "SUCCESS";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /** This method changes the name of a specified country on user demand.
     *
     * @param oldName
     * @param newName
     * @return status of the method execcution
     */
    public String changeCountryName(String oldName , String newName){
        try {
            if (countryHashMap.containsKey(oldName)) {
                countryHashMap.get(oldName).setCountryName(newName);
                countryHashMap.put(newName,countryHashMap.get(oldName));
                countryHashMap.remove(oldName);
            } else {
                return "COUNTRY DOES NOT EXIST";
            }

            return "SUCCESS";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method removes a whole country from the database on user demand.
     *
     * @param countryName
     * @return status of the method execcution
     */
    public String removeCountry(String countryName){
        try {
            String returnString;
            if (countryHashMap.containsKey(countryName)) {
                countryHashMap.get(countryName).getContinent().   //Removes the country from its continent's countrylist.
                        getCountries().remove(countryHashMap.get(countryName));
                returnString = validateContinent(countryHashMap.get(countryName).getContinent());
                if (returnString.equals("EMPTY")) {
                    this.removeContinent(countryHashMap.get(countryName).getContinent().getContinentName());
                }
                countryHashMap.remove(countryName);
                returnString = "SUCCESS";

            } else {
                return "COUNTRY DOES NOT EXIST";
            }
            return returnString;
        }catch (NullPointerException e){
                return "EXCEPTION IN ACCESSING DATA";
            }
    }

    /**This method validates if the continent has any country or not.
     *
     * @param continent
     * @return returns the appropriate signal
     */
    public String validateContinent(GameContinent continent){
        try {
            if (continent.getCountries().size() == 0) {
                return "EMPTY";
            }
            return "ALL GOOD";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method removes a continent from database when all countries from it are deleted by the user.
     *
     * @param continentName
     * @return status of the method execcution
     */
   public String removeContinent(String continentName){
       try {
           if (continentHashMap.containsKey(continentName)) {
               continentHashMap.remove(continentName);
               return "SUCCESS";
           } else {
               return "NO SUCH CONTINENT";
           }
       }catch(Exception e){
           return "CONTINENT NOT FOUND";
       }
    }

    /**This method changes the name of the specified continent.
     *
     * @param continentName
     * @param newContinentName
     * @return status of the method execcution
     */
    public String changeContinentName(String continentName, String newContinentName){
        try {
            if (continentHashMap.containsKey(continentName)) {
                continentHashMap.get(continentName).setContinentName(newContinentName);
                continentHashMap.put(newContinentName,continentHashMap.get(continentName));
                continentHashMap.remove(continentName);

                return "SUCCESS";
            } else {
                return "CONTINENT DOES NOT EXIST";
            }
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method changes the value of the continent.
     *
     * @param continentName
     * @param continentValue
     * @return status of the method execcution
     */
    public String changeContinentValue(String continentName, int continentValue){
        try {
            if (continentHashMap.containsKey(continentName)) {
                continentHashMap.get(continentName).setContinentValue(continentValue);
                return "SUCCESS";
            } else {
                return "CONTINENT DOES NOT EXIST";
            }
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }
    /*public static void main(String []args) throws Exception{
        MapGenerator mp = new MapGenerator();
        String op = mp.readConquestFile("C:\\Users\\shiva\\Desktop\\ABC_Map.map");
        System.out.println(op);
        System.out.println(MapGenerator.continentHashMap.keySet());
        System.out.println(MapGenerator.countryHashMap.keySet().size());

    }*/

    /***
     * This Method checks for all continents owned by a player
     * @param playerID
     * @return ArrayList<GameContinent> continentsOwnedByPlayer
     */
    public ArrayList<GameContinent> checkContinentsOwnedByOnePlayer(String playerID){
        try {
            ArrayList<GameContinent> continentsOwnedByPlayer = new ArrayList<>();
            for(String continentName : continentHashMap.keySet()){
                for (GameCountry country: continentHashMap.get(continentName).getCountries().values() ) {
                    if (!country.getCurrentPlayer().equals(playerID)){
                        break;
                    }
                }
                continentsOwnedByPlayer.add(continentHashMap.get(continentName));
            }

            if (!continentsOwnedByPlayer.isEmpty()){
                return continentsOwnedByPlayer;
            }
        }catch (Exception e){
            System.err.println("SOME ERROR IN CONTINENTOWNEDBYPLAYER");
        }
        return null;
    }


    public String reSetAllocations(){
        try {
            graphUtilObject.clearGraph();
            for (String country: countryHashMap.keySet()) {
                countryHashMap.remove(country);
            }
            for (String continent:continentHashMap.keySet()) {
                continentHashMap.remove(continent);
            }
            return "SUCCESS";
        }catch (Exception e){
            return "SOME ERROR IN RESETTING ALLOCATIONS";
        }
    }

}
