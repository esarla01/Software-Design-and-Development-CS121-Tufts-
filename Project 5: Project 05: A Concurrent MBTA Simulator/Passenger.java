import java.util.HashMap;

public class Passenger extends Entity {

  private static HashMap<String, Passenger> cache = new HashMap<>(); // maps train names to the train instances


  private Passenger(String name) { super(name); }

  public static Passenger make(String name) {
      if (cache.containsKey(name)) { // search in the cache if the instance exists
        return cache.get(name);     // if so, return that instance
      }
      cache.put(name, new Passenger(name));       // if not, initialize a new train and put it in the map
      return cache.get(name);                 // return that newly initialized train
    }

}

