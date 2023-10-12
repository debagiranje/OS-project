package model.memorija;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// mi treba da implementiramo c-scan nad HDD-om
// https://www.youtube.com/watch?v=wtReHRBOQ_U
// ukratko, imamo neke zahtjeve za lokacijama; pronadjemo prvi u listi zahtjeva(ako nije lokacija nula),
// i idemo udesno do kraja, kupeci zahtjeve, pa se vratimo skroz lijevo na pocetak, pa odakle nastavljamo
// briljantno objasnjeno ng... :)

public class DiskScheduler {
	
	private int currentHead;
    private List<Integer> headRequests;

    public DiskScheduler() {
        this.currentHead = 0;
        this.headRequests = new ArrayList<>();
    }

    public void setCurrentHead(int head) {
        this.currentHead = head;
    }

    public int getCurrentHead() {
        return currentHead;
    }

    public void addHeadRequest(int request) {
        headRequests.add(request);
    }

    public void clearHeadRequests() {
        headRequests.clear();
    }

    public int scheduleCScan(int max, int min) {
        int totalHeadMovement = 0;

        // Add end points to the requests
        headRequests.add(max);
        headRequests.add(min);

        // Arrange in ascending order
        arrangeAscending();

        int left = 0;
        int right = 0;

        for (int x = 0; x < headRequests.size(); x++) {
            if (headRequests.get(x) == currentHead) {
                if (x != 0)
                    left = Math.abs(headRequests.get(x) - headRequests.get(x - 1));

                if (x != headRequests.size() - 1)
                    right = Math.abs(headRequests.get(x) - headRequests.get(x + 1));

                break;
            }
        }

     
        if (left < right) {
            arrangeSequence();
            headRequests.add(headRequests.remove(0));

            for (int x = headRequests.size(); x > 0; x--)
                headRequests.add(headRequests.remove(x - 1));

            totalHeadMovement = (currentHead - min) + (max - headRequests.get(headRequests.size() - 1));
        } else if (left >= right) {
            arrangeSequence();
            totalHeadMovement = (max - currentHead) + (headRequests.get(headRequests.size() - 1) - min);
        }

        return totalHeadMovement;
    }

    private void arrangeAscending() {
        Collections.sort(headRequests);
    }

    private void arrangeSequence() {
        for (int x = 0; x < headRequests.size(); x++) {
            if (headRequests.get(0) != currentHead)
                headRequests.add(headRequests.remove(0));
            else
                break;
        }
    }
}




