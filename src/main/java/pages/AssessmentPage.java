package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Helpers;

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
    @FindBy(xpath = "//*[@id=\"nuskinBespokeApp\"]//button[@translate='back-btn-text']")
    public WebElement previousBtn;
    @FindBy(xpath = "//*[@id=\"nuskinBespokeApp\"]//button[@translate='next-btn-text']")
    public WebElement nextBtn;
    @FindBy(xpath = "//button[@translate='continue-btn-text']")
    public WebElement continueBtn;

    // Agreement
    @FindBy(xpath = "//*[@id=\"page-wrap-you\"]/div[3]/div/div/div[2]/p/label")
    public WebElement agreementBtn;
    @FindBy(xpath = "//*[@id=\"page-wrap-you\"]/div[3]/div/div/div[2]/button")
    public WebElement agreementContinueBtn;
    @FindBy(xpath = "//div[@id='header']//a[@id='agreeToCookies']")
    public WebElement cookieBtn;

    // Personal Info
    @FindBy(xpath = "//*[@id=\"name-text\"]")
    public WebElement nameTbx;
    @FindBy(xpath = "//*[@id=\"age-text\"]")
    public WebElement ageTbx;
    @FindBy(xpath = "//*[@id=\"nuskinBespokeApp\"]/div/div[3]/div/div[2]/div/button")
    public List<WebElement> sexes;

    // Ethnicity
    @FindBy(xpath = "//*[@id=\"ethnicity-window\"]/ul/li")
    public List<WebElement> ethnicities;

    // Location
    @FindBy(xpath = "//*[@id=\"locationText\"]")
    public WebElement locationTxb;
    @FindBy(xpath = "//div/h2[@ng-bind-html='subtitleText()']")
    public WebElement autoFindTxt;
    @FindBy(xpath = "//*[@id=\"map\"]")
    public WebElement map;
    @FindBy(xpath = "//*[@id=\"nuskinBespokeApp\"]/div/div[3]/div/div[2]/div/div[1]/div/i")
    public WebElement clearLocationBtn;

    // Dial Pages
    @FindBy(xpath = "//canvas")
    public WebElement sliderCtrl;

    // Skin Type
    @FindBy(xpath = "//div[@id='skin-type-window']/button")
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

    @FindBy(xpath = "//button[@translate='no-btn-text']")
    public WebElement noMoisturizerBtn;


    // Sun Protection Modal
    @FindBy(xpath = "//button[contains(@class,'change')]")
    public WebElement sunProtectionContinueBtn;

    @FindBy(xpath = "//button[contains(@class,'confirm')]")
    public WebElement sunProtectionChangeBtn;


    // Lighter Moisturizer Modal
    @FindBy(xpath = "//button[contains(@class,'change')]")
    public WebElement noModalBtn;

    @FindBy(xpath = "//button[contains(@class,'confirm')]")
    public WebElement yesModalBtn;


    // Review
    @FindBy(xpath = "//button[@translate='review-find-my-regimen']")
    public WebElement findCustomizedRegimenBtn;


    public void acceptAgreement() throws Exception {
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

    public void enterPersonalInfo(String name, String age, String sex) {
        nameTbx.sendKeys(name);
        ageTbx.sendKeys(age);
        Map<String, WebElement> buttons = new HashMap<>();
        for (WebElement element : sexes)
            buttons.put(element.getAttribute("innerHTML"),element);
        buttons.get(sex).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectEthnicity(String ethnicity) throws Exception {
        h.waitForElementToBeReady(ethnicities.get(0));
        h.getElementByInnerHtml(ethnicities, ethnicity).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void enterLocation(String location) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(map);
        Thread.sleep(1000);
        if (autoFindTxt.isDisplayed())
            clearLocationBtn.click();
        locationTxb.sendKeys(location);
        Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN, Keys.ENTER).perform();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectSkinType(String skinType) throws Exception {
        h.waitForElementToBeReady(skinTypes.get(1));
        h.getElementByInnerHtml(skinTypes, skinType).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectAHAExposure(String pollution) throws Exception {
        h.waitForElementToBeReady(ahas.get(0));
        pollution = h.compareStringToInnerHtml(h.splitCamelCase(pollution), ahas);
        h.getElementByInnerHtml(ahas, pollution).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectSkinFirmness(String firmness) throws Exception {
        h.waitForElementToBeReady(firmnessTypes.get(0));
        firmness = h.compareStringToAttribute(firmness, firmnessTypes);
        h.getElementByAttribute(firmnessTypes, firmness).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectSkinRadiance(String radiance) throws Exception {
        h.waitForElementToBeReady(radianceTypes.get(0));
        radiance = h.compareStringToAttribute(radiance, radianceTypes);
        h.getElementByAttribute(radianceTypes, radiance).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectSkinTexture(String texture) throws Exception {
        h.waitForElementToBeReady(textures.get(0));
        texture = h.compareStringToAttribute(texture, textures);
        h.getElementByAttribute(textures, texture).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    public void selectAddFragrance(String choice) throws Exception {
        h.waitForElementToBeReady(fragranceChoices.get(0));
        h.getElementByInnerHtml(fragranceChoices, choice).click();
        h.scrollToAndClickElement(nextBtn, 75);
    }



    private void selectContinueModalOption(String choice) {
        if (sunProtectionContinueBtn.isDisplayed()) {
            switch (choice) {
                case "continue":
                    sunProtectionContinueBtn.click();
                    break;
                case "change":
                    sunProtectionChangeBtn.click();
                    break;
                default:
                    sunProtectionContinueBtn.click();
                    break;
            }
        }
    }

    private void selectYesNoModalOption(String choice) {
        if (yesModalBtn.isDisplayed()) {
            switch (choice) {
                case "no":
                    noModalBtn.click();
                    break;
                case "yes":
                    yesModalBtn.click();
                    break;
                default:
                    yesModalBtn.click();
                    break;
            }
        }
    }

    public void findCustomizedRegimen() throws Exception {
        h.waitForElementToBeReady(findCustomizedRegimenBtn);
        findCustomizedRegimenBtn.click();
    }



    public void slideDial(String percent) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(sliderCtrl);

        int pc = Integer.valueOf(percent);
        int width = sliderCtrl.getSize().getWidth();
        int height = sliderCtrl.getSize().getHeight();
        double xOffset = 0.20 * width;      // Set xOffset at 20% of the width of the canvas image
        double yOffset = 0.80 * (height);   // Set yOffset at 80% of the height of the canvas image
        double percentage = pc / 100.0;
        double distance = percentage * (width - (2 * xOffset));

        //System.out.println("Width: " + width + ", Height: " + height + ", xOff: " + xOffset + ", yOff: " + yOffset + ", Distance: " + distance);

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
    }

    public void slideDayMoisturizerDial(String percent) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(sliderCtrl);

        int pc = Integer.valueOf(percent);
        int width = sliderCtrl.getSize().getWidth();
        int height = sliderCtrl.getSize().getHeight();
        double xOffset = 0.50 * width;      // Set xOffset at 50% of the width of the canvas image
        double yOffset = 0.80 * (height);   // Set yOffset at 80% of the height of the canvas image
        double percentage = pc / 100.0;
        double distance = percentage * ((width / 2) - (width * 0.2));

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

        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if (sunProtectionContinueBtn.isDisplayed())
                selectContinueModalOption("continue");

        }catch (Exception e) {
            System.out.println("No modal alert, proceeding to next section.");
        }

        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if (yesModalBtn.isDisplayed())
                selectYesNoModalOption("yes");
        }catch (Exception e) {
            System.out.println("No modal alert, proceeding to next section.");
        }
    }

    public void slideNightMoisturizerDial(String percent) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(sliderCtrl);

        int pc = Integer.valueOf(percent);
        int width = sliderCtrl.getSize().getWidth();
        int height = sliderCtrl.getSize().getHeight();
        double xOffset = 0.60 * width;      // Set xOffset at 50% of the width of the canvas image
        double yOffset = 0.80 * (height);   // Set yOffset at 80% of the height of the canvas image
        double percentage = pc / 100.0;
        double distance;

        // Knob starts at 60% of canvas, distance must be between that
        // and the end of the dial, which is 20% from the edge of the canvas,
        // which distance is canvas length minus 80% of the total length
        if (pc > 0) {
            distance = percentage * (width - (width * 0.80));
        } else {
            distance = percentage * ((width / 2)  - (width * 0.10));
        }

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

        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if (yesModalBtn.isDisplayed()) {
                selectYesNoModalOption("yes");
            }
        }catch (Exception e) {
            System.out.println("No modal alert, proceeding to next section.");
        }
    }


}
