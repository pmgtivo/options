package com.gopal.prashi.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ShortStrangeleAutomation {
	final static Logger log = Logger.getLogger(ShortStrangeleAutomation.class);

	private static String baseUrl = "https://kite.zerodha.com/positions";
	private static String chromeDriverPath = "chromedriver-103";
	public static WebDriver driver;
	private static double entryPoint = 20.0;
	public static int year = 22;
	public static int month = 7;
	public static String expiryDate = "07";
	public static int bankNiftyPrice = 0;
	private static HashMap<String, Double> entryMap = new HashMap<>();

	public static String xPathForWatchlistTab = "/html/body/div[1]/div[2]/div[1]/div/div[2]/div";
	public static String xPathForWatchlist7Link = "/html/body/div[1]/div[2]/div[1]/div/ul/li[7]";

	public static void main(String[] args) {
		System.out.println("Starting Option selling");
		driver = getChromeDriver();
		login();
		sleep2Seconds();

		takeStrikePositions();
		sleep2Seconds();

		createStopLoss();

		monitorPositions();

	}

	private static void takeStrikePositions() {
		clearAllWatchlistTab();
		getBankNiftyPrice();

		HashSet<String> ceStrikes = generateOptionsStrikes("CE");
		HashSet<String> peStrikes = generateOptionsStrikes("PE");
		HashMap<String, Double> ceShortListstrikes = shortListStrikes(ceStrikes);
		clearAllWatchlistTab();
		HashMap<String, Double> peShortListstrikes = shortListStrikes(peStrikes);
		clearAllWatchlistTab();

		entryStrikesStrategy(ceShortListstrikes);
		entryStrikesStrategy(peShortListstrikes);

		createBucketAndTakePositions();
	}

	private static void createBucketAndTakePositions() {
		// TODO Auto-generated method stub
	}

	private static void createStopLoss() {
		// TODO Auto-generated method stub

	}

	private static void monitorPositions() {
		WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
		List<WebElement> symbols = instruments.findElements(By.className("info"));
		while (true) {
			for (WebElement script : symbols) {
				String symbolTitle = script.findElement(By.className("symbol")).getText();
				String priceStr = script.findElement(By.className("last-price")).getText();
				double price = parsePrice(priceStr);
				System.out.println("values from watchList symbol:: " + symbolTitle + "lastPrice:: " + price);
			}
			sleepSeconds(60);
		}
	}

	private static void entryStrikesStrategy(HashMap<String, Double> strikes) {
		Double min = Double.MAX_VALUE;
		String minStrike = null;

		for (Entry<String, Double> entryStrike : strikes.entrySet()) {
			String strike = entryStrike.getKey();
			double price = entryStrike.getValue();
			double diff = Math.abs(price - entryPoint);

			if (min > diff) {
				minStrike = strike;
				min = diff;
			}
		}

		if (Strings.isNotEmpty(minStrike)) {
			entryMap.put(minStrike, min);
		}

		log.info("ENTRY STRIKES");
		log.info("Strike :: " + minStrike);
		log.info("");

	}

	private static void login() {
		Properties cred = loadCredentials();
		driver.get(baseUrl);
		driver.findElement(By.id("userid")).sendKeys((String) cred.getOrDefault("username", "default"));
		driver.findElement(By.id("password")).sendKeys((String) cred.getOrDefault("password", "default"));
		driver.findElement(By.id("password")).submit();
		sleep2Seconds();
		driver.findElement(By.id("pin")).sendKeys((String) cred.getOrDefault("pin", "000000"));
		driver.findElement(By.id("pin")).submit();
	}

	private static Properties loadCredentials() {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("credentials.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	private static WebDriver getChromeDriver() {
		System.out.println("Initialising chrome driver");
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	private static void getBankNiftyPrice() {
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div/div/input")).sendKeys("nifty bank");
		sleep2Seconds();
		// click on search result bar to add script
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div[1]/ul/div/li/span[1]/span")).click();
		sleep2Seconds();

		WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
		List<WebElement> symbols = instruments.findElements(By.className("info"));

		if (symbols != null && symbols.size() >= 1) {
			WebElement lastAddedScript = symbols.get(symbols.size() - 1);
			String symbolTitle = lastAddedScript.findElement(By.className("symbol")).getText();
			String priceStr = lastAddedScript.findElement(By.className("last-price")).getText();
			double price = parsePrice(priceStr);
			bankNiftyPrice = (int) (Math.round(price / 100.0) * 100);
			System.out.println(symbolTitle + " has assigned to Bank nifty with value - " + bankNiftyPrice);
		}
	}

	private static void clearAllWatchlistTab() {
		WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
		List<WebElement> symbols = instruments.findElements(By.className("info"));

		for (WebElement script : symbols) {
			WebElement parent = script.findElement(By.xpath("./.."));
			Actions actions = new Actions(driver);
			actions.moveToElement(script).build().perform();
			sleep2Seconds();
			parent.findElement(By.className("icon-trash")).click();
		}
	}

	private static void clearWatchListTab(WebElement script) {
		WebElement parent = script.findElement(By.xpath("./.."));
		Actions actions = new Actions(driver);
		actions.moveToElement(script).build().perform();
		sleep2Seconds();
		parent.findElement(By.className("icon-trash")).click();
	}

	private static HashMap<String, Double> shortListStrikes(HashSet<String> options) {
		HashMap<String, Double> strikes = new HashMap<>();
		// enter to Watchlist 7
		driver.findElement(By.xpath(xPathForWatchlist7Link)).click();
		sleep2Seconds();

		for (String strikeName : options) {
			// in search bar type script name
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div/div/input"))
					.sendKeys(strikeName);
			sleep2Seconds();

			// IF search couldn't find any strikes for search key then report and continue
			try {
				// click on search result bar to add script
				driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div[1]/ul/div/li/span[1]/span"))
						.click();
			} catch (Exception e) {
				System.err.println("SHORT LIST STRIKES WENT WRONG - STRIKE " + strikeName);
				System.out.println();
				e.printStackTrace();
				continue;
			}

			sleep2Seconds();

			WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
			List<WebElement> symbols = instruments.findElements(By.className("info"));

			if (symbols != null && symbols.size() >= 1) {
				WebElement lastAddedScript = symbols.get(symbols.size() - 1);

				String symbolTitle = lastAddedScript.findElement(By.className("symbol")).getText();
				String priceStr = lastAddedScript.findElement(By.className("last-price")).getText();
				double price = parsePrice(priceStr);
				if (price <= 25 && price >= 12) {
					strikes.put(symbolTitle, price);
				}
				clearWatchListTab(lastAddedScript);
				System.out.println("Last added script symbol:: " + symbolTitle + "lastPrice:: " + price);
			} else {
				System.err.println("Couldn't add this script to watchlist " + strikeName);
			}

		}

		return strikes;
	}

	private static HashSet<String> generateOptionsStrikes(String optionType) {
		HashSet<String> result = new HashSet<>();
		int initialPrice = bankNiftyPrice - 3000;
		int maxPrice = bankNiftyPrice + 3000;

		while (initialPrice <= maxPrice) {
			String strike = "BANKNIFTY" + year + month + expiryDate + initialPrice + optionType;

			result.add(strike);
			initialPrice = initialPrice + 100;
		}
		result.forEach(System.out::println);
		return result;

	}

	public static double parsePrice(String val) {

		if (val == null || val.isBlank()) {
			return Double.MAX_VALUE;
		}
		String a = val;
		String s = a.replaceAll(",", "").trim();
		String f = s.replaceAll(" ", "");
		double result = Double.MAX_VALUE;
		try {
			result = Double.parseDouble(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void sleep2Seconds() {
		sleepSeconds(2);
	}

	public static void sleep1Seconds() {
		sleepSeconds(1);
	}

	public static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
