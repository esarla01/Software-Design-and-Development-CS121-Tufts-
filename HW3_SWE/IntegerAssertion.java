public class IntegerAssertion {
    private int number;

    public IntegerAssertion(int i) {number = i;}

    public IntegerAssertion isEqualTo(int i2)  {
        if (number != i2) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

    public IntegerAssertion isLessThan(int i2) {
        if (number >= i2) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

    public IntegerAssertion isGreaterThan(int i2) {
        if (number <= i2) { throw new ArrayIndexOutOfBoundsException(); }
        return this;
    }

       
}
