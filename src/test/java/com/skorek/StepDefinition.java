package com.skorek;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
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
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class StepDefinition {
    private static WebDriver driver;
    String product_name;
    Double product_price;
    int number_of_products;

    MainPage_PO mainPage;
    ProductsPage_PO productsPage;
    ProductPage_PO productPage;
    ChartPage_PO chartPage;

    @Before
    public static void SetUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public static void cleanUp(){
        driver.close();
        driver.quit();
    }


    @Given("^I am on Amazon page$")
    public void i_am_on_Amazon_page() throws Throwable {
//        driver = new ChromeDriver();
        driver.get("http://www.amazon.com");
        mainPage = new MainPage_PO(driver);
    }

    @Given("^I go to best selling cameras$")
    public void i_go_to_best_selling_cameras() throws Throwable {

        productsPage = mainPage.enterProductPage();
    }

    @When("^I select position (\\d+)$")
    public void i_select_position(int position) throws Throwable {

        productPage = productsPage.goToProductOnPosition(position);
        product_price = productPage.getProductPrice();
    }

    @When("^I add it to shopping chart in quantity of (\\d+)$")
    public void i_add_it_to_shopping_chart_in_quantity_of(int quantity) throws Throwable {
        number_of_products = quantity;

        productPage.setQuantity(quantity);
        productPage.clickAddToChartAndDeclineCoverage();
    }

    @When("^I go to checkout$")
    public void i_go_to_checkout() throws Throwable {
        chartPage = productPage.goToChart();
    }

    @Then("^I should see proper product name on item list$")
    public void i_should_see_proper_product_name_on_item_list() throws Throwable {
        assert chartPage.driver.getPageSource().contains(product_name);
    }

    @Then("^I sould see proper final price$")
    public void i_sould_see_proper_final_price() throws Throwable {

        assert abs(chartPage.getTotalPrice() - product_price*number_of_products) < 0.01;
        driver.close();
        driver.quit();
    }
}
