public class ObjectAssertion {
        private Object obj;

        public ObjectAssertion(Object o) {obj = o;}

        public ObjectAssertion isNotNull() { 
            if (obj == null) { throw new ArrayIndexOutOfBoundsException(); }
            return this;
        }

        public ObjectAssertion isNull() {
            if (obj != null) { throw new ArrayIndexOutOfBoundsException(); }
            return this;
        }
        
        public ObjectAssertion isEqualTo(Object o2)  {
            if (!obj.equals(o2)) { throw new ArrayIndexOutOfBoundsException(); }
            return this;
        }

        public ObjectAssertion isNotEqualTo(Object o2)  {
            if (obj.equals(o2)) { throw new ArrayIndexOutOfBoundsException(); }
            return this;
        }

        public ObjectAssertion isInstanceOf(Class c) {
            if(!(c.isInstance(obj) )) { throw new ArrayIndexOutOfBoundsException(); }
                return this;
        }
           
}
