package optionselling;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class BNF_NSE_data {
	private static String baseUrl = "https://www.nseindia.com/option-chain";
	public static WebDriver driver;

	public static void main(String[] args) throws IOException {
		String driverPath = "chromedriver-100";
		System.setProperty("webdriver.chrome.driver", driverPath);

		String downloadFilepath = "/Users/pmg/BNF_data/";
//		Files.createParentDirs(new File(downloadFilepath));
		Map<String, Object> preferences = new Hashtable<String, Object>();
		preferences.put("profile.default_content_settings.popups", 0);
		preferences.put("download.prompt_for_download", "false");
		preferences.put("download.default_directory", downloadFilepath);

		// disable flash and the PDF viewer
//		preferences.put("plugins.plugins_disabled", new String[] { "Adobe Flash Player", "Chrome PDF Viewer" });

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", preferences);

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		
		sleepSeconds(5);
		
		driver.findElement(By.id("equity_optionchain_select")).click();
		
		Select indicesOption = new Select(driver.findElement(By.id("equity_optionchain_select")));
		indicesOption.selectByValue("BANKNIFTY");
		
		sleepSeconds(5);
		
		driver.findElement(By.id("downloadOCTable")).click();
		
	}
	
	private static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
