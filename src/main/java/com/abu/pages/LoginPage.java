package com.abu.pages;

import com.abu.selenium.Control;
import com.abu.selenium.ControlBy;
import com.abu.selenium.ControlFactory;

public class LoginPage implements IPage {

    public LoginPage() {
        ControlFactory.initControls(this);
    }

    @ControlBy(name = "username", description = "Username input field")
    public Control<LoginPage> usernameInput;

    @ControlBy(css = "input[type='password']", description = "Password input field")
    public Control<LoginPage> passwordInput;

    @ControlBy(id = "submit", description = "Submit button")
    public Control<LoginPage> submitBtn;

    @ControlBy(className = "post-title", description = "Post title")
    public Control<LoginPage> postTitle;

    @ControlBy(linkText = "Log out", description = "Log out button")
    public Control<LoginPage> logoutBtn;
}
