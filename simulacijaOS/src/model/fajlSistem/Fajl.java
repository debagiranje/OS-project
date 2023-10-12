package model.fajlSistem;
import java.util.ArrayList;
import java.util.List;

// direktorijum je samo fajl. tj sve je samo fajl --
// direktorijum je fajl koji sadrzi druge fajlove

public class Fajl {
    private String name;
    private int size;
    private byte[] content;
    private List<Fajl> children;
    private boolean isDirectory;
    private List<Integer> indexTable;  

    public Fajl(String name, int size, byte[] content, boolean isDirectory) {
        this.name = name;
        this.size = size;
        this.content = content;
        this.children = new ArrayList<>();
        this.isDirectory = isDirectory;
        this.indexTable = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public List<Fajl> getChildren() {
        return children;
    }

    public void addChild(Fajl child) {
        children.add(child);
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public List<Integer> getIndexTable() {
        return indexTable;
    }

    public void setIndexTable(List<Integer> indexTable) {
        this.indexTable = indexTable;
    }
    
    public Fajl findChild(String childName) {
        for (Fajl child : children) {
            if (child.getName().equals(childName)) {
                return child;
            }
        }
        return null;
    }
    
    public void removeChild(String childName) {
        Fajl childToRemove = null;
        for (Fajl child : children) {
            if (child.getName().equals(childName)) {
                childToRemove = child;
                break;
            }
        }

        if (childToRemove != null) {
            children.remove(childToRemove);
        }
    }
    
    // TODO: mozda ako stignes: vel direktorijuma je zbir vel djecice ako to ima smisla
    // -- mozda samo dodati metodu koja ce prebrojati velicinu sadrzaja u dir na zahtjev, bez da imamo designated polje

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nFajl -- naziv: " + name + ", vel:" + size + "\n ");

        if (isDirectory) {
            result.append("\n djecica:\n\t");
            for (Fajl child : children) {
                result.append(child.getName() + " velicina: " + child.getSize()).append(" ; ");
            }
        }
        
        result.append("kraj listanja u dir");

        return result.toString();
    }

}

    
    

