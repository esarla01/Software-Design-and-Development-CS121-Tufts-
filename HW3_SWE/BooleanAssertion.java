public class BooleanAssertion {

    private boolean bol;

    public BooleanAssertion(boolean b) {bol = b;}	

    public BooleanAssertion isEqualTo(boolean b2) {
        if (bol != b2) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }
    public BooleanAssertion isTrue() {
        if (bol == false) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }
    public BooleanAssertion isFalse() {
        if (bol == true) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

}
