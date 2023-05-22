package model;

//https://embeddedinventor.com/embedded-bootloader-and-booting-process-explained/

public class Bootloader {
	
	public static void main(String[] args) {
        Bootloader boot = new Bootloader();
        
        boot.initializeHardware();
        boot.initializeOS();
        boot.startOS();
    }
    
    public void initializeHardware() {
        System.out.println("Inicijalizacija hardvera...");
        // TODO -- shibni init hardvera - napraviti modele procesora, memorije, uredjaja i sl!!!
        System.out.println("Hardver inicijalizovan");
    }
    
    public void initializeOS() {
        System.out.println("Inicijalizacija OS-a...");
        // TODO -- init dzidzabidza - mem manager, process scheduler, drajveri(?), perif uredjaji(?)...
        System.out.println("OS inizijalizovan");
    }
    
    public void startOS() {
        System.out.println("Pokrecem...");
        // TODO -- exec OS (Napraviti klasu?)
        System.out.println("OS pokrenut");
    }

}
