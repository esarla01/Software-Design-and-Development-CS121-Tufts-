import static org.junit.Assert.*;
import org.junit.*;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class Tests {
  @Test
  public void testFactoryMethdos() {
    //check if a make method will return the same instance if it is called with the same name
    Train redTrain = Train.make("red");
    assertTrue(redTrain == Train.make("red"));
    assertTrue(Train.make("blue") == Train.make("blue"));

    Station davisStation = Station.make("davis");
    assertTrue(davisStation == Station.make("davis"));
    assertTrue(Station.make("porter") == Station.make("porter"));

    Passenger samPassenger = Passenger.make("Sam");
    assertTrue(samPassenger == Passenger.make("Sam"));
    assertTrue(Passenger.make("Jeff") == Passenger.make("Jeff"));

  }

  // manually check if load configuration record the lines and journeys correctly based on json file
  @Test
  public void test_LoadConfig() {
    MBTA mbta = new MBTA();
    mbta.loadConfig("sample.json");
    System.out.println(mbta.getAllLines().toString());
    System.out.println(mbta.getAllJourneys().toString());
  }

  @Test
  public void test_verify() {
    MBTA mbta = new MBTA();
    mbta.loadConfig("sample.json");
    System.out.println(mbta.getAllLines().toString());
    System.out.println(mbta.getAllJourneys().toString());
  }

  @Test
  public void testSimpleJourney() {
    MBTA mbta = new MBTA();

    Train green = Train.make("green");
    List<String> newGreenLine = List.of("Tufts", "Ball Square", "Magoun Square", "Gillman Square");
    mbta.addLine("green", newGreenLine);

    Station s1 = Station.make("Tufts");
    Station s2 = Station.make("Ball Square");
    Station s3 = Station.make("Magoun Square");
    Station s4 = Station.make("Gillman Square");

    Passenger gc = Passenger.make("Gizem");
    List<String> gcJourneyToFren = List.of("Tufts", "Ball Square", "Magoun Square");
    mbta.addJourney("Gizem", gcJourneyToFren);

    Log log = new Log();
    log.passenger_boards(gc, green, s1);
    log.train_moves(green, s1, s2);
    log.train_moves(green, s2, s3);
    log.passenger_deboards(gc, green, s3);

    try {
      Verify.verify(mbta, log);
      System.out.println(mbta.getCurrTrains());
      System.out.println(mbta.getCurrPassengersS());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test2Stops() {
    Train cool_train = Train.make("The Cool Train");
    Passenger john = Passenger.make("Coltrane");

    MBTA mbta = new MBTA();
    List<String> coolLine = List.of("A", "Love", "Supreme");

    mbta.addLine("The Cool Train", coolLine);
    mbta.addJourney("Coltrane", coolLine);

    Station a = Station.make("A");
    Station love = Station.make("Love");
    Station supreme = Station.make("Supreme");

    Log log = new Log();

    log.passenger_boards(john, cool_train, a);
    log.train_moves(cool_train, a, love);
    log.train_moves(cool_train, love, supreme);
    log.passenger_deboards(john, cool_train, supreme);

    try {
      Verify.verify(mbta, log);
      System.out.println(mbta.getCurrTrains());
      System.out.println(mbta.getCurrPassengersS());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testVerify() {
    MBTA mbta = new MBTA();
    Train trainABCD = Train.make("ABCD");
    Train trainSLICK = Train.make("SLICK");
    Passenger person = Passenger.make("Someone");
    Station sA = Station.make("A");
    Station sB = Station.make("B");
    Station sC = Station.make("C");
    Station sD = Station.make("D");
    Station sS = Station.make("S");
    Station sL = Station.make("L");
    Station sI = Station.make("I");
    Station sK = Station.make("K");

    List<String> line1 = List.of("A", "B", "C", "D");
    List<String> line2 = List.of("S", "L", "I", "C", "K");
    List<String> journey = List.of("A", "C", "I");
    mbta.addLine("ABCD", line1);
    mbta.addLine("SLICK", line2);
    mbta.addJourney("Someone", journey);

    Log log = new Log();
    log.passenger_boards(person, trainABCD, sA);

    log.train_moves(trainABCD,sA,sB);
    log.train_moves(trainABCD,sB,sC);
    log.passenger_deboards(person, trainABCD, sC);
    log.train_moves(trainABCD,sC,sD);
    log.train_moves(trainSLICK,sS,sL);
    log.train_moves(trainSLICK,sL,sI);
    log.train_moves(trainSLICK,sI,sC);
    log.passenger_boards(person,trainSLICK,sC);
    log.train_moves(trainSLICK,sC,sK);
    log.train_moves(trainSLICK,sK,sC);
    log.train_moves(trainSLICK,sC,sI);
    log.passenger_deboards(person,trainSLICK,sI);

    try {
      Verify.verify(mbta, log);
      System.out.println(mbta.getCurrTrains());
      System.out.println(mbta.getCurrPassengersS());
      System.out.println(mbta.getCurrPassengersT());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
