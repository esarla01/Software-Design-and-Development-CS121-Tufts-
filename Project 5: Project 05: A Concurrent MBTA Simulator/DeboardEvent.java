import java.util.*;

public class DeboardEvent implements Event {
  public final Passenger p; public final Train t; public final Station s;
  public DeboardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof DeboardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " deboards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }
  public void replayAndCheck(MBTA mbta) {

    /* Check if the train is at the given station and the passenger is at that train */

    Station stationOfTrain = mbta.getCurrTrains().get(t);   // get the station the train is in
    Train trainOfPassenger = mbta.getCurrPassengersT().get(p);  // get the train the passenger is in

    if (! (s.equals(stationOfTrain) && t.equals(trainOfPassenger))) {
      throw new UnsupportedOperationException("The train is not on the given station or the passenger is not on the train!");
    }

    /* Check if the given station is on the journey of the passenger */
    if (! mbta.getAllJourneys().get(p).contains(s)) {
      throw new UnsupportedOperationException("The station is not on the journey of the passenger.");
    }


    /* Update the station and train values of the passenger */
    mbta.updateCurrPassengerT(p, null);     // passenger is no longer in train, update the train value
    mbta.updateCurrPassengerS(p, s);     // passenger is at the station, update the station value
  }
}
