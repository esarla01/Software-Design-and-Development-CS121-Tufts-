import java.util.*;

public class BoardEvent implements Event {
  public final Passenger p; public final Train t; public final Station s;
  public BoardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof BoardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " boards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }
  public void replayAndCheck(MBTA mbta) {
    /* Check if the train and the passenger are at the given station */

    Station stationOfTrain = mbta.getCurrTrains().get(t); // get the station the train is in
    Station stationOfPassenger = mbta.getCurrPassengersS().get(p); // get the station the passenger is in

    if (! (s.equals(stationOfTrain) && s.equals(stationOfPassenger))) {
      throw new UnsupportedOperationException("The train and/ or the passenger is/are not on the given station!");
    }

    /* Update the station and train values of the passenger */
    mbta.updateCurrPassengerT(p, t);     // passenger boards a train, update the train value
    mbta.updateCurrPassengerS(p, null);     // passenger is no longer in a station, update the station value
  }
}
