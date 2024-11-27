import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MBTA {

  //public HashMap<Station, Boolean> buffer = new HashMap<>();
  private HashMap <Station, Train> currStation = new HashMap<>(); // the current train of each station

  public  HashMap<Train, Thread> trainThreads = new HashMap<>(); // all the threads for trains
  public HashMap<Passenger, Thread> passengerThreads = new HashMap<>(); // all the threads for trains


  public HashMap<Station, ReentrantLock> locks = new HashMap<>(); // locks for all stations
  public HashMap<Station, Condition>  conditions = new HashMap<>();  // condition for the lock of the stations


  private HashMap <Train, Station> currTrains = new HashMap<>(); // the current station of each train
  private HashMap <Train, Boolean> trainDirection = new HashMap<>(); // the current direction of the train

  private HashMap <Passenger, Train> currPassengersT = new HashMap<>(); // the current train of each passenger
  private HashMap <Passenger, Station> currPassengersS = new HashMap<>(); // the current station of each passenger


  private HashMap <Train, List<Station>> allLines = new HashMap<>(); // trains mapped to all the the stations in their line
  private HashMap <Passenger, List<Station>> allJourneys = new HashMap<>();  // passengers mapped to all the stations in their journey

  // Creates an initially empty simulation
  public MBTA() { }

  public Station stationForLock(ReentrantLock lock){
    try {
      for (Station s : locks.keySet()) {
        if (locks.get(s).equals(lock)) {
          return s;
        }
      }
    } catch(Exception e) { }
    return null;
  }

  public Train stationToTrain(Station station) {
    try {
    for (Train t: getCurrTrains().keySet()){
      if (getCurrTrains().get(t).equals(station)) {
        return t;
      }
    }
  } catch(Exception e) { }
    return null;
  }

  // public void update currTrains when the train moves
  public void updateCurrTrains(Train t, Station currS) {
    currTrains.put(t, currS);
  }

  public void updateTrainDirection(Train t, Boolean d) {
    trainDirection.put(t, d);
  }

  // public void update currPassengersT when the passenger boards or deboards a train
  public void updateCurrPassengerT(Passenger p, Train currT) {
    currPassengersT.put(p, currT);
  }

  // public void update currPassengersS when the passenger boards or deboards a train
  public void updateCurrPassengerS(Passenger p, Station currS) {
    currPassengersS.put(p, currS);
  }
  // public void update currPassengersS when the passenger boards or deboards a train
  public void updateCurrStation(Station s, Train t) {
    currStation.put(s, t);
  }



  public HashMap <Station, Train> getCurrStation() {
    return currStation;
  }


  // get current stations of the trains
  public HashMap <Train, Station> getCurrTrains() {
    return currTrains;
  }

  // get current direction of the trains on their line
  public HashMap <Train, Boolean> getTrainDirection() {
    return trainDirection;
  }

  // get current stations of the passengers
  public HashMap <Passenger, Train> getCurrPassengersT() {
    return currPassengersT;
  }

  // get current stations of the passengers
  public HashMap <Passenger, Station> getCurrPassengersS() {
    return currPassengersS;
  }

  // get allLines map
  public Map<Train, List<Station>> getAllLines() {
    return allLines;
  }

  // get allJourneys map
  public Map<Passenger, List<Station>> getAllJourneys() {
    return allJourneys;
  }


  // Adds a new transit line with given name and stations
  public void addLine(String name, List<String> stations) {
    List<Station>  stationList = new LinkedList<>();
    if (stations.isEmpty()) {
      return;
    }
    Station station = null;
    Train train = Train.make(name);
    for (String s : stations) {
      train = Train.make(name);
      station = Station.make(s);
      stationList.add(station);
    }
    allLines.put(train, stationList);
    currTrains.put(train, Station.make(stations.get(0))); // the current state of the train is the first station in the given stations
    trainDirection.put(train, true); // all trains are assume to go in the direction of their line

    currStation.put(stationList.get(0), train); // when each line is added, the train is at the first station on that line; therefore, the first station on that line is occupied
    for (int i = 1; i < stationList.size(); i ++) {
      if (! currStation.containsKey(station)) {    // if rest of the stations on the line are not already in the buffer, then put them to the buffer, not that they are not occupied
        currStation.put(stationList.get(i), null);
      }
    }
  }

  // Adds a new planned journey to the simulation
  public void addJourney(String name, List<String> stations) {
    List<Station>  stationList = new LinkedList<>();
    if (stations.isEmpty()) {
      return;
    }
    Station station = null;
    Passenger passenger = Passenger.make(name);
    for (String s : stations) {
      station = Station.make(s);
      stationList.add(station);
    }
    allJourneys.put(passenger, stationList);
    currPassengersS.put(passenger, Station.make(stations.get(0))); // passenger is at the first station in the given stations list
    currPassengersT.put(passenger, null); // passenger is not in a train, put (passenger, null) to  currPassengerT
  }

  // Return normally if initial simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkStart() {
    for (Train t : allLines.keySet()) {
      // check if the train is at the first station on its line
      if (currTrains.get(t) != allLines.get(t).get(0)) {
        throw new UnsupportedOperationException("A train is not in the first station of its line!");
      }
    }
  }

  // Return normally if final simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkEnd() {
    for (Passenger p : allJourneys.keySet()) {
      int lastStop = allJourneys.get(p).size();
      // check if the current station of the passenger matches the final stop in their journey
      if (currPassengersS.get(p) != allJourneys.get(p).get(lastStop-1)) {
        throw new UnsupportedOperationException("A passenger is not in its final stop!");
      }
    }
  }

  // reset to an empty simulation
  public void reset() {
    allLines.clear();
    allJourneys.clear();

  }

  // adds simulation configuration from a file
  public void loadConfig(String filename) {
    Gson gson = new Gson();

    Map mbta = null;
    try {
      // get the JSON object in the from of a map from the JSON file
      mbta = gson.fromJson(new FileReader(filename), Map.class);

      Map lines = (Map) mbta.get("lines"); // get the lines in the mbta map
      Map trips = (Map) mbta.get("trips"); // get the trips in the mbta map

      // go over the keys of the lines map
      for (Object oKey : lines.keySet()) { // call addLine on each key and list
        List<String> sLines = (List<String>) lines.get(oKey);
        String sKey = (String) oKey;
        addLine(sKey, sLines);
      }

      // go over the keys of the trips map
      for (Object oKey : trips.keySet()) { // call addJourney on each key and list
        List<String> sJourney = (List<String>) trips.get(oKey);
        String sKey = (String) oKey;
        addJourney(sKey, sJourney);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }




}
