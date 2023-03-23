package steps;

import context.ContextStore;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import pages.BasketScreen;

public class BasketScreenSteps {

    BasketScreen basketScreen = new BasketScreen();

    @Given("Verify the selected product name on the basket")
    public void verifyProductName() {
        String expectedName = ContextStore.get("expectedProduct").toString();
        String actualName = basketScreen.productName.getText();
        Assert.assertEquals("Product names not match!", expectedName, actualName);
        basketScreen.log.new Success("Product names match!");
    }
}
