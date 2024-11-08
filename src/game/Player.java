package edu.gonzaga.Farkle;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Player class represents a player in the Farkle game.
 */
public class Player {
    private String name;
    private int score;
    private ArrayList<Die> dice;
    private ArrayList<Meld> meldList;
    
    /**
     * Constructor for the player
     */
    public Player() {
        name = "";
        score = 0;
        dice = new ArrayList<>();
        meldList = new ArrayList<>();
    }

    /**
     * Copy constructor that is used when checkfing for a Farkle. It copies one object to become another
     * 
     * @see FarklGame class
     */
    public Player(Player otherPlayer) {
        this.name = otherPlayer.name;
        this.score = otherPlayer.score;
        this.dice = new ArrayList<>(otherPlayer.dice.size());
        
        for(int i = 0; i < 6; i++) {
            this.dice.add(otherPlayer.getDice().get(i));
        }
    }

    /**
     * This is a setter that sets the name of a player
     * 
     * @param name - name of the current player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is a getter that returns players name
     * 
     * @return name - Player's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the score of the game to the specified value.
     *
     * @param score The new score for the game.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retrieves the current score of the game.
     *
     * @return The current score of the game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves the list of dice objects in the game.
     *
     * @return An ArrayList containing the dice objects.
     */
    public ArrayList<Die> getDice() {
        return dice;
    }

    /**
     * Rolls the dice, generating random values for each die and updating the dice list.
     */
    public void rollDice() {
        for(int i = 0; i < 6; i++) {
            Die die = new Die();
            die.roll();
            dice.add(die);
        }
        sortDice();
    }

    /**
     * Prints the current configuration of dice in the game.
     */
    public void displayDice() {
        for(int i = 0; i < dice.size(); i++) {
            System.out.print(dice.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Sorts the dice in the game in ascending order based on their current values.
     */
    public void sortDice() {
        for(int i = 0; i < dice.size(); i++) {
            for(int j = 0; j < dice.size() - i - 1; j++) {
                if(dice.get(j) == null || dice.get(j + 1) == null) {
                    continue;
                } else if(dice.get(j).getSideUp() > dice.get(j + 1).getSideUp()) {
                    Die temp = dice.get(j);
                    dice.set(j, dice.get(j + 1));
                    dice.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Removes dice from the player's hand based on a meld.
     * 
     * @param meld the meld to be removed from the player's hand
     */
    public void removeDiceFromPlayer(Meld meld) {
        for(Die die: meld.getMeld()) {
            dice.remove(die);
        }
    }

    public void hotHandReroll() {
        if(dice.size() == 0) {
            for(int i = 0; i < 6; i++) {
                Die die = new Die();
                die.roll();
                dice.add(die);
            }
        }
    }
    
    /**
     * Rerolls the dice that are not null in the current configuration.
     * After rerolling, the dice are sorted in ascending order.
     */
    public void reroll() {        
        for(int i = 0; i < dice.size(); i++) {
            Die die = new Die();
            die.roll();
            dice.set(i, die);
        }
        sortDice();
    }

    /**
     * This method reads from input and sets name
     * 
     * @param sc Scanner
     */
    public void nameInput(Scanner sc) {
        name = sc.nextLine();
    }

    /**
     * This methods retunrs the meld list, which is a list of meld, this is done to keep track of score
     * 
     * @return ArrayList of Melds
     */
    public ArrayList<Meld> getMeldList() {
        return meldList;
    }
}
