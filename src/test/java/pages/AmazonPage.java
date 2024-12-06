package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utillity.Driver;

import java.util.List;

public class AmazonPage {

    public AmazonPage()  {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(id = "nav-cart-count-container")
    public WebElement cart;

    @FindBy(css = "div[data-component-type='s-search-result']")
    public List<WebElement> products;

    @FindBy(xpath = "//span[@class='a-truncate-cut']")
    public List<WebElement> productNamesInCart;

    @FindBy(id = "nav-hamburger-menu")
    public WebElement menu;

    public String discount(){
        return "span[class='a-price a-text-price']";
    }

    public String addToCartBtn(){
        return "button[class='a-button-text']";
    }

    public String productName(){
        return "span[class=\"a-size-medium a-color-base a-text-normal\"]";
    }
}
