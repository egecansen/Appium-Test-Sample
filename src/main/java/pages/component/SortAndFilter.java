package pages.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Utils;

import java.util.List;

public class SortAndFilter extends Utils {
    @FindBy(css = "android.widget.TextView")
    public List<WebElement> sortAndFilter;

    public WebElement getSortFilter(String productName) {
        return getElementFromList(productName, sortAndFilter);
    }
}
