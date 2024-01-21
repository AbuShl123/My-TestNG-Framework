package com.abu.selenium;

import com.abu.pages.IPage;
import com.abu.utils.Logger;
import org.apache.logging.log4j.Level;
import org.openqa.selenium.InvalidSelectorException;

import java.lang.reflect.Field;

public class ControlFactory {

    private ControlFactory() {
    }

    public synchronized static <T extends IPage> void initControls(T page) {
        if (page == null) throw new NullPointerException("Page instance not provided");

        Class<?> clazz = page.getClass();

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            ControlBy fieldAnnotation = field.getAnnotation(ControlBy.class);

            if (fieldAnnotation == null) continue;

            field.setAccessible(true);
            try {
                field.set(page, ControlFactory.getControl(page, fieldAnnotation));
            } catch (IllegalAccessException e) {
                Logger.log(Level.FATAL, "Failed to instantiate control fields in the page class " + page.getClass().getName());
                throw new RuntimeException(e);
            }
        }
    }

    private static <T extends IPage> Control<T> getControl(T page, ControlBy annotation) {
        Locator locator;
        StringBuilder value = new StringBuilder();
        String name;
        Runnable[] postActions;

        locator = findLocator(annotation, value);
        name = annotation.description();
        postActions = annotation.postActions();

        return new Control<>(page, locator, value.toString(), name, postActions);
    }

    private static Locator findLocator(ControlBy annotation, StringBuilder value) {
        if (!"".equals(annotation.css())) {
            value.append(annotation.css());
            return Locator.cssSelector;
        } else if (!"".equals(annotation.xpath())) {
            value.append(annotation.xpath());
            return Locator.xpath;
        } else if (!"".equals(annotation.name())) {
            value.append(annotation.name());
            return Locator.name;
        } else if (!"".equals(annotation.className())) {
            value.append(annotation.className());
            return Locator.className;
        } else if (!"".equals(annotation.linkText())) {
            value.append(annotation.linkText());
            return Locator.linkText;
        } else if (!"".equals(annotation.partialLinkText())) {
            value.append(annotation.partialLinkText());
            return Locator.partialLinkText;
        } else if (!"".equals(annotation.tagName())) {
            value.append(annotation.tagName());
            return Locator.tagName;
        } else if (!"".equals(annotation.id())) {
            value.append(annotation.id());
            return Locator.id;
        } else {
            throw new InvalidSelectorException("Selector on control is empty string?");
        }
    }
}
