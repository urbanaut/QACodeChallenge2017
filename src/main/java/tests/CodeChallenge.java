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
        ap = new AssessmentPage(driver, mobileTest);
        h = new Helpers();
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }


    @Test
    public void takeSingleAssessment() throws Exception {
        ap.takeAssessment(3);
    }

    @Test
    public void takeAllAssessments() throws Exception {
        int rows = h.getSpreadsheetRows();
        for (int i=1; i<rows; i++) {
            ap.takeAssessment(i);
        }
    }

}