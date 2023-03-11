package steps;

import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import java.util.concurrent.TimeUnit;

import utilities.Utils;
import utils.appium.Driver;
import utils.appium.ServiceFactory;


public class CommonSteps extends Utils {

    Driver driver = new Driver();
    Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        log.new Info("Running: " + highlighted(Color.BLUE, scenario.getName()));
        if (ServiceFactory.service == null) startService();
        this.scenario = scenario;
        driver.initialize();
    }

    @After
    public void kill(Scenario scenario) {
        if (scenario.isFailed()) log.new Warning(scenario.getName() + ": FAILED!");
        else log.new Success(scenario.getName() + ": PASS!");
        driver.terminate();
    }

    @Given("Wait {} seconds")
    public void wait(int duration) {
        log.new Info("Waiting for " + duration + " seconds");
        try {TimeUnit.SECONDS.sleep(duration);}
        catch (InterruptedException ignored){}
    }

}
