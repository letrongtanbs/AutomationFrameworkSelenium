package anhtester.com.projects.website.crm.testcases;

import anhtester.com.common.BaseTest;
import anhtester.com.constants.FrameworkConstants;
import anhtester.com.driver.DriverManager;
import anhtester.com.helpers.ExcelHelpers;
import anhtester.com.helpers.Helpers;
import anhtester.com.projects.website.crm.pages.Dashboard.DashboardPage;
import anhtester.com.projects.website.crm.pages.Projects.ProjectPage;
import anhtester.com.projects.website.crm.pages.SignIn.SignInPage;
import anhtester.com.helpers.DatabaseHelpers;
import anhtester.com.utils.WebUI;
import anhtester.com.utils.Log;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v97.network.Network;
import org.openqa.selenium.devtools.v97.network.model.Headers;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Listeners(TestListener.class)
public class TestHandle {

    WebDriver driver;
    DatabaseHelpers databaseHelpers;
    SignInPage signInPage;
    DashboardPage dashboardPage;
    ProjectPage projectPage;

    @BeforeMethod
    public void Setup() {
        driver = new BaseTest().createBrowser("chrome"); //Cách khởi tạo thứ 1
//        new BaseTest().createDriver("chrome"); //Cách khởi tạo thứ 2
//        driver = DriverManager.getDriver();
    }

    @Test
    public void handleZoomInZoomOut() {
        driver.get("https://anhtester.com");
        WebUI.sleep(1);
        //driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,Keys.ADD));
        //driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,Keys.SUBTRACT));
        WebUI.getJsExecutor().executeScript("document.body.style.zoom = '80%';");
        WebUI.sleep(1);
    }

    @Test
    public void handleNotificationsBrowser() {
        WebDriver driver = new ChromeDriver(WebUI.notificationsBlock());
        driver.manage().window().maximize();
        driver.get("https://oto.com.vn/mua-ban-xe");
        WebUI.sleep(4);
        driver.close();
    }

    @Test
    public void handleDragAndDrop() {
        driver.get("http://demo.guru99.com/test/drag_drop.html");
        By fromElement1 = By.xpath("//a[normalize-space()='BANK']");
        By toElement1 = By.xpath("(//div[@id='shoppingCart1']//div)[1]");

        By fromElement2 = By.xpath("(//li[@id='fourth'])[2]");
        By toElement2 = By.xpath("(//div[@id='shoppingCart4']//div)[1]");

        //WebUI.switchToFrameByElement(toElement);
        //WebUI.scrollToElement(toElement);
        WebUI.dragAndDrop(fromElement1, toElement1);
        WebUI.sleep(1);
        WebUI.dragAndDropElement(fromElement2, toElement2);
        WebUI.sleep(2);
    }

    @Test
    public void handleDragAndDropOffset() {
        driver.get("http://demo.guru99.com/test/drag_drop.html");
        By fromElement1 = By.xpath("//a[normalize-space()='BANK']");
        By toElement1 = By.xpath("(//div[@id='shoppingCart1']//div)[1]");

        int X1 = WebUI.findWebElement(fromElement1).getLocation().getX();
        int Y1 = WebUI.findWebElement(fromElement1).getLocation().getY();
        WebUI.logConsole(X1 + " , " + Y1);

        int X2 = WebUI.findWebElement(toElement1).getLocation().getX();
        int Y2 = WebUI.findWebElement(toElement1).getLocation().getY();
        WebUI.logConsole(X2 + " , " + Y2);

        //WebUI.switchToFrameByElement(toElement);
        //WebUI.scrollToElement(toElement);
        WebUI.dragAndDropOffset(fromElement1, -402, 246); //Nhớ là Tính từ vị trí click chuột đầu tiên
        WebUI.sleep(2);
    }

    @Test
    public void handleHighLightElement() {
        driver.get("https://hrm.anhtester.com/");
        By button = By.xpath("//button[@type='submit']");
        WebUI.highLightElement(button); //Tô màu viền đỏ cho Element trên website
        WebUI.verifyElementAttributeValue(button, "type", "submit", 10);
        WebUI.waitForElementClickable(button, 5);
        WebUI.sleep(2);
    }

    @Test
    public void handleUploadFile() {
        driver.get("https://demoqa.com/upload-download");
        WebUI.waitForPageLoaded();
        WebUI.sleep(1);

        //Cách 1 sendKeys link từ source
        WebUI.uploadFileSendkeys(By.xpath("//input[@id='uploadFile']"), Helpers.getCurrentDir() + "src/test/resources/DOCX_File_01.docx");

        //Cách 2 mở form local máy nên file là trong ổ đĩa máy tính
        WebUI.uploadFileForm(By.xpath("//input[@id='uploadFile']"), "D:\\Document.csv");

        WebUI.sleep(3);
    }

    @Test
    public void handleTable1() {
        Log.info("handleTable1");
        driver.get("https://colorlib.com/polygon/notika/data-table.html");
        WebUI.waitForPageLoaded();
        System.out.println(WebUI.getValueTableByColumn(2));
    }

    @Test
    public void handleTable2() {
        signInPage = new SignInPage();
        driver.get("https://crm.anhtester.com/signin");
        dashboardPage = signInPage.signIn("tld01@mailinator.com", "123456");
        projectPage = dashboardPage.openProjectPage();
        String dataSearch1 = "Project";
        String dataSearch2 = "Test";
        // Search cột 2 Title
        projectPage.searchByValue(dataSearch1);
        projectPage.checkContainsSearchTableByColumn(2, dataSearch1);
        // Search cột 3 Client
        projectPage.searchByValue(dataSearch2);
        projectPage.checkContainsSearchTableByColumn(3, dataSearch2);
    }

    @Test
    public void handlePrintPopup() throws AWTException {
        driver.get("https://pos.anhtester.com/login");
        WebUI.waitForPageLoaded();
        driver.findElement(By.xpath("//td[normalize-space()='user01@anhtester.com']")).click();
        driver.findElement(By.xpath("//button[normalize-space()='SignIn']")).click();
        driver.findElement(By.xpath("//a[@role='button']")).click();
        //driver.findElement(By.xpath("//span[normalize-space()='Sale']")).click();
        WebUI.waitForPageLoaded();
        driver.findElement(By.xpath("//a[normalize-space()='Manage Sale']")).click();
        driver.findElement(By.xpath("//span[normalize-space()='Print']")).click();

        WebUI.sleep(1);

        Set<String> windowHandles = driver.getWindowHandles();
        if (!windowHandles.isEmpty()) {
            driver.switchTo().window((String) windowHandles.toArray()[windowHandles.size() - 1]);
        }

        //driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
        Robot robotClass = new Robot();
        robotClass.keyPress(KeyEvent.VK_TAB);
        WebUI.sleep(1);
        robotClass.keyPress(KeyEvent.VK_ENTER);

        driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
//        if (!windowHandles.isEmpty()) {
//            driver.switchTo().window((String) windowHandles.toArray()[windowHandles.size() - 1]);
//        }
        WebUI.sleep(2);
    }

    @Test
    public void handleAuthentication() {

        // Authentication username & password
        String username = "admin";
        String password = "admin";

        // Get the devtools from the running driver and create a session
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        // Enable the Network domain of devtools
        devTools.send(Network.enable(java.util.Optional.of(100000), java.util.Optional.of(100000), java.util.Optional.of(100000)));
        String auth = username + ":" + password;

        // Encoding the username and password using Base64 (java.util)
        String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());

        // Pass the network header -> Authorization : Basic <encoded String>
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + encodeToString);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        // Load the application url
        driver.get("https://the-internet.herokuapp.com/basic_auth");
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
        String successFullyLoggedInText = driver.findElement(By.xpath("//p")).getText();
        Assert.assertEquals(successFullyLoggedInText, "Congratulations! You must have the proper credentials.");
    }

    @AfterMethod
    public void closeDriver() {
        DriverManager.quit();
    }

}
