package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Utils;

public class BasketScreen extends Utils {

    @FindBy(id = "trendyol.com:id/textViewBrandName")
    public WebElement productName;
}
