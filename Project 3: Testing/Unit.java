import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.nio.channels.UnsupportedAddressTypeException;


public class Unit {

    public static Map<String, Throwable> testClass(String name) {
        //stores all test cases in the given class, in which the keys of the map are the test 
        //case names, and the values are either the exception or error thrown by the test case.
        Map<String, Throwable> map = new HashMap<>();

        try {
        
        Class <?> cls = null;
        //get the class from the given name
        try {
            cls = Class.forName(name);
        } catch (ClassNotFoundException e) {
            System.out.println("Class is not found.");
        }
        //get all the methods in that class
        Method[] allMethods = null;
        try {
            allMethods = cls.getDeclaredMethods();
        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException();
        }
        

        //method storage
        List<Method> annBeforeClass = new ArrayList<Method>(); //stores all methods with BeforeClass annotation
        List<Method> annAfterClass = new ArrayList<Method>(); //stores all methods with AfterClass annotation
        List<Method> annBefore = new ArrayList<Method>(); //stores all methods with Before annotation
        List<Method
        > annAfter = new ArrayList<Method>(); //stores all methods with After annotation
        List<Method> annTest = new ArrayList<Method>(); //stores all methods with Test annotation
    

        for (int i = 0; i < allMethods.length; i++) {

            //Check if all methods have just one annotaion, if not: throw an unchecked exception
            Annotation[] annCurrMethod = allMethods[i].getAnnotations();
            if (annCurrMethod.length > 1) {
                throw new ArrayIndexOutOfBoundsException();
            }

            //Distinguish annotation types and store them in different lists 
            if (allMethods[i].getAnnotations()[0].annotationType().equals(BeforeClass.class)) { //isAnnotationpresent bi bak
                annBeforeClass.add(allMethods[i]); //store all methods with BeforeClass annotations in a list
            }
            else if(allMethods[i].getAnnotations()[0].annotationType().equals(AfterClass.class)) {
                annAfterClass.add(allMethods[i]); //store all methods with AfterClass annotations in a list
            }
            else if(allMethods[i].getAnnotations()[0].annotationType().equals(Before.class)) {
                annBefore.add(allMethods[i]);  //store all methods with Before annotations in a list
            }
            else if(allMethods[i].getAnnotations()[0].annotationType().equals(After.class)) {
                annAfter.add(allMethods[i]);  //store all methods with After annotations in a list
            }
            else if(allMethods[i].getAnnotations()[0].annotationType().equals(Test.class)) {
                annTest.add(allMethods[i]);  //store all methods with Test annotations in an array list
            }

        }

          
        //define a comparator to compare two method names alphabetically
        class ObjectComparator implements Comparator<Object> {

            public int compare(Object obj1, Object obj2) {
                Method m1 = (Method)obj1;
                Method m2 = (Method)obj2;
                return m1.getName().compareTo(m2.getName());
            }
        
        }
        
        //declare a comparator 
        ObjectComparator comparator = new ObjectComparator();

        //sort methods in each list alphabetically using the custom comparator
        Collections.sort(annBeforeClass, comparator);
        Collections.sort(annAfterClass, comparator);
        Collections.sort(annBefore, comparator);
        Collections.sort(annAfter, comparator);
        Collections.sort(annTest, comparator);


        Constructor constructor = null;
        Object o = null;

        try {
            //create an object of the class to pass to the available tests
            constructor = cls.getConstructor();
            o = constructor.newInstance(); 
            //System.out.println("Constructor created.");
        } catch (Exception e) {
            System.out.println("Constructor is not available!");
        }

       
        try {
            //invoke each beforeclass-annotation methods
            for (Method mBeforeClass : annBeforeClass) {

                if (Modifier.isStatic(mBeforeClass.getModifiers())) { //check if it is a defualt method, and if so, invoke the method
                    mBeforeClass.invoke(o);
                } else { throw new ArrayIndexOutOfBoundsException(); }         
            }
        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException();
        }
 

        //invoke all before-annotation, each test-annotation, all after-annotation methods iteratively.
        for (Method mTest : annTest) {

                if (!annBefore.isEmpty()) {
                    for (Method mBefore : annBefore) {
                        try {                   
                            //invoke all before-annotation methods before a test                     
                                mBefore.invoke(o);     
                        } catch (Exception e) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                    }
                }
                      
        
                try  {
                    mTest.invoke(o);
                    map.put(mTest.getName(), null);  
                } catch (InvocationTargetException e) {  //try-catch-exception-add to a map- google how to pull out the actual exception, InvocationTargetException                   
                    map.put(mTest.getName(), e.getTargetException());     
                } 
                catch (Exception e) { 
                    map.put(mTest.getName(), e); 
                }                    
                            
                if (!annAfter.isEmpty()) {
                    for (Method mAfter : annAfter) {
                        try {
                            //invoke all after-annotation methods after a test
                            mAfter.invoke(o);                           
                        } catch (Exception e) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                    }
                }
    
        }


            
        try {
            //invoke each afterclass-annotation methods
            for (Method mAfterClass : annAfterClass) {
                if (Modifier.isStatic(mAfterClass.getModifiers())) { //check if it is a defualt method, and if so, invoke the method
                    mAfterClass.invoke(o);
                } else { throw new ArrayIndexOutOfBoundsException(); }
            }
        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException();
        }
    
        } catch (Exception e) {
            System.out.println("Failed.");
        }

        return map;
    }


    public static Map<String, Object[]> quickCheckClass(String name) {

        Map<String, Object[]> map = new HashMap<>(); 
        
        Class <?> cls = null;
        //get the class from the given name
        try {
            cls = Class.forName(name);
        } catch (ClassNotFoundException e) {
            System.out.println("Class is not found.");
        }
        //get all the methods in that class
        Method[] allMethods = cls.getDeclaredMethods();  //get all the methods in that class     
        List<Method> annProperty = new ArrayList<Method>();  //stores all methods with annProperty annotation in a list

        annotationTypeCheck(allMethods, annProperty);
        sortAnnotationList(annProperty);

        Constructor cns = null;
        Object o = null;

        try {
            //create an object of the class to pass to the available tests
            cns = cls.getConstructor();
            o = cns.newInstance(); 
            //System.out.println("Constructor created.");
        } catch (Exception e) {
            System.out.println("Constructor is not available!");
        }
    
        for (Method method : annProperty) {                     //loop1 : every method
            List<List<Object>> permutationList = new LinkedList<>();
            List<List<Object>> argumentsList = new LinkedList<>();
            //get the parameter type 
            Parameter[] parameters = method.getParameters(); 

            for (Parameter param : parameters) {                //loop2 : every parameter
                //get the annotations of each parameter
                Annotation[] anns = param.getAnnotations();
                List<Object> argList = new LinkedList<>();

                if (anns[0] instanceof IntRange) {        
                    IntRange currAnn = (IntRange)anns[0];
                    int max = currAnn.max();
                    int min = currAnn.min();
                    for (int i = min; i <= max; i ++){ argList.add(i); }
                    permutationList.add(argList);
                    
                } else if (anns[0] instanceof StringSet) {
                    StringSet currAnn = (StringSet)anns[0];
                    String[] str = currAnn.strings();
                    for (int i = 0; i < str.length; i ++){ argList.add(str[i]); }
                    permutationList.add(argList);
                   
                } else if (anns[0] instanceof ListLength) {
                    ListLength currAnn = (ListLength)anns[0];
                    int listMax = currAnn.max();
                    int listMin = currAnn.min();
                    AnnotatedType[] annType = method.getAnnotatedParameterTypes();
                    AnnotatedParameterizedType prType = (AnnotatedParameterizedType)annType[0];
                    AnnotatedType[] actualTypes = prType.getAnnotatedActualTypeArguments();
                    Annotation[] annFinal = actualTypes[0].getAnnotations();
                
                    if (annFinal[0] instanceof IntRange) {
                        IntRange annLast = (IntRange)annFinal[0];
                        //System.out.println("IntRange");
                        int rangeMin = annLast.min();
                        int rangeMax = annLast.max();
                        //add the raneg of numbers to a list for IntRange
                        List<Object> entryList = new LinkedList<>();
                        for (int i = rangeMin; i <= rangeMax; i ++){ 
                            entryList.add(i); 
                        }
                        //find all permuations of the list
                        for (int i = listMin; i <= listMax; i ++) {
                            permutations(entryList, argList, i, new LinkedList<>());
                        } 
                        permutationList.add(argList);  

                    } else if (annFinal[0] instanceof StringSet) {
                        StringSet annLast = (StringSet)annFinal[0];
                        String[] str = annLast.strings();
                        //add the raneg of numbers to a list for IntRange
                        List<Object> entryList = new LinkedList<>();
                        for (int i = 0; i < str.length; i ++){ 
                            entryList.add(str[i]); 
                        }
                        //find all permuations of the list
                        for (int i = listMin; i <= listMax; i ++) {
                            permutations(entryList, argList, i, new LinkedList<>());
                        } 
                        permutationList.add(argList);  
                    }
                } else if (anns[0] instanceof ForAll) {
                    ForAll currAnn = (ForAll)anns[0];
                    String annName = currAnn.name();
                    int annInteger = currAnn.times();
                    Method met = null;
                    Object parObj = null;
                    Object flag = null;
                    try {
                        met = cls.getDeclaredMethod(annName);
                        for (int i = 0; i < annInteger; i ++){
                            parObj = met.invoke(o);
                            argList.add(parObj);                          
                        }
                    } catch (Exception e) {
                        throw new UnsupportedOperationException();
                    }
                    permutationList.add(argList); 
                } 

            }//for each parameter
            listAllPermutations(permutationList, argumentsList, 0, new LinkedList<Object>());

            int limit = argumentsList.size();
            if (limit > 100) { limit = 100;}
            Boolean resultInvoke = true;
 
            for (int i = 0; i < limit; i ++) { 
                Boolean bol = null;
                try {
                     bol = (Boolean)method.invoke(o, argumentsList.get(i).toArray());
                } catch (Exception e) {
                    resultInvoke = false;
                    map.put(method.getName(), argumentsList.get(i).toArray());
                    break;
                } 
                if (bol == false) {
                    resultInvoke = false;
                    map.put(method.getName(), argumentsList.get(i).toArray());
                    break;
                }
            }
                if (resultInvoke == true) { map.put(method.getName(), null); };

        } //for each method   
    
        return map;
    }


    private static void annotationTypeCheck(Method[] allMethods, List<Method> annList) {
        for (int i = 0; i < allMethods.length; i++) {
            if (allMethods[i].isAnnotationPresent(Property.class)) { 
                annList.add(allMethods[i]); //put all methods with Property annotations in a list
            }
        }
    }

    private static void sortAnnotationList(List<Method> list) {
        //define a comparator to compare two method names alphabetically
        class ObjectComparator implements Comparator<Object> {

            public int compare(Object obj1, Object obj2) {
                Method m1 = (Method)obj1;
                Method m2 = (Method)obj2;
                return m1.getName().compareTo(m2.getName());
            }
        
        } 
        //declare a comparator 
        ObjectComparator comparator = new ObjectComparator();
        //sort methods in each list alphabetically using the custom comparator
        Collections.sort(list, comparator);

    }

    //Inspiration/Resource: (incorporates the same logic given in the recursion) https://codereview.stackexchange.com/questions/91475/permutation-of-n-lists
    private static void listAllPermutations(List<List<Object>> entryList, List<List<Object>> argsList,
                                                                                int levelList, List<Object> elementList) {
        if (levelList == entryList.size()) { argsList.add(elementList); return;}
                        //System.out.println(levelList);
        for (int i = 0; i < entryList.get(levelList).size(); i++) {
                        //System.out.println("Element List: " + elementList);
            List<Object> elementListDuplicate = new LinkedList(elementList);
            elementListDuplicate.add(entryList.get(levelList).get(i));
                        //System.out.println("ElementListDuplicate List: " + elementListDuplicate);
            listAllPermutations(entryList, argsList, levelList +1, elementListDuplicate);
        }                                                                         
    }

    private static void permutations(List<Object> entryList, List<Object> resultList, int elementCount, List<Object> eachElement) {      
        if (eachElement.size() == elementCount) { 
            resultList.add(eachElement); 
            return; 
        }
        for (int i = 0; i < entryList.size(); i++) {
            List<Object> list = new LinkedList(eachElement);
            list.add(entryList.get(i));
            permutations(entryList, resultList, elementCount, list);
        }
    }



    
    
}






