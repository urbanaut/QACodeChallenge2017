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
import java.util.*;

public class Helpers {

    private WebDriver driver;
    private boolean mobileTest;

    public Helpers(){}
    
    public Helpers(WebDriver driver, boolean mobileTest) {
        this.driver = driver;
        this.mobileTest = mobileTest;
    }

    public void waitForElementToBeReady(WebElement element) throws Exception {
        do {
            Thread.sleep(1000);
        }while (!element.isDisplayed());
    }

    public void scrollToAndClickElement(WebElement element, int offset) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollTo(0," + element.getLocation().getY() + ")");
            jse.executeScript("window.scrollBy(0,-" + offset + ")");
            element.click();
            if (mobileTest)
                jse.executeScript("window.scrollTo(0,0)");
        } catch (Exception e) {
            System.out.println("Unable to locate element.");
            e.printStackTrace();
        }
    }

    public HashMap loadExcelFile(String dataFile) {
        HashMap<String, Map<Integer, List>> sheetData = new LinkedHashMap<>();
        Map<Integer, List> hashMap = new LinkedHashMap<>();
        File file = new File(dataFile);
        String sheetName;

        try(FileInputStream fis = new FileInputStream(file)) {
            HSSFWorkbook workBook = new HSSFWorkbook(fis);

            for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
                HSSFSheet sheet = workBook.getSheetAt(i);
                sheetName = workBook.getSheetName(i);
                Iterator rows = sheet.rowIterator();

                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    List<String> data = new LinkedList<String>();

                    while (cells.hasNext()) {
                        HSSFCell cell = (HSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        data.add(cell.toString());
                    }
                    hashMap.put(row.getRowNum(), data);
                }
                sheetData.put(sheetName, hashMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
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