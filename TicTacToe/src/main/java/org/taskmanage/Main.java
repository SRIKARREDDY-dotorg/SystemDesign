package org.taskmanage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Srikar", 'O');
        Player player2 = new Player("Sriya", 'X');
        Game game = new Game(player1, player2);
        game.play();
    }
}