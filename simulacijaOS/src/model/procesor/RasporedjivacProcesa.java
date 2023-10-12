package model.procesor;

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

    public synchronized void addProcess(Proces proces) {
        red.add(proces);
        notifyAll();
    }

    public synchronized void block() throws InterruptedException {
        while (trenutniProces != null) {
            trenutniProces.setBlocked(true);
            trenutniProces = null;
            notifyAll();
            wait();
        }
    }

    public synchronized void unblock() {
        for (Proces process : red) {
            if (process.isBlocked()) {
                process.setBlocked(false);
                notifyAll();
                return;
            }
        }
    }

    public synchronized void startScheduler() {
       // while (true) {
            while (red.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            trenutniProces = red.poll();
	        if(!trenutniProces.isBlocked()) {
	
	            System.out.println("Pokrenut proces: " + trenutniProces.getPid());
	
	            try {
	                Thread.sleep(trenutniProces.getExecutionTime());
	                trenutniProces.executeInstructions();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	
	 
	               System.out.println("Proces " + trenutniProces.getPid() + " - zavrsen.");
	            
	
	            trenutniProces = null;
	        }
	        else {
	        	System.out.println("Proces " + trenutniProces.getPid() + " - blokiran. dodajte ga ponovo");
	        }
        }
    //}
}


