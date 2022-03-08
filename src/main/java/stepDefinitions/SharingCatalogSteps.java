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

public class SharingCatalogSteps extends CommonSteps {

    public SharingCatalogSteps() {
        this.driver = CommonSteps.driver;
    }
    private final WebDriver driver;
    List<String> productNames = new ArrayList<>();
    List<String> priceList = new ArrayList<>();
    int catalogs = 0;

    private final By catalogOption = By.xpath("//span[@id='send-catalog-li']");
    private final By sendBtn = By.id("send-frdctl-span");
    private final By recentMsgSentList = By.xpath("//div[@class='message-send']//div[@class='user_txt']");
    private final By catalogPopupHdr = By.xpath("(//div[@id='dd_details']//div//div[2])[1]");


    @And("^Click on Catalog option$")
    public void clickOnCatalogOption() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(catalogOption).click();
        Assert.assertEquals("Not navigated to Catalog option","Send Catalog Images (Max 6)",driver.findElement(catalogPopupHdr).getText());
        log.print("Clicked on Catalog option!\n\n");

    }

    @Then("^Select \"([^\"]*)\" Catalog and Enter \"([^\"]*)\"$")
    public void selectCatalogAndEnter(int noOfCatalogs, String catalogPriceList) throws Throwable {
        List<String> catalogPrice = new ArrayList<>();

        if (catalogPriceList.contains(";")) {
            catalogPrice = Arrays.asList(catalogPriceList.split(";"));
        } else {
            catalogPrice.add(catalogPriceList);
        }

        priceList.addAll(catalogPrice);

        for (int i = 0; i < noOfCatalogs; i++) {
            driver.findElement(By.xpath("((//ul[@id='select_ctglprod_list']//li)[" + (i + 1) + "]//label)[1]")).click();

            driver.findElement(By.xpath("(//ul[@id='select_ctglprod_list']//li)[" + (i + 1) + "]//input[contains(" +
                    "@id,'_price')]")).sendKeys(catalogPrice.get(i));
            String productName = driver.findElement(By.xpath("(//ul[@id='select_ctglprod_list']//li)[" + (i + 1) + "]//div[@class='c-div clr']//p[1]")).getText();
            productNames.add(productName);
            Thread.sleep(2000);
        }
        catalogs = noOfCatalogs;

        log.print("Selected "+noOfCatalogs+" catalogs\n\n");

    }

    @And("^Click on Send button$")
    public void clickOnSendButton() {
        driver.findElement(sendBtn).click();
    }

    @Then("^Verify Catalog is sent$")
    public void verifyCatalogIsSent()  throws InterruptedException{
        List<WebElement> msgSentList = driver.findElements(recentMsgSentList);
        for (int i = 0; i < catalogs; i++) {
            String actMsg = msgSentList.get(i).getText();
            Assert.assertTrue(actMsg.contains(productNames.get(i)));
            Assert.assertTrue(actMsg.contains(String.valueOf(priceList.get(i))));

            String getLastMsgDateTime = driver.findElement(By.xpath("(//div[@class='time'])[last()-" + (catalogs-(i+1) + "]"))).getText();
            Assert.assertEquals("Quotation is not sent!", getCurrentDateTime(), getLastMsgDateTime);

            log.print("Catalogs is sent successfully to lead!\n\n");
            Thread.sleep(2000);
        }

    }
    @After
    public void tearDown()  {
        driver.quit();
    }

}
