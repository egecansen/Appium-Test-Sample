package pages.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Utils;

import java.util.List;

public class MainCategoryRows extends Utils {
    @FindBy(css = "android.widget.TextView")
    public List<WebElement> sectionRows;

    public WebElement getSectionRow(String section) {
        return getElementFromList(section, sectionRows);
    }
}
