package steps;

import context.ContextStore;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import pages.ProductScreen;

public class ProductScreenSteps {

    ProductScreen productScreen = new ProductScreen();

    @Given("Add selected product to basket")
    public void addToBasket() {
        productScreen.clickElementUntil(true, productScreen.addToBasketButton);
    }
}
