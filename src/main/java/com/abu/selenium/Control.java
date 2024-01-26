package com.abu.selenium;

import com.abu.pages.IPage;
import com.abu.utils.Logger;
import com.abu.utils.PostActions;
import com.abu.utils.Waits;
import com.abu.utils.WebUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static org.apache.logging.log4j.Level.*;

public class Control<T extends IPage> extends BaseElement {
    private final T page;
    private final Runnable[] postActions;
    private boolean condition;

    public Control(T page, Locator locator, String value, String name) {
        this(page, locator, value, name, PostActions.NONE);
    }

    public Control(T page, Locator locator, String value, String name, Runnable... postActions) {
        super(locator, value, name);
        this.page = page;
        this.postActions = postActions;
        this.condition = true;
    }

    /**
     * This method is useful if you want to operate upon the certain web-element which can be found in the List of web-elements when calling getElements() function
     * @param i the web-element index in the list of elements that is to be operated (starting from 0)
     * @return null if index is out of bounce, otherwise the current control instance
     */
    public Control<T> get(int i) {
        return super.get(i, this);
    }

    private void executePostActions() {
        for (Runnable action : postActions) {
            action.run();
        }
    }

    private boolean conditionFails() {
        if (!condition) {
            condition = true;
            return true;
        }

        return false;
    }

    public Control<T> doIf(boolean condition) {
        this.condition = condition;
        return this;
    }

    public Control<T> ifDisplayed() {
        return doIf(isDisplayed());
    }

    // action methods:

    public T click() {
        if (conditionFails()) return page;

        try {
            getElement().click();
            Logger.log("Click on " + name + toHTML());
        } catch (Exception e) {
            Logger.log(ERROR, "Failed to click on " + name + toHTML());
            throw e;
        }

        executePostActions();

        return page;
    }

    public T sendKeys(String s) {
        if (conditionFails()) return page;

        try {
            getElement().sendKeys(s);
            Logger.log("Enter text \"" + s + "\" to " + name + toHTML());
        } catch (Exception e) {
            Logger.log(ERROR, "Couldn't enter text \"" + s + "\" to " + name + toHTML());
            throw e;
        }

        executePostActions();

        return page;
    }

    // assertion methods:

    public T assertDisplayed() {
        if (conditionFails()) return page;

        Assert.assertTrue(isDisplayed(), "Element is not displayed: " + name + toHTML());

        Logger.log("Element is displayed: " + name + toHTML());

        return page;
    }

    public T assertTextEquals(String expectedText) {
        if (conditionFails()) return page;

        String actualText = getText();

        Assert.assertEquals(actualText, expectedText,
                "Element text validation failed. Expected: " + expectedText + ", actual: " + actualText + " on " + name + toHTML());

        Logger.log("Element text is verified, \"" + expectedText + "\" on " + name + toHTML());
        return page;
    }

    public T moveToElement() {
        if (conditionFails()) return page;

        WebUtils.getActions().moveToElement(getElement()).perform();

        return page;
    }

    // wait methods:

    public T waitUntilDisplayed() {
        if (conditionFails()) return page;

        try {
            Waits.getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            Logger.log(ERROR, "Failed to wait for visibility of " + name + toHTML());
        }

        Logger.log("Desired element is visible: " + name + toHTML());
        return page;
    }

    // return methods:

    public boolean isDisplayed() {
        try {
            return getElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getText() {
        try {
            return getElement().getText();
        } catch (Exception e) {
            Logger.log(WARN, "Element text not received: " + name + " - " + e + toHTML());
            return null;
        }
    }
}
