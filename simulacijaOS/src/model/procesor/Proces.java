package model.procesor;


import java.util.ArrayList;
import java.util.List;


import model.memorija.RAM;

public class Proces {

    private static int counter = 0;

    private int pid;
    private boolean blocked;
    private int executionTime;
    private List<String> instructions;

    public Proces(int executionTime, List<String> instructions2) {
        this.pid = counter++;
        this.blocked = false;
        this.executionTime = executionTime;
        this.instructions = instructions2;
    }

    public int getPid() {
        return pid;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void executeInstructions() {
    	for(String instr: instructions)
    	{
    		System.out.println("izvrsavam instrukciju: " + instr);
    	}
    }
    
    public static List<String> splitString(String input, int chunkSize) {
        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < input.length(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, input.length());
            String chunk = input.substring(i, endIndex);
            chunks.add(chunk);
        }

        return chunks;
    }
    
    @Override
    public String toString() {
    	return "proces pid: " + this.getPid() + " zauzece rama: " + this.getExecutionTime()/100 + " blokiran? " + this.isBlocked();
    }

    public static void main(String[] args) {
        Asembler asembler = new Asembler();
        String assemblyCode = "PUSH 5\nPUSH 6\nADD\nPUSH 10\nPUSH 2\nADD\nMUL\nPOP\n";
        asembler.assemble(assemblyCode);

     
        String instr = asembler.getInstrukcija();
        
        System.out.println("pi : " + instr);
        
        RAM memory = new RAM(4096);
        memory.allocatePartition(instr.length());
        memory.printMemoryState();
        
        List<String> instructions = splitString(instr, 8);

        System.out.println(instructions);

        Proces proces = new Proces(5000, instructions);
        Proces proces2 = new Proces(2000, instructions);
        RasporedjivacProcesa scheduler = new RasporedjivacProcesa();

        scheduler.addProcess(proces);
        scheduler.addProcess(proces2);
        scheduler.startScheduler();
    }
}

