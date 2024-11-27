package jrails;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;
public class JRouter {

    private HashMap<String, String> routerMap = new HashMap<>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        String key = verb + path; // router
        String value = clazz.getName() + '#' + method;
        routerMap.put(key, value);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        String route = verb + path;
        if(!routerMap.containsKey(route)) {
            return null;
        }
        return routerMap.get(route);
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        String route = getRoute(verb, path);
        if (route == null) {
            throw new UnsupportedOperationException("route does not exist!");
        }
        int index = route.indexOf("#");
        String className = route.substring(0, index);
        String methodName = route.substring(index + 1);

        //substring contMethod
        try {
            Class<?> c = Class.forName(className); //getname to first part if contMethod
            Constructor<?> cons =  c.getConstructor();
            Object ins = cons.newInstance();
            Method meth = c.getDeclaredMethod(methodName, Map.class);//maps.class gonder
            return  (Html) meth.invoke(ins, params); //
        }catch(Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
