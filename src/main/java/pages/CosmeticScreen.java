package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Utils;

import java.util.List;

public class CosmeticScreen extends Utils {

    @FindBy(css = "android.widget.ImageView")
    public List<WebElement> popularCategories;

}
