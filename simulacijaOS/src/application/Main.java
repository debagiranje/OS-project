package application;
	
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Bootloader;
import model.procesor.CPU;
import model.procesor.Proces;
import model.fajlSistem.FajlSistem;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/*
 * Htjela sam napraviti i GUI, ali nisam stigla. Neka ostane za svaki slucaj
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		//launch(args);
		Bootloader boot = new Bootloader();
		boot.initializeHardware();
		boot.initializeOS();
		boot.startOS();

		Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String trenutni = "root";
        
        FajlSistem fs = new FajlSistem(100);
        fs.createDirectory(trenutni, null);
        fs.setCurr(trenutni);
        
        CPU cpu = new CPU(4096);
        
        Proces tren = null;

        while (isRunning) {
            System.out.print(fs.getCurrDir().getName() + ": ");
            String unos = scanner.nextLine().trim();
            
            String[] parts = unos.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";
            
            switch (command) {
                case "cd":
                	fs.cd(argument);
                    break;
                case "ls":
                    fs.ls();
                    break;
                case "ps":
                    System.out.println(tren.toString());
                    break;
                case "mkdir":
                	fs.mkdir(argument);
                    break;
                case "load":
                    tren = cpu.loadProgram(argument);
                    break;
                case "run":
                    cpu.runProgram(); 
                    break;
                case "mem":
                    cpu.memory.printMemoryState();
                    break;
                case "exit":
                    System.out.println("Gasim sistem...");
                    isRunning = false;
                    System.exit(0);
                    break;
                case "rm":
                    fs.remove(argument);
                    break;
                case "blok":
                    tren.setBlocked(true);
                    break;
                default:
                    System.out.println("Nepoznata komanda.");
                    break;
            }
        }

        scanner.close();
    }
	
}
