package model.procesor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.memorija.RAM;

public class CPU {
    private Registri registers;
    public RAM memory;
    //private int programCounter;
    //public Proces trenutniProces = null;
    private Asembler asembler;
    private RasporedjivacProcesa scheduler;
    String assemblyCode = "";
    String instr = "";

    public CPU(int memorySize) {
        this.registers = new Registri();
        this.memory = new RAM(memorySize);
        this.scheduler = new RasporedjivacProcesa();
        this.asembler = new Asembler();
    }
    
    private static String readAssemblyFile(String naziv) throws IOException {
    	String filePath = "C:\\Users\\libor\\OneDrive\\Documents\\GitHub\\OS-project\\simulacijaOS\\src\\prog\\" + naziv + ".asm";
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }
    public Proces loadProgram(String naziv) throws IOException {
    	assemblyCode = readAssemblyFile(naziv);
    	asembler.assemble(assemblyCode);
    	
    	//memory.printMemoryState();
       
    	instr = asembler.getInstrukcija();
        List<String> instructions = Proces.splitString(instr, 8);
        
        memory.allocatePartition(instr.length());
        
        Proces trenutniProces = new Proces(instr.length()*100, instructions);
    	scheduler.addProcess(trenutniProces);
        System.out.println("asm:\n" + assemblyCode);
        return trenutniProces;
    }

    public void runProgram() {
        scheduler.startScheduler();
        //memory.deallocatePartition(instr.length());
    }


    public static void main(String[] args) throws IOException {
        CPU cpu = new CPU(4096);
        
        cpu.loadProgram("program");
        cpu.loadProgram("program2");
        cpu.runProgram();
        cpu.registers.toString();
    }
}
