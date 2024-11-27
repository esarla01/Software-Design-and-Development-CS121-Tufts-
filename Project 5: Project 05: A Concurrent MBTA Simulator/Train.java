import java.util.HashMap;

public class Train extends Entity {

  private static HashMap<String, Train> cache = new HashMap<>(); // maps train names to the train instances

  private Train(String name) { super(name); }

  public static Train make(String name) {
    if (cache.containsKey(name)) { // search in the cache if the instance exists
      return cache.get(name);     // if so, return that instance
    }
    cache.put(name, new Train(name));       // if not, initialize a new train and put it in the map
    return cache.get(name);                 // return that newly initialized train
  }
}