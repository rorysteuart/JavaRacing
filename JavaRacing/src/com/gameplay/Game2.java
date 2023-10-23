package com.gameplay;

	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.Fuel;
import model.Obstacles;
import model.OpenManhole;
import model.Roadblock;
import model.TyreSpikes;
import model.Vehicle;
/**
* This class is implements the 
* main method to run Game.java
*
* @author Rory Steuart
*
* @version 1.0.0
* 
*/

public class Game2{
    
    private static final int DISPLAY_COUNT = 10;
    
    private static int highwayLength;
    private static double maxFuelChoice;
    private static int obstaclesNumbers;

    private static List<Vehicle> vehiclesList = new ArrayList<>();
    private static Vehicle vehicle;
    private static List<Obstacles> generatedObstaclesList = new ArrayList<>();
    private static Obstacles[][] lanes;
    private static Scanner scanner;

    private static boolean isGameOver = false;
    
    public static void main(String[] args) throws IOException {
        //need to read from text file and send an error message back to user if location not found
        readFromFile();

        
        scanner = new Scanner(System.in); 
        
        inputName();// <-- order of methods which run the game
        chooseDifficulty();
        chooseVehicles();
        generateObstacles();
        generate3Lanes();
        move();
    }
    /**
    *Below I will read from vehicles.txt
    */

    private static void readFromFile() throws IOException {
        System.out.println("_____________________________________");
	    	System.out.println();
	    	System.out.println("<<<<< Welcome To Need For Java >>>>>");
	    	System.out.println();
	    	System.out.println("_____________________________________");
            System.out.println();
            
        //System.out.println("--- read from vehicles.txt ---"); 
        File file = new File("/Volumes/HDD Storage/Coding/Eclipse/Assignment 2/src/vehicles.txt");
        BufferedReader reader = null;
        try{ // <----- try catch statement 
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
	            while ((tempString = reader.readLine()) != null) {
	                //System.out.println("Vehicle " + line + " " + tempString); //This was to show the text that has been imported for my own reference
	                String name = tempString.split(":")[0];
	                int boost = Integer.parseInt(tempString.split(":")[1].split(" ")[0]);
	                int maxFuel = Integer.parseInt(tempString.split(":")[1].split(" ")[1]);   // <--- get max fuel references from here etc
	                int maxDamage = Integer.parseInt(tempString.split(":")[1].split(" ")[2]);
	                vehiclesList.add(new Vehicle(name, boost, maxFuel, maxFuel, 0, maxDamage)); //has to match method Vehicle which has 6 parameters in it's 
	                line++;
	            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        //System.out.println("--- read from vehicles.txt ---");
        System.out.println();
    }

    private static void inputName() {
        while(true){
            System.out.println("Please input your name: ");
            String name = scanner.nextLine();
            if(name.length() <3 || name.length() > 12){
                System.out.println("Oops! You need to input between 3 to 12 characters!");
                } else {
                    break;
                }
            }
        }
    /*Allows user to input difficulty level using a scanner. 
    *If user doens't input a number between 0-2
    *a prompt to try again will occur.
    */

    private static void chooseDifficulty() {
        while(true) {
            System.out.println("Please choose difficulty level:");
            System.out.println("Easy --- 0:");
            System.out.println("Moderate --- 1:");
            System.out.println("Hard --- 2:");

            int level = scanner.nextInt();  
            if(level != 0 && level != 1 && level != 2) {                 // <--initialise leel here
            System.out.println("Please input corect difficulty level"); 
            continue;
            }
/** 
* Switch case statement to set the amount of obstacles, 
* fuel appearing & the length of the highway
* I could have used other loop statements but this was simple while worked best
*/
        switch (level) {
            case 0:
               highwayLength = generateRandom(10, 15);  // <-- calls on fields
               maxFuelChoice = 1;
               obstaclesNumbers = 12;
               break;
            case 1:
               highwayLength = generateRandom(15, 30);
               maxFuelChoice = 0.8;
               obstaclesNumbers = 24;
               break;
            case 2:
               highwayLength = generateRandom(30, 50);
               maxFuelChoice = 0.5;
               obstaclesNumbers = 45; 
               break; 
            default:
               break;
        }
        break;
        }
    }
    

/**
* chooseVehicles uses a for loop to display available 
* vehicles from text file and allow user input
*/
    private static void chooseVehicles(){
        System.out.println("Please choose vehicles");
        int size = vehiclesList.size();
        for(int i =0; i <size; i++) {
            Vehicle vehicle = vehiclesList.get(i);
            System.out.println(i + "---" + vehicle.getName() + " boost is: " + vehicle.getBoost() + ", max fuel is: " + vehicle.getMaxFuel() + ", max damage is " + vehicle.getMaxDamage());   
        }
        int number = scanner.nextInt();

        System.out.println("Highway length is " + highwayLength);
        System.out.println("Fuel is " + (vehiclesList.get(number).getMaxFuel() * maxFuelChoice));
        vehicle = vehiclesList.get(number);
    }
/**
* @author Chancea, Source: https://stackoverflow.com/questions/28705491/reading-file-line-by-line-terminating-while-loop-after-reaching-last-line 20/06/21 - Modified
*
*/
    private static int generateRandom(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

/*Using the above method I generate obstacles by using the math.random
* 
* Not sure if this was the right way to do this
*/
    private static void generateObstacles() {
        for (int i = 0; i <obstaclesNumbers; i++) { //applying value to the field obstacleNumbers
            int random = generateRandom(0,10); 
            if (random <= 3) { //i.e Fuel appears 30% //random is smaller than 3 means 0-3
                generatedObstaclesList.add(new Fuel());
            } else if (random <= 6) { //4 5 6
                generatedObstaclesList.add(new Roadblock());
            } else if (random <= 8) { //7 8
                generatedObstaclesList.add(new TyreSpikes());
            } else if (random <= 9) { // 9
                generatedObstaclesList.add(new OpenManhole());
            }
        }

    }

/**
* @author Allen Downey & Chris Mayfield Source: https://books.trinket.io/thinkjava2/chapter14.html 20/06/21, Used as reference.
*/
    private static void generate3Lanes() {
        lanes = new Obstacles[3][highwayLength];
        for(int i = 0; i < lanes.length; i++) {
            for(int j = 0; j < highwayLength; j++){
                Obstacles obstacles = new Obstacles();
                obstacles.setDisplay("~");
                lanes[i][j] = obstacles;    //talks to the field highway length which then calls from the switch case with input e.g. generate random(10,15)
            }
        }
//Obstacles are distributed on 3 lanes
        for (int i = 0; i < generatedObstaclesList.size(); i++) {
             int random = generateRandom(0, 3);
        //if there is an obstacle in the current position, continue to look for a free position
        while (true) {
            int random1 = generateRandom(3, highwayLength -1);  // the 3 means there is nothing in the first 3 lanes
            if (lanes[random][random1].getDisplay().equals("~")) {  //stops two blocks being on top of each other
                lanes[random][random1] = generatedObstaclesList.get(i);
                break;
            }
        }
    }

    /**
    *To randomly place vehicle on a lane
    */
    int random = generateRandom(0, 3); //from line 0 to line 3
    vehicle.setDisplay("@");
    lanes[random][0] = vehicle;
    vehicle.setCurrentLaneIndex(random); //current lane index refers to the vertical
    vehicle.setCurrentIndexInLane(0); //current index in lane refers to the horizontal
    }

    private static void displayHighwayStatus(int getCurrentIndexInLane) {
        int i1 = getCurrentIndexInLane;
        for(Obstacles[] lane : lanes) {
            System.out.println();
            if (i1 + DISPLAY_COUNT >= highwayLength) {
                for (int i2 = highwayLength - DISPLAY_COUNT; i2 < highwayLength; i2++) {
                    System.out.print(lane[i2].getDisplay() + " "); //<----- fixed vertical line error println caused it to print vertical
                }
            } else {
                int max = i1 + DISPLAY_COUNT;
                for (int i2 = i1; i2 < max; i2++) {
                    System.out.print(lane[i2].getDisplay() + " ");  //<----- fixed vertical line error println caused it to print vertical
                }
            }
        }
    }

    private static void move(){
        while (!isGameOver){
            System.out.println();
            System.out.println("------  Highway  ------");
            System.out.println("Available Fuel = " + vehicle.getCurrentFuel() + " Damage = " + vehicle.getCurrentDamage() + "/" + vehicle.getMaxDamage());

            displayHighwayStatus(vehicle.getCurrentIndexInLane());

            System.out.println();
            System.out.println("1 to move forward");
            System.out.println("2 to swerve up");
            System.out.println("3 to swerve down");
            System.out.println("4 to boost");
            System.out.println("------  Highway  ------");

            int i = scanner.nextInt();
            if (i != 1 && i != 2 && i !=3 && i != 4) {
                System.out.println("Please input correct number!");
                continue;
            } else {
                if (canMoveUpOrDown(i)) {
                    switch (i) {
                        case 1:
                          forward();  //calls on the method forward
                          break;
                        case 2:
                          up();
                          break;
                        case 3:
                          down();
                          break;
                        case 4:
                          boost();
                          break;

                    }
                }
            }

        }
    }

    //method to create bounds so the player can move up & down
    private static boolean canMoveUpOrDown(int direction) {
        if (vehicle.getCurrentLaneIndex() == 0 && direction == 2) {
            return false;
        } else if (vehicle.getCurrentLaneIndex() == 2 && direction == 3) {
            return false;
        } else {
            return true;
        }
    }
    /**
    * with basic movement set and bounds set, 
    * below is the functionality for the move method
    */

    private static void forward() {
        int lane = vehicle.getCurrentLaneIndex();
        int index = vehicle.getCurrentIndexInLane();
        if (index >= highwayLength - 1) {
            System.out.println("Congradulations you finished the game!");
            isGameOver = true;
            return;
        }else {
            if (vehicle.getCurrentFuel() - 1 <= 0) {
                System.out.println("Game Over");
                isGameOver = true;
                return;
            }
            vehicle.setCurrentLaneIndex(lane);
            vehicle.setCurrentIndexInLane(index + 1); 
            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 1);
            Obstacles obstacles = lanes[lane][index + 1];
            if (obstacles instanceof Fuel) {
                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()){
                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());   
                } else {
                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
                }
            } else {
                int damage = obstacles.getDamage();
                if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
                        System.out.println("Game Over!");
                        isGameOver = true;
                        return;
                } else {
                        vehicle.setCurrentDamage(vehicle.getCurrentDamage() + damage);
                    }
                }
                lanes[lane][index + 1] = vehicle;
                Obstacles obstacles1 = new Obstacles();
                obstacles1.setDisplay("~");
                lanes[lane][index] = obstacles1;
            }
    }
    
    //Repeat for up 

    private static void up() {
        int lane = vehicle.getCurrentLaneIndex();
        int index = vehicle.getCurrentIndexInLane();
        if (index >= highwayLength - 1) {
            System.out.println("Congradulations you finished the game!");
            isGameOver = true;
            return;
        }else {
            if (vehicle.getCurrentFuel() - 1 <= 0) {
                System.out.println("Game Over");
                isGameOver = true;
                return;
            }

            vehicle.setCurrentLaneIndex(lane - 1);
            vehicle.setCurrentIndexInLane(index + 1);
            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 2);
            Obstacles obstacles = lanes[lane - 1][index + 1];
            if (obstacles instanceof Fuel) {
                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()){
                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());   
                } else {
                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
                }
                } else {
                    int damage = obstacles.getDamage();
                    if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
                        System.out.println("Game Over!");
                        isGameOver = true;
                        return;
                    } else {
                        vehicle.setCurrentDamage(vehicle.getCurrentDamage() + damage);
                    }
                }
                lanes[lane - 1][index + 1] = vehicle;
                Obstacles obstacles1 = new Obstacles();
                obstacles1.setDisplay("~");
                lanes[lane][index] = obstacles1;
            }
    }
    
    private static void down() {
        int lane = vehicle.getCurrentLaneIndex();
        int index = vehicle.getCurrentIndexInLane();  //establishing the int index which = the current lane the car is in
        if (index >= highwayLength - 1) {
            System.out.println("Congradulations you finished the game!");
            isGameOver = true;
            return;
        }else {
            if (vehicle.getCurrentFuel() - 1 <= 0) {
                System.out.println("Game Over");
                isGameOver = true;
                return;
            }

            vehicle.setCurrentLaneIndex(lane + 1);
            vehicle.setCurrentIndexInLane(index + 1);
            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 2);
            Obstacles obstacles = lanes[lane + 1][index + 1];
            if (obstacles instanceof Fuel) {
                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()){
                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());   
                } else {
                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
                }
                } else {
                    int damage = obstacles.getDamage();
                    if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
                        System.out.println("Game Over!");
                        isGameOver = true;
                        return;
                    } else {
                        vehicle.setCurrentDamage(vehicle.getCurrentDamage() + damage);
                    }
                }
                lanes[lane + 1][index + 1] = vehicle;
                Obstacles obstacles1 = new Obstacles();
                obstacles1.setDisplay("~");
                lanes[lane][index] = obstacles1;
            }
    }

    private static void boost() {
    	
        int lane = vehicle.getCurrentLaneIndex();
        int index = vehicle.getCurrentIndexInLane();
        
        if (index >= highwayLength - 1) {
            System.out.println("Congradulations you finished the game!");
            isGameOver = true;
            return;
        }else {
            if (vehicle.getCurrentFuel() - 3 <= 0) { //minus the used fuel to boost and then hits less then 0 then game 
                System.out.println("Game Over");
                isGameOver = true;
                return;
            }

            vehicle.setCurrentLaneIndex(lane);
            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 3);
            
            int count = 0; //int default to 0, below then sets the parameters to int

            while (count < vehicle.getBoost()) {   //stops the car boosting past the length of the highway
                int currentIndex = vehicle.getCurrentIndexInLane();
                if (currentIndex == highwayLength -1) {
                    System.out.println("Congradulations you finished the game!");
                    isGameOver = true;
                    return;
                }
                count++; //allows the user to input 4 as boost and run the remaining code otherwise game stops
                vehicle.setCurrentIndexInLane(index + 1);
                Obstacles obstacles = lanes[lane][currentIndex + 1];
            if (obstacles instanceof Fuel) {
                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()){
                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());   
                } else {
                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
                }
                } else {
                    int damage = obstacles.getDamage();
                    if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
                        System.out.println("Game Over!");
                        isGameOver = true;
                        return;
                    } else {
                        vehicle.setCurrentDamage(vehicle.getCurrentDamage() + damage);
                    }
                }
                lanes[lane][currentIndex + 1] = vehicle;
                Obstacles obstacles1 = new Obstacles();
                obstacles1.setDisplay("~");
                lanes[lane][currentIndex] = obstacles1;
            
            }
            
        }
    }

}

/**
* Noted bug - I can't make the program 
* run horizontally only vertically on one lane.
* I ran out of time to debug this.
*/


