package Pages;

import Base.Methods;
import com.aventstack.extentreports.Status;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class MainPage extends Methods {

    private static final By signInBtn = By.cssSelector("a[class='nav__button-secondary']");

    private static final By emailField = By.cssSelector("input[id='username']");

    private static final By passField = By.cssSelector("input[id='password']");

    private static final By signInBtnOther = By.xpath("(//*[text()[contains(.,'Sign in')]])[3]");

    private static final By searchField = By.cssSelector("input[class='search-global-typeahead__input always-show-placeholder']");

    private static final By notFound = By.xpath("//*[text()[contains(.,'Page not found')]]");


    public static WebElement notFound() {

        return driver.findElement(notFound);
    }

    public static WebElement searchField() {

        return driver.findElement(searchField);
    }

    public static WebElement signInBtn() {

        return driver.findElement(signInBtn);
    }

    public static WebElement emailField() {

        return driver.findElement(emailField);
    }

    public static WebElement passField() {

        return driver.findElement(passField);
    }

    public static WebElement signInBtnOther() {

        return driver.findElement(signInBtnOther);
    }

    public static void enterMainPage() {

        try {

            parentTest = extent.createTest("LinkedIn - Selenium/TestNG Framework");

            childTest = parentTest.createNode("Access LinkedIn");

            driver.get(homePage);

            childTest.log(Status.PASS, "SUCCESS: LinkedIn website has been accessed");


        } catch (Exception failStatus) {

            System.out.println("Failed to access Heroku app link");

            childTest.log(Status.FAIL, "FAIL: LinkedIn website could not be accessed");
        }


    }

    public static void confirmLinksAreValid() {

        try {

            childTest = parentTest.createNode("Check that all links are valid on LinkedIn main page");
            
            driver.get("https://www.linkedin.com/home/");

            jsAlerts("alert('The test will now validate functional links on the homepage, please wait');");

            linkValid("https://www.linkedin.com/home");

            childTest.log(Status.PASS, "SUCCESS: All available links have been scanned");

            childTest.log(Status.INFO, "A list of all links have been in the log file");

        } catch (Error failStatus) {

            System.out.println("Failed to confirm the validity of LinkedIn main page");

            childTest.log(Status.FAIL, "FAIL: Could not confirm the validity of pages on the LinkedIn website");

        }


    }

    public static void loginTheUser() {

        try {

            childTest = parentTest.createNode("Login user into website");

            click(signInBtn());

            justWait(1500);

            emailField().sendKeys("olga.test.auto@gmail.com");

            passField().sendKeys("QAZxsw123");

            click(signInBtnOther());

            childTest.log(Status.PASS, "SUCCESS: User is logged in");


        } catch (Error failStatus) {

            System.out.println("Failed to logged in user");

            childTest.log(Status.FAIL, "FAIL: Could not log in user");


        }


    }

    public static void searchAndInput() {

        try {

            childTest = parentTest.createNode("Input an SQL query within search input");

            searchField().sendKeys("SELECT * FROM Users WHERE UserId = 105 OR 1=1");

            justWait(1500);

            searchField().sendKeys(Keys.ENTER);

            justWait(2000);

            childTest.log(Status.PASS, "SUCCESS: SQL successfully inputted");

        } catch (Error failStatus) {

            System.out.println("Failed to input the SQL query");

            childTest.log(Status.FAIL, "FAIL: Could not input SQL query");


        }


    }

    public static void anotherPingExample() throws IOException {

        try {

            long currentTime = System.currentTimeMillis();
            boolean isPinged = InetAddress.getByName("www.linkedin.com").isReachable(2000);
            currentTime = System.currentTimeMillis() - currentTime;


            if (isPinged) {

                System.out.println("Pinged successfully");

                if (currentTime <= 20) {

                    System.out.println("Low ping time: " + currentTime + " milliseconds");

                    jsAlerts("alert('Low ping time " + currentTime + " milliseconds');");

                } else if (currentTime <= 100) {

                    System.out.println("Average ping time: " + currentTime + " milliseconds");

                    jsAlerts("alert('Average ping time " + currentTime + " milliseconds');");

                } else if (currentTime >= 120) {

                    System.out.println("High ping time: " + currentTime + " milliseconds");

                    jsAlerts("alert('High ping time " + currentTime + " milliseconds');");

                }

            } else {

                System.out.println("Ping failed.");

            }



        } catch (Error failStatus) {

            System.out.println("Failed to ping website");

            childTest.log(Status.FAIL, "FAIL: Could not ping website");

        }

    }

    public static void urlManip() throws URISyntaxException {

        try {

            childTest = parentTest.createNode("Manipulate URL in order to expose site files");

            jsAlerts("alert('The test will now attempt to access site files that should not be exposed to end users');");

            justWait(1500);

            driver.navigate().to("https://www.linkedin.com/index.php");

            if(notFound().isDisplayed()) {

                justWait(1500);

                driver.navigate().to("https://www.linkedin.com/robot.txt");

                if(notFound().isDisplayed()) {

                    justWait(1500);

                    driver.navigate().to("https://www.linkedin.com/manifest.xml");

                    if(notFound().isDisplayed()) {

                        childTest.log(Status.PASS, "SUCCESS: URL successfully manipulated but nothing was found");

                    } else {

                        childTest.log(Status.PASS, "SUCCESS: URL successfully manipulated and something was actually found");

                    }

                }

            }


        } catch (Error failStatus) {

            System.out.println("Failed to manipulate URL");

            childTest.log(Status.FAIL, "FAIL: Could not manipulate URL");


        }

    }


}
