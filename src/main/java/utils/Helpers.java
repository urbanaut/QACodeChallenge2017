package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static base.TestBase.DATA_FILE;
import static base.TestBase.SHEET_NAME;

public class Helpers {

    private WebDriver driver;
    private boolean isMobileTest;
    
    public Helpers(WebDriver driver, boolean isMobileTest) {
        this.driver = driver;
        this.isMobileTest = isMobileTest;
    }

    public Helpers(){}

    public void waitForElementToBeReady(WebElement element) throws Exception {
        do {
            Thread.sleep(500);
        }while (!element.isDisplayed());
    }

    public void scrollToAndClickElement(WebElement element, int offset) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollTo(0," + element.getLocation().getY() + ")");
            jse.executeScript("window.scrollBy(0,-" + offset + ")");
            element.click();
            if (isMobileTest)
                jse.executeScript("window.scrollTo(0,0)");
        } catch (Exception e) {
            System.out.println("Unable to locate element.");
            e.printStackTrace();
        }
    }

    public Map<String, Map<Integer, List<String>>> loadExcelFile(String dataFile) {
        Map<String, Map<Integer, List<String>>> sheetData = new LinkedHashMap<>();
        Map<Integer, List<String>> rowData = new LinkedHashMap<>();
        File file = new File(dataFile);
        String sheetName;

        try(InputStream fis = new FileInputStream(file)) {
            HSSFWorkbook workBook = new HSSFWorkbook(fis);

            for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
                HSSFSheet sheet = workBook.getSheetAt(i);
                sheetName = workBook.getSheetName(i);
                Iterator rows = sheet.rowIterator();

                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    List<String> cellData = new LinkedList<String>();

                    while (cells.hasNext()) {
                        HSSFCell cell = (HSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellData.add(cell.toString());
                    }
                    rowData.put(row.getRowNum(), cellData);
                }
                sheetData.put(sheetName, rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
    }

    public int getSpreadsheetRows() {
        Map<String, Map<Integer, List<String>>> excelData = loadExcelFile(DATA_FILE);
        return excelData.get(SHEET_NAME).size() - 1;
    }

    public WebElement getElementByInnerHtml(List<WebElement> elements, String option) throws InterruptedException {
        if (option.contains(" "))
            option = option.substring(0, option.indexOf(" "));
        Thread.sleep(1000);
        for (WebElement element : elements) {
            if(element.getAttribute("innerHTML").contains(option)) {
                return element;
            }
        }
        return null;
    }

    public WebElement getElementByAttribute(List<WebElement> elements, String option) {
        Map<String, WebElement> buttons = new HashMap<>();
        for (WebElement element : elements) {
            buttons.put(element.getAttribute("ng-class"), element);
        }
        return buttons.get(option);
    }

    public String compareStringToAttribute(List<WebElement> elements, String option) {
        for (WebElement element : elements) {
            String attribute = element.getAttribute("ng-class");
            if (attribute.toLowerCase().contains(option.toLowerCase()))
                return attribute;
        }
        return null;
    }

}