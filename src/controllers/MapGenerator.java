package controllers;

import models.GameContinent;
import models.GameCountry;
import models.GameMap;
import utils.GraphUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**This class hosts the method definitions with which any file access related operations are to be performed in the game.
 * This class also acts as a controller for the GameMap model.
 *
 */
public class  MapGenerator {

    GraphUtil graphUtilObject ;
    BufferedReader inputReader;
    boolean firstCountryFlag;
    MapValidator validator;
    GameMap gameMap;

    /** Class constructor that initializes the ubiquitous countryHashMap and ContinentHashMap
     *
     */
    public MapGenerator(GameMap map) {

        gameMap = map;
        setGuiHashMap();
        firstCountryFlag=true;
        validator = new MapValidator(gameMap);
    }

    /**This method sets the default initial parameters according to the conquest map format.
     *
     */
    public void setGuiHashMap() {

        gameMap.setGetGuiHashMapParameter("author","Default authorName");
        gameMap.setGetGuiHashMapParameter("warn","Default warning");
        gameMap.setGetGuiHashMapParameter("image","Default image");
        gameMap.setGetGuiHashMapParameter("wrap","Default wrapping");
        gameMap.setGetGuiHashMapParameter("scroll","Default scrolling");
    }

    /**
     * Passes game map
     * @return game map
     */
    public GameMap getGameMap() {
        return this.gameMap;
    }

    /**Reads continents from a file and store them in required structure.
     *
     * @param inputReader BufferedReader for reading continents
     * @return object of buffered reader to continue reading after continents are read.
     */
    public String readContinentList(BufferedReader inputReader) {
        try {
            String inputLine;

            inputLine = inputReader.readLine();
            while(inputLine.equals("")) {
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
                if (gameMap.containsKey(name)) {
                    return "ONE OR MORE DUPLICATE CONTINENTS ENCOUNTERED.";
                }
                continent.setContinentName(name);
                continent.setContinentValue(value);
                gameMap.addContinent(continent);
                inputLine = inputReader.readLine();
            }
            return "SUCCESS";
        }catch (IOException e) {
            return null;
        }catch (NullPointerException e) {
            return null;
        }
    }

    /**Reads countries from the file and stores them in the required structure.
     * @param inputReader BufferedReader Object
     * @return object of buffered reader to continue reading after continents are read.
     */
    public String readCountryList(BufferedReader inputReader) {
        try {
            String inputLine;

            inputLine = inputReader.readLine();

            while(!inputLine.equals("")) {

                String[] inpList = inputLine.split(",");
                for(int i=0;i<inpList.length;i++) {
                    if(inpList[i].equals("")) {
                        return "ONE OR MORE VALUES ARE EMPTY";
                    }
                }
                GameCountry currentCountry;
                String continentName;

                if(countryExists(inpList[0])==null) {

                    currentCountry = new GameCountry();
                    currentCountry.setCountryName(inpList[0]);
                    gameMap.addCountry(currentCountry);
                } else {
                    currentCountry = countryExists(inpList[0]);
                    if(currentCountry.getNeighbouringCountries().size()!=0) {
                        String returnString =  "ONE OR MORE DUPLICATE COUNTRIES ENCOUNTERED";
                        return returnString;
                    }
                }

                currentCountry.setCoordinateX(Integer.parseInt(inpList[1]));
                currentCountry.setCoordinateY(Integer.parseInt(inpList[2]));
                continentName = inpList[3];
                if(gameMap.containsKey(continentName)) {
                    currentCountry.setContinent(gameMap.getContinentHashMap().get(continentName));
                    gameMap.getContinentHashMap().get(continentName).setCountries(currentCountry);
                } else {
                    return "One or more continents not present in the list.";
                }
                ArrayList<String> neighbours = new ArrayList<>();
                for(int iterator=4;iterator<inpList.length; iterator++) {
                    neighbours.add(inpList[iterator]);
                }
                if(validator.hasDuplicateNeighbours(neighbours)) {
                    return "ONE OR MORE DUPLICATE NEIGHBOURS ENCOUNTERED";
                }
                for(int iterator=4;iterator<inpList.length; iterator++) {
                    GameCountry neighborCountry;
                    if(countryExists(inpList[iterator])==null) {
                        neighborCountry = new GameCountry();

                        neighborCountry.setCountryName(inpList[iterator]);
                        gameMap.addCountry(neighborCountry);
                    } else {
                        neighborCountry = countryExists(inpList[iterator]);
                    }
                    currentCountry.addNeighbouringCountry(neighborCountry);
                }
                inputLine = inputReader.readLine();
            }
            return "SUCCESS";
        }catch (IOException e) {
            return "IOException";
        }catch (NumberFormatException e) {
            return "The file is incomplete + "+e.toString();
        }catch (ArrayIndexOutOfBoundsException e) {
            return "The file is incomplete + "+e.toString();
        }
        catch (NullPointerException e) {
            return "SUCCESS";
        }
    }

    /**Checks if a country already exist using the country name.
     * @param countryName NAme of country to check if exists or not
     * @return null if the country does not exist and the country object if the country already exists.
     */
    public GameCountry countryExists(String  countryName) {
        if(gameMap.getCountryHashMap().containsKey(countryName)) {
            return gameMap.getCountryHashMap().get(countryName);
        }
        return null;
    }

    /**This method gets the initial parameters from the map file.
     * @param inputReader BufferedReader Object
     * @return returns the status of execution of the method
     */
    public String getGuiParameters(BufferedReader inputReader) {
        try {
            String inputLine = inputReader.readLine();
            inputLine = inputReader.readLine();
            while (!inputLine.equals("")) {
                String[] inpList = inputLine.split("=");
                gameMap.setGetGuiHashMapParameter(inpList[0],inpList[1]);
                inputLine = inputReader.readLine();
            }
            return "SUCCESS";
        }catch(Exception e) {
            return "THE INITIAL PARAMETERS HAVE WRONG FORMAT";
        }
    }

    /**This method is called when a user wants to load the file from the system.
     * @param filePath PAth of Map file
     * @return status of the operation
     * @throws IOException
     */
    public String readConquestFile(String filePath) {
        try {
            File inputMap = new File(filePath);
            String returnString;
            inputReader = new BufferedReader(new FileReader(inputMap));
            returnString=this.getGuiParameters(inputReader);
            if(!returnString.equals("SUCCESS")) {
                return returnString;
            }
            String inputLine;
            int lineCounter = 6;
            while ((inputLine = inputReader.readLine()) != null) {
                if (lineCounter == 6) {
                    if (inputLine.equals("[Continents]")) {
                        returnString= this.readContinentList(inputReader);
                        if (!returnString.equals("SUCCESS")) {
                            return "ONE OR MORE DUPLICATE CONTINENTS ENCOUNTERED";
                        }
                    } else {
                        return "THE MAP FORMAT IS WRONG";
                    }
                    lineCounter++;
                }
                returnString = this.readCountryList(inputReader);
                if (!returnString.equals("SUCCESS")) {
                    return returnString;
                }
                lineCounter++;
            }
            if(validator.isFullyLinked()) {
                returnString = "SUCCESS";
            } else {
                returnString = "The Countries are not properly linked to each other";
            }
            return returnString;
        }catch (FileNotFoundException e) {
            return "THE FILE NOT FOUND";
        }catch (IOException e) {
            return "ERROR IN READING THE SPECIFIED FILE";
        }
    }

    /**Writes the map into a text file following the Conquest File Format.
     *
     * @return status of the operation
     */
    public String writeConquestFile(String fileName) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName+".map"), "utf-8"));
            writer.write("[map]");
            ((BufferedWriter) writer).newLine();
            for (String s : gameMap.getGuiHashMap().keySet()) {
                writer.write(s+"="+gameMap.getGuiHashMap().get(s)+"\n");
            }
            writer.write("\n");
            writer.write("[Continents]");
            ((BufferedWriter) writer).newLine();
            for (GameContinent continent: gameMap.getContinentHashMap().values()) {
                writer.write(continent.getContinentName() + "=" + continent.getContinentValue()+"\n");
            }
            writer.write("[Territories]");
            ((BufferedWriter) writer).newLine();
            for (GameCountry country :gameMap.getCountryHashMap().values() ) {
                String neighbours = "";
                for (GameCountry neighbour: country.getNeighbouringCountries().values()) {
                    neighbours += ","+neighbour.getCountryName();
                }
                writer.write(country.getCountryName()+","+country.getCoordinateX()+","+country.getCoordinateY()+","
                        + country.getContinent().getContinentName()+neighbours + "\n");
            }
        } catch (IOException ex) {
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
        return "SUCCESS";
    }

    /**Method builds a graph from the list of continents and country data acquired.
     * @return returns the graph.
     */
    public GraphUtil buildGraph() {
        try {
            GraphUtil graphUtilObject = new GraphUtil();
            graphUtilObject.setCountryGraph(gameMap.getCountryHashMap());
            return graphUtilObject;
        }catch(Exception e) {
            return null;
        }
    }

    /**This method can be used to access the object of GraphUtil(The utility used for building a graph)
     *
     * @return GraphUtilObject for building graph for the map
     */
    public GraphUtil getGraphUtilObject() {
        return graphUtilObject;
    }

    /**This method adds a continent in the map.
     *
     * @param continentName Name of Continent to be added
     * @param continentValue Value of continent
     * @return returns the status of the method execution
     */
    public String addContinent(String continentName, int continentValue) {

        try {
            if ( gameMap.getContinentHashMap().isEmpty() || !gameMap.getContinentHashMap().containsKey(continentName)) {
                GameContinent newContinent = new GameContinent();
                newContinent.setContinentName(continentName);
                newContinent.setContinentValue(continentValue);
                gameMap.addContinent(newContinent);
                return "CONTINENT ADDED SUCCESSFULLY";
            }
            return "THE CONTINENT ALREADY EXISTS";
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
        }
    }

    /**This method adds a country to the map.
     * @param continentname Name of Continent to be added
     * @param countryName Name of country to be added in the continent
     * @param neighbours Country neighbours
     * @return returns the status of the method execution
     */
    public String addCountry(String continentname, String countryName, ArrayList<String> neighbours) {
        String returnString;
        try {
            if  ((firstCountryFlag||(!gameMap.getCountryHashMap().containsKey(countryName))||((gameMap.getCountryHashMap().containsKey(countryName))
                    &&gameMap.getCountryHashMap().get(countryName).getContinent()==null))) {
                firstCountryFlag=false;
                GameCountry newCountry;
                if(countryExists(countryName)==null) {
                    newCountry = new GameCountry();
                    newCountry.setContinent(gameMap.getContinentHashMap().get(continentname));
                    newCountry.setCountryName(countryName);
                    gameMap.addCountry(newCountry);
                } else {
                    newCountry=countryExists(countryName);
                    newCountry.setContinent(gameMap.getContinentHashMap().get(continentname));
                }
                gameMap.getContinentHashMap().get(continentname).setCountries(newCountry);
                for (String tempNeighbourName : neighbours) {
                    GameCountry newNeighbour;
                    if (countryExists(tempNeighbourName)==null) {
                        newNeighbour = new GameCountry();
                        newNeighbour.setCountryName(tempNeighbourName);
                        gameMap.addCountry(newNeighbour);
                        gameMap.addCountry(newCountry);
                        newCountry.addNeighbouringCountry(newNeighbour);
                        newNeighbour.addNeighbouringCountry(newCountry);
                    } else {
                        newCountry.addNeighbouringCountry(countryExists(tempNeighbourName));
                        countryExists(tempNeighbourName).addNeighbouringCountry(newCountry);
                    }
                }

                returnString = "SUCCESS";
                return returnString;
            }
            return "THE COUNTRY ALREADY EXISTS";
        }catch(NullPointerException e) {
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
        }
    }

    /**This method validates a map when the user wants to load the map after reading , editing a map.
     *
     * @return Status of the method execution
     */
    public String validateMap() {

        String returnString = "SUCCESS";
        if(validator.hasSingleCountryContinent(new ArrayList<>(gameMap.getContinentHashMap().values()))) {
            return "One of the continents have only one country or less";
        }
        if(validator.containsLoop(new ArrayList<>(gameMap.getCountryHashMap().values()))) {
            return "One of the country is connected to itself";
        }
        for(GameCountry country : gameMap.getCountryHashMap().values()) {
            if(!validator.hasNeighbor(country)) {
                returnString = "ONE OR MORE COUNTRIES HAVE NO NEIGHBOURS";
                break;
            }
            if(!validator.hasValidNumberOfNeighbors(country)) {
                returnString = "INVALID NUMBER OF NEIGHBOURS";
                break;
            }
        }
        if(!validator.hasValidNumberOfContinents(new ArrayList<>(gameMap.getContinentHashMap().values()))) {
            returnString =  "NUMBER OF CONTINENTS IS NOT VALID";
        }
        if(!validator.hasValidNumberOfCountries(new ArrayList<>(gameMap.getCountryHashMap().values()))) {
            returnString = "INVALID NUMBER OF COUNTRIES";
        }
        graphUtilObject = this.buildGraph();
        if(!validator.isWholeMapConnected(graphUtilObject)) {
            returnString = "THE WHOLE MAP IS NOT CONNECTED";
        }
        for(GameContinent continent : gameMap.getContinentHashMap().values()) {
            if(!(validator.isContinentFullyLinked(continent,graphUtilObject))) {
                returnString = "ONE OR MORE CONTINENTS ARE NOT CONNECTED WITHIN THEMSELVES";
            }
        }
        return returnString;
    }

    /**Method gives a list of Continents of the map.
     *
     * @return arraylist of objects of each continent
     */
    public ArrayList<String> getListOfContinents() {
        return  new ArrayList<>(gameMap.getContinentHashMap().keySet());
    }

    /**Method gets the list of countries of the map.
     *
     * @return arraylist of objects of each country
     */
    public ArrayList<String> getListOfCountries() {
        ArrayList<String> countries = new ArrayList<>(gameMap.getCountryHashMap().keySet());
        return countries;
    }

    /**This method adds a neighbour for a specified country on user demand.
     *
     * @param countryName Name of country whose neighbour is to be added
     * @param neighborName Name of neighbour to be added
     * @return status of the method execcution
     */
    public String addNeighbor(String countryName , String neighborName) {
        try {
            HashMap <String,GameCountry> neighbors = gameMap.getCountryHashMap().get(countryName).getNeighbouringCountries();
            if (gameMap.getCountryHashMap().containsKey(neighborName)) {
                neighbors.put(neighborName,gameMap.getCountryHashMap().get(neighborName));
                gameMap.getCountryHashMap().get(neighborName).addNeighbouringCountry(gameMap.getCountryHashMap().get(countryName));
            } else {
                return "NEIGHBOR DOES NOT EXIST";
            }
            return "SUCCESS";
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method changes the continent of a specified country on user demand.
     *
     * @param countryName Name of country whose continent to be added
     * @param continentName Name of continent where it is to be added
     * @return status of the method execcution
     */
    public String changeCountryContinent(String countryName, String continentName) {
        try {
            String oldContinentName = gameMap.getCountryHashMap().get(countryName).getContinent().getContinentName();
            gameMap.getContinentHashMap().get(oldContinentName).getCountries().remove(gameMap.getCountryHashMap().get(countryName));
            if (gameMap.getCountryHashMap().containsKey(continentName)) {
                gameMap.getCountryHashMap().get(countryName).setContinent(gameMap.getContinentHashMap().get(continentName));
                gameMap.getContinentHashMap().get(continentName).setCountries(gameMap.getCountryHashMap().get(countryName));
            } else {
                return "CONTINENT DOES NOT EXIST";
            }
            return "SUCCESS";
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /** This method changes the name of a specified country on user demand.
     *
     * @param oldName Change name from
     * @param newName Change name to
     * @return status of the method execution
     */
    public String changeCountryName(String oldName , String newName) {
        try {
            if (gameMap.getCountryHashMap().containsKey(oldName)) {
                gameMap.getCountryHashMap().get(oldName).setCountryName(newName);
                gameMap.addCountry(gameMap.getCountryHashMap().get(oldName));
                gameMap.removeCountry(oldName);
            } else {
                return "COUNTRY DOES NOT EXIST";
            }
            return "SUCCESS";
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method removes a whole country from the database on user demand.
     *
     * @param countryName Name of country which is to be removed
     * @return status of the method execcution
     */
    public String removeCountry(String countryName) {
        try {
            String returnString;
            for(GameCountry neighbor : gameMap.getCountryHashMap().get(countryName).getNeighbouringCountries().values()) {
                neighbor.getNeighbouringCountries().remove(countryName);
            }
            if (gameMap.getCountryHashMap().containsKey(countryName)) {
                gameMap.getCountryHashMap().get(countryName).getContinent().
                        getCountries().remove(gameMap.getCountryHashMap().get(countryName));
                returnString = validateContinent(gameMap.getCountryHashMap().get(countryName).getContinent());
                gameMap.removeCountry(countryName);
                if (returnString.equals("EMPTY")) {
                    gameMap.removeContinentFromMap(gameMap.getCountryHashMap().get(countryName).getContinent().getContinentName());
                }
                returnString = "SUCCESS";
            } else {
                return "COUNTRY DOES NOT EXIST";
            }
            return returnString;
        } catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method validates if the continent has any country or not.
     *
     * @param continent Continent to be validated
     * @return returns the appropriate signal
     */
    public String validateContinent(GameContinent continent) {
        try {
            if (continent.getCountries().size() == 0) {
                return "EMPTY";
            }
            return "ALL GOOD";
        } catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING CONTINENT DATA";
        }
    }

    /**This method changes the name of the specified continent.
     *
     * @param continentName Name of continent whose name is to be changed
     * @param newContinentName new continent name
     * @return status of the method execcution
     */
    public String changeContinentName(String continentName, String newContinentName) {
        try {
            if (gameMap.getContinentHashMap().containsKey(continentName)) {
                gameMap.getContinentHashMap().get(continentName).setContinentName(newContinentName);
                gameMap.addContinent(gameMap.getContinentHashMap().get(continentName));
                gameMap.removeContinentFromMap(continentName);

                return "SUCCESS";
            } else {
                return "CONTINENT DOES NOT EXIST";
            }
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /**This method changes the value of the continent.
     *
     * @param continentName Old continent value
     * @param continentValue new continent value
     * @return status of the method execcution
     */
    public String changeContinentValue(String continentName, int continentValue) {
        try {
            if (gameMap.getContinentHashMap().containsKey(continentName)) {
                gameMap.getContinentHashMap().get(continentName).setContinentValue(continentValue);
                return "SUCCESS";
            } else {
                return "CONTINENT DOES NOT EXIST";
            }
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /***
     * This Method checks for all continents owned by a player
     * @param playerID ID of player
     * @return ArrayList<GameContinent> continentsOwnedByPlayer
     */
    public ArrayList<GameContinent> checkContinentsOwnedByOnePlayer(int playerID) {
        try {
            ArrayList<GameContinent> continentsOwnedByPlayer = new ArrayList<>(gameMap.getContinentHashMap().values());
            for(String continentName : gameMap.getContinentHashMap().keySet()) {
                for (GameCountry country: gameMap.getContinentHashMap().get(continentName).getCountries().values() ) {
                    if (country.getCurrentPlayer().getId()!=playerID) {
                        continentsOwnedByPlayer.remove(gameMap.getContinentHashMap().get(continentName));
                        break;
                    }
                }
            }
            return continentsOwnedByPlayer;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *  Resets all the existing data structures.
     * @return returns a string indicating the result of the method call
     */
    public String reSetAllocations() {
        try {
           gameMap.getCountryHashMap().clear();
            gameMap.getContinentHashMap().clear();
            gameMap.guiHashMap.clear();
            return "SUCCESS";
        } catch (Exception e) {
            return "SOME ERROR IN RESETTING ALLOCATIONS";
        }
    }

}