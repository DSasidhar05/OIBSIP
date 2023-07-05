import java.util.*;
import java.util.Random;
import java.util.Scanner;

public class NGG {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Random random = new Random();

            int rangeStart = 1;
            int rangeEnd = 100;
            int maxAttempts = 10;
            int score = 0;
            boolean playAgain = true;

            System.out.println("Welcome to the Number Guessing Game!");

            while (playAgain) {
                int secretNumber = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
                int attempts = 0;
                boolean guessedCorrectly = false;

                System.out.println("I have generated a number between " + rangeStart + " and " + rangeEnd + ". Can you guess it?");

                while (!guessedCorrectly && attempts < maxAttempts) {
                    System.out.print("Enter your guess: ");
                    int guess = scanner.nextInt();
                    attempts++;

                    if (guess == secretNumber) {
                        guessedCorrectly = true;
                        score += (100 - attempts);

                        System.out.println("Congratulations! You guessed the number correctly.");
                        System.out.println("Score for this round: " + score);
                    } else if (guess < secretNumber) {
                        System.out.println("Wrong guess! The number is higher.");
                    } else {
                        System.out.println("Wrong guess! The number is lower.");
                    }
                }

                if (!guessedCorrectly) {
                    System.out.println("Oops! You couldn't guess the number.");
                    System.out.println("The number was: " + secretNumber);
                }

                System.out.print("Do you want to play again? (yes/no): ");
                String playAgainResponse = scanner.next();
                playAgain = playAgainResponse.equalsIgnoreCase("yes");
            }

            System.out.println("Thank you for playing the Number Guessing Game!");
            System.out.println("Total Score: " + score);
        }
    }
}
