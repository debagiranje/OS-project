package model;

import java.util.LinkedList;
import java.util.Queue;

// TODO -- svasta. Al boze moj, imamo nekakav okvir :D anja spasi me
// https://inst.eecs.berkeley.edu/~cs162/su20/static/lectures/11.pdf -- prica o fcfs, convoy effect... fifo q bez preempting
// da li se za nas pod "bez prekida" odnosi na to sto nemamo preempting? ...

public class RasporedjivacProcesa {
	
	private Queue<Proces> red;
    private Proces trenutniProces;

    public RasporedjivacProcesa() {
        red = new LinkedList<>();
        trenutniProces = null;
    }

    public synchronized void addProcess(Proces proces) { // dodaje i obavjestava da je stigao novi !!!
        red.add(proces);
        notifyAll(); 
    }

    public synchronized void block() throws InterruptedException { // obavijesti da je blokiran, cekaj dok se ne odblokira
        if (trenutniProces != null) {
            trenutniProces.setBlocked(true);
            trenutniProces = null;
            notifyAll(); 
            wait();
        }
    }

    public synchronized void unblock() { // odblokira, obavjestava 
        for (Proces process : red) {
            if (process.isBlocked()) {
                process.setBlocked(false);
                red.remove(process);
                red.add(process);
                break;
            }
        }
        notifyAll(); 
    }

    public synchronized void startScheduler() { // ceka dok se ne pojavi bar jedan u redu, potom izvrsava
        while (true) {
            while (red.isEmpty()) {
                try {
                    wait(); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            trenutniProces = red.poll(); // -> poll jer ne baca exception vec vraca null ako je prazan red
            System.out.println("Running process: " + trenutniProces.getPID());

            try {
                Thread.sleep(trenutniProces.getExecutionTime()); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (trenutniProces.isBlocked()) {
                System.out.println("Proces " + trenutniProces.getPID() + " - blokiran.");
                trenutniProces = null;
            } else {
                System.out.println("Proces " + trenutniProces.getPID() + " - zavrsen.");
                trenutniProces = null;
            }
        }
    }
}


