package steps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebElement;
import pages.CosmeticScreen;

public class CozmeticScreenSteps {

    CosmeticScreen cosmeticScreen = new CosmeticScreen();

    @Given("Click category named {} from the selected section page")
    public void selectCategory(String category) {
        for (WebElement element : cosmeticScreen.popularCategories)
            if (element.getAttribute("content-desc").equalsIgnoreCase(category)) {
                cosmeticScreen.clickElementUntil(false, element);
                break;
            }
    }
}
