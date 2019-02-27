package controllers;
import models.GameContinent;
import models.GameCountry;
import utils.GraphUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MapGenerator {
    ArrayList<GameContinent> continentList;
    HashMap<String,GameCountry> countryHashMap;
    HashMap<String,GameContinent> continentHashMap;
    ArrayList<GameCountry> countryList;

    public MapGenerator() {
        this.countryHashMap = new HashMap<>();
        this.continentHashMap = new HashMap<>();
    }

    public BufferedReader readContinentList(BufferedReader inputReader) throws IOException {
        String inputLine;
        continentList = new ArrayList<>();
        inputLine = inputReader.readLine();
        while(!inputLine.equals("[Territories]")){
            GameContinent continent = new GameContinent();
            String name = inputLine.substring(0,inputLine.indexOf("="));
            int value = Integer.parseInt(inputLine.substring(inputLine.indexOf("=")+1));
            continent.setContinentName(name);
            continent.setContinentValue(value);
            continentList.add(continent);
            inputLine = inputReader.readLine();
        }
        return inputReader;          //Return the current position of reader file so that countries can be loaded.
    }
    public BufferedReader readCountryList(BufferedReader inputReader) throws IOException {
        String inputLine;
        countryList = new ArrayList<>();
        inputLine = inputReader.readLine();
        while(inputLine!=null){

            String[] inpList = inputLine.split(",");
            GameCountry currentCountry;
            String continentName;

            if(countryExists(inpList[0])==null) {
                currentCountry = new GameCountry();
                countryList.add(currentCountry);
            }
            else{
                currentCountry = countryExists(inpList[0]);
            }
            currentCountry.setCountryName(inpList[0]);
            currentCountry.setCoordinateX(Integer.parseInt(inpList[1]));
            currentCountry.setCoordinateY(Integer.parseInt(inpList[2]));
            continentName = inpList[3];

            for(GameContinent continent : continentList){
                if(continent.getContinentName().equals(continentName)){
                    currentCountry.setContinent(continent);
                    continent.setCountries(currentCountry);
                }
            }
            for(int iterator=4;iterator<inpList.length; iterator++){
                GameCountry neighborCountry;
                if(countryExists(inpList[iterator])==null){
                    neighborCountry = new GameCountry();
                    countryList.add(neighborCountry);
                    neighborCountry.setCountryName(inpList[iterator]);
                }
                else{
                    neighborCountry = countryExists(inpList[iterator]);
                }
                currentCountry.addNeighbouringCountry(neighborCountry);
            }
            inputLine = inputReader.readLine();
        }

        return inputReader;
    }
    public GameCountry countryExists(String  countryname){

        for(GameCountry tempCountry : countryList){
            if(tempCountry.getCountryName().equals(countryname)){
                return tempCountry;
            }
        }
        return null;
    }

    public String ReadConquestFile(String filePath) throws IOException {
        File inputMap = new File(filePath);
        BufferedReader inputReader = new BufferedReader(new FileReader(inputMap));

        String inputLine;
        int lineCounter=0;
        while((inputLine=inputReader.readLine())!=null){
            /*[Map]  //check if the first line equals [map]
                image=world.bmp
                wrap=yes
                scroll=horizontal           Read the first six lines here and then continue.
                author=Your name
                warn=yes*/
            if(lineCounter==6 && (inputLine=="[Continents]")){

                inputReader=this.readContinentList(inputReader);    //reader is returned as we need to have a single reader reading different parts of the map file.
            }
            inputReader = this.readCountryList(inputReader);


            lineCounter++;
        }

        return null;
    }

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
            for (GameContinent continent: continentList) {
                writer.write(continent.getContinentName() + "=" + continent.getContinentValue());
            }
            ((BufferedWriter) writer).newLine();
            writer.write("[Territories]");
            ((BufferedWriter) writer).newLine();
            for (GameCountry country : countryList) {
                String neighbours = "";
                for (GameCountry neighbour: country.getNeighbouringCountries()) {
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
        return "SUCESSFUL";

    }

    public String GenerateMap(){
        return null;
    }

    public void getCountriesFromUser(){
        for( GameContinent continent : continentList){
            System.out.println("Enter the countries for  "+ continent.getContinentName());
            getContriesForEachContinent(continent);
        }
    }

    /**
     * in the GUI the view can directly call this method after appropriate options are selected by the user
     * @param continent
     */
    public void getContriesForEachContinent(GameContinent continent){

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


    }
    public GraphUtil buildGraph(){
        GraphUtil graphUtilObject = new GraphUtil();
        graphUtilObject.setCountryGraph(countryList);
        return graphUtilObject;
    }
    public void editMap(){

    }

    public ArrayList<String> getListOfContinents(){
        ArrayList<String> continents = new ArrayList<>(continentHashMap.keySet());
        return continents;
    }

    public ArrayList<String> getListOfCountries(){
        ArrayList<String> countries = new ArrayList<>(countryHashMap.keySet());
        return countries;
    }

    public String removeNeighbor(String countryName , String neighborName){
        ArrayList<GameCountry> neighbors = countryHashMap.get(countryName).getNeighbouringCountries();
        for (GameCountry neighbor : neighbors){
            if (neighbor.getCountryName() == neighborName){
                neighbors.remove(neighbor);
            }
        }
        countryHashMap.get(countryName).setNeighbouringCountries(neighbors);
        return "SUCCESS";
    }

    public String addNeighbor(String countryName , String neighborName){
        ArrayList<GameCountry> neighbors = countryHashMap.get(countryName).getNeighbouringCountries();
        if (countryHashMap.containsKey(neighborName)){
            neighbors.add(countryHashMap.get(neighborName));
        }else {
           return "NEIGHBOR DOES NOT EXIST";
        }
        countryHashMap.get(neighborName).setNeighbouringCountries(neighbors);
        return "SUCCESS";
    }

    public String changeCountryContinent(String countryName, String continentName){
        if (continentHashMap.containsKey(continentName)){
            countryHashMap.get(countryName).setContinent(continentHashMap.get(continentName));
        }else {
           return "CONTINENT DOES NOT EXIST";
        }

        return "SUCCESS";
    }

    public String changeCountryName(String oldName , String newName){
        if (countryHashMap.containsKey(oldName)){
            countryHashMap.get(oldName).setCountryName(newName);
        }else {
            return "COUNTRY DOES NOT EXIST";
        }

        return "SUCCESS";
    }

    public String removeCountry(String countryName){
        if (countryHashMap.containsKey(countryName)){
            countryHashMap.remove(countryName);
            return "SUCCESS";
        }else{
            return "COUNTRY DOES NOT EXIST";
        }
    }

}
