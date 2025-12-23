import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class DOMInvestigator {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        try {
            System.out.println("=== LinkedIn Puzzle DOM Investigation ===\n");
            
            driver.get("https://www.linkedin.com/games/tango");
            System.out.println("Navigated to puzzle page. Waiting 5 seconds for load...");
            Thread.sleep(5000);
            
            // Get page source
            String pageSource = driver.getPageSource();
            System.out.println("\n=== PAGE SOURCE (first 500 chars) ===");
            System.out.println(pageSource.substring(0, Math.min(500, pageSource.length())) + "...\n");
            
            // Try to find puzzle containers
            System.out.println("=== SEARCHING FOR PUZZLE TANGO GAME CONTAINER ===");
            List<WebElement> possibleContainers = driver.findElements(
                By.cssSelector("div[class*='puzzle'], div[class*='board'], div[class*='game'], div[class*='grid']")
            );
			// The above cssSelector needs to be updated based on actual class names used in the LinkedIn Tango game
            
            System.out.println("Found " + possibleContainers.size() + " potential containers\n");
            
            for (int i = 0; i < Math.min(possibleContainers.size(), 5); i++) {
                WebElement container = possibleContainers.get(i);
                System.out.println("Container " + i + ":");
                System.out.println("  Tag: " + container.getTagName());
                System.out.println("  Class: " + container.getAttribute("class"));
                System.out.println("  ID: " + container.getAttribute("id"));
                
                String innerHTML = container.getAttribute("innerHTML");
                if (innerHTML != null && innerHTML.length() > 0) {
                    System.out.println("  InnerHTML (first 200 chars): " 
                        + innerHTML.substring(0, Math.min(200, innerHTML.length())) + "...");
                }
                System.out.println();
            }
            
            // Look for cells
            System.out.println("=== SEARCHING FOR CELLS ===");
            List<WebElement> cells = driver.findElements(
                By.cssSelector("[class*='cell'], [data-row], td, button[class*='cell']")
            );
            System.out.println("Found " + cells.size() + " potential cells\n");
            
            if (cells.size() > 0) {
                System.out.println("First few cells:");
                for (int i = 0; i < Math.min(cells.size(), 3); i++) {
                    WebElement cell = cells.get(i);
                    System.out.println("Cell " + i + ":");
                    System.out.println("  Tag: " + cell.getTagName());
                    System.out.println("  Class: " + cell.getAttribute("class"));
                    System.out.println("  data-row: " + cell.getAttribute("data-row"));
                    System.out.println("  data-col: " + cell.getAttribute("data-col"));
                    System.out.println("  Text: " + cell.getText());
                    System.out.println();
                }
            }
            
            // Check for JavaScript game state
            System.out.println("=== CHECKING JAVASCRIPT STATE ===");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            String[] possibleVars = {
                "window.gameState", 
                "window.puzzle", 
                "window.__INITIAL_STATE__",
                "window.appState"
            };
            
            for (String varName : possibleVars) {
                try {
                    Object state = js.executeScript("return " + varName + ";");
                    if (state != null) {
                        System.out.println(varName + " = " + state);
                    }
                } catch (Exception e) {
                    // Variable doesn't exist
                }
            }
            
            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("investigation.png"));
            System.out.println("\n✓ Screenshot saved to: investigation.png");
            
            System.out.println("\n=== Investigation complete! ===");
            System.out.println("The browser will stay open so you can manually inspect.");
            System.out.println("Press F12 in the browser to open DevTools and explore.");
            System.out.println("Press Enter in this terminal when done to close browser...");
            System.in.read();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}