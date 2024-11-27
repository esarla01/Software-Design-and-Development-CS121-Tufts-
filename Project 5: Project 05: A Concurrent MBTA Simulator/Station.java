import java.util.HashMap;

public class Station extends Entity {

  private static HashMap<String, Station> cache = new HashMap<>(); // maps train names to the train instances

  private Station(String name) { super(name); }

  public static Station make(String name) {
    if (cache.containsKey(name)) { // search in the cache if the instance exists
      return cache.get(name);     // if so, return that instance
    }
    cache.put(name, new Station(name));       // if not, initialize a new train and put it in the map
    return cache.get(name);                 // return that newly initialized train
  }
}
