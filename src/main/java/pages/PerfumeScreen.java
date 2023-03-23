package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Utils;

import java.util.List;

public class PerfumeScreen extends Utils {

    @FindBy(id = "trendyol.com:id/textview_title_product")
    public List<WebElement> products;

    public WebElement getProduct(String productName) {
        for (WebElement product : products) {
            System.out.println(product.getText());
            log.new Info("Getting product from the given product list");
            if (product.getText().equalsIgnoreCase(productName))
                return product;
        }
        return null;
    }

}
