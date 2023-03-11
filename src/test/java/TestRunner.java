import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utils.appium.Driver;
import utils.appium.ServiceFactory;

import static utils.FileUtilities.properties;

/**
 * Unit test for simple App.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/features"},
        plugin = {"json:target/reports/Cucumber.json", "html:target/reports/Cucumber.html"},
        glue = {"steps"},
        publish = true
)
public class TestRunner
{
    static Boolean useAppium2 = Boolean.parseBoolean(properties.getProperty("use-appium2", "false"));

    @BeforeClass
    public static void initialSequence(){
        if (useAppium2) Driver.startService();
    }

    @AfterClass
    public static void finalSequence(){
        if (useAppium2) ServiceFactory.service.stop();
    }
}
