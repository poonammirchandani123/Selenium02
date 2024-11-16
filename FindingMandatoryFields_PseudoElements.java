import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class FindingMandatoryFields_PseudoElements {

    public static void main(String[] args) {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the OpenCart registration page
            driver.get("https://demo.opencart.com/index.php?route=account/register");
            driver.manage().window().maximize();

            // Find all the label elements within the registration form
            List<WebElement> elements = driver.findElements(By.xpath("//form[@id='form-register']//label"));

            // JavaScript Executor to check for pseudo-elements
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Loop through each label element to check if it's marked as mandatory
            for (WebElement element : elements) {
                // JavaScript to get the 'content' property of the ::before pseudo-element
                String script = "return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');";
                String content = (String) js.executeScript(script, element);

                // Debugging output (optional)
                System.out.println("Debug - Content from ::before: " + content);

                // Check if the content contains '*' indicating a mandatory field
                if (content != null && (content.contains("*") || content.contains("\\002a"))) {
                    // Print the label text followed by "Mandatory field"
                    System.out.println(element.getText() + " - Mandatory field");
                } else {
                    // Print the label text followed by "Not a mandatory field"
                    System.out.println(element.getText() + " - Not a mandatory field");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}

