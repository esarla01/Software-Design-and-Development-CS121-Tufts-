import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PassengerThread extends Thread implements Runnable{
    private static Log log = new Log();
    private Passenger p;
    private MBTA mbta;
    private List<Station> journey;
    private List<Station> alreadyBoarded;
    private List<Station> alreadyDeboarded;
    // if already boarded you cannot

    public PassengerThread(Passenger passenger, MBTA mbta, Log log) { // the log in the sim will be fed to passenger thread
        this.log = log;
        this.mbta = mbta;
        this.p = passenger;
        this.journey = mbta.getAllJourneys().get(passenger);
        this.alreadyBoarded = new LinkedList<>();
        this.alreadyDeboarded = new LinkedList<>();
    }

    public void run() {
        Station stt = mbta.getAllJourneys().get(p).get(mbta.getAllJourneys().get(p).size()-1);
        // Consumer

        while (journey.size() > 0) {
            // determine if the passenger tries to board or deboard (the passenger can wither be in a station or in a train)
            //System.out.println("Is Boarding: " + mbta.getCurrPassengersS().get(p));

            //System.out.println("32 for passenger " + p + " at station " + mbta.getCurrPassengersS().get(p));
            /* Case1: The passenger is in a Station. */
            if (mbta.getCurrPassengersS().get(p) != null) { // if the passenger is in a station, then try board

                mbta.locks.get(mbta.getCurrPassengersS().get(p)).lock();

//                ReentrantLock currLock = mbta.locks.get(mbta.getCurrPassengersS().get(p));
//                Condition currCondition = mbta.conditions.get(mbta.getCurrPassengersS().get(p));


                    //System.out.println("43");
                    Station passengerStation = mbta.getCurrPassengersS().get(p);


                    // waits on two conditions: (1)there is no train in the station or (2) there is train, but that train does not go to the next station in the passenger's journey
                    Station nextStationInJourney = journey.get(1);
                    // there is a train and the train goes to
                    while (mbta.getCurrStation().get(mbta.getCurrPassengersS().get(p)) == null  || !mbta.getAllLines().get(mbta.getCurrStation().get(mbta.getCurrPassengersS().get(p))).contains(journey.get(1))) {
                        try {
                            //System.out.println("55");
                            mbta.conditions.get(mbta.getCurrPassengersS().get(p)).await();
//                            mbta.conditions.get(mbta.getCurrPassengersS().get(p)).await(); // call await with its lock held
                        } catch (InterruptedException e) {
                            return; // bi dusun
                        }
                    }



                    Train currTrain = mbta.stationToTrain(passengerStation);
                    Station nextStation = journey.get(1);

                    mbta.updateCurrPassengerT(p, currTrain);     // passenger boards a train, update the train value
                    mbta.updateCurrPassengerS(p, null);     // passenger is no longer in a station, update the station value

                    log.passenger_boards(p, currTrain, passengerStation);

                    //System.out.println("66" + journey.toString());
                    journey.remove(0);
                    //System.out.println("68" + journey.toString());

                    //lockCondition.signalAll();
                    mbta.conditions.get(passengerStation).signalAll();

                    //stationLock.unlock();
                    mbta.locks.get(passengerStation).unlock();


//                } else {
//                    journey.remove(0);
//                    System.out.println("75");
//                    return;
//                }

            } /* Case2: The passenger is in a Train. */
            else {
                mbta.locks.get(journey.get(0)).lock();
                //if (!alreadyBoarded.contains(trainStation) && journey.get(0).equals(mbta.getCurrTrains().get(passengerTrain))){
                    Station tStation = mbta.getCurrTrains().get(mbta.getCurrPassengersT().get(p));
                    //Condition lockCondition = mbta.conditions.get(journey.get(0));


                    while (!journey.get(0).equals(mbta.getCurrTrains().get(mbta.getCurrPassengersT().get(p)))) {
                        try {
                            //System.out.println("For passenger " + p);
                            mbta.conditions.get(journey.get(0)).await(); // call await with its lock held
                        } catch (InterruptedException e) {
                            return; //iki dusun
                        }
                    }
                    Train deboardedTrain = mbta.getCurrPassengersT().get(p);

                    mbta.updateCurrPassengerT(p, null);     // passenger is no longer in train, update the train value
                    mbta.updateCurrPassengerS(p, journey.get(0));     // passenger is at the station, update the station value
                    alreadyDeboarded.add(journey.get(0));

                    log.passenger_deboards(p, deboardedTrain, journey.get(0));

                    //mbta.conditions.get(journey.get(0)).signalAll();

                    mbta.locks.get(journey.get(0)).unlock();
//                    journey.remove(0);
                    if (journey.size() == 1) {
                        return;
                    }

               // }

            }
        }

    }
}



