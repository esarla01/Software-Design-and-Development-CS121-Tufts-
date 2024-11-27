
public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    static ObjectAssertion assertThat(Object o) { 
        return new ObjectAssertion(o);    
    }
    
    static StringAssertion assertThat(String s) {
        return new StringAssertion(s);
    }
	
    static BooleanAssertion assertThat(boolean b) {
	    return new BooleanAssertion(b);
    }
    static IntegerAssertion assertThat(int i) {
	    return new IntegerAssertion(i);
    } 
    
}