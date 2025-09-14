import java.util.Scanner;

import controller.Controller;
import repository.ElektronikRepo;
import repository.MakananRepo;
import view.Console;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Console view = new Console(input);
        ElektronikRepo elektronikRepo = new ElektronikRepo();
        MakananRepo makananRepo = new MakananRepo();

        Controller App = new Controller(view, elektronikRepo, makananRepo);

        App.run();
        input.close();
    }

}
