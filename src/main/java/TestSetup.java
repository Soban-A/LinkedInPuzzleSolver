import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSetup {
    public static void main(String[] args) {
        System.out.println("=== Starting Test ===");
        
        try {
            System.out.println("Step 1: Setting up ChromeDriver...");
            WebDriverManager.chromedriver().setup();
            System.out.println("✓ ChromeDriver setup complete");
            
            System.out.println("Step 2: Creating Chrome browser instance...");
            WebDriver driver = new ChromeDriver();
            System.out.println("✓ Chrome opened");
            
            System.out.println("Step 3: Navigating to LinkedIn Tango...");
            driver.get("https://www.linkedin.com/games/tango");
			Thread.sleep(5000);
			Thread.sleep(2000);
			System.out.println("TESTING FOR FINDING ELEMENT");
			WebElement startButton = driver.findElement(By.id("launch"));
startButton.click();
            System.out.println("✓ Navigation complete");
			
			System.out.println("CLICKING START");
			// startButton.get(0).click();
            System.out.println("Step 5: Closing browser...");
            driver.quit();
            System.out.println("✓ Browser closed");
            
            System.out.println("\n✓✓✓ SETUP SUCCESSFUL! ✓✓✓");
            
        } catch (Exception e) {
            System.err.println("\n✗✗✗ ERROR OCCURRED ✗✗✗");
            System.err.println("Error type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            System.err.println("\nFull stack trace:");
            e.printStackTrace();
        }
    }
}