package controllers;
import models.GameContinent;
import models.GameCountry;

import java.io.*;
import java.util.ArrayList;

public class MapGenerator {
    ArrayList<GameContinent> continentList;
    ArrayList<GameCountry> countryList;

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
            for(int iterator=3;iterator<inpList.length; iterator++){
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

    public String WriteConquestFile(){

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

}
