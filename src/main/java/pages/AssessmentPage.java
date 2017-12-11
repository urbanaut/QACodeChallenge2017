package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Helpers;

import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AssessmentPage {

    private WebDriver driver;
    private boolean mobileTest;
    private Helpers h;

    public AssessmentPage(WebDriver driver, boolean mobileTest) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.mobileTest = mobileTest;
        h = new Helpers(driver, mobileTest);
    }

    // Navigation Buttons
    @FindBy(xpath = "//a[contains(@href,'you-start')]")
    public WebElement takeAssessmentLnk;
    @FindBy(xpath = "//a[contains(@href,'purchase-options')]")
    public WebElement takeAssessmentLinkUS;
    @FindBy(xpath = "//button[@translate='continue-btn-text']")
    public WebElement continueBtn;
    @FindBy(xpath = "//div[@id='nuskinBespokeApp']//button[@translate='back-btn-text']")
    public WebElement previousBtn;
    @FindBy(xpath = "//div[@id='nuskinBespokeApp']//button[@translate='next-btn-text']")
    public WebElement nextBtn;
    @FindBy(xpath = "//div[@class='pagination']/span")
    public WebElement progressIndicator;

    // Agreement
    @FindBy(xpath = "//div[@id='page-wrap-you']//label")
    public WebElement agreementBtn;
    @FindBy(xpath = "//div[@id='page-wrap-you']/div[3]//button")
    public WebElement agreementContinueBtn;
    @FindBy(xpath = "//div[@id='header']//a[@id='agreeToCookies']")
    public WebElement cookieBtn;

    // Personal Info
    @FindBy(xpath = "//input[@id='name-text']")
    public WebElement nameTbx;
    @FindBy(xpath = "//input[@id='age-text']")
    public WebElement ageTbx;
    @FindBy(xpath = "//div[@id='nuskinBespokeApp']//button[@class='bespokeButton ng-scope']")
    public List<WebElement> sexes;

    // Ethnicity
    @FindBy(xpath = "//div[@id='ethnicity-window']/ul/li")
    public List<WebElement> ethnicities;

    // Location
    @FindBy(xpath = "//input[@id='locationText']")
    public WebElement locationTxb;
    @FindBy(xpath = "//div[@id='map']")
    public WebElement map;
    @FindBy(xpath = "//div[@class='input-wrap top-spacer']//i[@class='clear-input']")
    public WebElement clearLocationBtn;

    // Dial Pages
    @FindBy(xpath = "//canvas")
    public WebElement sliderCtrl;

    // Skin Type
    @FindBy(xpath = "//div[@id='skin-type-window']/button[@ng-click='op2()' or @ng-click='op3()' or @ng-click='op4()' or @ng-click='op5()']")
    public List<WebElement> skinTypes;

    // Alpha Hydroxy Acids
    @FindBy(xpath = "//div[@id='skin-aha-window']/button")
    public List<WebElement> ahas;

    // Firmness
    @FindBy(xpath = "//div[@id='skin-firmness-window']/button")
    public List<WebElement> firmnessTypes;

    // Radiance
    @FindBy(xpath = "//div[@id='skin-radiance-window']/button")
    public List<WebElement> radianceTypes;

    // Texture
    @FindBy(xpath = "//div[@id='skin-texture-window']/button")
    public List<WebElement> textures;

    // Day Moisturizer
    @FindBy(xpath = "//div[@id='preferences-fragrance-window']/button")
    public List<WebElement> fragranceChoices;

    // Modal Alert Options
    @FindBy(xpath = "//div[@class='alert-options']/button")
    public List<WebElement> modalOptions;

    // Review
    @FindBy(xpath = "//div[@class='page-content']")
    public WebElement assessmentSummaryTxt;
    @FindBy(xpath = "//button[@translate='review-find-my-regimen']")
    public WebElement findCustomizedRegimenBtn;
    @FindBy(xpath = "//div[@class='final-bottom']/p[@ng-class='extraCodeClass']")
    public WebElement assessmentCodeTxt;


    public void navigateToCountryUrl(String url) {
        driver.navigate().to(url);
        if (driver.getCurrentUrl().contains("en_US"))
            takeAssessmentLinkUS.click();
        else
            takeAssessmentLnk.click();
    }

    public void acceptAgreement() throws Exception {
        h.waitForElementToBeReady(agreementContinueBtn);
        if (mobileTest) {
            try {
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                if (cookieBtn.isDisplayed())
                    cookieBtn.click();
            } catch (Exception e) {
                System.out.println("No Cookie Use button found, continuing.");
            }
        }
        agreementBtn.click();
        h.scrollToAndClickElement(agreementContinueBtn, 50);
    }

    public void continueAssessment() throws Exception {
        h.waitForElementToBeReady(continueBtn);
        h.scrollToAndClickElement(continueBtn, 50);
    }

    public void enterPersonalInfo(String name, String age, String sex) throws Exception {
        nameTbx.sendKeys(name);
        ageTbx.sendKeys(age);
        selectOption(sexes, sex, 50);
    }

    public void selectEthnicity(String ethnicity) throws Exception {
        h.waitForElementToBeReady(ethnicities.get(0));
        h.getElementByInnerHtml(ethnicities, ethnicity).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void enterLocation(String location) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(map);
        clearLocationBtn.click();
        locationTxb.sendKeys(location);
        Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN, Keys.ENTER).perform();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectOption(List<WebElement> elements, String option, int offset) throws Exception {
        h.waitForElementToBeReady(elements.get(0));
        option = h.compareStringToAttribute(elements, option);
        h.getElementByAttribute(elements, option).click();
        h.scrollToAndClickElement(nextBtn, offset);
    }

    public void getCustomizedRegimen(String output) throws Exception {
        FileOutputStream fos = new FileOutputStream(output);
        h.waitForElementToBeReady(findCustomizedRegimenBtn);
        fos.write((assessmentSummaryTxt.getText()+"\n").getBytes());
        h.scrollToAndClickElement(findCustomizedRegimenBtn, 50);
        do {
            Thread.sleep(500);
        } while (assessmentCodeTxt.getText().equals(""));
        fos.write(("Assessment Code: " + assessmentCodeTxt.getText()).getBytes());
        fos.close();
    }

    public void slideDial(String percent) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(sliderCtrl);

        if (percent.equals(""))
            percent = "25";
        int pc = Integer.valueOf(percent);
        double percentage = pc / 100.0;
        int width = sliderCtrl.getSize().getWidth();
        int height = sliderCtrl.getSize().getHeight();
        double yOffset = 0.80 * (height);   // Set yOffset at 80% of the height of the canvas image
        double xOffset;
        double distance;

        // Determine Slider Type
        if (progressIndicator.getText().equals("18/20")) {
            xOffset = 0.50 * width; // Set xOffset at 50% of the width of the canvas image
            distance = percentage * ((width / 2) - (width * 0.2));
        } else if (progressIndicator.getText().equals("20/20")) {
            // Knob starts at 60% of canvas, distance must be between that
            // and the end of the dial, which is 20% from the edge of the canvas,
            // which distance is canvas length minus 80% of the total length
            xOffset = 0.60 * width; // Set xOffset at 60% of the width of the canvas image
            if (pc > 0)
                distance = percentage * (width - (width * 0.80));
            else
                distance = percentage * ((width / 2)  - (width * 0.10));
        } else {
            xOffset = 0.20 * width; // Set xOffset at 20% of the width of the canvas image
            distance = percentage * (width - (2 * xOffset));
        }

        // Handle Mobile Test
        if (!mobileTest) {
            actions.moveToElement(sliderCtrl, (int) xOffset, (int) yOffset)
                    .clickAndHold()
                    .moveByOffset((int) distance, 0)
                    .release()
                    .perform();
        } else {
            xOffset = xOffset + distance;
            actions.moveToElement(sliderCtrl, (int) xOffset, (int) yOffset).click().perform();
        }
        h.scrollToAndClickElement(nextBtn, 50);

        // Handle Modal Alerts
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if (modalOptions.get(0).isDisplayed())
                if (progressIndicator.getText().equals("18/20"))
                    modalOptions.get(0).click();
                else
                    modalOptions.get(1).click();
        } catch (Exception e) {
            System.out.println("No modal alert, proceeding to next section.");
        }
    }
}