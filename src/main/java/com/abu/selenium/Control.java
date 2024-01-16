package com.abu.selenium;

import com.abu.pages.IPage;
import com.abu.utils.Logger;
import com.abu.utils.PostActions;
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
    }

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

    // action methods

    public T click() {
        if (conditionFails()) return page;

        try {
            getElement().click();
        } catch (Exception e) {
            Logger.log(ERROR, "Failed to click on " + name + toHTML());
            throw e;
        }

        executePostActions();

        return page;
    }

    // return methods

    public boolean isDisplayed() {
        try {
            return getElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
