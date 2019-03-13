package controllers;
import models.GameContinent;
import models.GameCountry;
import utils.GraphUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/** This class hosts the data of countries and contients. It also hosts the method definitions with
 * which any map related operations are to be performed in the game.
 *
 */
public class MapGenerator {

    public static HashMap<String,GameCountry> countryHashMap = new HashMap<>();
    public static HashMap<String,GameContinent> continentHashMap = new HashMap<>();
    static HashMap <String,String>guiHashMap;
    GraphUtil graphUtilObject ;
    BufferedReader inputReader;
    boolean firstCountryFlag;
    MapValidator validator;
    /** Class constructor that initializes the ubiquitious countryHashMap and ContinentHashMap
     *
     */
    public MapGenerator() {


        guiHashMap = new HashMap<>();
        setGuiHashMap();
        firstCountryFlag=true;
        validator = new MapValidator();
    }

    /**This method sets the default initial parameters according to the conquest map format.
     *
     */
    public static void setGuiHashMap() {
        guiHashMap.put("author","Default authorName");
        guiHashMap.put("warn","Default warning");
        guiHashMap.put("image","Default image");
        guiHashMap.put("wrap","Default wrapping");
        guiHashMap.put("scroll","Default scrolling");
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
                inputLine = inputReader.readLine();
            }
            return "SUCCESS";
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
                }else{
                    currentCountry = countryExists(inpList[0]);
                    if(currentCountry.getNeighbouringCountries().size()!=0){
                        String returnString =  "ONE OR MORE DUPLICATE COUNTRIES ENCOUNTERED";
                        return returnString;
                    }
                }

                currentCountry.setCoordinateX(Integer.parseInt(inpList[1]));
                currentCountry.setCoordinateY(Integer.parseInt(inpList[2]));
                continentName = inpList[3];

                if(continentHashMap.containsKey(continentName)){
                    currentCountry.setContinent(continentHashMap.get(continentName));
                    continentHashMap.get(continentName).setCountries(currentCountry);
                }else{
                    return "One or more continents not present in the list.";
                }
                ArrayList<String> neighbours = new ArrayList<>();
                for(int iterator=4;iterator<inpList.length; iterator++){
                    neighbours.add(inpList[iterator]);
                }
                if(validator.hasDuplicateNeighbours(neighbours)){
                    return "ONE OR MORE DUPLICATE NEIGHBOURS ENCOUNTERED";
                }
                for(int iterator=4;iterator<inpList.length; iterator++){
                    GameCountry neighborCountry;
                    if(countryExists(inpList[iterator])==null){
                        neighborCountry = new GameCountry();

                        neighborCountry.setCountryName(inpList[iterator]);
                        countryHashMap.put(inpList[iterator],neighborCountry);
                    }else{
                        neighborCountry = countryExists(inpList[iterator]);
                    }
                    currentCountry.addNeighbouringCountry(neighborCountry);
                }

                inputLine = inputReader.readLine();
            }


            return "SUCCESS";
        }catch (IOException e){
            return "IOException";
        }catch (NumberFormatException e){
            return "The file is incomplete + "+e.toString();
        }catch (ArrayIndexOutOfBoundsException e){
            return "The file is incomplete + "+e.toString();
        }
        catch (NullPointerException e){

            return "SUCCESS";
        }
    }

    /**Checks if a country already exist using the country name.
     *
     * @param countryname
     * @return null if the country does not exist and the country object if the country already exists.
     */
    public GameCountry countryExists(String  countryname){
        if(countryHashMap.containsKey(countryname) ){
            return countryHashMap.get(countryname);
        }
        return null;
    }

    /**This method gets the initial parameters from the map file.
     *
     * @param inputReader
     * @return returns the status of execution of the method
     */
    public String getGuiParameters(BufferedReader inputReader){
        try {
            String inputLine = inputReader.readLine();
            inputLine = inputReader.readLine();

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

                if (lineCounter == 6) {
                    if (inputLine.equals("[Continents]")) {
                        returnString= this.readContinentList(inputReader);//reader is returned as we need to have a single reader reading different parts of the map file.
                        if (!returnString.equals("SUCCESS")) {
                            return "ONE OR MORE DUPLICATE CONTINENTS ENCOUNTERED";
                        }
                    }else{

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
            MapValidator validator = new MapValidator();
            if(validator.isFullyLinked()){
                returnString = "SUCCESS";
            }else{
                returnString = "The Countries are not properly linked to each other";
            }



            return returnString;
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
    public String writeConquestFile(String fileName){


        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName+".map"), "utf-8"));
            writer.write("[map]");
            ((BufferedWriter) writer).newLine();
            for (String s : guiHashMap.keySet()){
                writer.write(s+"="+guiHashMap.get(s)+"\n");
            }
            writer.write("\n");
            writer.write("[Continents]");
            ((BufferedWriter) writer).newLine();
            for (GameContinent continent: continentHashMap.values()) { //check if for each works with collections or not
                writer.write(continent.getContinentName() + "=" + continent.getContinentValue()+"\n");
            }

            writer.write("[Territories]");
            ((BufferedWriter) writer).newLine();
            for (GameCountry country : countryHashMap.values()) {
                String neighbours = "";
                for (GameCountry neighbour: country.getNeighbouringCountries().values()) {
                    neighbours += ","+neighbour.getCountryName();
                }
                writer.write(country.getCountryName()+","+country.getCoordinateX()+","+country.getCoordinateY()+","
                        + country.getContinent().getContinentName()+neighbours + "\n");
            }
        } catch (IOException ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
        return "SUCCESS";

    }
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

    /**This method can be used to access the object of GraphUtil(The utility used for building a graph)
     *
     * @return
     */
    public GraphUtil getGraphUtilObject(){
        return graphUtilObject;
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



            if  ((firstCountryFlag||(!countryHashMap.containsKey(countryName))||((countryHashMap.containsKey(countryName))&&countryHashMap.get(countryName).getContinent()==null))) {
                firstCountryFlag=false;
                GameCountry newCountry;
                if(countryExists(countryName)==null) {
                    newCountry = new GameCountry();
                    newCountry.setContinent(continentHashMap.get(continentname));
                    newCountry.setCountryName(countryName);
                    countryHashMap.put(countryName,newCountry);
                }else{
                    newCountry=countryExists(countryName);
                    newCountry.setContinent(continentHashMap.get(continentname));

                }
                continentHashMap.get(continentname).setCountries(newCountry);
                for (String tempNeighbourName : neighbours) {
                    GameCountry newNeighbour;
                    if (countryExists(tempNeighbourName)==null) {
                        newNeighbour = new GameCountry();
                        newNeighbour.setCountryName(tempNeighbourName);
                        countryHashMap.put(tempNeighbourName,newNeighbour);

                        newCountry.addNeighbouringCountry(newNeighbour);
                        newNeighbour.addNeighbouringCountry(newCountry);


                    }else{
                        newCountry.addNeighbouringCountry(countryExists(tempNeighbourName));
                        countryExists(tempNeighbourName).addNeighbouringCountry(newCountry);
                    }
                }

                returnString = "SUCCESS";


                return returnString;
            }


            return "THE COUNTRY ALREADY EXISTS";
        }catch(NullPointerException e){
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
        }
    }

    /**This method validates a map when the user wants to load the map after reading , editing a map.
     *
     * @return Status of the method execution
     */
    public String validateMap(){

        String returnString = "SUCCESS";
        if(validator.hasSingleCountryContinent(new ArrayList<>(continentHashMap.values()))){
            return "One of the continents have only one country";
        }
        if(validator.containsLoop(new ArrayList<>(countryHashMap.values()))){
            return "One of the country is connected to itself";
        }
        for(GameCountry country : countryHashMap.values()){

            if(!validator.hasNeighbor(country)){
                returnString = "ONE OR MORE COUNTRIES HAVE NO NEIGHBOURS";
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
        if(!validator.isWholeMapConnected(graphUtilObject)){
            returnString = "THE WHOLE MAP IS NOT CONNECTED";
        }

        return returnString;
    }

    /**Method gives a list of Continents of the map.
     *
     * @return arraylist of objects of each continent
     */
    public ArrayList<String> getListOfContinents(){
        return  new ArrayList<>(continentHashMap.keySet());

    }

    /**Method gets the list of countries of the map.
     *
     * @return arraylist of objects of each country
     */
    public ArrayList<String> getListOfCountries(){
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
                countryHashMap.get(neighborName).addNeighbouringCountry(countryHashMap.get(countryName));
            }else{
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
            }else{
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
            }else{
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

            }else{
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
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
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
            }else{
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
            }else{
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
            }else{
                return "CONTINENT DOES NOT EXIST";
            }
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /***
     * This Method checks for all continents owned by a player
     * @param playerID
     * @return ArrayList<GameContinent> continentsOwnedByPlayer
     */
    public ArrayList<GameContinent> checkContinentsOwnedByOnePlayer(int playerID){
        try {
            ArrayList<GameContinent> continentsOwnedByPlayer = new ArrayList<>(continentHashMap.values());
            for(String continentName : continentHashMap.keySet()){
                for (GameCountry country: continentHashMap.get(continentName).getCountries().values() ) {
                    if (country.getCurrentPlayer().getId()!=playerID){
                        continentsOwnedByPlayer.remove(continentHashMap.get(continentName));
                        break;
                    }
                }
            }
            return continentsOwnedByPlayer;
        }catch (Exception e){
            return null;
        }

    }

    /**
     *
     * @return
     */
    public String reSetAllocations(){
        try {
           countryHashMap.clear();
           continentHashMap.clear();
            return "SUCCESS";
        }
        catch (Exception e){
            return "SOME ERROR IN RESETTING ALLOCATIONS";
        }
    }

}