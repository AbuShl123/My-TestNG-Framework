package com.abu.pages;

import com.abu.selenium.Control;
import com.abu.selenium.ControlFactory;
import com.abu.selenium.ControlWith;

public class LoginPage implements IPage {

    public LoginPage() {
        ControlFactory.initControls(this);
    }

    @ControlWith(name = "username", description = "Username input field")
    public Control<LoginPage> usernameInput;

    @ControlWith(css = "input[type='password']", description = "Password input field")
    public Control<LoginPage> passwordInput;

    @ControlWith(id = "submit", description = "Submit button")
    public Control<LoginPage> submitBtn;

    @ControlWith(className = "post-title", description = "Post title")
    public Control<LoginPage> postTitle;

    @ControlWith(linkText = "Log out", description = "Log out button")
    public Control<LoginPage> logoutBtn;
}
