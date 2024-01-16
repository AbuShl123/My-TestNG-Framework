package com.abu.selenium;

import com.abu.utils.Logger;
import com.abu.utils.WebUtils;
import org.apache.logging.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.NoSuchElementException;


public abstract class BaseElement {
    public final Locator locator;
    public final String value;
    public final String name;
    public By by;

    private boolean isSticky = false;
    private WebElement element;

    public BaseElement(Locator locator, String value, String name) {
        this.name = name;
        this.locator = locator;
        this.value = value;
        this.by = findBy(locator, value);
    }

    public WebElement getElement() {
        if (isSticky && element != null) return element;

        try {
            element = WebUtils.findElement(by);
        } catch (Exception e) {
            Logger.log(Level.ERROR, "Failure finding element, " + e.getMessage());
            throw e;
        }

        return element;
    }

    public List<WebElement> getElements() {
        return WebUtils.findElements(by);
    }

    public <T extends Control<?>> T get(int i, T control) {
        List<WebElement> elements = getElements();
        if (i >= elements.size()) return null;

        this.element = elements.get(i);
        this.isSticky = true;

        return control;
    }

    protected String toHTML() {
        String html = " <span style=\"color: gray;\">%s</span>";

        return String.format(html, by);
    }

    private static By findBy(@Nonnull Locator locator, String value) {
        By by;
        switch (locator) {
            case CssSelector:
                by = By.cssSelector(value);
                break;
            case Xpath:
                by = By.xpath(value);
                break;
            case Id:
                by = By.id(value);
                break;
            case Name:
                by = By.name(value);
                break;
            case TagName:
                by = By.tagName(value);
                break;
            case ClassName:
                by = By.className(value);
                break;
            case LinkText:
                by = By.linkText(value);
                break;
            case PartialLinkText:
                by = By.partialLinkText(value);
                break;
            default:
                throw new IllegalArgumentException("Unknown locator type: " + locator);
        }
        return by;
    }
}
