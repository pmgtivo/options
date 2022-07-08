package com.gopal.prashi.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ShortStrangeleAutomation {
	final static Logger log = Logger.getLogger(ShortStrangeleAutomation.class);

	private static String positionUrl = "https://kite.zerodha.com/positions";
	private static String basketUrl = "https://kite.zerodha.com/orders/baskets";
	private static String chromeDriverPath = "chromedriver-103";
	private static String positionSize = "25";
	public static WebDriver driver;
	private static double entryPoint = 20.0;
	private static double stopLossPercentage = 2.5;
	public static int year = 22;
	public static int month = 7;
	public static String expiryDate = "14";
	public static int bankNiftyPrice = 0;
	private static HashMap<String, Double> entryMap = new HashMap<>();
	private static HashMap<String, Integer> stopLossMap = new HashMap<>();

	public static String xPathForWatchlistTab = "/html/body/div[1]/div[2]/div[1]/div/div[2]/div";
	public static String xPathForWatchlist7Link = "/html/body/div[1]/div[2]/div[1]/div/ul/li[7]";

	static {
		driver = getChromeDriver();

//		entryMap.put("banknifty2270734000PE", 18.7);
//		entryMap.put("banknifty2270735100CE", 17.2);
	}

	public static void main(String[] args) {
		log.info("Starting Option selling");

		takeEntryController();

		boolean isLoggedIn = isLoggedIn();
		log.info("LoggedIn :: " + isLoggedIn);

//		monitorPositions();

//		sleepSeconds(120);
//		driver.close();
		log.info("closing application");

	}

	private static void takeEntryController() {
		log.info("Starting to take Entry at time: " + LocalDateTime.now());
		login();
		sleep1Seconds();

		takeStrikePositions();
		log.info("Finished Entry at time: " + LocalDateTime.now());
		log.info("Initiated StopLoss at time: " + LocalDateTime.now());
//		createBucketAndTakePositions();
//		sleep1 Seconds();

		clearAllWatchlistTab();
		addStopLoss();
		log.info("Finished StopLoss at time: " + LocalDateTime.now());
	}

	private static void takeStrikePositions() {
		log.info("taking positions");
		// enter to Watchlist 7
		driver.findElement(By.xpath(xPathForWatchlist7Link)).click();

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
		log.info("done taking positions");
	}

	private static void createBucketAndTakePositions() {
		driver.get(basketUrl);
		String xpathNewBucket = "//*[@id=\"app\"]/div[2]/div[2]/div[2]/div/section/div/div/span[1]/button";
		driver.findElement(By.xpath(xpathNewBucket)).click();

		String xpathBucketCreateName = "//*[@id=\"app\"]/div[3]/div/div/div/div[2]/div/form/div[1]/input";
		String xpathBucketCreateButton = "//*[@id=\"app\"]/div[3]/div/div/div/div[2]/div/form/div[2]/button";
		driver.findElement(By.xpath(xpathBucketCreateName)).sendKeys(getCurrentDate());
		driver.findElement(By.xpath(xpathBucketCreateButton)).click();

		String xpathSearchInput = "//*[@id=\"app\"]/div[3]/div/div/div/div[2]/div/div[1]/div/div/div/input";
		String xpathSearchResultFirstEl = "//*[@id=\"app\"]/div[3]/div/div/div/div[2]/div/div[1]/div/ul/div/li[1]/span[1]";

		for (String strike : entryMap.keySet()) {
			log.info("Taking positions in - " + strike);
			driver.findElement(By.xpath(xpathSearchInput)).sendKeys(strike);
			driver.findElement(By.xpath(xpathSearchResultFirstEl)).click();
			sleep1Seconds();
			String xpathBuySellModelTogelInput = "/html/body/div[1]/form/header/div[1]/div[2]/div/div[2]/span/div/input";
			String xpathBuySellModelTogelLabel = "/html/body/div[1]/form/header/div[1]/div[2]/div/div[2]/span/div/label";
			WebElement buySellModelTogel = driver.findElement(By.xpath(xpathBuySellModelTogelLabel));
			String buySellTogelVal = driver.findElement(By.xpath(xpathBuySellModelTogelInput)).getAttribute("value");
			sleep1Seconds();

			if ("BUY".equalsIgnoreCase(buySellTogelVal)) {
				buySellModelTogel.click();
			}

			String xpathBuySellModelQtyInput = "//*[@id=\"app\"]/form/section/div/div[2]/div[2]/div[1]/div[1]/div/input";
			driver.findElement(By.xpath(xpathBuySellModelQtyInput)).sendKeys(positionSize);

			String xpathPlaceMarketValue = "/html/body/div[1]/form/section/div/div[2]/div[2]/div[2]/div[2]/div/div[1]/label";
			driver.findElement(By.xpath(xpathPlaceMarketValue)).click();

			sleepSeconds(10);
			String xpathSubmitButton = "/html/body/div[1]/form/section/div/footer/div[2]/button[1]";
			driver.findElement(By.xpath(xpathSubmitButton)).click();

		}

		String xpathExecuteBucket = "/html/body/div[1]/div[3]/div/div/div/div[3]/div/div/div[2]/button[1]";
		String xpathCloseBucket = "/html/body/div[1]/div[3]/div/div/div/div[3]/div/div/div[2]/button[2]";
		driver.findElement(By.xpath(xpathCloseBucket)).click();

	}

	private static void addStopLoss() {
		log.info("Creating stop loss");
		// enter to Watchlist 7
		driver.findElement(By.xpath(xPathForWatchlist7Link)).click();
		calculateStopLoss();
		sleep1Seconds();

		for (String strike : entryMap.keySet()) {
			addToWatchlist(strike);
			sleep1Seconds();

			WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
			List<WebElement> symbols = instruments.findElements(By.className("info"));

			if (symbols != null && symbols.size() >= 1) {
				WebElement lastAddedScript = symbols.get(symbols.size() - 1);
				String symbolTitle = lastAddedScript.findElement(By.className("symbol")).getText();

				actionOnWatchList(lastAddedScript, "buy");

				String xpathTradeMISLabel = "/html/body/div[1]/form/section/div/div[2]/div[1]/div/div[1]/label";
				String xpathTradeMISInput = "/html/body/div[1]/form/section/div/div[2]/div[1]/div/div[1]/input";
				driver.findElement(By.xpath(xpathTradeMISLabel)).click();
				String tradeTypeval = driver.findElement(By.xpath(xpathTradeMISInput)).getAttribute("value");
				if (!"MIS".equalsIgnoreCase(tradeTypeval)) {
					throw new RuntimeException("Trade type is Inalid while adding stop loss");
				}
				String xpathTradeQty = "/html/body/div[1]/form/section/div/div[2]/div[2]/div[1]/div[1]/div/input";
				driver.findElement(By.xpath(xpathTradeQty)).sendKeys(positionSize);

				if (!stopLossMap.containsKey(symbolTitle) || stopLossMap.get(symbolTitle) == null) {
					throw new RuntimeException("NO STOP LOSS FOUND while adding stop loss - strike : " + symbolTitle);
				}
				Integer stopLossPrice = stopLossMap.get(symbolTitle);

				String xpathTradeSLLabel = "/html/body/div[1]/form/section/div/div[2]/div[2]/div[2]/div[3]/div/div[1]/label";
				String xpathTradeSLInput = "/html/body/div[1]/form/section/div/div[2]/div[2]/div[2]/div[3]/div/div[1]/input";
				driver.findElement(By.xpath(xpathTradeSLLabel)).click();
				String tradeSLval = driver.findElement(By.xpath(xpathTradeSLInput)).getAttribute("value");
				if (!"SL".equalsIgnoreCase(tradeSLval)) {
					throw new RuntimeException("Trade SL is Inalid while adding stop loss");
				}

				String xpathPrice = "/html/body/div[1]/form/section/div/div[2]/div[2]/div[1]/div[2]/div/input";
				String xpathTriggerPrice = "/html/body/div[1]/form/section/div/div[2]/div[2]/div[1]/div[3]/div/input";
				driver.findElement(By.xpath(xpathPrice)).clear();
				driver.findElement(By.xpath(xpathTriggerPrice)).clear();
				driver.findElement(By.xpath(xpathPrice)).sendKeys(String.valueOf(stopLossPrice));
				driver.findElement(By.xpath(xpathTriggerPrice)).sendKeys(String.valueOf(stopLossPrice));

				String xpathSubmitButton = "/html/body/div[1]/form/section/div/footer/div[2]/button[1]";
				String xpathCancelButton = "/html/body/div[1]/form/section/div/footer/div[2]/button[2]";
				sleepSeconds(30);

				driver.findElement(By.xpath(xpathCancelButton)).click();
			}
		}

		log.info("DONE - Creating stop loss");
	}

	private static void calculateStopLoss() {
		driver.get(positionUrl);
		String xpathPositionTableBody = "/html/body/div[1]/div[2]/div[2]/div/div/section[1]/div/div/table/tbody";
		WebElement tableBodyEle = driver.findElement(By.xpath(xpathPositionTableBody));
		List<WebElement> rowVals = tableBodyEle.findElements(By.tagName("tr"));

		for (WebElement row : rowVals) {
			String instrument = row.findElement(By.className("tradingsymbol")).getText();
			String averagePrice = row.findElement(By.className("average-price")).getText();
			int stopLoss = (int) Math.round(parsePrice(averagePrice) * stopLossPercentage);
			log.info("Stop loss calculated for instrument: " + instrument + " AveragePrice: " + averagePrice
					+ " StopLoss: " + stopLoss);
			stopLossMap.put(instrument, stopLoss);
		}

		stopLossMap.keySet().stream().forEach(val -> log.info("StopLoss key : " + val));
	}

	private static void actionOnWatchList(WebElement lastAddedScript, String action) {
		try {
			WebElement parent = lastAddedScript.findElement(By.xpath("./.."));
			Actions actions = new Actions(driver);
			actions.moveToElement(lastAddedScript).build().perform();
			sleep1Seconds();
			parent.findElement(By.className(action)).click();
		} catch (NoSuchElementException e) {
			log.error(e);
			actionOnWatchList(lastAddedScript, action);
		}
	}

	private static void monitorPositions() {
		driver.get(positionUrl);
		String xpathPositionTableBody = "/html/body/div[1]/div[2]/div[2]/div/div/section[1]/div/div/table/tbody";
		WebElement tableBodyEle = driver.findElement(By.xpath(xpathPositionTableBody));
		List<WebElement> rowVals = tableBodyEle.findElements(By.tagName("tr"));

		for (WebElement row : rowVals) {
			String instrument = row.findElement(By.className("instrument")).getText();
			String averagePrice = row.findElement(By.className("average-price")).getText();

		}

		String xpathTotalProfitLoss = "/html/body/div[1]/div[2]/div[2]/div/div/section[1]/div/div/table/tfoot/tr/td[4]";

		WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
		List<WebElement> symbols = instruments.findElements(By.className("info"));
		while (true) {
			for (WebElement script : symbols) {
				String symbolTitle = script.findElement(By.className("symbol")).getText();
				String priceStr = script.findElement(By.className("last-price")).getText();
				double price = parsePrice(priceStr);
				log.info("values from watchList symbol:: " + symbolTitle + "lastPrice:: " + price);
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
		log.info("logging in");
		Properties cred = loadCredentials();
		driver.get(positionUrl);
		sleep1Seconds();
		driver.findElement(By.id("userid")).sendKeys((String) cred.getOrDefault("username", "default"));
		driver.findElement(By.id("password")).sendKeys((String) cred.getOrDefault("password", "default"));
		driver.findElement(By.id("password")).submit();
		sleep1Seconds();
		driver.findElement(By.id("pin")).sendKeys((String) cred.getOrDefault("pin", "000000"));
		driver.findElement(By.id("pin")).submit();
		log.info("logging in done");
		sleep1Seconds();
		String currentUrl = driver.getCurrentUrl();
		if (positionUrl.equals(currentUrl)) {
			log.info("Logging in is successful");
		} else {
			throw new RuntimeException("Logging in failed");
		}
	}

	public static boolean isLoggedIn() {
		String xpathUserId = "//*[@id=\"app\"]/div[1]/div/div[2]/div[2]/div/a/span";
		boolean result = false;
		try {
			String userName = driver.findElement(By.xpath(xpathUserId)).getText();
			Properties cred = loadCredentials();
			String userId = (String) cred.getProperty("username");

			if (userName != null && userName.equalsIgnoreCase(userId)) {
				result = true;
			}
		} catch (NoSuchElementException e) {
			log.error("Logging failed : " + e);
		}
		return result;
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
		log.info("Initialising chrome driver");
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	private static void getBankNiftyPrice() {
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div/div/input")).sendKeys("nifty bank");
		sleep1Seconds();
		// click on search result bar to add script
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div[1]/ul/div/li/span[1]/span")).click();
		sleep1Seconds();

		WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
		List<WebElement> symbols = instruments.findElements(By.className("info"));

		if (symbols != null && symbols.size() >= 1) {
			WebElement lastAddedScript = symbols.get(symbols.size() - 1);
			String symbolTitle = lastAddedScript.findElement(By.className("symbol")).getText();
			String priceStr = lastAddedScript.findElement(By.className("last-price")).getText();
			double price = parsePrice(priceStr);
			bankNiftyPrice = (int) (Math.round(price / 100.0) * 100);
			log.info(symbolTitle + " has assigned to Bank nifty with value - " + bankNiftyPrice);
		}
	}

	private static void clearAllWatchlistTab() {
		log.info("Clearing all watch list tab scripts");
		// enter to Watchlist 7
		driver.findElement(By.xpath(xPathForWatchlist7Link)).click();
		try {
			WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
			List<WebElement> symbols = instruments.findElements(By.className("info"));

			for (WebElement script : symbols) {
				WebElement parent = script.findElement(By.xpath("./.."));
				Actions actions = new Actions(driver);
				actions.moveToElement(script).build().perform();
				sleep1Seconds();
				parent.findElement(By.className("icon-trash")).click();
			}
		} catch (NoSuchElementException e) {
			log.error("No such element exception");
			log.error(e);
			clearAllWatchlistTab();
		}

		log.info("DONE - Clearing all watch list tab scripts");
	}

	private static void clearWatchListTab(WebElement script) {
		try {
			WebElement parent = script.findElement(By.xpath("./.."));
			Actions actions = new Actions(driver);
			actions.moveToElement(script).build().perform();
			sleep1Seconds();
			parent.findElement(By.className("icon-trash")).click();
		} catch (NoSuchElementException e) {
			log.error("No such element exception");
			log.error(e);
			clearWatchListTab(script);
		}
	}

	private static HashMap<String, Double> shortListStrikes(HashSet<String> options) {
		HashMap<String, Double> strikes = new HashMap<>();
		// enter to Watchlist 7
		driver.findElement(By.xpath(xPathForWatchlist7Link)).click();
		sleep1Seconds();

		for (String strikeName : options) {
			// IF search couldn't find any strikes for search key then report and continue
			try {
				addToWatchlist(strikeName);
			} catch (Exception e) {
				log.error("SHORT LIST STRIKES WENT WRONG - STRIKE " + strikeName);
				log.error(e);
				continue;
			}
			sleep1Seconds();

			WebElement instruments = driver.findElement(By.xpath(xPathForWatchlistTab));
			List<WebElement> symbols = instruments.findElements(By.className("info"));

			if (symbols != null && symbols.size() >= 1) {
				WebElement lastAddedScript = symbols.get(symbols.size() - 1);

				String symbolTitle = lastAddedScript.findElement(By.className("symbol")).getText();
				String priceStr = lastAddedScript.findElement(By.className("last-price")).getText();
				double price = parsePrice(priceStr);
				if (price <= 23 && price >= 14) {
					strikes.put(symbolTitle, price);
				}
				clearWatchListTab(lastAddedScript);
				log.trace("Last added script symbol:: " + symbolTitle + "lastPrice:: " + price);
			} else {
				log.error("Couldn't add this script to watchlist " + strikeName);
			}
		}

		return strikes;
	}

	private static void addToWatchlist(String strikeName) {
		// in search bar type script name
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div/div/input")).sendKeys(strikeName);
		// click on search result bar to add script
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div[1]/ul/div/li/span[1]/span")).click();
	}

	private static HashSet<String> generateOptionsStrikes(String optionType) {
		HashSet<String> result = new HashSet<>();
		int initialPrice = Integer.MAX_VALUE;
		int maxPrice = Integer.MIN_VALUE;

		if ("CE".equals(optionType)) {
			initialPrice = bankNiftyPrice;
			maxPrice = bankNiftyPrice + 3000;
		}else if("PE".equals(optionType)) {
			initialPrice = bankNiftyPrice - 3000;
			maxPrice = bankNiftyPrice;
		}

		while (initialPrice <= maxPrice) {
			String strike = "BANKNIFTY" + year + month + expiryDate + initialPrice + optionType;

			result.add(strike);
			initialPrice = initialPrice + 100;
		}
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

	private static String getCurrentDate() {
		return LocalDate.now().toString();
	}

}
