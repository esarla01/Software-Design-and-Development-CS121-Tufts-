import java.util.*;

class testClass1 {

    public testClass1() {}

    @BeforeClass 
    public static void b1() {
        System.out.println("Using @BeforeClass annotations ,executed before all test cases (b1) ");
    }

    @BeforeClass 
    public static void a1() {
        System.out.println("Using @BeforeClass annotations ,executed before all test cases (a1)");
    }

    @BeforeClass 
    public static void c1() {
        System.out.println("Using @BeforeClass annotations ,executed before all test cases (c1) ");
    }
   

    @AfterClass
    public static void a2() {
        System.out.println("Using @AfterClass ,executed after all test cases (a2)");
    }

    @AfterClass
    public static void c2() {
        System.out.println("Using @AfterClass ,executed after all test cases (c2)");
    }

    @AfterClass
    public static void b2() {
        System.out.println("Using @AfterClass ,executed after all test cases (b2)");
    }

    @Before 
    public static void b3() {
        System.out.println("Using @Before annotations ,executed before each test cases (b3)");
    }

    @Before 
    public static void a3() {
        System.out.println("Using @Before annotations ,executed before each test cases (a3)");
    }

    @After
    public void a4() {
        System.out.println("Using @After ,executed after each test cases (a4)");
    }

    @After
    public void b4() {
        System.out.println("Using @After ,executed after each test cases (b4)");
    }

    @Test
    public void b5() {
        String erin = null;
        System.out.println("test - b5");
        System.out.println("Result: " + Assertion.assertThat(erin).isNotNull()); 
        
    }

    @Test
    public void a5() {
        String emir = "emir";
        System.out.println("test - a5");
        System.out.println("Result: "); //why not throwing error?
        Assertion.assertThat(emir).isNotNull().isEqualTo("emir");
    }
}
