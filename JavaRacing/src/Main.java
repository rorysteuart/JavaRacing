
	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

	public class Main {
	    // Display in terminal
	    private static final int DISPLAY_COUNT = 10;

	    private static int highwayLength;
	    private static double maxFuelChoice;
	    private static int obstaclesNumbers;

	    // Store data from vehicles.txt
	    private static List<Vehicle> vehicleList = new ArrayList<>();
	    private static Vehicle vehicle;
	    private static List<Obstacles> generatedObstaclesList = new ArrayList<>();
	    private static Obstacles[][] lanes;
	    private static Scanner scanner;
	    // Control game finished flag
	    private static boolean isGameOver = false;

	    public static void main(String[] args) throws IOException {
	        readFromFile();

	        scanner = new Scanner(System.in);

	        inputName();

	        chooseDifficult();

	        chooseVehicles();

	        generateObstacles();

	        generate3Lanes();

	        move();
	    }

	    /**
	     * Read from vehicles.txt
	     *
	     * Data in vehicles.txt:
	     * Motorcycle:4 100 30
	     * Car:3 120 200
	     * Garbage Truck:2 150 400
	     *
	     */
	    private static void readFromFile() throws IOException {
	        System.out.println("------ read from vehicles.txt ------");
	        File file = new File("/Volumes/HDD Storage/Coding/Eclipse/Assignment 2/src/vehicles.txt");
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString;
	            int line = 1;
	            while ((tempString = reader.readLine()) != null) {
	                System.out.println("car " + line + ": " + tempString);
	                String name = tempString.split(":")[0];
	                int boost = Integer.parseInt(tempString.split(":")[1].split(" ")[0]);
	                int maxFuel = Integer.parseInt(tempString.split(":")[1].split(" ")[1]);
	                int maxDamage = Integer.parseInt(tempString.split(":")[1].split(" ")[2]);
	                vehicleList.add(new Vehicle(name, boost, maxFuel, maxFuel, 0, maxDamage));
	                line++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                reader.close();
	            }
	        }
	        System.out.println("------ read from vehicles.txt ------");
	        System.out.println();
	    }

	    private static void inputName() {
	        while (true) {
	            System.out.println("Please input your name：");
	            String name = scanner.nextLine();
	            if (name.length() < 3 || name.length() > 12) {
	                System.out.println("Sorry! Input name error, please input again!");
	            } else {
	                break;
	            }
	        }
	    }

	    private static void chooseDifficult() {
	        while (true) {
	            System.out.println("Please choose difficulty level: ");
	            System.out.println("Easy --- 0");
	            System.out.println("Moderate --- 1");
	            System.out.println("Hard --- 2");
	            int level = scanner.nextInt();
	            if (level != 0 && level != 1 && level != 2) {
	                System.out.println("Please input correct difficulty level!");
	                continue;
	            }
	            switch (level) {
	                case 0:
	                    highwayLength = generateRandom(10, 15);
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
	     * Generate random number between min and max
	     * For example, randomly selected between 10 and 15
	     */
	    private static int generateRandom(int min, int max) {
	        return new Random().nextInt(max) % (max - min + 1) + min;
	    }

	    private static void chooseVehicles() {
	        System.out.println("please choose vehicles");
	        int size = vehicleList.size();
	        for (int i = 0; i < size; i++) {
	            Vehicle vehicle = vehicleList.get(i);
	            System.out.println(i + " --- " + vehicle.getName() + " boost is: " + vehicle.getBoost() + ", max fuel is： " + vehicle.getMaxFuel() + ", max damage is " + vehicle.getMaxDamage());
	        }
	        int number = scanner.nextInt();

	        System.out.println("highway length is " + highwayLength);
	        System.out.println("fuel is " + (vehicleList.get(number).getMaxFuel() * maxFuelChoice));

	        vehicle = vehicleList.get(number);
	    }

	    /**
	     * Corresponding probability here
	     * There may be bugs here, because some types may not be generated
	     * I'm not sure this is correct implementation
	     *
	     * <p>
	     * - Fuel
	     * - Provides the player with 10 fuel points
	     * - The probability of occurring is 30%
	     * - Represented in the game as an ‘F”
	     * - Roadblock
	     * - Cause 20 damage to the player’s vehicle
	     * - The probability of occurring is 40%
	     * - Represented in the game as an ‘B'
	     * - Tyre Spikes
	     * - Cause 45 damage to the player’s vehicle
	     * - Probability of occurring is 20%
	     * - Represented in the game as an ‘S’
	     * - Open manhole
	     * - Cause 60 damage to the player’s vehicle
	     * - The Probability of occurring is 10%
	     * - Represented in the game as an ‘O’
	     */
	    private static void generateObstacles() {
	        for (int i = 0; i < obstaclesNumbers; i++) {
	            int random = generateRandom(0, 10);
	            if (random <= 3) {
	                generatedObstaclesList.add(new Fuel());
	            } else if (random <= 7) {
	                generatedObstaclesList.add(new Roadblock());
	            } else if (random <= 9) {
	                generatedObstaclesList.add(new TyreSpikes());
	            } else {
	                generatedObstaclesList.add(new OpenManhole());
	            }
	        }
	    }

	    private static void generate3Lanes() {
	        lanes = new Obstacles[3][highwayLength];
	        for (int i = 0; i < lanes.length; i++) {
	            for (int j = 0; j < highwayLength; j++) {
	                Obstacles obstacles = new Obstacles();
	                obstacles.setDisplay("~");
	                lanes[i][j] = obstacles;
	            }
	        }

	        // Obstacles are distributed on 3 lanes
	        for (int i = 0; i < generatedObstaclesList.size(); i++) {
	            int random = generateRandom(0, 3);
	            // If there is an obstacle in the current position, continue to look for a free position
	            while (true) {
	                int random1 = generateRandom(3, highwayLength - 1);
	                if (lanes[random][random1].getDisplay().equals("~")) {
	                    lanes[random][random1] = generatedObstaclesList.get(i);
	                    break;
	                }
	            }
	        }

	        // Randomly place vehicle on lane
	        int random = generateRandom(0, 3);
	        vehicle.setDisplay("@");
	        lanes[random][0] = vehicle;
	        vehicle.setCurrentLaneIndex(random);
	        vehicle.setCurrentIndexInLane(0);

	        System.out.println();
	        System.out.println("------ raw highway data ------");
	        for (Obstacles[] lane : lanes) {
	            System.out.println();
	            for (int i1 = 0; i1 < highwayLength; i1++) {
	                System.out.print(lane[i1].getDisplay() + " ");
	            }
	        }
	        System.out.println();
	        System.out.println();
	        System.out.println("------ raw highway data ------");
	    }

	    private static void move() {
	        while (!isGameOver) {
	            System.out.println();
	            System.out.println("------ current highway data ------");
	            System.out.println("Available Fuel = " + vehicle.getCurrentFuel() + " Damage = " + vehicle.getCurrentDamage() + "/" + vehicle.getMaxDamage());

	            displayHighwayStatus(vehicle.getCurrentIndexInLane());

	            System.out.println();
	            System.out.println("1 to move forward");
	            System.out.println("2 to Swerve Up");
	            System.out.println("3 to Swerve Down");
	            System.out.println("4 to move Boost");
	            System.out.println("------ current highway data ------");

	            int i = scanner.nextInt();
	            if (i != 1 && i != 2 && i != 3 && i != 4) {
	                System.out.println("Please input correct number!");
	                continue;
	            } else {
	                if (canMoveUpOrDown(i)) {
	                    switch (i) {
	                        case 1:
	                            forward();
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
	                        default:
	                            break;
	                    }
	                }
	            }
	        }
	    }

	    // Only show 10 locations
	    private static void displayHighwayStatus(int currentIndexInLane) {
	        int i1 = currentIndexInLane;
	        for (Obstacles[] lane : lanes) {
	            System.out.println();
	            if (i1 + DISPLAY_COUNT >= highwayLength) {
	                for (int i2 = highwayLength - DISPLAY_COUNT; i2 < highwayLength; i2++) {
	                    System.out.print(lane[i2].getDisplay() + " ");
	                }
	            } else {
	                int max = i1 + DISPLAY_COUNT;
	                for (int i2 = i1; i2 < max; i2++) {
	                    System.out.print(lane[i2].getDisplay() + " ");
	                }
	            }
	        }
	    }

	    // Determine whether it can move up or down
	    private static boolean canMoveUpOrDown(int direction) {
	        if (vehicle.getCurrentLaneIndex() == 0 && direction == 2) {
	            return false;
	        } else if (vehicle.getCurrentLaneIndex() == 2 && direction == 3) {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    private static void forward() {
	        int lane = vehicle.getCurrentLaneIndex();
	        int index = vehicle.getCurrentIndexInLane();
	        if (index >= highwayLength - 1) {
	            System.out.println("Game Completed!");
	            isGameOver = true;
	            return;
	        } else {
	            if (vehicle.getCurrentFuel() - 1 <= 0) {
	                System.out.println("Game Over!");
	                isGameOver = true;
	                return;
	            }
	            vehicle.setCurrentLaneIndex(lane);
	            vehicle.setCurrentIndexInLane(index + 1);
	            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 1);
	            Obstacles obstacles = lanes[lane][index + 1];
	            if (obstacles instanceof Fuel) {
	                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()) {
	                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());
	                } else {
	                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
	                }
	            } else {
	                int damage = obstacles.getCurrentDamage();
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

	    private static void up() {
	        int lane = vehicle.getCurrentLaneIndex();
	        int index = vehicle.getCurrentIndexInLane();
	        if (index >= highwayLength - 1) {
	            System.out.println("Game Completed!");
	            isGameOver = true;
	            return;
	        } else {
	            if (vehicle.getCurrentFuel() - 1 <= 0) {
	                System.out.println("Game Over!");
	                isGameOver = true;
	                return;
	            }
	            vehicle.setCurrentLaneIndex(lane - 1);
	            vehicle.setCurrentIndexInLane(index + 1);
	            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 2);
	            Obstacles obstacles = lanes[lane - 1][index + 1];
	            if (obstacles instanceof Fuel) {
	                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()) {
	                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());
	                } else {
	                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
	                }
	            } else {
	                int damage = obstacles.getCurrentDamage();
	                if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
	                    System.out.println("Game Over!");
	                    isGameOver = true;
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
	        int index = vehicle.getCurrentIndexInLane();
	        if (index >= highwayLength - 1) {
	            System.out.println("Game Completed!");
	            isGameOver = true;
	            return;
	        } else {
	            if (vehicle.getCurrentFuel() - 1 <= 0) {
	                System.out.println("Game Over!");
	                isGameOver = true;
	                return;
	            }
	            vehicle.setCurrentLaneIndex(lane + 1);
	            vehicle.setCurrentIndexInLane(index + 1);
	            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 2);
	            Obstacles obstacles = lanes[lane + 1][index + 1];
	            if (obstacles instanceof Fuel) {
	                if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()) {
	                    vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());
	                } else {
	                    vehicle.setCurrentFuel(vehicle.getMaxFuel());
	                }
	            } else {
	                int damage = obstacles.getCurrentDamage();
	                if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
	                    System.out.println("Game Over!");
	                    isGameOver = true;
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
	            System.out.println("Game Completed!");
	            isGameOver = true;
	            return;
	        } else {
	            if (vehicle.getCurrentFuel() - 3 <= 0) {
	                System.out.println("Game Over!");
	                isGameOver = true;
	                return;
	            }
	            vehicle.setCurrentLaneIndex(lane);
	            vehicle.setCurrentFuel(vehicle.getCurrentFuel() - 3);
	            int count = 0;

	            while (count < vehicle.getBoost()) {
	                int currentIndex = vehicle.getCurrentIndexInLane();
	                if (currentIndex == highwayLength - 1) {
	                    System.out.println("Game Completed!");
	                    isGameOver = true;
	                    return;
	                }
	                count++;
	                vehicle.setCurrentIndexInLane(currentIndex + 1);
	                Obstacles obstacles = lanes[lane][currentIndex + 1];
	                if (obstacles instanceof Fuel) {
	                    if (vehicle.getCurrentFuel() + obstacles.getFuel() < vehicle.getMaxFuel()) {
	                        vehicle.setCurrentFuel(vehicle.getCurrentFuel() + obstacles.getFuel());
	                    } else {
	                        vehicle.setCurrentFuel(vehicle.getMaxFuel());
	                    }
	                } else {
	                    int damage = obstacles.getCurrentDamage();
	                    if (vehicle.getCurrentDamage() + damage >= vehicle.getMaxDamage()) {
	                        System.out.println("Game Over!");
	                        isGameOver = true;
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



