package optionselling;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OptionSellingStrategy {
	private static String baseUrl = "https://kite.zerodha.com/positions";
	public static WebDriver driver;
	
	private static String putSymbol = " APR 35200 PE";
	private static String callSymbol = "NIFTY 13th w APR 17500 CE";
	
	private static double putEntry = 5.5;
	private static double putExit = 7.3;
	
	private static double callEntry = 5.5;
	private static double callExit = 7.3;
	
	private static boolean isCallEntryDone = false;
	private static boolean isPutEntryDone = false;
	private static int callStopLossCounter = 0;
	private static int putStopLossCounter = 0;

	public static void main(String[] args) throws InterruptedException {
		String driverPath = "chromedriver-100";
		System.out.println("Starting");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		driver.findElement(By.id("userid")).sendKeys("YH3095");
		driver.findElement(By.id("password")).sendKeys("Home27653462!");
		driver.findElement(By.id("password")).submit();
		driver.findElement(By.id("pin")).sendKeys("010691");
		driver.findElement(By.id("pin")).submit();

		// enter to Watchlist 7
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/ul/li[7]")).click();

		addBothOptionsToWatchlist();
		Thread.sleep(5 * 1000);
		WebElement instruments = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[2]/div"));
		List<WebElement> symbols = instruments.findElements(By.className("info"));
		while (true) {
			for (WebElement script : symbols) {
				String symbolTitle = script.findElement(By.className("symbol")).getText();
				String priceStr = script.findElement(By.className("last-price")).getText();
				double price = parsePrice(priceStr);
				System.out.println("Symbol Title: " + symbolTitle);
				System.out.println("Price:: " + priceStr);

				// put logic
				if (putStopLossCounter < 3 && putSymbol.equalsIgnoreCase(symbolTitle)) {
					// if entry is not done then take entry on putEntry value or less than
					if (!isPutEntryDone && price <= putEntry) {
						System.out.println("PUT entry done");
						isPutEntryDone = true;
					}

					if (isPutEntryDone) {
						// then look if stop loss hit
						if (price >= putExit) {
							putStopLossCounter++;
							System.out.println("PUT Stop loss hit");
						}
					}
				}

				// call logic
				if (callStopLossCounter < 3 && callSymbol.equalsIgnoreCase(symbolTitle)) {
					if (!isCallEntryDone && price <= callEntry) {
						System.out.println("CALL entry done");
						isCallEntryDone = true;
					}

					if (isCallEntryDone) {
						if (price >= callExit) {
							callStopLossCounter++;
							System.out.println("CALL stop loss hit");
						}
					}
				}
			}
			Thread.sleep(5 * 1000);
		}

	}

	private static double parsePrice(String val) {
		double result = Double.MAX_VALUE;
		if (val != null && !val.isEmpty()) {
			try {
				result = Double.parseDouble(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private static void addBothOptionsToWatchlist() {
		// in search bar type script name
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div/div/input")).sendKeys(putSymbol);
		// click on search result bar to add script
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div[1]/ul/div/li/span[1]/span")).click();

		// in search bar type script name
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div/div/input")).sendKeys(callSymbol);
		// click on search result bar to add script
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div[1]/ul/div/li/span[1]/span")).click();

	}

}
