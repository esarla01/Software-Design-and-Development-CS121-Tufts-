import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TrainThread extends Thread implements Runnable{
    private static Log log = new Log();
    private Train t;
    private MBTA mbta;

    public TrainThread(Train train, MBTA mbta, Log log) { // the log in the sim will be fed to train thread
        this.log = log;
        this.mbta = mbta;
        this.t = train;
    }

//    public boolean stopThread() {
//        boolean complete = true;
//        for (Passenger p : mbta.getAllJourneys().keySet()) {
//            try {
//            int lastStop = mbta.getAllJourneys().get(p).size();
//            if (lastStop != 0) {
//                // check if the current station of the passenger matches the final stop in their journey
//                if (mbta.getCurrPassengersS().get(p) != mbta.getAllJourneys().get(p).get(lastStop - 1)) {
//                    complete = false;
//                    break;
//                }
//            }
//            } catch (Exception e) {  complete = true; }
//        }
//        return complete;
//    }

    public void run(){
        while (!this.isInterrupted()) {
                //System.out.println("Train:" + t.toString() + " Thread Start");

                /* Grab the lock of the current station! */


                Station currentStation = mbta.getCurrTrains().get(t); // get the current station of the train
                ReentrantLock currentStationLock = mbta.locks.get(mbta.getCurrTrains().get(t));  // get the lock for that station
                Condition currentLockCondition = mbta.conditions.get(currentStation);  // get condition for the lock

                mbta.locks.get(mbta.getCurrTrains().get(t)).lock();     // acquire the lock of the station

                //System.out.println("47");

                /* Find the next station in the train's track! */
                Boolean direction = mbta.getTrainDirection().get(t).equals(true);  // get the direction of the train
                int currStationIndex = mbta.getAllLines().get(t).indexOf(currentStation); // get the index of the current train in the line
                Station nextStation; // get the next station of the train below

                if (direction.equals(true)) {   // train moves to right
                    if (currStationIndex == mbta.getAllLines().get(t).size() - 1) { // when the train is at the end of the line, the next
                        nextStation = mbta.getAllLines().get(t).get(currStationIndex - 1); // station is one before bc the train turns around
                    } else {
                        nextStation = mbta.getAllLines().get(t).get(currStationIndex + 1);
                    }
                } else {     // train moves to left
                    if (currStationIndex == 0) { // when the train is at the first station, the next station is the
                        nextStation = mbta.getAllLines().get(t).get(1); // second station bc the train turns around
                    } else {
                        nextStation = mbta.getAllLines().get(t).get(currStationIndex - 1);
                    }

                }

                /* Try to grab the lock of the next station by calling await! */
                ReentrantLock nextStationLock = mbta.locks.get(nextStation);  // get the lock for that station
                Condition nextLockCondition = mbta.conditions.get(nextStation);  // get condition for the lock

                nextStationLock.lock();

                while (mbta.getCurrStation().get(nextStation) != null) { // if the current station has a train, call await
                    try {
                        mbta.conditions.get(nextStation).await(); // call await with its lock held
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                //System.out.println("Train grabbed the lock for the next station: " + mbta.stationForLock(nextStationLock));


                /* The next station becomes occupied and the last station becomes unoccupied. */

                /* Make the changes in the hash maps and add to the log */
                Boolean direction1 = mbta.getTrainDirection().get(t).equals(true);  // get the direction of the train

                if (direction1.equals(true)) {    // train moves to right

                    if (currStationIndex == mbta.getAllLines().get(t).size() - 1) {
                        Station nextStation1 = mbta.getAllLines().get(t).get(currStationIndex - 1);
                        mbta.updateTrainDirection(t, false); // the trains start to travel the opposite way
                        mbta.updateCurrTrains(t, nextStation1);

                        mbta.updateCurrStation(nextStation1, t);
                        mbta.updateCurrStation(currentStation, null);
                        //System.out.println("next: " + nextStation1 + " " + mbta.getCurrStation().get(nextStation1) + " curr: " + currentStation + " check: " + mbta.getCurrStation().get(currentStation) );
                        log.train_moves(t, currentStation, nextStation1);
                        //System.out.println("85: " + "Current Station:" + currentStation.toString() + "NextStation: " + nextStation1 + "HashMap: " + mbta.getCurrTrains().get(t));

                    } else {
                        Station nextStation1 = mbta.getAllLines().get(t).get(currStationIndex + 1);
                        mbta.updateCurrTrains(t, nextStation1);
                        mbta.updateCurrStation(nextStation1, t);
                        mbta.updateCurrStation(currentStation, null);

                        //System.out.println("next: " + nextStation1 + " " + mbta.getCurrStation().get(nextStation1) + " curr: " + currentStation + " check: " + mbta.getCurrStation().get(currentStation) );
                        log.train_moves(t, currentStation, nextStation1);
                        //System.out.println("96: " + "Current Station:" + currentStation.toString() + "NextStation: " + nextStation1 + "HashMap: " + mbta.getCurrTrains().get(t));

                    }

                } else {     // train moves to left

                    if (currStationIndex == 0) {
                        Station nextStation1 = mbta.getAllLines().get(t).get(1);
                        mbta.updateTrainDirection(t, true); // the trains start to travel the opposite way
                        mbta.updateCurrTrains(t, nextStation1);
                        mbta.updateCurrStation(nextStation1, t);
                        mbta.updateCurrStation(currentStation, null);
                        //System.out.println("next: " + nextStation1 + " " + mbta.getCurrStation().get(nextStation1) + " curr: " + currentStation + " check: " + mbta.getCurrStation().get(currentStation) );
                        log.train_moves(t, currentStation, nextStation1);
                        //System.out.println("109: " + "Current Station:" + currentStation.toString() + "NextStation: " + nextStation1 + "HashMap: " + mbta.getCurrTrains().get(t));


                    } else {
                        Station nextStation1 = mbta.getAllLines().get(t).get(currStationIndex - 1);
                        mbta.updateCurrTrains(t, nextStation1);
                        mbta.updateCurrStation(nextStation1, t);
                        mbta.updateCurrStation(currentStation, null);
                        //System.out.println("next: " + nextStation1 + "check:  " + mbta.getCurrStation().get(nextStation1) + " curr: " + currentStation + " check: " + mbta.getCurrStation().get(currentStation) );
                        log.train_moves(t, currentStation, nextStation1);

                        //System.out.println("118: " + "Current Station:" + currentStation.toString() + "NextStation: " + nextStation1 + "HashMap: " + mbta.getCurrTrains().get(t));

                    }

                }
                nextLockCondition.signalAll(); // signal all the passengers to board and deboard
                currentLockCondition.signalAll();


                nextStationLock.unlock(); // release the lock of the current station
                //System.out.println("Train released the lock for the next station: " + mbta.stationForLock(nextStationLock));


                currentStationLock.unlock();


                //System.out.println("Train released the lock for the current station: " + mbta.stationForLock(currentStationLock));
                try {
                // thread sleeps
                Thread.sleep(1);

            }catch(InterruptedException ex) {
                return;
            }

        }

    }

}


//System.out.println("Train released the lock for the station: " + mbta.stationForLock(stationLock));

//System.out.println("The train moved from " + currentStation.toString() + " " + nextStation.toString() + ".");

//System.out.println("Train:" + t.toString() + " Thread End");
