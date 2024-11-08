package edu.gonzaga.Farkle;

/**
 * The Menu class handles everything when it comes to display output onto the terminal, updating
 * the instance of the Farkle game fluidly.
 */
public class Menu {
    /**
     * Displays the initial menu when it's the player's turn.
     *
     * @param player the player whose turn it is
     */
    public void initialMenu(Player player) {
        System.out.println("\n" + player.getName() + ", it's your turn! Rolling Dice...\n");
        System.out.print("Your Die are: ");
        player.displayDice();

        System.out.println("*************************** Current hand and meld *******************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("------------+---------------");
        for(int i = 0; i < player.getDice().size(); i++) {
            char opt = (char)('A' + i);

            System.out.print(" (" + opt + ")    " + player.getDice().get(i).getSideUp() + "   |\n");
        }
        System.out.println("------------+---------------"); 
        System.out.println("Meld Score: 0");
        System.out.println("Total Score: " + player.getScore() + "\n");
        System.out.println("(K) Bank Meld & End Round");
        System.out.println("(Q) Quit Game\n");
        System.out.println("Enter letter for your chioce: ");        
    }

    /**
     * Displays the menu during the game.
     *
     * @param player the player whose turn it is
     */
    public void displayMenu(Player player) {
        System.out.print("Your Die are: ");
        player.displayDice();

        System.out.println("*************************** Current hand and meld *******************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("------------+---------------");
        for(int i = 0; i < player.getDice().size(); i++) {
            char opt = (char)('A' + i);

            System.out.print(" (" + opt + ")    " + player.getDice().get(i).getSideUp() + "   |\n");
        }
        System.out.println("------------+---------------"); 
        System.out.println("Meld Score: 0");
        System.out.println("Total Score: " + player.getScore() + "\n");
        System.out.println("(K) Bank Meld & End Round");
        System.out.println("(Q) Quit Game\n");
        System.out.println("Enter letter for your chioce: ");        
    }

    /**
     * Updates the menu based on player actions and game state.
     *
     * @param player        the player whose turn it is
     * @param meld          the meld formed by the player
     * @param alreadyPressed an array indicating which dice have already been selected
     */
    public void updateMenu(Player player, Meld meld, boolean[] alreadyPressed) {
        System.out.print("Your Die are: ");
        player.displayDice();

        System.out.println("*************************** Current hand and meld *******************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("------------+---------------");
        for(int i = 0; i < player.getDice().size(); i++) {
            char opt = (char)('A' + i);

            System.out.print(" (" + opt + ")    ");

            if(!alreadyPressed[i]) {
                System.out.println(player.getDice().get(i).getSideUp() + "   |");
            } else {
                System.out.println("    |     "  + player.getDice().get(i).getSideUp());
            }
        }
        System.out.println("------------+---------------"); 
        System.out.println("Meld Score: " + meld.getScore());
        System.out.println("Total Score: "  + player.getScore());
        System.out.println("(K) End Round");
        System.out.println("(R) Bank Meld and Reroll");
        System.out.println("(Q) Quit Game\n");
        System.out.println("Enter letter for your chioce: ");
    }
}