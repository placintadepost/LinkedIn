package Tests;

import Pages.MainPage;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class test_MainPage extends MainPage {

    @Test(priority = 0)
    public void enterMainPAge() {

        MainPage.enterMainPage();

    }

    @Test(priority = 1)
    public void loginDummyAccount() {

        MainPage.loginTheUser();

    }

    @Test(priority = 2)
    public void inputQuery() {

        MainPage.searchAndInput();

    }

    @Test(priority = 3)
    public void pingIt() throws IOException {

        MainPage.anotherPingExample();

    }

    @Test(priority = 4)
    public void urlManipulation() throws URISyntaxException {

        MainPage.urlManip();

    }
    
    @Test(priority = 5)
    public void testAllLinksValid() {

        MainPage.confirmLinksAreValid();

    }


}
