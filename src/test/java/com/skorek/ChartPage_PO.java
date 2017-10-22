package com.skorek;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChartPage_PO extends PageObject {
    public ChartPage_PO(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"sc-subtotal-amount-activecart\"]/span")
    private WebElement totalPriceElement;

    public double getTotalPrice(){
        String total_price_text = totalPriceElement.getText();
        total_price_text = total_price_text.replace("$", "").replace(",", "");
        return Double.parseDouble(total_price_text);
    }
}
