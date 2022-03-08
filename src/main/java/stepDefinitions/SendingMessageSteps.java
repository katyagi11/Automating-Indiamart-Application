package stepDefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Th;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class SendingMessageSteps extends CommonSteps {

    public SendingMessageSteps() {
        this.driver = CommonSteps.initialization();
    }

    private final WebDriver driver;
    String expMsg = "Hi, Welcome to Indiamart!";

    private final By sellLink = By.id("ch_sell");
    private final By mobTextField = By.id("mobNo");
    private final By startSellingBtn = By.xpath("//button[text()='Start Selling ']");
    private final By enterOTPPopup = By.xpath("(//div[@id='divH']//p)[1]");
    private final By signInBtn = By.id("sbmtbtnOtp");
    private final By userName = By.xpath("//div[@id='lshead']//a");
    private final By chatBoxCloseIcon = By.xpath("//span[@class='glob_sa_close']");
    private final By leadManagerTab = By.xpath("//li[@id='message_tab']//a");
    private final By orderTab = By.id("order_tab");
    private final By menu = By.xpath("//span[@class='msg_thdt_hover']");
    private final By addLeadOption = By.xpath("//*[text()='Add Lead'][1]");
    private final By contactNameField = By.id("addContactName");
    private final By contactMobField = By.id("addContactMobile");
    private final By addContactBtn = By.id("addcon");
    private final By messageField = By.id("massage-text");
    private final By sendBtn = By.id("send-reply-span");
    private final By recentMsgSent = By.xpath("//div[@class='message-send']//div[@class='user_txt']");
    private final By lastMsgDateTime = By.xpath("(//div[@class='time'])[last()]");
    private final By closePopup = By.id("gst_covering_div");
    private final By laterBtn = By.xpath("//span[text()='Later']");
//    private final By searchTxtBox = By.xpath("//input[@id='searchauto']");
    private final By searchResult = By.xpath("//ul[@id='fetch_list']//li//p[contains(@class,'name')]");
    private final By noResults = By.xpath("//ul[@id='fetch_list']//li");
    private final By leadManagerPageHeadr = By.xpath("//div[@id='message_center_leftsection']//h1[text()='All Leads']");

    @Given("^User enters URL$")
    public void userEntersURL() {
        Assert.assertEquals("Title is incorrect", "IndiaMART - Indian Manu" +
                "facturers Suppliers Exporters Directory, India Exporter Manufacturer", driver.getTitle());
        log.print("User navigated to site successfully!\n\n");
    }

    @When("^User navigates to Sellers tool$")
    public void userNavigatesToSellersTool() {
        driver.findElement(sellLink).click();
        Assert.assertEquals("Seller Page Title is incorrect!!", "IndiaMART Seller Registration | Sell products online on IndiaMART | Seller Central Dashboard Login",
                driver.getTitle());

    }

    @When("^User enters \"([^\"]*)\" and click on Start Selling button$")
    public void userEntersAndClickOnStartSellingButton(String arg0) throws Throwable {
        Thread.sleep(5000);
        driver.findElement(mobTextField).sendKeys(arg0);
        Thread.sleep(5000);
        driver.findElement(startSellingBtn).click();
        Thread.sleep(5000);
        Assert.assertTrue("OTP Popup is not displayed", driver.findElement(enterOTPPopup).isDisplayed());

    }

    @And("^Wait till OTP is entered manually And click on Login Button$")
    public void waitTillOTPIsEnteredManually() throws InterruptedException {
        Thread.sleep(5000);
        WebElement btn = driver.findElement(signInBtn);
        String profileName = driver.findElement(userName).getText();

        long start = System.currentTimeMillis();
        long end = start + 50 * 1000;
        while (profileName.contains("Sign In")) {
            btn.click();
            Thread.sleep(15000);
            if (driver.getTitle().equals("Welcome to Seller IndiaMART")) {
                Assert.assertTrue(true);
                break;
            } else if (System.currentTimeMillis() > end) {
                Assert.assertFalse("Time Exceeded to enter the OTP", false);
            }

        }
        closePopupsIfPresent();
        driver.findElement(chatBoxCloseIcon).click();
    }


    @Then("^User navigates to Lead Manager section$")
    public void userNavigatesToLeadManagerSection() throws InterruptedException {
        driver.findElement(leadManagerTab).click();
        Thread.sleep(3000);
        driver.findElement(orderTab).click();
        Thread.sleep(3000);
        driver.findElement(leadManagerTab).click();
        Assert.assertTrue("Not navigated to Lead Manager section", driver.findElement(leadManagerPageHeadr).isDisplayed());
        log.print("User navigated to Lead Manager section!\n\n");
    }

//    @Then("^User Create New Lead if Lead is not available with \"([^\"]*)\"$")
//    public void userCreateNewLeadIfIsNotAvailable(String lead, String mobileNo) throws Throwable {
//        driver.findElement(searchTxtBox).sendKeys(lead);
//        Thread.sleep(2000);
//        if(driver.findElement(noResults).getText().equals("No results found")){
//            Thread.sleep(5000);
//            driver.findElement(menu).click();
//            Thread.sleep(5000);
//            driver.findElement(addLeadOption).click();
//
//            driver.findElement(contactNameField).sendKeys(lead);
//            driver.findElement(contactMobField).sendKeys(mobileNo);
//            driver.findElement(addContactBtn).click();
//            Thread.sleep(5000);
//            WebElement l = driver.findElements(By.xpath("//p[text()='" + lead + "']//parent::div")).get(0);
//            Assert.assertTrue(l.isDisplayed());
//            l.click();
//        }
//        log.print("Selected Lead: " + lead+"\n\n");
//
//    }

    @Then("^User search Existing Lead \"([^\"]*)\"$")
    public void userSearchExistingLead(String leadName) throws Throwable {
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='searchauto']"));
        waitTillElementVisibilityAndClick(searchBox);
        searchBox.sendKeys(leadName);
        Assert.assertEquals("Search result is not as expected", leadName, driver.findElement(searchResult).getText());
        log.print("Selected Lead: " + leadName + "\n\n");
    }

    @Then("^User Enter message to Lead$")
    public void userEntersMessageToLead() {
        driver.findElement(messageField).sendKeys(expMsg);
        Assert.assertEquals("Message is not entered", expMsg, driver.findElement(messageField).getText());

    }

    @And("^Clicks on Send button$")
    public void clicksOnSendButton() throws InterruptedException {
        driver.findElement(sendBtn).click();
        Thread.sleep(2000);
    }

    @Then("^Message should be sent and visible in displayed space$")
    public void messageShouldBeSentAndVisibleInDisplayedSpace() {
        List<WebElement> msgSentList = driver.findElements(recentMsgSent);
        String actMsg = msgSentList.get(msgSentList.size() - 1).getText();
        Assert.assertEquals(expMsg, actMsg);

        String getLastMsgDateTime = driver.findElement(lastMsgDateTime).getText();
        Assert.assertEquals("Message is not sent!", getCurrentDateTime(), getLastMsgDateTime);
        log.print("Sent Message to Lead successfully\n\n");

    }

    public void closePopupsIfPresent() {
        boolean popupDisplayed = driver.findElements(closePopup).size() > 0;
        if (popupDisplayed == Boolean.TRUE)
            driver.findElement(laterBtn).click();
    }


    @After
    public void tearDown() {
        driver.quit();
    }


}
