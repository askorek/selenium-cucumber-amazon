package com.skorek.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends PageObject {
    public ProductPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"productTitle\"]")
    private WebElement productNameElement;

    @FindBy(xpath = "//*[@id=\"priceblock_ourprice\"]")
    private WebElement productPriceElement;

    @FindBy(xpath = "//*[@id=\"quantity\"]")
    private WebElement quantityMenu;

    @FindBy(xpath = "//*[@id=\"add-to-cart-button\"]")
    private WebElement addToChartButton;

    @FindBy(xpath = "//*[@id=\"siNoCoverage-announce\"]")
    private WebElement noCoverangeButton;


    public String getProductName(){
        return productNameElement.getText();
    }

    public double getProductPrice(){
        String product_price_text = productPriceElement.getText();
        product_price_text = product_price_text.replace("$", "").replace(",", "");
        return Double.parseDouble(product_price_text);
    }

    public void setQuantity(int quantity){
        Select quantitySelect = new Select(quantityMenu);
        quantitySelect.selectByValue(Integer.toString(quantity));
    }

    public void clickAddToChartAndDeclineCoverage(){
        addToChartButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(noCoverangeButton));
        noCoverangeButton.click();
    }

    public CheckoutPage goToCheckout(){
        driver.get("https://www.amazon.com/gp/cart/view.html/ref=nav_cart");
        return new CheckoutPage(driver);
    }




}
