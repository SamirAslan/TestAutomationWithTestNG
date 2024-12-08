package tests;

import api.ApiResponse;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonPage;
import pages.AmazonPageWebService;
import utillity.ConfigReader;
import utillity.Driver;
import utillity.Methods;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static utillity.Driver.driver;


public class AmazonTest {
    private static final Logger logger = LoggerFactory.getLogger(AmazonTest.class);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    AmazonPage amazonPage = new AmazonPage();
    Methods methods = new Methods();

    @Test
    public void TC_001() throws InterruptedException {

        logger.info("Starting TC_001...");
        logger.info("The test scenario will take approximately 7 minutes to complete.");
        driver.get(ConfigReader.getProperty("amazonUrl"));
        Assert.assertEquals(driver.getTitle(),ConfigReader.getProperty("homepageExpected"));
        logger.info("Homepage was visited.");

        amazonPage.searchBox.sendKeys(ConfigReader.getProperty("product") + Keys.ENTER);
        Assert.assertEquals(driver.getTitle(),"Amazon.com : "+ConfigReader.getProperty("product")+"");
        logger.info("{} search was made.", ConfigReader.getProperty("product"));

        List<WebElement> productList = amazonPage.products;
        List<String> productNamesAdded = new ArrayList<>();
        logger.info("{} products found in the list.", productList.size());

        int productCount = 0;

        logger.info("Searching for products without discount and with 'Add to Cart' option...");
        for (WebElement product : productList) {
            try {
                // Check the discount tag
                boolean isDiscounted = !product.findElements(By.cssSelector(amazonPage.discount())).isEmpty();
                // Check the "Add to Cart" button
                boolean hasAddToCartButton = !product.findElements(By.cssSelector(amazonPage.addToCartBtn())).isEmpty();
                // If there is no discount and there is an "Add to Cart" button, add to cart
                if (!isDiscounted && hasAddToCartButton) {
                    String productName = product.findElement(By.cssSelector(amazonPage.productName())).getText();
                    logger.info("Added Product: " + productName);
                    productNamesAdded.add(productName);
                    product.findElement(By.cssSelector(amazonPage.addToCartBtn())).click();
                    productCount++;
                    logger.info("Product added to cart!");
                    logger.info("********************************************************************************");
                }
            } catch (Exception e) {
                logger.error("Error while processing the product: " + e.getMessage());
            }
        }
        logger.info("Total number of items added to cart: " + productCount);
        methods.scrollUp();
        Thread.sleep(1000);
        amazonPage.cart.click();

        for (WebElement productNameText : amazonPage.productNamesInCart){
            String productNameInCart = productNameText.getText().replace("…","");
            logger.info("Product In Cart: " + productNameInCart);

            if (methods.containsProductName(productNamesAdded, productNameInCart)){
                Assert.assertTrue(true);
                logger.info("The items in the cart have been confirmed.");
            }else {
                Assert.fail("There is no product in the cart or its name is incorrect...");
            }
        }
        Driver.closeDriver();
        logger.info("Finished TC_001.");
    }

    @Test
    public void TC_002() throws InterruptedException, IOException {

        logger.info("Starting TC_002...");
        logger.info("The test scenario will take approximately 11 minutes to complete.");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        BufferedWriter writer = new BufferedWriter(new FileWriter(timestamp + "_results.txt"));

        Set<String> uniqueEntries = new HashSet<>();


        driver.get(ConfigReader.getProperty("amazonUrl"));
        WebElement menu = amazonPage.menu;
        menu.click();

        AmazonPageWebService pageResponse = new AmazonPageWebService();

        for (int i = 5; i < 27; i++) {
            List<WebElement> links = driver.findElements(By.xpath("//ul[@data-menu-id='" + i + "']/li/a"));
            for (WebElement link : links) {
                String url = link.getAttribute("href");
                if (url != null && !url.isEmpty() && !url.equals("https://www.amazon.com/")) {
                    AmazonPageWebService.PageResponse response = pageResponse.getPageResponse(url);
                    String status = response.getStatus();
                    String pageTitle = response.getPageTitle();
                    String expectedLine = url + " - " + pageTitle + " - " + status;

                    if (uniqueEntries.add(expectedLine)) {
                        System.out.println(expectedLine);
                        writer.write(expectedLine);
                        writer.newLine();
                    }
                }
            }
        }
        writer.close();
        Driver.closeDriver();
        logger.info("Finished TC_002.");
    }

    @Test
    public void TC_003(){
        logger.info("Starting TC_003...");

        Response response = given()
                .baseUri(ConfigReader.getProperty("apiEndpoint"))
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<ApiResponse> apiResponse = response.jsonPath().getList("", ApiResponse.class);
        logger.info(apiResponse.toString()); //Take a commend if you want

        Map<Integer, Long> userPostCounts = apiResponse.stream()
                .collect(Collectors.groupingBy(ApiResponse::getUserId, Collectors.counting()));

        userPostCounts.forEach((userId, count) -> System.out.println("(" + userId + ", " + count + ")"));

        logger.info("Finished TC_003...");
    }





}
