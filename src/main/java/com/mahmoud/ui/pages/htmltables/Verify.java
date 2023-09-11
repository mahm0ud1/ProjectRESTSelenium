package com.mahmoud.ui.pages.htmltables;

import com.mahmoud.ui.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Verify {
    private final String customerTableID = "customers";

    public WebElement getCustomerTable() {
        return BrowserDriver.findElementByID(customerTableID);
    }
    public String getTableCellText(WebElement table, int searchColumn, String searchText, int returnColumn) {
        try {
            return getTableCellTextByXpath(table,searchColumn,searchText,returnColumn);
        }
        catch (Exception e) {
            System.out.println("Unable to find the text");
        }
        return null;
    }

    public boolean verifyTableCellText(WebElement table, int searchColumn, String searchText, int returnColumn, String expectedText) {
        String actualText = getTableCellText(table, searchColumn, searchText, returnColumn);
        return actualText.equals(expectedText);
    }

    public String getTableCellTextByXpath(WebElement table, int searchColumn, String searchText, int returnColumn) throws Exception {
        WebElement returnElement = table.findElement(By.xpath(
                String.format("./tbody/tr/td[%d][text()='%s']/../td[%d]", searchColumn, searchText, returnColumn)
        ));
        return returnElement.getText();
    }
}
