package utilities;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import utils.MobileUtilities;
import utils.appium.Driver;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static resources.Colors.*;


public abstract class Utils extends MobileUtilities {

    public int counter;

    public void waitFor(double seconds){
        if (seconds > 1) log.new Info("Waiting for "+BLUE+seconds+GRAY+" seconds");
        try {Thread.sleep((long) (seconds* 1000L));}
        catch (InterruptedException exception){Assert.fail(GRAY+exception.getLocalizedMessage()+RESET);}
    }

    public void clickAndDeleteAll(WebElement element) {
        clickElementUntil(false, element);
        log.new Info("Deleting text");
        element.sendKeys(Keys.COMMAND + "a", Keys.DELETE);
    }

    public void clickAllElements(List<WebElement> elements) {
        for (WebElement element : elements) {
            clickElementUntil(false, element);
        }
    }

    public void clickAtAnOffset(WebElement element, int xOffset, int yOffset, boolean scroll){

        if (scroll) centerElement(element);

        Actions builder = new org.openqa.selenium.interactions.Actions(Driver.driver);
        builder
                .moveToElement(element, xOffset, yOffset)
                .click()
                .build()
                .perform();
    }

    public void dragDropToAction(WebElement element, WebElement destinationElement){

        centerElement(element);

        Actions action = new Actions(Driver.driver);
        action.moveToElement(element)
                .clickAndHold(element)
                .moveToElement(destinationElement)
                .release()
                .build()
                .perform();
        waitFor(0.3);
    }

    //This method performs click, hold, dragAndDropBy action on at a certain offset
    public void dragDropByAction(WebElement element, int xOffset, int yOffset){

        centerElement(element);

        Actions action = new Actions(Driver.driver);
        action.moveToElement(element)
                .clickAndHold(element)
                .dragAndDropBy(element, xOffset, yOffset)
                .build()
                .perform();
        waitFor(0.3);
    }

    //This method performs click, hold, drag and drop action on at a certain offset
    public void dragDropAction(WebElement element, int xOffset, int yOffset){

        centerElement(element);

        Actions action = new Actions(Driver.driver);
        action.moveToElement(element)
                .clickAndHold(element)
                .moveToElement(element,xOffset,yOffset)
                .release()
                .build()
                .perform();
        waitFor(0.3);
    }

    public void waitUntilDisplayed(WebElement element) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(Driver.driver);

        fluentWait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

        try {
            log.new Info("Waiting the element to be displayed");
            fluentWait.until(webDriver -> element.isDisplayed());
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred");

        }
    }

    public void waitUntilListToBeDisplayed(List<WebElement> elements) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(Driver.driver);

        fluentWait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

        try {
            log.new Info("Waiting the element list to be displayed");
            fluentWait.until(ExpectedConditions.visibilityOfAllElements(elements));
        } catch (StaleElementReferenceException e) {
            throw new RuntimeException("Exception occurred: ", e);
        }
    }

    public void waitUntilStable(WebElement element) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(Driver.driver);

        log.new Info("Waiting " + element.getText() + " to be stable");
        final long startTime = System.currentTimeMillis();

        fluentWait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

        fluentWait.until(ExpectedConditions.stalenessOf(element));

        long endTime = System.currentTimeMillis();
        int totalTime = (int) (endTime - startTime);
        log.new Info("Waited for " + totalTime + " second(s)");
    }

    public File getLastModified(String directoryFilePath) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        return chosenFile;
    }

    public void waitUntilDownloaded(String directory, String fileName) {
        long initialTime = System.currentTimeMillis();
        boolean timeOut;
        log.new Info("Waiting the files existence");

        do {
            timeOut = System.currentTimeMillis() - initialTime > 300000;
            waitFor(3);
        }
        while(!timeOut && !FileUtils.listFiles(new File(directory), new String[]{fileName}, false).isEmpty());
        if (timeOut) log.new Warning("Download time out!");
    }

    public void cleanDirectory(String directoryFilePath) {
        log.new Info("Deleting all items from the given directory");
        try {
            FileUtils.cleanDirectory(new File(directoryFilePath));
        }
        catch (IOException e) {e.getMessage();}
    }

    public void clickElementUntil(Boolean scroll, WebElement element) {
        final long startTime = System.currentTimeMillis();
        boolean isClicked = false;
        int attemptCounter = 0;
        int duration = 30000;

        if (scroll)
            centerElement(element);
        log.new Info("Clicking the element named: " + element.getText());
        while (System.currentTimeMillis() - startTime < duration) {
            try {
                element.click();
                isClicked = true;
                break;
            } catch (WebDriverException e) {
                attemptCounter++;
            }
        }
        if (!isClicked) {
            throw new RuntimeException("Could not click the element after " + attemptCounter + " attempts");
        }
    }

    public void moveToElement(WebElement target) {
        Actions actions = new Actions(Driver.driver);
        actions.moveToElement(target).perform();
    }

    public void moveToElementAndClick(WebElement element) {
        Actions actions = new Actions(Driver.driver);
        actions.moveToElement(element);
        actions.perform();
        clickElementUntil(false, element);
    }

    public void verifyImgStatus(WebElement imgElement) {
        try {
            RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
            HttpClientBuilder customizedClientBuilder = HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
            CloseableHttpClient client = customizedClientBuilder.build();
            HttpGet request = new HttpGet(imgElement.getAttribute("src"));
            HttpResponse response = client.execute(request);
            String imgLink = imgElement.getAttribute("src");
            log.new Info("Image Link: " + imgLink);
            log.new Info("Status: " + response.getStatusLine());
            // print name
            //WebElement followingSibling = imgElement.findElement(By.xpath("following-sibling::*"));
            //log.new Info("Image name: " + followingSibling.getText());

            if (response.getStatusLine().getStatusCode() < 200) counter++;
            else if (response.getStatusLine().getStatusCode() > 226) counter++;
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred\n" + e.getMessage());
        }
    }

    public void verifyLinkElements(List<WebElement> linkElements) {
        for (WebElement linkElement : linkElements) {
            if(linkElement.getAttribute("href") != null) {
                try {
                    String webLink = linkElement.getAttribute("href");
                    log.new Info("Link: " + webLink);

                    RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
                    HttpClientBuilder customizedClientBuilder = HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
                    CloseableHttpClient client = customizedClientBuilder.build();
                    HttpGet request = new HttpGet(webLink);
                    HttpResponse response = client.execute(request);
                    log.new Info("Status: " + response.getStatusLine());

                    if (response.getStatusLine().getStatusCode() >= 400) counter++;
                } catch (WebDriverException | IOException e) {
                    log.new Warning(e.getMessage());
                }
            }
        }
    }

    public void saveLinkInfoOnTextFile(String fileName, List<WebElement> linkElements) {
        for (WebElement linkElement : linkElements) {
            if(linkElement.getText().length() > 1 && linkElement.getAttribute("href") != null) {
                try {
                    String webLink = linkElement.getAttribute("href");
                    String elementText = linkElement.getText();
                    log.new Info("Element name: " + elementText);
                    log.new Info("Link: " + webLink);

                    RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
                    HttpClientBuilder customizedClientBuilder = HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
                    CloseableHttpClient client = customizedClientBuilder.build();
                    HttpGet request = new HttpGet(linkElement.getAttribute("href"));
                    HttpResponse response = client.execute(request);
                    log.new Success("Status: " + response.getStatusLine());

                    if (response.getStatusLine().getStatusCode() >= 400) counter++;

                    saveTextToTextFile(fileName, "Element name: " + elementText
                            + "\n" + "Status: " + response.getStatusLine()
                            + "\n" + "Links: " + webLink + "\n");

                } catch (WebDriverException | IOException e) {
                    log.new Warning(e.getMessage());
                }
            }
        }
    }

    public String turkishCharReplacer(String text) {
        return text.replace("ı", "i")
                .replace("ö", "o")
                .replace("ö", "o")
                .replace("ş", "s")
                .replace("ğ", "g")
                .replace("ç", "c")
                .replace("Ü", "U")
                .replace("İ", "I")
                .replace("Ö", "O")
                .replace("Ü", "U")
                .replace("Ş", "S")
                .replace("Ğ", "G")
                .replace("Ç", "C");
    }

    public void verifyTextOfListedElement(String labelText, List<WebElement> elements) {
        Assert.assertEquals("Text of the listed element could not be verified", labelText, getElementFromList(labelText, elements).getText());
    }

    public WebElement getElementFromList(String labelText, List<WebElement> elements) {
        log.new Info("Getting '" + labelText + "' from the given list");
        for (WebElement element : elements){
            if (element.getText().equalsIgnoreCase(labelText)) return element;}
        throw new RuntimeException("Element not found!!");
    }

    public WebElement getElementFromListUntil(String labelText, List<WebElement> elements) {
        final long startTime = System.currentTimeMillis();
        int duration = 30000;
        log.new Info("Getting '" + labelText + "' from the given list");
        while (System.currentTimeMillis() - startTime < duration){
            for (WebElement element : elements)
                if (element.getText().equalsIgnoreCase(labelText)){
                    return element;
                }
        }
        throw new RuntimeException("Element not found!!");
    }

    public WebElement getElementFromElements(List<WebElement> elements) {
        try {
            for (WebElement element : elements)
                return element;
        }
        catch (WebDriverException e){
            log.new Warning(e.getMessage());
        }
        return null;
    }

    public void saveLinksToTextFile(String fileName, List<WebElement> elements) {
        log.new Info("Saving links to a text file named: " + fileName);
        File f = new File(fileName + ".txt");
        if (f.exists()) f.delete();
        try {
            FileWriter fw = new FileWriter(f, true);
            for (WebElement element : elements) {
                String linkText = element.getAttribute("href");
                //write text to file
                fw.write(linkText + "\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.new Info("Error!" + e.getMessage());
        }
    }

    public List<String> readTextFromFile(String fileName) {
        List<String> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));

            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void saveTextToTextFile(String fileName, String text) {
        log.new Info("Saving text on a text file named: " + fileName);
        File f = new File("src/test/resources/results/" + fileName + ".txt");
        try {
            FileWriter fw = new FileWriter(f, true);
            fw.write(text + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.new Info("Error!" + e.getMessage());
        }
    }

}
