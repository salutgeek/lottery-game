package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LotteryGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = generateWelcomeMessage(scanner);

        if (choice == 3) {
            System.out.println("Goodbye...");
            return;
        }

        List<Integer> winningNumbers = generateWinningNumbers();
        
        int betAmount = getPlayerBetAmount(scanner); 
        
        List<Integer> playerNumbers = getPlayerNumbers(scanner);
        System.out.println("----------------------------------------------------------");

        if (choice == 1) {
            System.out.println("The winning numbers are: " + winningNumbers);
            System.out.println("Your guessed numbers are: " + playerNumbers);

            int matchedNumbersCount = countMatchedNumbers(playerNumbers, winningNumbers);
            int reward = calculateReward(matchedNumbersCount, betAmount);

            System.out.println("Your matched numbers are: " + playerNumbers);
            System.out.println("You matched " + matchedNumbersCount + " numbers.");
            System.out.println("Your reward is: $" + reward);
        } else if (choice == 2) {
            Random random = new Random();
            int luckyNumber = random.nextInt(10) + 1;

            int playerLuckyNumber = getPlayerLuckeyNumber(scanner);

            System.out.println("The winning numbers are: " + winningNumbers);
            System.out.println("Your guessed numbers are: " + playerNumbers);

            System.out.println("The lucky number is: " + luckyNumber);
            int matchedNumbersCount = countMatchedNumbers(playerNumbers, winningNumbers);
            int reward = calculateSuperBetReward(matchedNumbersCount, betAmount, playerLuckyNumber, luckyNumber);
            System.out.println("You matched " + matchedNumbersCount + " numbers.");

            System.out.println("Your reward is: $" + reward);
        }

        scanner.close();    
    }

    private static int generateWelcomeMessage(Scanner scanner) {
        String s = new StringBuilder()
        .append("Welcome to Tolo Lottery Game!\n\n")
        .append("In regular Bet Game, you'll bet and guess the numbers of a lottery draw.\n")
        .append("A draw consists of 4 different numbers.\n")
        .append("Each number is in a range between 1 and 20.\n")
        .append("If you can guess 3 numbers, the reward is 5 times your bet amount!\n")
        .append("If you can guess 4 numbers, the reward is 50 times your bet amount!\n\n")
        .append("You can also play the Super Bet Game where you'll guess a lucky number!\n")
        .append("And if you can guess the lucky number, your reward is 5 times what you earned in regular Bet Game!\n\n")
        .append("GLHF!\n")
        .append("----------------------------------------------------------------------------")
        .toString();
        System.out.println(s);
        System.out.println("\nChoose your game type:");
        System.out.println("1. Bet");
        System.out.println("2. Super Bet");
        System.out.println("3. Quit");

        Map<Integer, String> choiceMapping = new HashMap<Integer, String>();
        choiceMapping.put(1, "Bet Game");
        choiceMapping.put(2, "Super Bet Game");
        choiceMapping.put(3, "Quit");

        int choice;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) {
                    System.out.println("--> You've chosen: " + choiceMapping.get(choice));
                    System.out.println("----------------------------------------------------------");
                    return choice;
                } 
                else {
                    System.out.println("The choice must be 1 or 2 or 3 !!");
                }
            }
            catch (NumberFormatException nfe){
                System.out.println("It's not even a whole number dude !!"); 
            }
        }
    }

    private static List<Integer> generateWinningNumbers () {
        List<Integer> winningNumbers = new ArrayList<>();
        Random random = new Random();

        while (winningNumbers.size() < 4) {
            int winningNumber = random.nextInt(20) + 1;
            if (!winningNumbers.contains(winningNumber)) {
                winningNumbers.add(winningNumber);
            }
        }
        return winningNumbers;
    }

    private static List<Integer> getPlayerNumbers (Scanner scanner) {
        List<Integer> playerNumbers = new ArrayList<>();

        System.out.println("Please enter your 4 numbers between 1 and 20 (inclusive)");

        while (playerNumbers.size() < 4) {
            System.out.println("--> Your current guessed numbers are: " + playerNumbers);
            System.out.print("Enter a number between 1 and 20: ");
            try {
                int playerNumber = Integer.parseInt(scanner.nextLine());
                if (playerNumber >= 1 && playerNumber <= 20) {
                    playerNumbers.add(playerNumber);
                } 
                else {
                    System.out.println("The number must be between 1 and 20!!");
                }
            }
            catch (NumberFormatException nfe){
                System.out.println("It's not a whole number, please try again!!"); 
            }
        }
        return playerNumbers;
    }

    private static int getPlayerBetAmount(Scanner scanner) {
        int betAmount;
        while (true) {
            try {
                System.out.print("Enter the amount of money you want to bet: ");
                betAmount = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch (NumberFormatException nfe){
                System.out.println("It's not even a whole number dude !!"); 
            }
        }

        System.out.println("--> Your bet: " + betAmount + "$");
        return betAmount;
    }

    private static int getPlayerLuckeyNumber(Scanner scanner) {
        int playerLuckyNumber;
        while (true) {
            try {
                System.out.print("Enter your lucky number (1-10): ");
                playerLuckyNumber = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch (NumberFormatException nfd) {
                System.out.println("It's not even a whole number dude !!"); 
            }
        }
        return playerLuckyNumber;
    }

    private static int countMatchedNumbers(List<Integer> playerNumbers, List<Integer> winningNumbers) {
        playerNumbers.retainAll(winningNumbers);
        return playerNumbers.size();
    }

    private static int calculateReward(int matchedNumbers, int betAmount) {
        switch (matchedNumbers) {
            case 4:
                return 50 * betAmount;
            case 3:
                return 5 * betAmount;
            default:
                return 0;
        }
    }

    private static int calculateSuperBetReward(int matchedNumbers, int betAmount, int playerLuckyNumber, int luckyNumber) {
        if (playerLuckyNumber == luckyNumber) {
            System.out.println("You matched the lucky number!");
            return 5 * calculateReward(matchedNumbers, betAmount);
        } else {
            System.out.println("You did not match the lucky number.");
            return calculateReward(matchedNumbers, betAmount);
        }
    }
}