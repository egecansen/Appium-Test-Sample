package pages.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Utils;

import java.util.List;

public class CountryRows extends Utils {

    @FindBy(id = "trendyol.com:id/textViewCountryName")
    public List<WebElement> countries;

    public WebElement getCountryRow(String countryName) {
        return getElementFromList(countryName, countries);
    }

}
