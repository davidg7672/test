package edu.gonzaga.Farkle;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * The FarkleGame class represents the main game logic and control for the Farkle game.
 * It manages player initialization, rounds, scoring, and game progression.
 * This class implements the game rules and interactions between players and the game environment.
 */
public class FarkleGame {
    private int maxScore;
    private int playerCount;
    private boolean endGame;
    private ArrayList<Player> playerList;

    /**
     * Constructs a new FarkleGame object with default settings.
     * Initializes the player list, sets the default maximum score, player count, and end game flag.
     */
    public FarkleGame() {
        playerList = new ArrayList<>();
        maxScore = 10000;
        playerCount = 1;
        endGame = true;
    }

    public void test() {
        Meld meld = new Meld();
        
        Die one = new Die(6, 1);
        Die two = new Die(6, 1);
        Die three = new Die(6, 1);
        Die four = new Die(6, 4);
        Die five = new Die(6, 4);
        Die six = new Die(6, 6);

        meld.getMeld().add(one);
        meld.getMeld().add(two);
        meld.getMeld().add(three);
        meld.getMeld().add(four);
        meld.getMeld().add(five);
        meld.getMeld().add(six);

        meld.calculateScore();
    }
    
    /**
     * Starts the Farkle game by displaying the start menu, initializing players, and handling rounds.
     * This method manages the game loop until the end game condition is met.
     */
    public void startGame() {
        Scanner sc = new Scanner(System.in);
        startMenu(sc);

        while(true) {
            initilizePlayers();
            for(Player player: playerList) {
                round(sc, player);
            }
            System.out.println("\nCurrent Total Points:");
            displayPlayersInformation();
            playerRoundFinished(sc);
        }
    }

    /**
     * Handles the end of a player's round, prompting for user input to continue to the next round.
     *
     * @param sc Scanner object for reading input
     */
    public void playerRoundFinished(Scanner sc) {
        System.out.println("\nPress Any Key to Continue");
        sc.nextLine();
        sc.nextLine();
    }

    /**
     * Manages a round of the game for a specific player.
     * It handles player actions such as rolling dice, making selections, and updating the game state.
     *
     * @param sc Scanner object for reading player input
     * @param player The player participating in the round
     */
    public void round(Scanner sc, Player player) {
        Menu menu = new Menu();
        boolean outer = false, inner = false;
        boolean[] alreadyPressed = {
            false, false, false, false, false, false
        };

        menu.initialMenu(player);
        
        // checking for roll die farkle
        if(detectFarkle(sc, player)) {
            return;
        }

        while(!outer) {
            Meld meld = new Meld();
            player.getMeldList().add(meld);
            inner = false;
            do {
                // check input
                char letter = sc.next().charAt(0);
                letter = Character.toUpperCase(letter);

                if(validateInput(letter)) {
                    updateRound(player, meld, letter, alreadyPressed);
                    menu.updateMenu(player, meld, alreadyPressed);
                } else if(letter == 'K') {
                    player.removeDiceFromPlayer(meld);
                    calculateTotalScore(sc, player);
                    inner = true;
                    outer = true;
                } else if(letter == 'R') {
                    if(checkHotHand(player)) {
                        char input = hotHand(sc);
                        if(input == 'R') {
                            player.hotHandReroll();
                            inner = true;
                            menu.displayMenu(player);
                        } else if(input == 'K') {
                            menu.displayMenu(player);
                        }
                        break;
                    }
                    
                    player.removeDiceFromPlayer(meld);
                    player.reroll();
                    
                    if(detectFarkle(sc, player)) {
                        outer = true;
                        player.setScore(0);
                        System.out.println("Farkle Was detected");

                        break;
                    }
                    
                    for(int i = 0; i < 6; i++) {
                        alreadyPressed[i] = false;
                    }

                    inner = true;
                    calculateTotalScore(sc, player);
                    menu.displayMenu(player);
                } else if(letter == 'Q') {
                    quit(sc);
                } else {
                    System.out.println("Invalid Input");
                }
            } while(!inner);
        }
    }

    /**
     * Updates the current round based on player input, selections, and game state.
     * This method is called within the round method to manage player actions during the round.
     *
     * @param player The player participating in the round
     * @param meld The meld object associated with the player's current selections
     * @param letter The letter corresponding to the player's action/input
     * @param alreadyPressed Array indicating which dice have already been selected
     */
    public void updateRound(Player player, Meld meld, char letter, boolean[] alreadyPressed) {
        int index = letter - 'A';

        if(!alreadyPressed[index]) {
            alreadyPressed[index] = true;
            meld.addToMeld(player, index);
        } else {
            alreadyPressed[index] = false;
            meld.removeFromMeld(player, player.getDice().get(index));
        }
        meld.calculateScore();
    }

    /**
     * Validates player input during the round to ensure it corresponds to valid actions.
     *
     * @param letter The letter corresponding to the player's input/action
     * @return True if the input is valid; otherwise, false
     */
    public boolean validateInput(char letter) {
        if(letter >= 'A' && letter <= 'F') {
            return true;
        }
        return false;
    }

    /**
     * Calculates the total score for a player based on their melds and updates the player's score.
     * If the player reaches the maximum score and the end game flag is set, it triggers the end game sequence.
     *
     * @param sc Scanner object for reading input
     * @param player The player whose score needs to be calculated and updated
     */
    public void calculateTotalScore(Scanner sc, Player player) {
        int totalScore = 0;
        
        for(Meld meld: player.getMeldList()) {
            totalScore += meld.getScore();
        }
        player.setScore(totalScore);

        if(totalScore >= maxScore && endGame) {
            endGame = false;
            initilizePlayers();
            endGame(sc, player);
            System.exit(0);
        }
    }

    /**
     * Handles the end game sequence, including final rounds for each player and determining the winner.
     *
     * @param sc Scanner object for reading input
     * @param skip The player to skip during the final rounds (current leader)
     */
    public void endGame(Scanner sc, Player skip) {
        System.out.println("\nEnd Game and Final Round!");
        System.out.println("Current Leader: " + skip.getName());
        System.out.println("Current Score: " + skip.getScore() + "\n");
        System.out.println("Press Any Key To Continue");
        sc.nextLine();
        sc.nextLine();

        for(Player player: playerList) {
            if(player == skip) {
                continue;
            }
            round(sc, player);
        }

        for(Player player: playerList) {
            calculateTotalScore(sc, player);
        }
        
        Player winner = playerList.get(0);
        for(Player player: playerList) {
            if(player.getScore() > winner.getScore()) {
                winner = player;
            }
        }

        System.out.flush();
        System.out.println("Final Scores");
        displayPlayersInformation();
        System.out.println("\nWinner: " + winner.getName());
        System.out.println("Total Score: " + winner.getScore());
    }

    /**
     * Quits the game by closing the scanner and exiting the program.
     *
     * @param sc The Scanner object used for input.
     */
    public void quit(Scanner sc) {
        sc.close();
        System.exit(0);
    }

    /**
     * Displays the start menu for the game, prompting the user to start or quit the game.
     * It also prompts for game settings such as maximum score and number of players.
     *
     * @param sc The Scanner object used for input.
     */
    public void startMenu(Scanner sc) {
        char letter = 'a';
        boolean done = true;
        
        System.out.print("\033[H\033[2J");
        System.out.println("************************************************************************");
        System.out.println("*                                                                      *");
        System.out.println("*                                                                      *");
        System.out.println("*                                                                      *");        
        System.out.println("*               Zag Farkle by David Sosa!                              *");
        System.out.println("*               Copyright: 2024                                        *");
        System.out.println("*                                                                      *");
        System.out.println("*                                                                      *");
        System.out.println("*                                                                      *");
        System.out.println("************************************************************************");
        System.out.println("\n");

        do {
            System.out.println("Press A) to continue");
            System.out.println("Press Q) to quit");
            letter = sc.next().charAt(0);
            letter = Character.toUpperCase(letter);

            if(letter == 'A') {
                done = false;
            } else if(letter == 'Q') {
                sc.close();
            } else {
                System.out.println("Invalid Input");
            }
        } while(done);

        promptScore(sc);
        promptPlayers(sc);
        promptPlayerNames(sc);
    }

    /**
     * Initializes the players for the game by clearing their dice and rolling new dice.
     */
    public void initilizePlayers() {
        // removes any die from the players dice
        for(Player player: playerList) {
            player.getDice().clear();
        }

        for(Player player: playerList) {
            player.rollDice();
        }
    }

    /**
     * Prompts the user to enter the maximum score to play to.
     *
     * @param sc The Scanner object used for input.
     */
    public void promptScore(Scanner sc) {
        int score = 0;
        boolean validInput = false;
        
        System.out.println("How many points would like to play to this time?");
        sc.nextLine();

        while (!validInput) {
            String userInput = sc.nextLine();

            if(userInput.isEmpty()) {
                this.maxScore = 10000;
                validInput = true;
            } else {
                try {
                    score = Integer.parseInt(userInput);

                    if(score >= 0) {
                        this.maxScore = score;
                        validInput = true;
                    } else {
                        System.out.println("Invalid Input. Enter a positive integer or hit enter");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input. Please enter a valid integer or hit enter");
                }
            }
        }
    }
    
    /**
     * Prompts the user to enter the number of players for the game.
     *
     * @param sc The Scanner object used for input.
     */
    public void promptPlayers(Scanner sc) {
        int players = 0;
        boolean validInput = false;
        
        System.out.println("How many players are going to be playing?");

        while (!validInput) {
            String userInput = sc.nextLine();

            if(userInput.isEmpty()) {
                this.playerCount = 1;
                validInput = true;
            } else {
                try {
                    players = Integer.parseInt(userInput);

                    if(players >= 0) {
                        this.playerCount = players;
                        validInput = true;
                    } else {
                        System.out.println("Invalid Input. Enter a positive integer or hit enter");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input. Please enter a valid integer or hit enter");
                }
            }
        }
    }
    
    /**
     * Prompts the user to enter names for each player in the game.
     *
     * @param sc The Scanner object used for input.
     */
    public void promptPlayerNames(Scanner sc) {
        int counter = 1;
        for(int i = 1; i <= playerCount; i++) {
            System.out.println("What is the player #" + i + "'s name?");
            String name = sc.nextLine();

            if(name.isEmpty()) {
                name = "Unkown Player #" + counter;
                counter++;
            }

            Player player = new Player();
            player.setName(name);
            playerList.add(player);
        }
    }

    /**
     * Displays the information of all players in the game, including their names and scores.
     */
    public void displayPlayersInformation() {
        for(Player player: playerList) {
            System.out.println(player.getName() + " : " + player.getScore());
        }
    }

    /**
     * Detects if a Farkle condition has occurred for a player's current dice roll.
     *
     * @param sc     The Scanner object used for input.
     * @param player The player whose dice roll is being checked for Farkle.
     * @return True if Farkle is detected, false otherwise.
     */
    public boolean detectFarkle(Scanner sc, Player player) {
        Meld temp = new Meld();

        for(int i = 0; i < player.getDice().size(); i++) {
            temp.addToMeld(player, i);
        }

        temp.calculateScore();
        if(temp.getScore() == 0) {
            System.out.println("\nFARKLE");
            System.out.print("You rolled: ");
            player.displayDice();
            return true;
        }

        return false;
    }

    /**
     * Checks if a player is in a "Hot Hand" state, where they can roll again due to scoring dice.
     *
     * @param player The player being checked for a "Hot Hand."
     * @return True if the player is in a "Hot Hand" state, false otherwise.
     */
    public boolean checkHotHand(Player player) {
        if(player.getDice().size() == 0) {
            for(Meld meld: player.getMeldList()) {
                if(meld.getScore() > 0) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the "Hot Hand" scenario, where a player can choose to roll again or end their turn.
     *
     * @param sc The Scanner object used for input.
     * @return The user's choice of action: 'R' for roll again, 'K' for bank and end turn.
     */
    public char hotHand(Scanner sc) {
        boolean done = false;
        char letter = 'a';
        System.out.println("***** HOT HAND! ****");
        System.out.println("Would you like to roll 6 new dice, or bank and end your turn?");
        System.out.println("************************\n");
        System.out.println("(K) Bank Total and End Your Turn");
        System.out.println("(R) Roll 6 New Dice and Bank Score");
        
        while (!done) {
            letter = sc.next().charAt(0);
            letter = Character.toUpperCase(letter);
            if(letter == 'K') {
                done = true;
            }
            else if(letter == 'R') {
                done = true;
            } else {
                System.out.println("Invalid Input");
            }
        }
        return letter;
    }

    /**
     * Getter method to check if the game has ended.
     *
     * @return True if the game has ended, false otherwise.
     */
    public boolean getEndGame() {
        return endGame;
    }
}
