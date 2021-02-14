package ui;

import model.Game;
import model.GameList;

import java.util.Scanner;

// Citation: Based on the CPSC 210 TellerApp Example
// Main Menu application
public class MainMenuApp {
    private GameList savedGames;
    private Scanner input;

    // EFFECTS: Runs main menu
    public MainMenuApp() {
        runMenu();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMenu() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nUntil next time...!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("s")) {
            selectGame();
        } else if (command.equals("c")) {
            createNewGame();
        } else if (command.equals("d")) {
            deleteGame();
        } else {
            System.out.println("Please enter a valid selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes games
    private void init() {
        savedGames = new GameList();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("Main Menu");
        System.out.println("\ts - Select Existing Game");
        System.out.println("\tc - Create New Game");
        System.out.println("\td - Delete Game");
        System.out.println("\tq - Quit Game");
    }

    // EFFECTS: allows user to select an existing saved game and launches it
    private void selectGame() {
        String selection;

        if (savedGames.size() == 0) {
            System.out.println("You currently have no saved games.");
        } else {
            System.out.println("Name of game:");
            selection = input.next();
            if (savedGames.hasGame(selection)) {
                System.out.println("Launching " + selection + ".");
            } else {
                System.out.println("There is no existing game with that name.");
            }
        }
    }

    // EFFECTS: allows user to create a new game
    private void createNewGame() {
        Game g = new Game("Untitled");
        System.out.println("Name:");
        String name = input.next();
        if (savedGames.hasGame(name)) {
            System.out.println("There exists a game with that name. Please select another one.");
            createNewGame();
        } else {
            g.setName(name);
            savedGames.addGame(g);
            System.out.println("New game created: " + name);
        }
    }

    // EFFECTS: allows user to delete a saved game
    private void deleteGame() {
        String selection;

        if (savedGames.size() == 0) {
            System.out.println("You currently have no saved games.");
        } else {
            System.out.println("Name of game:");
            selection = input.next();
            if (savedGames.hasGame(selection)) {
                Game g = savedGames.getGameWithName(selection);
                savedGames.deleteGame(g);
                System.out.println(selection + " successfully deleted.");
            } else {
                System.out.println("There is no existing game with that name.");
            }
        }
    }
}
