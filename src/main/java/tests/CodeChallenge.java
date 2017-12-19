package tests;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AssessmentPage;
import utils.Helpers;

public class CodeChallenge extends TestBase {

    private AssessmentPage ap;

    public static final String SUMMARY_FILE = "src/main/java/output/summary.txt";
    public static final String RESULTS_FILE = "src/main/java/output/results.txt";
    public static final String DATA_FILE = "src/main/resources/data-provider/assessmentdata.xls";
    public static final String SHEET_NAME = "Sheet1";
    private static final int ROW_NUMBER = 2;

    @BeforeClass
    public void setup() {
        ap = new AssessmentPage(driver, mobileTest);
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }


    @Test
    public void takeSingleAssessment() throws Exception {
        ap.takeAssessment(ROW_NUMBER);
    }

    @Test
    public void takeAllAssessments() throws Exception {
        Helpers h = new Helpers();
        int rows = h.getSpreadsheetRows();
        for (int i=1; i<rows; i++) {
            ap.takeAssessment(i);
        }
    }

}