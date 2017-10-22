package com.skorek;

import com.skorek.PageObjects.CheckoutPage;
import com.skorek.PageObjects.MainPage;
import com.skorek.PageObjects.ProductPage;
import com.skorek.PageObjects.ProductCategoryPage;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class StepDefinition {
    private static WebDriver driver;
    private String product_name;
    private Double product_price;
    private int number_of_products;

    private MainPage mainPage;
    private ProductCategoryPage productCategoryPage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;

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
    public void i_am_on_Amazon_page() {
        driver.get("http://www.amazon.com");
        mainPage = new MainPage(driver);
    }

    @And("^I go to category \"([^\"]*)\"$")
    public void iGoToCategory(String category) {
        productCategoryPage = mainPage.goToProductCategory(category);
    }

    @When("^I select position (\\d+)$")
    public void i_select_position(int position) {

        productPage = productCategoryPage.goToProductOnPosition(position);
        product_price = productPage.getProductPrice();
        product_name = productPage.getProductName();
    }

    @When("^I add it to shopping chart in quantity of (\\d+)$")
    public void i_add_it_to_shopping_chart_in_quantity_of(int quantity) {
        number_of_products = quantity;

        productPage.setQuantity(quantity);
        productPage.clickAddToChartAndDeclineCoverage();
    }

    @When("^I go to checkout$")
    public void i_go_to_checkout() {
        checkoutPage = productPage.goToCheckout();
    }

    @Then("^I should see proper product name on item list$")
    public void i_should_see_proper_product_name_on_item_list() {
        assert checkoutPage.containsString(product_name);
    }

    @Then("^I sould see proper final price$")
    public void i_sould_see_proper_final_price() {

        assert abs(checkoutPage.getTotalPrice() - product_price*number_of_products) < 0.01;
        driver.close();
        driver.quit();
    }
}
