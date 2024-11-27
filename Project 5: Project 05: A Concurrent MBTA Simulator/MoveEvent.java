import java.util.*;

public class MoveEvent implements Event {
  public final Train t; public final Station s1, s2;
  public MoveEvent(Train t, Station s1, Station s2) {
    this.t = t; this.s1 = s1; this.s2 = s2;
  }
  public boolean equals(Object o) {
    if (o instanceof MoveEvent e) {
      return t.equals(e.t) && s1.equals(e.s1) && s2.equals(e.s2);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(t, s1, s2);
  }
  public String toString() {
    return "Train " + t + " moves from " + s1 + " to " + s2;
  }
  public List<String> toStringList() {
    return List.of(t.toString(), s1.toString(), s2.toString());
  }
  public void replayAndCheck(MBTA mbta) {

    //System.out.println("In replay and check");

    /* Check if s1 exists in the line: If so, get its index on the line! */
    List<Station> stations = mbta.getAllLines().get(t); // get the stations on the line of the train
    int s1Index;
    try{
      s1Index = stations.indexOf(s1);
    } catch (Exception e) {
      throw new UnsupportedOperationException("S1 does not exist!");
    }

    /* Check if s1 and s2 are consequtive stations on the trains direction */
    Boolean trainDirection = mbta.getTrainDirection().get(t); // get the direction of the train

    // (1) Train moves to along the stations in its line
    if (trainDirection.equals(true)) {
      // case 1: when train is at the end of the line
      if (s1Index == stations.size() - 1){
            if (s2.equals(stations.get(s1Index - 1))) {
              mbta.updateTrainDirection(t, false); // the trains start to travel the opposite way
            } else {
              throw new UnsupportedOperationException("Stations are not consequtive!"); // invalid move
            }
      } // case 2: when the train is in any station except the last
      else {
            if (! s2.equals(stations.get(s1Index + 1))) {
              throw new UnsupportedOperationException("Stations are not consequtive!"); // invalid move
            }
      }
    }

    // (2) Train moves opposite the stations in its line
    if (trainDirection.equals(false)) {
      // case 1: when train is at the start of the line
      if (s1Index == 0){
        if (s2.equals(stations.get(1))) {
          mbta.updateTrainDirection(t, true); // the trains start to travel the regular way
        } else {
          throw new UnsupportedOperationException("Stations are not consequtive!"); // invalid move
        }
      } // case 2: when the train is in any station except the first
      else {
        if (! s2.equals(stations.get(s1Index - 1))) {
          throw new UnsupportedOperationException("Stations are not consequtive!"); // invalid move
        }
      }
    }


    /* Check if there is no train in the goal station */
    for (Train train : mbta.getCurrTrains().keySet()) {
        Station currS = mbta.getCurrTrains().get(train);
        if (s2.equals(currS)) { // if there is any train at the goal station, return
          throw new UnsupportedOperationException("There is a train at the goal station!");
      }
    }

    /* update the station that the train is at */
    mbta.updateCurrTrains(t, s2);
    mbta.updateCurrStation(s1, null);   // current station of the train becomes empty
    mbta.updateCurrStation(s2, t);    // the target station becomes occupied
  }
}
