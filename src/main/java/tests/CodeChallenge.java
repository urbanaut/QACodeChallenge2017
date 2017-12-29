package tests;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AssessmentPage;
import utils.Helpers;

public class CodeChallenge extends TestBase {

    private AssessmentPage ap;
    private Helpers h;

    @BeforeClass
    public void setup() {
        h = new Helpers();
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }

    @Test
    public void takeSingleAssessmentStandard() throws Exception {
        setIsMobileTest(false);
        ap = new AssessmentPage(driver, false);
        ap.takeAssessment(1);
    }

    @Test
    public void takeSingleAssessmentMobile() throws Exception {
        setIsMobileTest(true);
        ap = new AssessmentPage(driver, true);
        ap.takeAssessment(1);
    }

    //@Test
    public void takeAllAssessmentsStandard() throws Exception {
        setIsMobileTest(false);
        ap = new AssessmentPage(driver, false);
        int rows = h.getSpreadsheetRows();
        for (int i=1; i<rows; i++) {
            ap.takeAssessment(i);
        }
    }

    //@Test
    public void takeAllAssessmentsMobile() throws Exception {
        setIsMobileTest(true);
        ap = new AssessmentPage(driver, true);
        int rows = h.getSpreadsheetRows();
        for (int i=1; i<rows; i++) {
            ap.takeAssessment(i);
        }
    }

}