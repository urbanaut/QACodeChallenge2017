package utils;

import org.apache.commons.lang3.StringUtils;
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
    
    public Helpers(WebDriver driver, boolean mobileTest) {
        this.driver = driver;
        this.mobileTest = mobileTest;
    }

    public Helpers(){}

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

    public HashMap loadExcelFile() {
        HashMap<String, LinkedHashMap<Integer, List>> sheetData = new LinkedHashMap<>();
        LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<>();
        File file = new File("src/main/resources/data-provider/assessmentdata.xls");
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
                    List data = new LinkedList();

                    while (cells.hasNext()) {
                        HSSFCell cell = (HSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        data.add(cell);
                    }
                    hashMap.put(row.getRowNum(), data);
                }
                sheetData.put(sheetName, hashMap);
                hashMap = new LinkedHashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
    }

    public WebElement getElementByInnerHtml(List<WebElement> elements, String option) {
        Map<String, WebElement> buttons = new HashMap<>();
        for (WebElement element : elements) {
            buttons.put(element.getAttribute("innerHTML"), element);
        }
        return buttons.get(option);
    }

    public WebElement getElementByAttribute(List<WebElement> elements, String option) {
        Map<String, WebElement> buttons = new HashMap<>();
        for (WebElement element : elements) {
            buttons.put(element.getAttribute("ng-class"), element);
        }
        return buttons.get(option);
    }

    public String compareStringToInnerHtml(String option, List<WebElement> elements) {
        for (WebElement element : elements) {
            String s = element.getAttribute("innerHTML");
            if (s.toLowerCase().contains(option.toLowerCase()))
                return s;
        }
        return option;
    }

    public String compareStringToAttribute(String option, List<WebElement> elements) {
        for (WebElement element : elements) {
             String s = element.getAttribute("ng-class");
            if (s.toLowerCase().contains(option.toLowerCase()))
                return s;
        }
        return option;
    }

    public String capitalize(String word) {
        return StringUtils.capitalize(word);
    }

    public String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])")
                , " ");
    }

    public String chop(String word) {
        int i = word.indexOf(" ");
        word = word.substring(0, i);
        return word;
    }
}