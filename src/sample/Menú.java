package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Pong.Pong;
import sample.Runner.Runner;
import sample.TresEnRaya.TresEnRaya;

import java.util.Scanner;

public class Menú extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Scanner scanner = new Scanner(System.in);
    Pong pong;
    Runner runner;
    TresEnRaya tresEnRaya;

    public void MenuExample(Pong pong, Runner runner, TresEnRaya tresEnRaya) {
        this.pong = pong;
        this.runner = runner;
        this.tresEnRaya = tresEnRaya;
    }

    public void show() {
        menuPricipal();
    }

    private void menuPricipal() {
        int option;
        for() {
            System.out.println("1. Tres en raya");
            System.out.println("2. Mage Jump");
            System.out.println("3. Memory Game");
            System.out.println("4. Exit");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    pong.play();
                    break;
                case 2:
                    runner.play();
                    break;
                case 3:
                    tresEnRaya.play();
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