import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sim {

  public static void run_sim(MBTA mbta, Log log) {

//    System.out.println(mbta.getCurrStation().toString());
//    System.out.println(mbta.getAllJourneys().toString());
//    System.out.println(mbta.getAllLines());
//    System.out.println(mbta.getCurrTrains());
//    System.out.println(mbta.getCurrPassengersT());
//    System.out.println(mbta.getCurrPassengersS());


    /* Create new threads for each train */
    for (Train t: mbta.getAllLines().keySet()) {
      TrainThread tt = new TrainThread(t, mbta, log);
      mbta.trainThreads.put(t, tt);
    }

    /* Create new threads for each train */
    for (Passenger p: mbta.getAllJourneys().keySet()) {
      PassengerThread pt = new PassengerThread(p, mbta, log);
      mbta.passengerThreads.put(p, pt);
    }

//    System.out.println(mbta.trainThreads.keySet());
//    System.out.println(mbta.passengerThreads.keySet());
//    System.out.println(mbta.buffer);
//    System.out.println(mbta.getAllJourneys().toString());
//    System.out.println(mbta.getAllLines().toString());

    /* Initialize the station locks and conditions for those locks */
    for (Station s: mbta.getCurrStation().keySet()) {
      ReentrantLock sLock = new ReentrantLock();   // create a lock for each station
      Condition lCondition = sLock.newCondition();    // create a condition for the lock
      mbta.locks.put(s, sLock); // put the lock
      mbta.conditions.put(s, lCondition);  // put the condition
    }

    try {

      for (Thread t : mbta.trainThreads.values()) {
          t.start();
//        try {
//          t.join(); // once all te passengers complete their journey, the program continues
//        } catch (Exception e) { }
      }
      for (Thread t : mbta.passengerThreads.values()) {
          t.start();
      }

      for (Thread t : mbta.passengerThreads.values()) {
        try {
          t.join(); // once all te passengers complete their journey, the program continues
        } catch (Exception e) { System.out.println("this project is drving me crazy ;("); }

      }



      for (Thread t : mbta.passengerThreads.values()) {
        try {
          t.join(); // once all te passengers complete their journey, the program continues
        } catch (Exception e) { System.out.println("erininindenfkjghaekl"); }

      }

      for (Thread t : mbta.trainThreads.values()) {
          t.interrupt();
        System.out.println("interrupted");
      }
    } catch(Exception e) { return; }

  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("usage: ./sim <config file>");
      System.exit(1);
    }

    MBTA mbta = new MBTA();
    mbta.loadConfig(args[0]);

    Log log = new Log();

    run_sim(mbta, log);


    String s = new LogJson(log).toJson();
    PrintWriter out = new PrintWriter("log.json");
    out.print(s);
    out.close();


    mbta.reset();


    mbta.loadConfig(args[0]);
    Verify.verify(mbta, log);
  }
}
