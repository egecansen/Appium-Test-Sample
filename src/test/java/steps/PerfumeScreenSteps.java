package steps;

import context.ContextStore;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebElement;
import pages.PerfumeScreen;
import utils.MobileUtilities;

public class PerfumeScreenSteps {

    PerfumeScreen perfumeScreen = new PerfumeScreen();

    @Given("Select product named {} on the selected category page")
    public void selectProduct(String productName) {
        final long startTime = System.currentTimeMillis();
        long timeOut = 45000;
        WebElement targetProduct = perfumeScreen.getProduct(productName);
        while (targetProduct == null && System.currentTimeMillis() - startTime < timeOut) {
            perfumeScreen.swiper(MobileUtilities.Direction.DOWN);
            targetProduct = perfumeScreen.centerElement(perfumeScreen.getProduct(productName));
        }
        perfumeScreen.clickElementUntil(false, targetProduct);
        ContextStore.put("expectedProduct", productName);
    }

}
