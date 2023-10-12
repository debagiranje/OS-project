package model.memorija;

// modelovacemo HDD
// virtuelizacija diska = fs + disc sched


class Disk {
    private int totalBlocks;
    private Blok[] blocks;

    public Disk(int totalBlocks) {
        this.totalBlocks = totalBlocks;
        this.blocks = new Blok[totalBlocks];

        for (int i = 0; i < totalBlocks; i++) {
            blocks[i] = new Blok(i); 
        }
    }

    public Blok getBlock(int blockNumber) {
        if (blockNumber >= 0 && blockNumber < totalBlocks) {
            return blocks[blockNumber];
        } else {
            System.err.println("Error: Invalid block number.");
            return null;
        }
    }
}
