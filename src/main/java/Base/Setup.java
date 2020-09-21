package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.Hashtable;


public class Setup {

    protected static WebDriver driver;
    static String path = "src\\loggers\\List.xlsx";
    public static PrintWriter csv;
    public static Workbook wb = new XSSFWorkbook();
    public static File searchTerm = new File(path);
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest parentTest;
    public static ExtentTest childTest;
    public static String homePage = "https://www.linkedin.com/";
    public static ChromeOptions options = new ChromeOptions();


    public static String getScreenshot() {

        TakesScreenshot ts=(TakesScreenshot) driver;

        File src=ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir") + "/Screenshots/" + System.currentTimeMillis()+".png";

        File destination=new File(path);

        try {

            FileUtils.copyFile(src, destination);

        } catch (IOException e) {

            System.out.println("Capture Failed "+e.getMessage());
        }

        return path;
    }
    public static void extentReports() {

        htmlReporter = new ExtentHtmlReporter("src/loggers/ExtentReport/Report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.config().setTheme(Theme.DARK);

    }
    public static WebDriver startBrowser(String browserName) {

        System.out.println("Browser name is: " + browserName);

        if (browserName.equalsIgnoreCase("chrome")) {

            System.out.println(System.getProperty("user.dir"));
            System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
//            options.addArguments("src\\main\\resources\\ChromeProfile");
//            options.addArguments("--disable-notifications");
            //options.addExtensions(new File("src/main/resources/Extensions/extension_3_3_8_0.crx"));
            driver = new ChromeDriver(options);

            installAdBlockWait();

        } else if (browserName.equalsIgnoreCase("firefox")) {

            System.out.println(System.getProperty("user.dir"));
            System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
            FirefoxProfile fxProfile = new FirefoxProfile();
            fxProfile.setPreference("permissions.default.desktop-notification", 1);
            FirefoxOptions options = new FirefoxOptions().setProfile(fxProfile);
            driver = new FirefoxDriver(options);

        } else if (browserName.equalsIgnoreCase("opera")) {

            System.out.println(System.getProperty("user.dir"));
            System.setProperty("webdriver.opera.driver", "src\\main\\resources\\operadriver.exe");
            OperaOptions options = new OperaOptions();
            options.setBinary("src/main/resources/Opera/66.0.3515.44/opera.exe");
            driver = new OperaDriver(options);

        } else if (browserName.equalsIgnoreCase("IE")) {

            System.out.println(System.getProperty("user.dir"));
            System.setProperty("webdriver.ie.driver", "src/main/resources/IEDriverServer.exe");
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.ignoreZoomSettings();
            options.setCapability("nativeEvents", false);
            driver = new InternetExplorerDriver(options);

        }

        driver.manage().window().maximize();
        return driver;
    }
    public static void csvInitialize() throws FileNotFoundException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");
        LocalDateTime now = LocalDateTime.now();
        csv = new PrintWriter("src\\loggers\\log" + dtf.format(now) + ".csv");
    }
    public static void installAdBlockWait() {

            Base.Methods.justWait(8000);
            String title = driver.getTitle();
            if (title.contains("Thank you for installing AdGuard!")) {
                driver.close();
                Base.Methods.handle(0);

        }

    }
    
    public static void desktopWebSetup(String browserName) throws IOException {

        FileUtils.cleanDirectory(new File("src\\loggers"));
        extentReports();
        csvInitialize();
        startBrowser(browserName);


    }


    @BeforeSuite
    @Parameters({"browserName"})
    public static void setup(String browserName) throws IOException {

            desktopWebSetup(browserName);
    }

    @AfterSuite
    public void methodTearDown() {

        Methods.closeLogger(csv);
        extent.flush();
        driver.quit();

    }


}



