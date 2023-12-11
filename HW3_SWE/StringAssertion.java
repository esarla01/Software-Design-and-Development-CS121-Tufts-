public class StringAssertion  {
    private String str;

    public StringAssertion(String s) {str = s;}	

    public StringAssertion isNotNull() { 
        if (str == null) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }
    
    public StringAssertion isNull() {
        if (str != null) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }
    
    public StringAssertion isEqualTo(Object o)  {
        if (!str.equals((String)o)) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

    public StringAssertion isNotEqualTo(Object o)  {
        if (str.equals((String)o)) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

    public StringAssertion startsWith(String s2)  {
        if (!str.startsWith(s2)) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

    public StringAssertion isEmpty()  {
        if (!str.isEmpty()) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }
    public StringAssertion contains(String s2)  {
        if (!str.contains(s2)) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

}
