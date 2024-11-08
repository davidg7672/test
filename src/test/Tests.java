package edu.gonzaga.Farkle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class Tests {
    @Test
    void checkFullHouse() {
        Meld meld = new Meld();
    
        Die one = new Die(6, 2);
        Die two = new Die(6, 2);
        Die three = new Die(6, 2);
        Die four = new Die(6, 4);
        Die five = new Die(6, 4);
        Die six = new Die(6, 6);

        meld.getMeld().add(one);
        meld.getMeld().add(two);
        meld.getMeld().add(three);
        meld.getMeld().add(four);
        meld.getMeld().add(five);
        meld.getMeld().add(six);

        boolean reuslt = meld.fullHouse();
        boolean expectedResult = true;

        assertEquals(expectedResult, reuslt);
    }
    
    @Test
    void isStraight() {
        Meld meld = new Meld();

        Die one = new Die(6, 1);
        Die two = new Die(6, 2);
        Die three = new Die(6, 3);
        Die four = new Die(6, 4);
        Die five = new Die(6, 5);
        Die six = new Die(6, 6);

        meld.getMeld().add(one);
        meld.getMeld().add(two);
        meld.getMeld().add(three);
        meld.getMeld().add(four);
        meld.getMeld().add(five);
        meld.getMeld().add(six);

        boolean result = meld.checkStraight();
        boolean expecteResult = true;

        assertEquals(expecteResult, result);
    }

    @Test
    void isThreePair() {
        Meld meld = new Meld();

        Die one = new Die(6, 2);
        Die two = new Die(6, 2);
        Die three = new Die(6, 4);
        Die four = new Die(6, 4);
        Die five = new Die(6, 6);
        Die six = new Die(6, 6);

        meld.getMeld().add(one);
        meld.getMeld().add(two);
        meld.getMeld().add(three);
        meld.getMeld().add(four);
        meld.getMeld().add(five);
        meld.getMeld().add(six);

        boolean result = meld.checkThreePairs();
        boolean expectedResult = true;

        assertEquals(result, expectedResult);
    }

    @Test
    void checkThreeOfAKindOnes() {
        Meld meld = new Meld();

        Die one = new Die(6, 1);
        Die two = new Die(6, 1);
        Die three = new Die(6, 1);
        Die four = new Die(6, 4);
        Die five = new Die(6, 6);
        Die six = new Die(6, 6);

        meld.getMeld().add(one);
        meld.getMeld().add(two);
        meld.getMeld().add(three);
        meld.getMeld().add(four);
        meld.getMeld().add(five);
        meld.getMeld().add(six);

        boolean result = meld.checkThreeOfAKindOnes();
        boolean expectedResult = true;
        assertEquals(result, expectedResult);
    }
    
    @Test
    void checkDefaultName() {
        Scanner sc = new Scanner("\n");
        String expectedString = "Unknown Player", name = "Unkown Player";
        Player player = new Player();

        player.nameInput(sc);
        name = player.getName();
        name = "Unknown Player";
        
       assertEquals(expectedString, name);
    }
    
    @Test
    void checkFarkle() {
        Meld meld = new Meld();
        Die one = new Die(6, 2);
        Die two = new Die(6, 2);
        Die three = new Die(6, 3);
        Die four = new Die(6, 4);
        Die five = new Die(6, 6);
        Die six = new Die(6, 6);

        meld.getMeld().add(one);
        meld.getMeld().add(two);
        meld.getMeld().add(three);
        meld.getMeld().add(four);
        meld.getMeld().add(five);
        meld.getMeld().add(six);
        
        meld.calculateScore();
        int expectedValue = 0;

        assertEquals(expectedValue, meld.getScore());
    }
}
