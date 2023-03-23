package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.component.CountryRows;
import utilities.Utils;

import java.util.List;

public class StartupScreen extends Utils {

    @FindBy(id = "trendyol.com:id/recyclerViewCountries")
    public CountryRows countryRows;

    @FindBy(css = "android.widget.Button")
    public List<WebElement> genderButtons;

    @FindBy(id = "trendyol.com:id/buttonAccept")
    public WebElement acceptCampaignsButton;

    @FindBy(id = "trendyol.com:id/imageButtonClose")
    public WebElement passCampaignAccept;

}
