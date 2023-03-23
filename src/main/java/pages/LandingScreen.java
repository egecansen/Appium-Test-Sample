package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.component.MainCategoryRows;
import utilities.Utils;


public class LandingScreen extends Utils {

    @FindBy(id = "trendyol.com:id/imageViewTooltipClose")
    public WebElement closeTipButton;

    @FindBy(id = "trendyol.com:id/recyclerView")
    public MainCategoryRows mainCategoryRowsections;

}
