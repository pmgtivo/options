package optionselling;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OptionIronCondorStrategy {
	private static String positionsUrl = "https://kite.zerodha.com/positions";
	public static WebDriver driver;

	// 1 minute if it continues near to SL then trigger exit
	private static int stopLossLimit = 4;

	private static boolean is130StopLossResetDone = false;
	private static boolean is230StopLossResetDone = false;
	private static boolean isExitDone = false;

	private static HashMap<String, Integer> stopLossCounterMap = new HashMap<>();
	private static HashMap<String, Float> stopLossMap = new HashMap<>();

	public static void main(String[] args) throws InterruptedException {
		String driverPath = "chromedriver-100";
//		String driverPath = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(positionsUrl);
		driver.findElement(By.id("userid")).sendKeys("YH3095");
		driver.findElement(By.id("password")).sendKeys("Home27653462!");
		driver.findElement(By.id("password")).submit();
		driver.findElement(By.id("pin")).sendKeys("010691");
		driver.findElement(By.id("pin")).submit();

		List<WebElement> positionsRows = driver
				.findElements(By.xpath("//*[@id=\"app\"]/div[2]/div[2]/div/div/section[1]/div/div/table/tbody/tr"));
		while (true) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("");
			System.out.println("----------------------------------------------------------------");
			String currentUrl = driver.getCurrentUrl();
			if (!positionsUrl.equalsIgnoreCase(currentUrl)) {
				driver.get(positionsUrl);
			}
			// look for time logic
			int currentHour = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault()).getHour();
			int currentMinute = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault()).getMinute();

			if (currentHour == 1 && currentMinute == 30 && !is130StopLossResetDone) {
				stopLossMap.clear();
				is130StopLossResetDone = true;
			}
			if (currentHour == 2 && currentMinute == 30 && !is230StopLossResetDone) {
				stopLossMap.clear();
				is230StopLossResetDone = true;
			}

			if (currentHour == 3 && currentMinute == 15 && !isExitDone) {
				isExitDone = true;
				// TODO exit from all positions
			}
//
//			System.out.println(
//					"Get Hours  " + LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault()).getHour());
//			System.out.println(
//					"Get Minute " + LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault()).getMinute());

			for (WebElement pos : positionsRows) {
				WebElement tradingsymbolWE = pos.findElement(By.className("tradingsymbol"));
				WebElement lastPriceWE = pos.findElement(By.className("last-price"));
				WebElement quantityWE = pos.findElement(By.className("quantity"));

				String tradingSymbol = tradingsymbolWE.getText();
				float lastPrice = parseString(lastPriceWE.getText());
				int qty = (int) parseString(quantityWE.getText());

				// negative qty means sell scripts
				if (qty < 0) {
					if (!stopLossMap.containsKey(tradingSymbol)) {
						System.err.println("Stop Loss not set for Symbol :: " + tradingSymbol);
						setStopLoss(tradingSymbol, lastPrice);
					}
					float stopLoss = stopLossMap.get(tradingSymbol);
					if (lastPrice >= stopLoss) {
						int initSL = stopLossCounterMap.getOrDefault(tradingSymbol, 0);
						if (initSL > stopLossLimit) {
							// initialise stop loss order
							// TODO Exit from this script.. if iron condor then BUY order also
						} else {
							stopLossCounterMap.put(tradingSymbol, initSL + 1);
						}
					} else {
						if (stopLossCounterMap.containsKey(tradingSymbol)) {
							stopLossCounterMap.remove(tradingSymbol);
						}
					}
				}
				System.out.println(tradingSymbol);
				System.out.println(lastPrice);
				System.out.println(qty);
			}
			sleepSeconds(20);
		}

	}

	private static void setStopLoss(String tradingSymbol, float lastPrice) {
		float stopLoss = (float) (lastPrice * 2.5);
		stopLossMap.put(tradingSymbol, stopLoss);
		System.out.println("Stop loss set for symbol :: " + tradingSymbol + " stop loss :: " + stopLoss);
	}

	private static float parseString(String val) {
		float result = Float.MAX_VALUE;
		if (val != null && !val.isEmpty()) {
			try {
				result = Float.parseFloat(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
