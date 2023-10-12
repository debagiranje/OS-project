package model.fajlSistem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// ineksirana alokacija fajlova + indeksirano upravljanje slobodnim prostorom 
// pa imamo dva dicta -- 1 za fajl -- 1 za empty space
// moram handlovati nekako ucitavanje iz actual fajla (kopiranje bytestreama/bytearraya)

public class FajlSistem {
    private Map<String, Fajl> files;
    private Map<Integer, Boolean> freeBlocks;
    private Fajl currDir;

    public FajlSistem(int totalBlocks) {
        this.files = new HashMap<>();
        this.freeBlocks = new HashMap<>();
        // (init -- svi su free)
        for (int i = 0; i < totalBlocks; i++) {
            freeBlocks.put(i, true);
        }
        this.setCurrDir(null);
    }

    public void createFile(String name, int size, byte[] content) {
        Fajl file = new Fajl(name, size, content, false);
        
        allocateBlocks(file, size);

        if (currDir != null) {
            currDir.addChild(file);
        } else {
            files.put(name, file);
        }
    }

    public void createDirectory(String name, String parentDirectory) {
        Fajl directory = new Fajl(name, 0, null, true);
        files.put(name, directory);

        if (parentDirectory != null) {
            Fajl parent = files.get(parentDirectory);
            if (parent != null && parent.isDirectory()) {
                parent.addChild(directory);
            }
        }
    }

    public void allocateBlocks(Fajl file, int size) {
        List<Integer> indexTable = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int freeBlock = findFreeBlock();
            if (freeBlock == -1) {
                System.out.println("Insufficient free space");
                return;
            }

            indexTable.add(freeBlock);
            freeBlocks.put(freeBlock, false);
        }

        file.setIndexTable(indexTable);
    }

    private int findFreeBlock() {
        for (Map.Entry<Integer, Boolean> entry : freeBlocks.entrySet()) {
            if (entry.getValue()) {
                return entry.getKey();
            }
        }
        return -1;
    }
    
    public void setCurr(String directoryName) {
        Fajl directory = files.get(directoryName);
        if (directory != null && directory.isDirectory()) {
            this.setCurrDir(directory);
        } else {
            System.out.println("dnf err");
        }
    }
    
    public String cd(String directoryName) {
        if (currDir != null) {
            Fajl directory = currDir.findChild(directoryName);
            if (directory != null && directory.isDirectory()) {
                currDir = directory;
            } else {
                System.out.println("Nepostojeci direktorijum");
            }
        }
        return currDir.getName();
    }

    public void mkdir(String directoryName) {
        if (currDir!= null) {
            createDirectory(directoryName, currDir.getName());
        }
    }

    public void ls() {
        if (currDir != null) {
            System.out.println("Djecica " + currDir.getName() + ":");
            for (Fajl file : currDir.getChildren()) {
                System.out.print(file.getName() + "\t");
            }
            System.out.println("");
        }
    }
    
    public void remove(String name) {
        if (currDir != null) {
            currDir.removeChild(name);
        } else {
            Fajl fileToRemove = files.get(name);
            if (fileToRemove != null) {
                files.remove(name);
            }
        }
    }

    public static void main(String[] args) {
        FajlSistem fileSystem = new FajlSistem(100);

        fileSystem.createDirectory("root", null);
        
        fileSystem.setCurr("root");
        //fileSystem.cd("root");
        
        fileSystem.createDirectory("docs", "root");
        fileSystem.cd("docs");
        fileSystem.createFile("file1.txt", 8, null);
        fileSystem.createDirectory("images", "root");
        fileSystem.cd("images");
        fileSystem.createFile("file2.png", 12, null);
        fileSystem.createFile("file3.txt", 5, null);

        System.out.println(fileSystem);
        fileSystem.remove("file3.txt");

        System.out.println(fileSystem);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Svi fajlovi:\n");

        for (Fajl file : files.values()) {
            result.append(file.toString()).append("\n");
        }

        return result.toString();
    }

	public Fajl getCurrDir() {
		return currDir;
	}

	public void setCurrDir(Fajl currDir) {
		this.currDir = currDir;
	}
}
