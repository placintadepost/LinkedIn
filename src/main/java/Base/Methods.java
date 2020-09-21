package Base;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Methods extends Setup {

    static Sheet sheet1 = wb.createSheet("Sheet1");

    public static void workbookExcel(int rowNumber, String itemName, String itemCode, String price, String colorName, String colorCode, String sizeOptions, String itemPhotoUrl, String itemQuatity) throws IOException {
        CreationHelper createHelper = wb.getCreationHelper();


        //creez + populez randuri
        Row item = sheet1.createRow(rowNumber);
        item.createCell(0).setCellValue(createHelper.createRichTextString(itemName));
        item.createCell(1).setCellValue(createHelper.createRichTextString(itemCode));
        item.createCell(2).setCellValue(createHelper.createRichTextString(price));
        item.createCell(3).setCellValue(createHelper.createRichTextString(colorName));
        item.createCell(4).setCellValue(createHelper.createRichTextString(colorCode));
        item.createCell(5).setCellValue(createHelper.createRichTextString(sizeOptions));
        item.createCell(6).setCellValue(createHelper.createRichTextString(itemPhotoUrl));
        item.createCell(7).setCellValue(createHelper.createRichTextString(itemQuatity));

        try {

        } catch (Exception e) {
            System.out.println("Failed to excel");
        }


    }

    public static WebElement getElement(String name) {

        try {

            try {
                basicWait();
                return driver.findElement(By.id(name));

            } catch (Exception ex) {

                basicWait();
                return driver.findElement(By.className(name));
            }

        } catch (Exception es) {

            try {
                basicWait();
                return driver.findElement(By.xpath(name));

            } catch (Exception lel) {

                basicWait();
                return driver.findElement(By.cssSelector(name));
            }


        }

    }

    public static WebElement elemGet(String locator, String elem) {

        WebElement temp = null;

        switch (locator) {

            case "name":
                basicWait();
                temp = driver.findElement(By.name(elem));
                break;
            case "xpath":
                basicWait();
                temp = driver.findElement(By.xpath(elem));
                break;
            case "class":
                basicWait();
                temp = driver.findElement(By.className(elem));
                break;
            case "id":
                basicWait();
                temp = driver.findElement(By.id(elem));
                break;
            case "tagName":
                basicWait();
                temp = driver.findElement(By.tagName(elem));
                break;
            case "css":
                basicWait();
                temp = driver.findElement(By.cssSelector(elem));
                break;
            default:
                System.out.println("Suck a dick Horia");
                break;
        }
        return temp;
    }

    public static void justWait(int x) {

        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForEmail(String titleEmailContains) {


        for (int i = 0; i <= 30; i++) {

            try {

                getElement(titleEmailContains).isDisplayed();

            } catch (Exception e) {

                justWait(5000);
                i++;

            }
        }
    }

    public static void captureScreenShot(String string) {


        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {


            FileUtils.copyFile(src, new File("C:/selenium/Screens/" + string + "-" + System.nanoTime() + ".png"));
        } catch (IOException e) {

            System.out.println(e.getMessage());

        }
    }

    public static void hoverElementAndClick(String name, String othername) {

        Actions action = new Actions(driver);
        action.moveToElement(Methods.getElement(name)).build().perform();

        Methods.justWait(1000);
        action.moveToElement(Methods.getElement(othername)).click().build().perform();

    }

    public static void hover(String name) {

        Actions action = new Actions(driver);
        WebElement element = getElement(name);
        action.moveToElement(element).build().perform();
        justWait(1000);

    }

    public static void scrollToElementAndClick(String name, String othername) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(name));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getElement(othername).click();

    }

    public static void scrollToElementAndSendKeys(String name, String string) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(name));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getElement(name).sendKeys(string);
    }

    public static void scrollToJS(String name) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(name));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void hoverToJS(String name) {

        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";


        ((JavascriptExecutor) driver).executeScript(javaScript, getElement(name));

    }

    public static void scrollDownAndUp(String x, String y) {

        ((JavascriptExecutor) driver).executeScript("scroll(" + x + "," + y + ");");

    }

    public static void switchToIFrame(String name) {

        driver.switchTo().frame(Methods.getElement(name));

    }

    public static void waitForDisplay(WebElement name) {

        for (int i = 0; i <= 10; ) {

            try {

                (new WebDriverWait(driver, 5))
                        .until(ExpectedConditions.visibilityOf(name));

            } catch (Exception e) {

                i++;
            }


        }


    }

    public static void waitForInvisibility(String name) {

        WebElement bla = Methods.getElement(name);

        (new WebDriverWait(driver, 300000))
                .until(ExpectedConditions.invisibilityOf(bla));

    }

    public static void openNewTab() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open();");

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    public static void waitForClickable(String name) {

        for (int i = 0; i <= 10; ) {

            try {

                (new WebDriverWait(driver, 5))
                        .until(ExpectedConditions.elementToBeClickable(Methods.getElement(name)));

            } catch (Exception e) {

                i++;
            }

        }
    }

    public static void okHttpCall(String projectName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "action=create&item=projectName&theme=colibri%7Cdev");
        Request request = new Request.Builder()
                .url("http://10.44.205.4/wp-sites/auto")
                .post(body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Origin", "http://10.44.205.4")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .addHeader("Referer", "http://10.44.205.4/wp-sites/olga")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "en-US,en;q=0.9")
                .addHeader("Cookie", "screenResolution=1920x1080; JSESSIONID.e8f56535=node01kqn6nycmb4op1bto0er2p7sbx248.node0; filemanager=m81qqpdbp8nqmfrjrubc6oufl1")
                .addHeader("cache-control", "no-cache")
                .build();

        client.newCall(request).execute();
    }

    public static void click(WebElement name) {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(name));
        element.click();

    }

    public static void basicWait() {

        try {
            sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void scrollSmooth() {

        for (int i = 0; i < 6000; i++) {

            basicWait();

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1)", "");

        }
    }

    public static void openWindowRobotIncog() throws AWTException {

        Robot bot = new Robot();

        bot.delay(3000);
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_SHIFT);
        bot.keyPress(KeyEvent.VK_N);
        bot.delay(2000);
        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.keyRelease(KeyEvent.VK_SHIFT);
        bot.keyRelease(KeyEvent.VK_N);
    }

    public static void handle(int nr) {

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(nr));

    }

    //deals with clicking on options whose children are the same name/class everywhere (hence the parent)
    public static void getElementsIndexClick(String parent, String child, int index) {

        getElement(parent).findElements(new ByAll(By.id(child),
                By.className(child),
                By.xpath(child),
                By.cssSelector(child),
                By.tagName(child))).get(index).click();

    }

    public static void getElementsIndexParentChild(String parent, String child) {

        Random rand = new Random();

        List<WebElement> elementList = getElement(parent).findElements(new ByAll(By.id(child),
                By.className(child),
                By.xpath(child),
                By.cssSelector(child)));

        int index = rand.nextInt(elementList.size() - 1);
        basicWait();
        elementList.get(index).click();

    }

    public static void getImage(String name) throws IOException {

        String src = getElement(name).getAttribute("src");
        BufferedImage bufferedImage = ImageIO.read(new URL(src));
        File outputfile = new File("src/Tests.test/captchas/" + "PUTARAND" + ".png");
        ImageIO.write(bufferedImage, "png", outputfile);
    }

    public static void waitForPageLoad() {

        new WebDriverWait(driver, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

    }

    public static void Log(String comment, PrintWriter csvw) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        csvw.append(comment).append(", ").append(dtf.format(now));
        csvw.append("\n");

    }

    public static File getLastModified(String directoryFilePath) {

        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }

    public static String[][] readCSVData() throws Exception {

        String[][] testData;

        //Get the workbook
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_YYYY_HH_mm");
        LocalDateTime now = LocalDateTime.now();

        String path = System.getProperty("user.dir");
        File file = new File(String.valueOf(getLastModified("\\src\\loggers\\")));

        Reader fileInputStream = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(fileInputStream);


        int numberOfRecords = 0;
        int numberOfColumns = 0;


        for (CSVRecord record : records
        ) {
            System.out.println("Reading record line #" + ++numberOfRecords);
            numberOfColumns = record.size();
        }

        testData = new String[numberOfRecords - 1][numberOfColumns];
        System.out.println("numberOfRecords = " + numberOfRecords);
        System.out.println("numberOfColumns = " + numberOfColumns);


        int currentRecord = 0;


        fileInputStream = new FileReader(file);
        records = CSVFormat.EXCEL.parse(fileInputStream);

        for (CSVRecord record : records
        ) {

            System.out.println("Reading test data ");
            if (record.getRecordNumber() == 1) {
                System.out.println("record = " + record);
                continue;
            }

            for (int i = 0; i < record.size(); i++) {
                testData[currentRecord][i] = record.get(i);

            }
            currentRecord++;
        }


        return testData;
    }

    public static void closeLogger(PrintWriter closeLogger) {

        closeLogger.close();
    }

    public static void addToExcel() throws IOException {

        OutputStream fileOut = new FileOutputStream(searchTerm);
        wb.write(fileOut);
        fileOut.close();
    }

    public static void assertEquals(String expected, String actual) {

        try {

            Assert.assertEquals(expected, actual);

        } catch (Error e) {

            System.out.println("Expected element didn't match with actual element - Actual is " + actual);
        }
    }

    public static void assertTrue(boolean thisIsTrue) {

        try {

            Assert.assertTrue(thisIsTrue);

        } catch (Error e) {

            System.out.println("Expected element didn't match with actual element.");
        }
    }

    //gets a list of elements and its index
    public static WebElement getAllElementsAndIndex(String name, int nr) {

        List<WebElement> elementList = driver.findElements(new ByAll(By.id(name),
                By.className(name),
                By.xpath(name),
                By.cssSelector(name)));

        int index = ThreadLocalRandom.current().nextInt(0, elementList.size() - nr);
        return elementList.get(index);

    }

    public static List<WebElement> getListElements(String name) {

        List<WebElement> elementList = driver.findElements(new ByAll(By.id(name),
                By.className(name),
                By.xpath(name),
                By.cssSelector(name)));

        return elementList;
    }

    public static void scrollToLoadProduct(WebDriver driver) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            long lastHeight = ((Number) js.executeScript("return document.body.scrollHeight")).longValue();
            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
                Thread.sleep(1000);

                long newHeight = ((Number) js.executeScript("return document.body.scrollHeight")).longValue();
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        basicWait();
    }

    public static void getAndAssertText(String element, String textExpected) {
        String getText = getElement(element).getText();
        assertTrue(getText.contains(textExpected));
    }

    public static void linkValid(String link) {

          String url = link;
          HttpURLConnection huc = null;
          int respCode = 200;

        List<WebElement> links = driver.findElements(By.tagName("a"));

        Iterator<WebElement> it = links.iterator();


        while (it.hasNext()) {

            url = it.next().getAttribute("href");

            if (url == null || url.isEmpty()) {
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }

            if (!url.startsWith(homePage)) {
                System.out.println("URL belongs to another domain, skipping it.");
                continue;
            }

            try {

                huc = (HttpURLConnection) (new URL(url).openConnection());

                huc.setRequestMethod("HEAD");

                huc.connect();

                respCode = huc.getResponseCode();

                if (respCode >= 400) {

                    System.out.println(url + " is a broken link");
                    Log(url + " is a broken link", csv);

                } else {

                    System.out.println(url + " is a valid link");
                    Log(url + " is a valid link", csv);
                }

            } catch (IOException e) {

                e.printStackTrace();

            }


        }
    }

    public static void jsExecutor(String script) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }

    public static void jsAlerts(String script) {

        jsExecutor(script);
        justWait(1500);
        driver.switchTo().alert().accept();

    }



}