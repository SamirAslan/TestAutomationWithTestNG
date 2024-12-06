package utillity;

import org.openqa.selenium.JavascriptExecutor;
import java.util.List;
import static utillity.Driver.driver;

public class Methods {

    public void scrollUp() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
        Thread.sleep(2000);
    }

    public boolean containsProductName(List<String> list, String substring) {
        for (String item : list) {
            if (item.contains(substring)) {
                return true;
            }
        }
        return false;
    }

}