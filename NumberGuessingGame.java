import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    static final String STAT_FILE = "statistics.txt";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); 
        // changed from 'scanner' to 'input'
        
        Random random = new Random(); 
        // Creates a new Random object to generate random numbers later (for the secret number in the game)

        HashMap<String, String> statistics = loadStatistics(); 
        // Loads previously saved statistics from the file into a HashMap (key-value pairs as Strings)

        int totalGamesPlayed = Integer.parseInt(statistics.getOrDefault("Games Played", "0")); 
        // Gets the number of games played from the statistics (or "0" if not found) and converts it from String to int

        int gamesWon = Integer.parseInt(statistics.getOrDefault("Games Won", "0")); 
        // Gets the number of games won from the statistics (or "0" if not found) and converts it to int

        int totalGuesses = Integer.parseInt(statistics.getOrDefault("Total Guesses", "0")); 
        // Gets the total number of guesses from the statistics (or "0" if not found) and converts it to int


        boolean continueProgram = true;

        System.out.println("=== NUMBER GUESSING GAME ===");

        while (continueProgram) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Check Statistics");
            System.out.println("2. Play Game");
            System.out.println("3. Clear Statistics");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            
            int choice = -1; 
            // This sets up a variable called 'choice' to hold the user's menu choice. It's starting at -1 as a default value.

            if (input.hasNextInt()) { 
                // This checks if the next thing the user types is a number.

                choice = input.nextInt(); 
                // If it's a number, it saves that number into the 'choice' variable.

            } else {
                input.next(); // consume invalid input 
                // If it's not a number (like a letter), this throws away the bad input so the program can keep going.
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- GAME STATISTICS ---");  
        // Print a title for the statistics section.

        if (statistics.isEmpty()) {  
            // Check if the statistics HashMap has no data (i.e., no games played yet)

            System.out.println("No game played yet.");  
            // If no data is found, inform the user that no games were played
        } else {  
            // If there is data in the statistics... c:

            for (String key : statistics.keySet()) {  
                // Loop through each key (like "Games Played", "Games Won", etc.) in the statistics

                System.out.println(key + ": " + statistics.get(key));  
                // Print each statistic in the format: key: value (Print every stats here by line)
            }
        }
               break;  
        // End of case 1. Go back to the menu after showing statistics.

                case 2:
                    int minNumber = 1, maxNumber = 100, maxAttempts = 10;  
    // These are the starting settings for the game:
        // The number to guess will be between 1 and 100, and the player gets 10 tries.
        // This is the default value
                    int difficultyLevel = 0;
                     // This will store the difficulty level the player chooses (like Easy, Medium, etc.)

                    System.out.println("\nSelect difficulty level:");
                    System.out.println("1. Easy (1-50, 15 attempts)");
                    System.out.println("2. Medium (1-100, 10 attempts)");
                    System.out.println("3. Hard (1-150, 7 attempts)");
                    System.out.println("4. Expert (1-200, 4 attempts)");

                    while (difficultyLevel < 1 || difficultyLevel > 4) {
                        // While the value of difficultyLevel is less than 1 OR greater than 4, continue prompting the user to enter a valid number between 1 and 4
                        System.out.print("Enter your choice (1-4): ");
                        if (input.hasNextInt()) {
                             // This checks if the user typed a number (like 1, 2, 3, or 4)

                            difficultyLevel = input.nextInt();
                             // If they did, we save that number in the variable 'difficultyLevel'

                            switch (difficultyLevel) {
                                case 1: maxNumber = 50; maxAttempts = 15; break;
                                case 2: maxNumber = 100; maxAttempts = 10; break;
                                case 3: maxNumber = 150; maxAttempts = 7; break;
                                case 4: maxNumber = 200; maxAttempts = 4; break;
                            }
                        } else {
                            System.out.println("Invalid input!");
                            input.next();
                        }
                    }

                    int secretNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;
                    // Generate a random number between minNumber and maxNumber ( random number is generated and how the range is adjusted)
                    // Without +1, the generated number will not include the upper bound (maxNumber)
                    // In this example, the 100 would not be a possible outcome, and the highest random number you can get is 99
                    System.out.println("\n--- Game Started ---");
                    // The \n is a newline character. It creates a line break before the next text starts, so the prompt appears on a new line.
                    System.out.println("I'm thinking of a number between " + minNumber + " and " + maxNumber);
                    System.out.println("You have " + maxAttempts + " attempts to guess it.");

                    int attempts = 0;
                    // sets the default value of the attempts variable to 0
                    boolean hasWon = false;
                    // wala pa nadaog/ did not won and the loop wont end and will not show the statistic

                    while (attempts < maxAttempts && !hasWon) {
                        // Continue looping as long as the number of attempts is less than the maximum allowed (maxAttempts)
                          // AND the player has not yet won the game (!hasWon)
                          // can be read as --> While the number of attempts is less than the maximum allowed attempts, and the player has not yet won
                        System.out.print("Attempt " + (attempts + 1) + "/" + maxAttempts + ": Enter your guess: ");
                        if (input.hasNextInt()) {
                            int guess = input.nextInt();
                            attempts++;
                            totalGuesses++;

                            if (guess < minNumber || guess > maxNumber) {
                                System.out.println("Out of range!");
                            } else if (guess < secretNumber) {
                                System.out.println("Too low!");
                            } else if (guess > secretNumber) {
                                System.out.println("Too high!");
                            } else {
                                System.out.println("Correct! You guessed it in " + attempts + " attempts.");
                                hasWon = true;
                                gamesWon++;
                            }
                        } else {
                            System.out.println("Invalid input!");
                            input.next();
                        }
                    }

                    if (!hasWon) {
                        System.out.println("Game Over! The number was: " + secretNumber);
                    }

                    totalGamesPlayed++;

                    int gamesLost = totalGamesPlayed - gamesWon;
                    double winRatio = (gamesLost > 0) ? (double) gamesWon / gamesLost : gamesWon;
                    double avgGuesses = (double) totalGuesses / totalGamesPlayed;

                    statistics.put("Games Played", String.valueOf(totalGamesPlayed));
                    statistics.put("Games Won", String.valueOf(gamesWon));
                    statistics.put("Games Lost", String.valueOf(gamesLost));
                    statistics.put("Win/Loss Ratio", String.format("%.2f", winRatio));
                    statistics.put("Average Guesses per Game", String.format("%.2f", avgGuesses));
                    statistics.put("Total Guesses", String.valueOf(totalGuesses));

                    saveStatistics(statistics);
                    break;

                case 3:

                    clearHistory();
                    statistics.clear();
                    totalGamesPlayed = 0;
                    gamesWon = 0;
                    totalGuesses = 0;
                    break;

                case 4:
                    continueProgram = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        System.out.println("\nThank you for playing!");
        input.close();
    }

    public static void clearHistory() {
        File file = new File("statistics.txt");
        if (!file.exists()) {
            System.out.println("No history file found.");
        } else if (file.delete()) {
            System.out.println("Game statistics cleared!");
        } else {
            System.out.println("Error clearing history.");
        }
    }

    public static void saveStatistics(HashMap<String, String> stats) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("statistics.txt"))) {
            for (String key : stats.keySet()) {
                writer.println(key + "=" + stats.get(key));
            }
        } catch (IOException e) {
            System.out.println("Error saving statistics.");
        }
    }

    public static HashMap<String, String> loadStatistics() {
        HashMap<String, String> stats = new HashMap<>();
        File file = new File("statistics.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader("statistics.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        stats.put(parts[0], parts[1]);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading statistics.");
            }
        }
        return stats;
    }
}
    
