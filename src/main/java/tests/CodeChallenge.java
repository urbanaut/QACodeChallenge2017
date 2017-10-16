package tests;

import base.TestBase;
import org.testng.annotations.Test;
import pages.AssessmentPage;
import utils.Helpers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CodeChallenge extends TestBase {

    @Test
    public void testAssessment() throws Exception {
        AssessmentPage ap = new AssessmentPage(driver, mobileTest);
        Helpers h = new Helpers();
        HashMap<String, LinkedHashMap<Integer, List>> excelData = h.loadExcelLines();
        List data = excelData.get("Sheet1").get(1);
        Map<String, String> inputData = new HashMap<>();

        inputData.put("name", data.get(1).toString());
        inputData.put("countryURL", data.get(2).toString());
        inputData.put("age", data.get(4).toString());
        inputData.put("sex", h.capitalize(data.get(5).toString()));
        inputData.put("ethnicity", data.get(6).toString());
        inputData.put("location", h.capitalize(data.get(7).toString()) + ", " + data.get(3));
        inputData.put("pollution", data.get(8).toString());
        inputData.put("environment", data.get(9).toString());
        inputData.put("skinType", h.capitalize(data.get(10).toString()));
        inputData.put("sensitivity", h.capitalize(data.get(11).toString()));
        inputData.put("aha", data.get(12).toString());
        inputData.put("ageSpots", data.get(13).toString());
        inputData.put("eyeWrinkles", data.get(14).toString());
        inputData.put("mouthWrinkles", data.get(15).toString());
        inputData.put("foreheadWrinkles", data.get(16).toString());
        inputData.put("pores", data.get(17).toString());
        inputData.put("firmness", data.get(18).toString());
        inputData.put("radiance", data.get(19).toString());
        inputData.put("texture", data.get(20).toString());
        inputData.put("dayFragrance", h.capitalize(data.get(21).toString()));
        inputData.put("dayMoisturizer", data.get(22).toString());
        inputData.put("nightFragrance", h.capitalize(data.get(23).toString()));
        inputData.put("nightMoisturizer", data.get(24).toString());

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
        ap.selectSkinType(inputData.get("skinType"));
        ap.slideDial(inputData.get("sensitivity"));
        ap.selectAHAExposure(inputData.get("aha"));
        ap.slideDial(inputData.get("ageSpots"));
        ap.slideDial(inputData.get("eyeWrinkles"));
        ap.slideDial(inputData.get("mouthWrinkles"));
        ap.slideDial(inputData.get("foreheadWrinkles"));
        ap.slideDial(inputData.get("pores"));
        ap.selectSkinFirmness(inputData.get("firmness"));
        ap.selectSkinRadiance(inputData.get("radiance"));
        ap.selectSkinTexture(inputData.get("texture"));
        ap.continueAssessment();
        ap.selectAddFragrance(inputData.get("dayFragrance"));
        ap.slideDial(inputData.get("dayMoisturizer"));
        ap.selectAddFragrance(inputData.get("nightFragrance"));
        ap.slideDial(inputData.get("nightMoisturizer"));
        ap.findCustomizedRegimen();
    }
}
