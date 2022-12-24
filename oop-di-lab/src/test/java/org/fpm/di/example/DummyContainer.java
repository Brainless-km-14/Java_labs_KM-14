package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContainer implements Container {
    private List<Class<?>> onlyClass = new ArrayList<>();
    private Map<Class<?>, Class<?>> ClassClass = new HashMap<>();
    private Map<Class<?>, Object> SingletonEmulation = new HashMap<>();

    public DummyContainer(DummyBinder binder){
        onlyClass = binder.getOnlyClass();
        ClassClass = binder.getClassClass();
        SingletonEmulation = binder.getSingletonEmulation();

    }
    @Override
    public <T> T getComponent(Class<T> clazz) {
        if(onlyClass.contains(clazz)) {
            return createNewObjext(clazz);
        }
        if(ClassClass.containsKey(clazz)){
            return (T) checkDepedencies(ClassClass.get(clazz));
        }
        if(SingletonEmulation.containsKey(clazz)){
            return (T) SingletonEmulation.get(clazz);
        }
        return null;
    }

    private <T> T checkDepedencies(Class<T> clazz){
        T t;
        if((t=getComponent(clazz))!=null)
            return t;
        return createNewObjext(clazz);
    }

    private  <T> T createNewObjext(Class<T> clazz){
        for (Constructor<?> method: clazz.getConstructors()){
            if(method.getAnnotation(Inject.class)!=null){
                try {
                    return (T) method.newInstance(getComponent(method.getParameterTypes()[0]));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
