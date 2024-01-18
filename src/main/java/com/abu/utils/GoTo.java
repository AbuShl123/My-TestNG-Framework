package com.abu.utils;

import com.abu.pages.IPage;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;

public class GoTo {

    private GoTo() {
    }

    public static  <T extends IPage> T page(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Logger.log(Level.FATAL, "Failure instantiating page object");
            throw new RuntimeException(e);
        }
    }
}
