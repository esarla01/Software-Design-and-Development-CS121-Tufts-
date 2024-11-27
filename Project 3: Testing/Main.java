import java.util.*;
public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        Unit sample = new Unit();
        Map<String, Throwable> map = sample.testClass("testClass1");
        System.out.println(map);


        Map<String, Object[]> map2= sample.quickCheckClass("testClass");
        System.out.println(map2);
    } 
    
}
