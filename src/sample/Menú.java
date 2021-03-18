package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Menú extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Scanner scanner = new Scanner(System.in);
    Game1 game1;
    Game2 game2;
    Game3 game3;

    public void MenuExample(Game1 game1, Game2 game2, Game3 game3) {
        this.game1 = game1;
        this.game2 = game2;
        this.game3 = game3;
    }

    public void show() {
        menuPricipal();
    }

    private void menuPricipal() {
        int option;
        for () {
            System.out.println("1. Tres en raya");
            System.out.println("2. Mage Jump");
            System.out.println("3. Memory Game");
            System.out.println("4. Exit");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    game1.play();
                    break;
                case 2:
                    game2.play();
                    break;
                case 3:
                    game3.play();
                    break;
                case 4:
                    break;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
    }
}