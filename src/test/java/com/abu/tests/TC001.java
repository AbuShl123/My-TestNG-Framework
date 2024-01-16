package com.abu.tests;

import com.abu.framework.TestBase;
import com.abu.pages.LoginPage;
import com.abu.selenium.Driver;
import com.abu.utils.GoTo;
import org.testng.annotations.Test;

public class TC001 extends TestBase {

    @Test(description = "Verify user can successfully login the application")
    public void tc001() {
        Driver.getDriver().navigate().to("https://practicetestautomation.com/practice-test-login/");

        String username = "student";
        String password = "Password123";

        GoTo.page(LoginPage.class)
                .usernameInput.sendKeys(username)
                .passwordInput.sendKeys(password)
                .submitBtn.click()
                .postTitle.waitUntilDisplayed()
                .postTitle.assertDisplayed()
                .postTitle.assertTextEquals("Logged In Successfully")
                .logoutBtn.ifDisplayed().click();
    }
}
