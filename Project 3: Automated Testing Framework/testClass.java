import java.util.*;
class testClass {

    public testClass() {}

    @Property
    public Boolean absNonNeg(@StringSet(strings={"a1", "a2", "a5"}) String n, @StringSet(strings={"s1", "s2"}) String s, @IntRange(min=-10, max=10) Integer i) {
        //System.out.println(n + " " + s + " " + i);
        return true;
        
    }

    @Property
    public Boolean testListLengthRange(@ListLength(min=0, max=2) List<@IntRange(min=5, max=7) Integer> i) {
        //System.out.println("Parameter: " + i);
        return true;
        
    }

    @Property
    public Boolean testListLengthString(@ListLength(min=0, max=3) List<@StringSet(strings={"erin", "idil", "mina"}) String> s) {
        //System.out.println("Parameter: " + s);
        return true;
        
    }

    @Property 
    public Boolean ZestFoo(@ForAll(name="genIntSet", times=10) Object o) {
        Set s = (Set) o;
        s.add("foo");
        return s.contains("foo");
      }
      
      int count = 0;
      public Object genIntSet() {
        Set s = new HashSet();
        for (int i=0; i<count; i++) { s.add(i); }
        count++;
        return s;
      }
          


    

    
}
