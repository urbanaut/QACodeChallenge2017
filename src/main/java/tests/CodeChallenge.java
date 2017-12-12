package tests;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AssessmentPage;
import utils.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeChallenge extends TestBase {

    private AssessmentPage ap;
    private Helpers h;
    private HashMap<String, Map<Integer, List>> excelData;

    private static final String OUTPUT_FILE = "src/main/java/output/summary.txt";
    private static final String DATA_FILE = "src/main/resources/data-provider/assessmentdata.xls";
    private static final String SHEET_NAME = "Sheet1";
    private static final int ROW_NUMBER = 6;

    @BeforeClass
    public void setup() {
        ap = new AssessmentPage(driver, mobileTest);
        h = new Helpers();
        excelData = h.loadExcelFile(DATA_FILE);
    }

    @Test
    public void takeSingleAssessment() throws Exception {
        takeAssessment(ROW_NUMBER);
    }

    @Test
    public void takeAllAssessments() throws Exception {
        int rows = excelData.get(SHEET_NAME).size() - 1;
        for (int i=1; i<rows; i++) {
            takeAssessment(i);
        }
    }


    private void takeAssessment(int rowNumber) throws Exception {
        List<String> data = excelData.get(SHEET_NAME).get(rowNumber);
        Map<String, String> inputData = new HashMap<>();

        inputData.put("name", data.get(1));
        inputData.put("countryURL", data.get(2));
        inputData.put("age", data.get(4));
        inputData.put("sex", h.capitalize(data.get(5)));
        inputData.put("ethnicity", data.get(6));
        inputData.put("location", h.capitalize(data.get(7)) + ", " + data.get(3));
        inputData.put("pollution", data.get(8));
        inputData.put("environment", data.get(9));
        inputData.put("skinType", h.capitalize(data.get(10)));
        inputData.put("sensitivity", h.capitalize(data.get(11)));
        inputData.put("aha", data.get(12));
        inputData.put("ageSpots", data.get(13));
        inputData.put("eyeWrinkles", data.get(14));
        inputData.put("mouthWrinkles", data.get(15));
        inputData.put("foreheadWrinkles", data.get(16));
        inputData.put("pores", data.get(17));
        inputData.put("firmness", data.get(18));
        inputData.put("radiance", data.get(19));
        inputData.put("texture", data.get(20));
        inputData.put("dayFragrance", h.capitalize(data.get(21)));
        inputData.put("dayMoisturizer", data.get(22));
        inputData.put("nightFragrance", h.capitalize(data.get(23)));
        inputData.put("nightMoisturizer", data.get(24));

        // Execute Assessment Test
        ap.navigateToCountryUrl(inputData.get("countryURL"));
        ap.acceptAgreement();
        ap.continueAssessment();
        ap.enterPersonalInfo(inputData.get("name"), inputData.get("age"), inputData.get("sex"));
        ap.selectEthnicity(inputData.get("ethnicity"));
        ap.enterLocation(inputData.get("location"));
        ap.slideDial(inputData.get("pollution"));
        ap.slideDial(inputData.get("environment"));
        ap.continueAssessment();
        ap.selectOption(ap.skinTypes, inputData.get("skinType"), 50);
        ap.slideDial(inputData.get("sensitivity"));
        ap.selectOption(ap.ahas, "aha", 50);
        ap.slideDial(inputData.get("ageSpots"));
        ap.slideDial(inputData.get("eyeWrinkles"));
        ap.slideDial(inputData.get("mouthWrinkles"));
        ap.slideDial(inputData.get("foreheadWrinkles"));
        ap.slideDial(inputData.get("pores"));
        ap.selectOption(ap.firmnessTypes, inputData.get("firmness"), 50);
        ap.selectOption(ap.radianceTypes, inputData.get("radiance"), 50);
        ap.selectOption(ap.textures, inputData.get("texture"), 50);
        ap.continueAssessment();
        ap.selectOption(ap.fragranceChoices, inputData.get("dayFragrance"), 75);
        ap.slideDial(inputData.get("dayMoisturizer"));
        ap.selectOption(ap.fragranceChoices, inputData.get("nightFragrance"), 75);
        ap.slideDial(inputData.get("nightMoisturizer"));
        ap.getCustomizedRegimen(OUTPUT_FILE);
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }

}