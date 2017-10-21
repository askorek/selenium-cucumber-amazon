package com.skorek;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

import static java.lang.Math.abs;

public class StepDefinition {
    WebDriver driver;
    String product_name;
    Double product_price;
    int number_of_products;

    @Given("^I am on Amazon page$")
    public void i_am_on_Amazon_page() throws Throwable {
        driver = new ChromeDriver();
        driver.get("http://www.amazon.com");
        String title = driver.getTitle();
        assert title.toLowerCase().contains("amazon");

    }

    @Given("^I go to best selling cameras$")
    public void i_go_to_best_selling_cameras() throws Throwable {

        // List<WebElement> elements = driver.findElements(By.cssSelector("[id*=uber-widget]"));
        WebElement element =  driver.findElements(By.xpath("//*[contains(@id,\"uber-widget-ns\")]/div[1]/span/a")).get(1);
        element.click();
    }
    @When("^I select position (\\d+)$")
    public void i_select_position(int position) throws Throwable {

        //WebElement element =  driver.findElement(By.xpath("""//*[@id=\"zg_centerListWrapper\"]/div[6]/div[2]/div/a/div[1]/img"));
        String xpath_to_position = String.format("//*[@id=\"zg_centerListWrapper\"]/div[%s]/div[2]/div/a/div[1]/img", position+1);
        WebElement element =  driver.findElement(By.xpath(xpath_to_position));
        element.click();
    }

    @When("^I add it to shopping chart in quantity of (\\d+)$")
    public void i_add_it_to_shopping_chart_in_quantity_of(int quantity) throws Throwable {
        number_of_products = quantity;

        product_name = driver.findElement(By.xpath("//*[@id=\"productTitle\"]")).getText();
        String product_price_text = driver.findElement(By.xpath("//*[@id=\"priceblock_ourprice\"]")).getText();
        product_price_text = product_price_text.replace("$", "").replace(",", "");
        product_price = Double.parseDouble(product_price_text);

        WebElement quantityMenu = driver.findElement(By.xpath("//*[@id=\"quantity\"]"));
        Select quantitySelect = new Select(quantityMenu);
        quantitySelect.selectByValue(Integer.toString(quantity));
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]")).click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"siNoCoverage-announce\"]")));
        WebElement no_button = driver.findElement(By.xpath("//*[@id=\"siNoCoverage-announce\"]"));
        no_button.click();
        driver.get("https://www.amazon.com/gp/cart/view.html/ref=nav_cart");
    }

    @When("^I go to checkout$")
    public void i_go_to_checkout() throws Throwable {
        driver.get("https://www.amazon.com/gp/cart/view.html/ref=nav_cart");

    }

    @Then("^I should see proper product name on item list$")
    public void i_should_see_proper_product_name_on_item_list() throws Throwable {
        assert driver.getPageSource().contains(product_name);
    }

    @Then("^I sould see proper final price$")
    public void i_sould_see_proper_final_price() throws Throwable {

        String total_price_string = driver.findElement(By.xpath("//*[@id=\"sc-subtotal-amount-activecart\"]/span")).getText();
        total_price_string = total_price_string.replace("$", "").replace(",", "");
        Double total_price = Double.parseDouble(total_price_string);
        assert abs(total_price - product_price*number_of_products) < 0.01;
        driver.close();
        driver.quit();
    }
}
