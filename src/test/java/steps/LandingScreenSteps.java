package steps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebElement;
import pages.LandingScreen;

public class LandingScreenSteps {

    LandingScreen landingScreen = new LandingScreen();

    @Given("Click on tip pop-up close button")
    public void closePopUpTip(){
        landingScreen.clickElementUntil(false, landingScreen.closeTipButton);
    }

    @Given("Swipe from {} to {} on section rows from the landing page")
    public void swipeTo(String elementFrom, String destination) {
        WebElement from = landingScreen.mainCategoryRowsections.getSectionRow(elementFrom);
        WebElement to = landingScreen.mainCategoryRowsections.getSectionRow(destination);
        landingScreen.swipeFromTo(from, to);
    }

    @Given("Click on {} category section on section rows from the landing page")
    public void selectCategory(String section) {
        landingScreen.clickElementUntil(false, landingScreen.mainCategoryRowsections.getSectionRow(section));
    }

}
