package steps;

import io.cucumber.java.en.Given;
import pages.StartupScreen;

public class StartupScreenSteps {

    StartupScreen startupScreen = new StartupScreen();
    @Given("Country: {}")
    public void selectCountry(String country) {
       startupScreen.clickElementUntil(true, startupScreen.countryRows.getCountryRow(country));
    }

    @Given("Gender: {}")
    public void selectGender(String gender) {
        startupScreen.clickElementUntil(true, startupScreen.getElementFromList(gender, startupScreen.genderButtons));
    }

    @Given("Click on {} button for accept getting news page on the startup")
    public void acceptCampaigns(String opt) {
        if (opt.equalsIgnoreCase("accept"))
            startupScreen.clickElementUntil(true, startupScreen.acceptCampaignsButton);
        if (opt.equalsIgnoreCase("close"))
            startupScreen.clickElementUntil(true, startupScreen.passCampaignAccept);
    }
}
