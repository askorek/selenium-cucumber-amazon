package com.skorek;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage_PO extends PageObject {

    public MainPage_PO(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//*[contains(@id,\"uber-widget-ns\")]/div[1]/span/a")
    private WebElement bestSellingCameras;

    public ProductsPage_PO enterProductPage(){
        this.bestSellingCameras.click();
        return new ProductsPage_PO(driver);
    }
}
