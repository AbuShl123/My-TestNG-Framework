package com.abu.pages;

import com.abu.selenium.Control;
import com.abu.selenium.Locator;

public class LoginPage implements IPage {

    public Control<LoginPage> usernameInput =
            new Control<>(this, Locator.name,
                    "username",
                    "Username input field");

    public Control<LoginPage> passwordInput =
            new Control<>(this, Locator.cssSelector,
                    "input[type='password']",
                    "Password input field");

    public Control<LoginPage> submitBtn =
            new Control<>(this, Locator.id,
                    "sumit",
                    "Submit button");

    public Control<LoginPage> postTitle =
            new Control<>(this, Locator.className,
                    "post-title",
                    "Post title");

    public Control<LoginPage> logoutBtn =
            new Control<>(this, Locator.linkText,
                    "Log out",
                    "Log out button");
}
