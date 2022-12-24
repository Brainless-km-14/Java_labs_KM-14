package org.fpm.di.example;

import org.fpm.di.Binder;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyBinder implements Binder {
    private List<Class<?>> onlyClass = new ArrayList<>();
    private Map<Class<?>, Class<?>> ClassClass = new HashMap<>();
    private Map<Class<?>, Object> SingletonEmulation = new HashMap<>();

    @Override
    public <T> void bind(Class<T> clazz) {
        if(clazz.getAnnotation(Singleton.class) != null) {
            try {
                SingletonEmulation.put(clazz, clazz.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        else
            onlyClass.add(clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        ClassClass.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        SingletonEmulation.put(clazz, instance);
    }

    public List<Class<?>> getOnlyClass(){
        return onlyClass;
    }
    public Map<Class<?>, Class<?>> getClassClass(){
        return ClassClass;
    }
    public Map<Class<?>, Object> getSingletonEmulation(){
        return SingletonEmulation;
    }
}
