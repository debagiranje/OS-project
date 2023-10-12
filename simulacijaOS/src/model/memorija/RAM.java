package model.memorija;

import java.util.ArrayList;
import java.util.List;

/*
 * Dinamicki rasparcavamo particiju tj sav ram u (pod)partcicije velicine sirine procesa uzimajuci prvu odgovarajucu slobodnu
 * Pri dealokaciji particije, pokusavamo da sazmemo prazan prostor -- defrag
 */

public class RAM {
    private int totalMemory;
    private List<Particija> partitions;

    public RAM(int totalMemory) {
        this.totalMemory = totalMemory;
        this.partitions = new ArrayList<>();
        partitions.add(new Particija(0, totalMemory, false));
    }

    public int allocatePartition(int size) {
        for (int i = 0; i < partitions.size(); i++) {
            Particija partition = partitions.get(i);
            if (!partition.isAllocated() && partition.getSize() >= size) {
                int allocatedSize = Math.min(size, partition.getSize());
                partitions.set(i, new Particija(partition.getStartAddress(), allocatedSize, true));
                return partition.getStartAddress();
            }
        }
        int startAddress = partitions.isEmpty() ? 0 : partitions.get(partitions.size() - 1).getEndAddress();
        int remainingSize = totalMemory - startAddress;
        if (remainingSize >= size) {
            partitions.add(new Particija(startAddress, size, true));
            return startAddress;
        } else {
            System.out.println("NEM err");
            return -1;
        }
    }


    public boolean deallocatePartition(int startAddress) {
        for (int i = 0; i < partitions.size(); i++) {
            Particija partition = partitions.get(i);
            if (partition.getStartAddress() == startAddress && partition.isAllocated()) {
                partitions.remove(i);
                partition.setAllocated(false);

                defragment();

                return true;
            }
        }

        System.out.println("ups");
        return false;
    }


    // pokusavamo da sazmemo mem tako sto razdvojimo zauzete od od nezauzetih i poravnamo adrese zauzetim
    private void defragment() {
        List<Particija> allocatedPartitions = new ArrayList<>(); 
        List<Particija> freePartitions = new ArrayList<>();

  
        for (Particija partition : partitions) {
            if (partition.isAllocated()) {
                allocatedPartitions.add(partition);
            } else {
                freePartitions.add(partition);
            }
        }

   
        partitions.clear();
        partitions.addAll(allocatedPartitions);
        partitions.addAll(freePartitions);

   
        int currentStartAddress = 0;
        for (Particija partition : partitions) {
            if (partition.isAllocated()) {
                partition.setStartAddress(currentStartAddress);
                currentStartAddress += partition.getSize();
            }
        }
    }

    
    public int getUsedMemory() {
        int usedMemory = 0;

        for (Particija partition : partitions) {
            if (partition.isAllocated()) {
                usedMemory += partition.getSize();
            }
        }

        return usedMemory;
    }

    public void printMemoryState() {
        System.out.println("ukupno: " + totalMemory + "B");
        System.out.println("partiticije: ");
        for (Particija partition : partitions) {
            System.out.println("start addr: " + partition.getStartAddress() + " vel: " + partition.getSize() + " alocirana: " + partition.isAllocated());
        }
    }

    
    public static void main(String[] args) {
        RAM memory = new RAM(4096);
        memory.allocatePartition(150);
        memory.allocatePartition(360); 
        memory.allocatePartition(2);
        memory.printMemoryState();
        System.out.println("mem zauz: " + memory.getUsedMemory() + "B");

        
        memory.deallocatePartition(150);
        //memory.defragment();  
        memory.printMemoryState();
        System.out.println("mem zauz: " + memory.getUsedMemory() + "B");
    }
}
