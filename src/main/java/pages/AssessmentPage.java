package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Helpers;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static base.TestBase.*;

public class AssessmentPage {

    private WebDriver driver;
    private Helpers h;
    private boolean isMobileTest;

    public AssessmentPage(WebDriver driver, boolean isMobileTest) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.isMobileTest = isMobileTest;
        h = new Helpers(driver, isMobileTest);
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

    // Dial
    @FindBy(xpath = "//canvas")
    public WebElement dial;
    @FindBy(xpath = "//input[@class='knob']")
    public WebElement knob;

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


    private void navigateToCountryUrl(String url) {
        driver.navigate().to(url);
        if (driver.getCurrentUrl().contains("en_US"))
            takeAssessmentLinkUS.click();
        else
            takeAssessmentLnk.click();
    }

    private void acceptAgreement() throws Exception {
        h.waitForElementToBeReady(agreementContinueBtn);
        if (isMobileTest) {
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

    private void continueAssessment() throws Exception {
        h.waitForElementToBeReady(continueBtn);
        h.scrollToAndClickElement(continueBtn, 50);
    }

    private void enterPersonalInfo(String name, String age, String sex) throws Exception {
        nameTbx.sendKeys(name);
        ageTbx.sendKeys(age);
        selectOption(sexes, sex, 50);
    }

    private void selectEthnicity(String ethnicity) throws Exception {
        h.waitForElementToBeReady(ethnicities.get(0));
        h.getElementByInnerHtml(ethnicities, ethnicity).click();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    private void enterLocation(String location) throws Exception {
        Actions actions = new Actions(driver);
        h.waitForElementToBeReady(map);
        clearLocationBtn.click();
        locationTxb.sendKeys(location);
        Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN, Keys.ENTER).perform();
        h.scrollToAndClickElement(nextBtn, 50);
    }

    private void selectOption(List<WebElement> elements, String option, int offset) throws Exception {
        h.waitForElementToBeReady(elements.get(0));
        option = h.compareStringToAttribute(elements, option);
        h.getElementByAttribute(elements, option).click();
        h.scrollToAndClickElement(nextBtn, offset);
    }

    private void getCustomizedRegimen(String output) throws Exception {
        OutputStream fos = new FileOutputStream(output);
        h.waitForElementToBeReady(findCustomizedRegimenBtn);
        fos.write((assessmentSummaryTxt.getText()+"\n").getBytes());
        h.scrollToAndClickElement(findCustomizedRegimenBtn, 50);
        do {
            Thread.sleep(500);
        } while (assessmentCodeTxt.getText().equals(""));
        fos.write(("Assessment Code: " + assessmentCodeTxt.getText()).getBytes());
        fos.close();
    }

    private void verifyAssessmentCode(String code, String resultsFile, int rowNumber) throws Exception {
        OutputStream fos = new FileOutputStream(resultsFile, true);
        String timestamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
        if (!code.equals(assessmentCodeTxt.getText())) {
            String line = "\n\nFailed Test, " + timestamp + "\n" +
                    "Row: " + String.valueOf(rowNumber) +
                    ", Expected Code: " + code +
                    ", Actual Code: " + assessmentCodeTxt.getText();
            fos.write(line.getBytes());
            fos.close();
        }
    }

    private void slideDial(String percentage) throws Exception {
        h.waitForElementToBeReady(dial);

        int width = dial.getSize().getWidth();
        int height = dial.getSize().getHeight();
        double startingValue = Double.valueOf(knob.getAttribute("value"));
        double yOffset = height * 0.80;   // Set yOffset at 80% of the height of the canvas image
        double xOffset = width * (20 + (0.6 * startingValue))/100;

        if (percentage.equals("")) {
            percentage = "0";
        }

        Actions actions = new Actions(driver);
        int increment = 1;
        if (Integer.valueOf(percentage) > 0) {
            actions.moveToElement(dial, (int) xOffset, (int) yOffset).clickAndHold().perform();
            while (!knob.getAttribute("value").equals(percentage)) {
                actions.moveByOffset(increment, 0).perform();
                if (Integer.valueOf(percentage) < Integer.valueOf(knob.getAttribute("value"))) {
                    increment = -1;
                }
            }
            actions.release().perform();
        }

        h.scrollToAndClickElement(nextBtn, 50);

        // Handle modal popup
        if (modalOptions.get(0).isDisplayed()) {
            try {
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                if (progressIndicator.getText().equals("18/20"))
                    modalOptions.get(0).click();
                else
                    modalOptions.get(1).click();
            } catch (Exception e) {
                System.out.println("ERROR: Failed to select modal option.");
                e.printStackTrace();
            }
        }
    }

    public void takeAssessment(int rowNumber) throws Exception {
        Map<String, Map<Integer, List<String>>> excelData = h.loadExcelFile(DATA_FILE);
        List<String> data = excelData.get(SHEET_NAME).get(rowNumber);
        Map<String, String> inputData = new LinkedHashMap<>();

        inputData.put("name", data.get(1));
        inputData.put("countryURL", data.get(2));
        inputData.put("age", data.get(4));
        inputData.put("sex", data.get(5));
        inputData.put("ethnicity", data.get(6));
        inputData.put("location", data.get(7) + ", " + data.get(3));
        inputData.put("pollution", data.get(8));
        inputData.put("environment", data.get(9));
        inputData.put("skinType", data.get(10));
        inputData.put("sensitivity", data.get(11));
        inputData.put("aha", data.get(12));
        inputData.put("ageSpots", data.get(13));
        inputData.put("eyeWrinkles", data.get(14));
        inputData.put("mouthWrinkles", data.get(15));
        inputData.put("foreheadWrinkles", data.get(16));
        inputData.put("pores", data.get(17));
        inputData.put("firmness", data.get(18));
        inputData.put("radiance", data.get(19));
        inputData.put("texture", data.get(20));
        inputData.put("dayFragrance", data.get(21));
        inputData.put("dayMoisturizer", data.get(22));
        inputData.put("nightFragrance", data.get(23));
        inputData.put("nightMoisturizer", data.get(24));
        inputData.put("assessmentCode", data.get(25));

        // Execute Assessment Test
        navigateToCountryUrl(inputData.get("countryURL"));
        acceptAgreement();
        continueAssessment();
        enterPersonalInfo(inputData.get("name"), inputData.get("age"), inputData.get("sex"));
        selectEthnicity(inputData.get("ethnicity"));
        enterLocation(inputData.get("location"));
        slideDial(inputData.get("pollution"));
        slideDial(inputData.get("environment"));
        continueAssessment();
        selectOption(skinTypes, inputData.get("skinType"), 50);
        slideDial(inputData.get("sensitivity"));
        selectOption(ahas, "aha", 50);
        slideDial(inputData.get("ageSpots"));
        slideDial(inputData.get("eyeWrinkles"));
        slideDial(inputData.get("mouthWrinkles"));
        slideDial(inputData.get("foreheadWrinkles"));
        slideDial(inputData.get("pores"));
        selectOption(firmnessTypes, inputData.get("firmness"), 50);
        selectOption(radianceTypes, inputData.get("radiance"), 50);
        selectOption(textures, inputData.get("texture"), 50);
        continueAssessment();
        selectOption(fragranceChoices, inputData.get("dayFragrance"), 75);
        slideDial(inputData.get("dayMoisturizer"));
        selectOption(fragranceChoices, inputData.get("nightFragrance"), 75);
        slideDial(inputData.get("nightMoisturizer"));
        getCustomizedRegimen(SUMMARY_FILE);
        verifyAssessmentCode(inputData.get("assessmentCode"), RESULTS_FILE, rowNumber);
    }

}