package com.skorek;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage_PO extends PageObject {

    public MainPage_PO(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//*[contains(@id,\"uber-widget-ns\")]/div[1]/span/a")
    private List<WebElement> bestSellingCameras;

    public ProductsPage_PO enterProductPage(){
        bestSellingCameras.get(1).click();
        return new ProductsPage_PO(driver);
    }
}
