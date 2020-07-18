package org.cooldieye.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectorHelper {
    /**
     * Create new default (empty constructor) instance of object
     * @param className name of class with all class path
     * @return instance of object
     */
    public static Object newDefaultInstance(String className)  {
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
            Object instance = aClass.newInstance();
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set a field value to an object
     * @param parent Parent object
     * @param fieldName set value directly to field, can do for private field also
     * @param child Child object
     */
    public static void setField(Object parent, String fieldName, Object child) {
        try {
            Class<?> aClass = parent.getClass();
            Field declaredField = aClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(parent, child);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Execute a method if exist, if not found just ignore it
     * @param object target object to call method
     * @param methodName public method name
     */
    public static void invokeMethodIfExist(Object object, String methodName) {
        Class<?> aClass = object.getClass();
        try {
            Method method = aClass.getMethod(methodName);
            method.invoke(object);
        } catch (NoSuchMethodException e) {
            //just ignore
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
