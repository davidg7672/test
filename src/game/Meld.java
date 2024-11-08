package edu.gonzaga.Farkle;

import java.util.ArrayList;

/**
 * The Meld class represents a combination of dice in the Farkle game.
 */
public class Meld {
    private int score;
    private ArrayList<Die> meld;

    /**
     * Constructor for creating a new meld object.
     */
    public Meld() {
        score = 0;
        meld = new ArrayList<>();
    }

    /**
     * Retrieves the list of dice in the meld.
     *
     * @return an ArrayList containing the dice in the meld
     */
    public ArrayList<Die> getMeld() {
        return meld;
    }

    /**
     * Retrieves the score of the meld.
     *
     * @return the score of the meld
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the meld to the specified value.
     *
     * @param score the new score for the meld
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Adds a die from the player's hand to the meld at the specified index.
     *
     * @param player the player whose die is being added to the meld
     * @param index  the index of the die in the player's hand to be added to the meld
     */
    public void addToMeld(Player player, int index) {
        meld.add(player.getDice().get(index));
    }

    /**
     * Removes a die from the meld.
     *
     * @param player the player whose die is being removed from the meld
     * @param die    the die to be removed from the meld
     */
    public void removeFromMeld(Player player, Die die) {
        meld.remove(die);
    }

    /**
     * Sorts the dice in the meld in ascending order based on their current values.
     */
    public void sortMeld() {
        for(int i = 0; i < meld.size(); i++) {
            for(int j = 0; j < meld.size() - i - 1; j++) {
                if(meld.get(j) == null || meld.get(j + 1) == null) {
                    continue;
                } else if(meld.get(j).getSideUp() > meld.get(j + 1).getSideUp()) {
                    Die temp = meld.get(j);
                    meld.set(j, meld.get(j + 1));
                    meld.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Displays the current configuration of dice in the meld.
     */
    public void displayMeld() {
        sortMeld();
        for(int i = 0; i < meld.size(); i++) {
            System.out.print(meld.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Calculates the score of the current meld based on various combinations of dice.
     */
    public void calculateScore() {
        score = 0;
        sortMeld();

        boolean threeOfOneOfAKind = false;

        if(fullHouse()) {
            return;
        }

        if(checkStraight() || checkThreePairs()) {
            return;
        }
        threeOfOneOfAKind = checkThreeOfAKindOnes();

        if(threeOfOneOfAKind) {
            checkThreeOfAKind();
            checkSpecialOnes();
            checkFives();
        } else {
            if(!checkSixOfAKind()) {
                if(!checkFiveOfAKind()) {
                    if(!checkFourOfAKind()) {
                        if(!checkThreeOfAKind()) {
                        }
                    }
                }
            }
            checkOnes();
            checkFives();
        }
    }

    /**
    * Checks for special ones in the meld and adds 100 points for each special one.
    */
    public void checkSpecialOnes() {
        for(int i = 4; i < meld.size(); i++) {
            if(meld.get(i) == null) {
                continue;
            }
            if(meld.get(i).getSideUp() == 1) {
                score += 100;
            }
        }
    }
    /**
    * Checks for three of a kind with ones and adds 1000 points if present.
    *
    * @return True if three of a kind with ones is present, false otherwise.
     */
    public boolean checkThreeOfAKindOnes() {
        for(int i = 0; i < meld.size() - 2; i++) {
            if(meld.get(i) != null && meld.get(i + 1) != null && meld.get(i + 2) != null) {
                if(meld.get(i).getSideUp() != 1) {
                    return false;
                }
                if(meld.get(i).getSideUp() == meld.get(i + 1).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 2).getSideUp()) {
                    score += 1000;
                    return true;
               }
            }
        }
        return false;
    }
    
    /**
    * Checks for three of a kind and adds points based on the value of the dice.
    *
    * @return True if three of a kind is present, false otherwise.
    */
    public boolean checkThreeOfAKind() {
        for(int i = 0; i < meld.size() - 2; i++) {
            if(meld.get(i) != null && meld.get(i + 1) != null && meld.get(i + 2) != null) {
                if(meld.get(i).getSideUp() == meld.get(i + 1).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 2).getSideUp()) {
                    if(meld.get(i).getSideUp() == 1) {
                        return false;
                    }
                    score += 100 * meld.get(i).getSideUp();
                    return true;
               }
            }
        }
        return false;
    }

    /**
    * Checks for four of a kind and adds points based on the value of the dice.
    *
    * @return True if four of a kind is present, false otherwise.
    */
    public boolean checkFourOfAKind() {
        for(int i = 0; i < meld.size() - 3; i++) {
            if(meld.get(i) != null && meld.get(i + 1) != null && meld.get(i + 2) != null && meld.get(i + 3) != null) {
                if(meld.get(i).getSideUp() == meld.get(i + 1).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 2).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 3).getSideUp()) {
                    score += 100 * meld.get(i).getSideUp() + meld.get(i).getSideUp() * 100;
                    return true;
                }
            }
        }   
        return false;
    }


    /**
    * Checks for five of a kind and adds points based on the value of the dice.
    *
    * @return True if five of a kind is present, false otherwise.
    */  
    public boolean checkFiveOfAKind() {
        for(int i = 0; i < meld.size() - 4; i++) {
            if(meld.get(i) != null && meld.get(i + 1) != null && meld.get(i + 2) != null && meld.get(i + 3) != null && meld.get(i + 4) != null) {
                if(meld.get(i).getSideUp() == meld.get(i + 1).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 2).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 3).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 4).getSideUp()) {
                    score += 100 * meld.get(i).getSideUp() + meld.get(i).getSideUp() * 100 + meld.get(i).getSideUp() * 100;
                    return true;
                }
            }
        }   
        return false;
    }

    /**
    * Checks for six of a kind and adds points based on the value of the dice.
    *
    * @return True if six of a kind is present, false otherwise.
    */
    public boolean checkSixOfAKind() {
        for(int i = 0; i < meld.size() - 5; i++) {
            if(meld.get(i) != null && meld.get(i + 1) != null && meld.get(i + 2) != null && meld.get(i + 3) != null && meld.get(i + 4) != null && meld.get(i + 5) != null) {
                if(meld.get(i).getSideUp() == meld.get(i + 1).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 2).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 3).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 4).getSideUp() && meld.get(i).getSideUp() == meld.get(i + 5).getSideUp()) {   
                    score += (100 * meld.get(i).getSideUp()) + (meld.get(i).getSideUp() * 100) + (meld.get(i).getSideUp() * 100) + (meld.get(i).getSideUp() * 100);
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * Checks for three pairs and adds 750 points if present.
    *
    * @return True if three pairs are present, false otherwise.
     */
    public boolean checkThreePairs() {
        int pairCount = 0;

        for(int i = 0; i < meld.size() - 1; i++) {
            if(meld.get(i) != null && meld.get(i + 1) != null) {
                if(meld.get(i).getSideUp() == meld.get(i + 1).getSideUp()) {
                    i++;
                    pairCount++;
                }
            }
        }

        if(pairCount == 3) {
            score += 750;
        }
        
        return pairCount == 3;
    }

    /**
    * Checks for a straight in the meld, where each die has a consecutive side value starting from 1.
    * If a straight is found, it adds 1000 points to the meld score.
    *
    * @return True if a straight is present in the meld; otherwise, false.
    */
    public boolean checkStraight() {
        if(meld.size() < 6) {
            return false;
        }
        for(int i = 0; i < meld.size(); i++) {
            if(meld.get(i) == null) {
                return false;
            }
            if(meld.get(i).getSideUp() != i + 1) {
                return false;
            }
        }
        score += 1000;
        return true;
    }

    /**
    * Checks for ones in the meld and adds 100 points for each one found to the meld score.
     */
    public void checkOnes() {
        for(int i = 0; i < meld.size(); i++) {
            if(meld.get(i) == null) {
                continue;
            } else if(meld.get(i).getSideUp() == 1) {
                score += 100;
            }
        }
    }

    /**
    * Checks for fives in the meld and adds 50 points for each five found to the meld score.
    */
    public void checkFives() {
        for(int i = 0; i < meld.size(); i++) {
            if(meld.get(i) == null) {
                continue;
            } else if(meld.get(i).getSideUp() == 5) {
                score += 50;
            }
        }
    }

    /**
    * Checks the meld for a pair and returns the value of the pair if found.
    *
    * @return the value of the pair if found, otherwise 0
    */
    public int checkPair(int n) {
        int[] values = new int[6];

        for(int i = 0; i < meld.size(); i++) {
            values[i] = meld.get(i).getSideUp();
        }

        for(int i = 1; i < values.length; i++) {
            for(int j = 0; j < i; j++) {
                if(values[i] == values[j]) {
                    if(values[i] == n) {
                        continue;
                    }
                    return values[i];
                }
            }
        }
        return 0;
    }

    /**
     * Checks the meld for three of a kind and returns the value of the dice if found.
     *
     * @return the value of the dice in the three of a kind if found, otherwise 0
    */
    public int checkThreeOfAKindFullHouse() {
        int[] values = new int[6];

        for(int i = 0; i < meld.size(); i++) {
            values[i] = meld.get(i).getSideUp();
        }

        for(int i = 0; i < meld.size() - 2; i++) {
            if(values[i] == values[i + 1] && values[i] == values[i + 2]) {
                // returning back the three of a kind number
                return values[i];
            }
        }

        // returning 0, indicating that we didn't find the value
        return 0;
    }

    /**
    * Checks if the meld is a full house (three of a kind and a pair) and updates the score accordingly.
    *
    * @return true if the meld is a full house, false otherwise
    */
    public boolean fullHouse() {
        int three = checkThreeOfAKindFullHouse();
        int pair = checkPair(three);

        if(three != 0 && pair != 0 && three != pair) {
            score += 1500;
            return true;
        }
        return false;
    }
}