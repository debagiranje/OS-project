package model.memorija;

// https://en.wikipedia.org/wiki/Disk_sector za razliku izmedju sektora i bloka

// sve je fajl, samo pusti fajl
// okej nema veze skontala sam, mi modeliramo logicki blok (logicno, andjela)
// i on je fiksne velicine, najcesce (apparently 4 kb u modernim sistemima) i to ima smisla! -- osvrni se na razliku izmedju
// sektora i bloka... 
// https://www.geeksforgeeks.org/difference-between-page-and-block-in-operating-system/

public class Blok {
	
	private int blokAdr;
    private byte[] data;

    public Blok(int blokAdr) {
        this.blokAdr = blokAdr;
        this.data = new byte[4096];
    }
    public int getBlokAdr() {
		return blokAdr;
	}

	public byte[] getData() {
        return data;
    }

    public void setData(byte[] newData) {
        if (newData.length <= data.length) {
            System.arraycopy(newData, 0, data, 0, newData.length);
        } else {
            System.err.println("Bajojajo! podatak prevelik....");
        }
    }
}

