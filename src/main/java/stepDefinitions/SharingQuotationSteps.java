package stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class SharingQuotationSteps extends CommonSteps {

    public SharingQuotationSteps() {
        this.driver = CommonSteps.driver;
    }

    private final WebDriver driver;
    int pricesList;

    private final By quotationOption = By.xpath("(//span[@title='Send quotation in 2 clicks' and @class='message-svg snd_quot'])[1]");
    private final By selectAllCheckbox = By.xpath("//label[@for='select_all']");
    private final By nextBtn = By.xpath("//button[@id='form2']");
    private final By generateQuotation = By.xpath("//button[@id='form3']");
    private final By sendQuotation = By.xpath("//button[@id='form4']");
    private final By recentMsgSentList = By.xpath("//div[@class='message-send']//div[@class='user_txt']");
    private final By closeIcon = By.id("cls_button");
    private final By quotPopup = By.xpath("(//div[@id='conf_windw']//span)[1]");
    private final By lastMsgDateTime = By.xpath("(//div[@class='time'])[last()]");
    private final By quotationPopupHdr = By.xpath("//div[@id='sourcediv11']//div[@id='timeline']");
    private final By secondPage = By.xpath("//div[@class='bar_stp f1 active' and @id='step2']");
    private final By thirdPage = By.xpath("//div[@class='bar_stp f1 active' and @id='step2b']");
    private final By fourthPage = By.xpath("//div[@class='bar_stp f1 active' and @id='step3']");
    private final String productNameField = "(//div[@id='prodct_dspl']//table//span[text()='Product Name:']//following-sibling::input)";
    private final String priceField = "(//div[@id='prodct_dspl']//table//span[text()='Price: ']//following-sibling::input[1])";
    private final String unitField = "(//div[@id='prodct_dspl']//table//input[@placeholder='Unit'";

    @And("^Click on Quotation$")
    public void clickOnQuotation() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(quotationOption).click();
        Assert.assertEquals("Not navigated to Quotation option", "Create your customized quotation for requirement", driver.findElement(quotationPopupHdr).getText());
        log.print("Selected Quotation option\n\n");

    }

    @And("^Uncheck Select All Checkbox$")
    public void uncheckSelectAllCheckbox() {
        driver.findElement(selectAllCheckbox).click();
    }

    @Then("^Enter Details of \"([^\"]*)\" Product \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void enterDetailsOfProduct(int noOfQuotations, String productNames, String prices, String units) throws Throwable {
        List<String> productName = new ArrayList<>();
        List<String> price = new ArrayList<>();
        List<String> unit = new ArrayList<>();
        if (productNames.contains(";")) {
            productName = Arrays.asList(productNames.split(";"));
            price = Arrays.asList(prices.split(";"));
            unit = Arrays.asList(units.split(";"));
        } else {
            productName.add(productNames);
            price.add(prices);
            unit.add(units);
        }
        for (String p : price) {
            pricesList = pricesList + Integer.parseInt(p);
        }
        for (int i = 0; i < noOfQuotations; i++) {
            driver.findElement(By.xpath("(//div[@id='prodct_dspl']//span[@class='ms_checkbox']//label)[" + (i + 1) + "]")).click();

            driver.findElement(By.xpath(productNameField + "[" + (i + 1) + "]")).sendKeys(productName.get(i));
            driver.findElement(By.xpath(priceField + "[" + (i + 1) + "]")).sendKeys(price.get(i));
            driver.findElement(By.xpath(unitField + "])[" + (i + 1) + "]")).sendKeys(unit.get(i));

//            Assert.assertTrue(driver.findElement(By.xpath(productNameField + "[@title='" + productName.get(i) + "'])[" + (i + 1) + "]")).isDisplayed());
//            Assert.assertTrue(driver.findElement(By.xpath(priceField + "[@title='" + price.get(i) + "'][1])[" + (i = 1) + "]")).isDisplayed());
//            Assert.assertTrue(driver.findElement(By.xpath(unitField + " and @title='" + unit.get(i) + "'])[" + (i + 1) + "]")).isDisplayed());
        }
        log.print("Enter details of " + noOfQuotations + " quotations\n\n");
    }

    @Then("^Click Next$")
    public void clickNext() {
        waitTillElementVisibilityAndClick(driver.findElement(nextBtn));
        Assert.assertTrue("Not navigated to Second Page",driver.findElement(secondPage).isDisplayed());

    }

    @Then("^Click Next Again$")
    public void clickNextAgain() {
        waitTillElementVisibilityAndClick(driver.findElement(nextBtn));
        Assert.assertTrue("Not navigated to Third Page",driver.findElement(thirdPage).isDisplayed());

    }

    @Then("^Click Generate Quotation$")
    public void clickGenerateQuotation() {
        waitTillElementVisibilityAndClick(driver.findElement(generateQuotation));
        Assert.assertTrue("Not navigated to Fourth Page",driver.findElement(fourthPage).isDisplayed());
    }

    @Then("^Click Send Quotation$")
    public void clickSendQuotation() {
        waitTillElementVisibilityAndClick(driver.findElement(sendQuotation));
        Assert.assertTrue("Popup for Quotation sent is not displayed",driver.findElement(By.xpath("//*[text()='Your quotation has been sent to buyer successfully.']")).isDisplayed());

    }

    @And("^User should be able to send Quotation successfully$")
    public void userShouldBeAbleToSendQuotationSuccessfully() throws InterruptedException {
        List<WebElement> msgSentList = driver.findElements(recentMsgSentList);

        String actMsg = msgSentList.get(msgSentList.size() - 1).getText();
        Assert.assertEquals(pricesList, Integer.parseInt(actMsg.replace("Price : Rs ", "")));

        log.print("Quotation generated and sent successfully!!\n\n");
        Thread.sleep(2000);
    }

    @And("^Click on Close icon$")
    public void clickOnCloseIcon() throws InterruptedException {
        driver.findElement(closeIcon).click();
    }

    @Then("^User is able to see Popup asking to Leave or Stay$")
    public void userIsAbleToSeePopupAskingToLeaveOrStay() {
        WebElement quotationPopup = driver.findElement(quotPopup);
        WebElement stayBtn = driver.findElement(By.xpath("(//div[@class='btm_btn']//span[text()='Stay'])[1]"));
        WebElement leaveBtn = driver.findElement(By.xpath("(//div[@class='btm_btn']//span[text()='Leave'])[1]"));

        String popupText = quotationPopup.getText();
        Assert.assertEquals("Are you sure you want to Quit this Quotation ?", popupText);
        Assert.assertTrue("Stay Button is not displayed", stayBtn.isDisplayed());
        Assert.assertTrue("Leave button is not displayed", leaveBtn.isDisplayed());

    }

    @Then("^User click on Leave option$")
    public void userClickOnLeaveOption() {
        WebElement leaveBtn = driver.findElement(By.xpath("(//div[@class='btm_btn']//span[text()='Leave'])[1]"));
        leaveBtn.click();
        log.print("Clicked Leave option for Quotation popup\n\n");

    }

    @Then("^Popup should be closed and no Quotation should be sent$")
    public void popupShouldBeClosedAndNoQuotationShouldBeSent() {
        boolean isPopupDisplayed = driver.findElement(quotPopup).isDisplayed();
        Assert.assertFalse("Quotation Popup is still displayed!", isPopupDisplayed);
        String getLastMsgDateTime = driver.findElement(lastMsgDateTime).getText();
        Assert.assertNotEquals("Quotation is sent!", getCurrentDateTime(), getLastMsgDateTime);

        log.print("Popup is closed and No quotation is sent\n\n");

    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
