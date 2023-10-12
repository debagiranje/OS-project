package model.memorija;

/*
 * RAM cemo dijeliti u particije, a disk u blokove (jer je navodno, disk blokovska memorija...) 
 * Treba da dinamicki izdijelimo radnu memoriju -- koristicemo first fit (bolji je od best fita ironicno xD)
 * Mozda da se napravi klasa dinamickeParticije ili nesto, vidjecy
 */

public class Particija {
    private int startAddress;
    private int size;
    private boolean allocated;

    public Particija(int startAddress, int size, boolean allocated) {
        this.startAddress = startAddress;
        this.size = size;
        this.allocated = allocated;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    public void deallocate() {
        allocated = false;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public int getSize() {
        return size;
    }

    public int getEndAddress() {
        return startAddress + size;
    }

	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
		
	}
}


