import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.util.List;

public class DOMInvestigator
{
    public static void main(String[] args)
	{
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        try
		{
			System.out.println("Step 1: Navigating to LinkedIn Tango puzzle page...");
			driver.get("https://www.linkedin.com/games/tango");

            System.out.println("Navigated to puzzle page. Waiting 5 seconds for load...");
            Thread.sleep(5000);
            
            // Get page source
            String pageSource = driver.getPageSource();
            System.out.println("\n=== PAGE SOURCE (first 500 chars) ===");
            System.out.println(pageSource.substring(0, Math.min(500, pageSource.length())) + "...\n");
            
			// Find the iframe
            System.out.println("\nStep 2: Looking for iframe...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("Found " + iframes.size() + " iframe(s)");
			
            if (iframes.isEmpty())
			{
                System.out.println("✗ No iframe found!");
                return;
            }
            
            // Switch to the iframe
            System.out.println("\nStep 3: Switching to iframe...");

            WebElement iframe = iframes.get(0);
            
            driver.switchTo().frame(iframe);
            System.out.println("✓ Switched to iframe!");
            
            // Wait a moment for iframe content to load
            Thread.sleep(2000);
            
            // Now search for the button INSIDE the iframe
            System.out.println("\nStep 4: Searching for button inside iframe...");
			
            // Try to find puzzle containers
            System.out.println("=== SEARCHING FOR PUZZLE TANGO GAME CONTAINER ===");
			
			WebElement startButton = driver.findElement(By.id("launch-footer-start-button"));
			startButton.click();
            
        }
		catch (Exception e)
		{
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } 
		finally {
            driver.quit();
        }
    }
}