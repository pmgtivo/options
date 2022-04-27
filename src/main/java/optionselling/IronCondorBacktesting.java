package optionselling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IronCondorBacktesting {

	public static class OptionStrike {
		public LocalDateTime date;
		public String strike;
		public double lastprice;

		@Override
		public String toString() {
			return this.strike + " - " + this.lastprice + " - " + this.date;
		}
	}

	private static LinkedHashMap<String, ArrayList<OptionStrike>> strikeMap = new LinkedHashMap<>();
	private static HashMap<String, Double> stopLossMap = new HashMap<>();
	private static HashMap<String, Double> stopLossHitMap = new HashMap<>();
	private static HashMap<String, Double> entryMap = new HashMap<>();
	final static Logger log = Logger.getLogger(IronCondorBacktesting.class);
	private static int rowNum = 0;
	private static int col = 0;
	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;
	private static Row row = null;
	
	private static double entryPrice = 20.00;

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

		callController();
//		backTestController("sampledata/2022-04-01.json");

	}

	private static void callController() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Transactions");
		row = sheet.createRow(rowNum++);
		row.createCell(col++).setCellValue("DATE");
		row.createCell(col++).setCellValue("DAY");
		row.createCell(col++).setCellValue("PROFIT POINTS");
		row.createCell(col++).setCellValue("PROFITS PER LOT");
		row.createCell(col++).setCellValue("PROFITS FOR 4 LOTS");
		
		File directoryPath = new File("sampledata");
		String contents[] = directoryPath.list();
		Arrays.sort(contents);
		
		for (String backTestDataFile : contents) {
			clearAllData();
			row = sheet.createRow(rowNum++);

			try {
				backTestController("sampledata/" + backTestDataFile);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream("Result.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void clearAllData() {
		strikeMap.clear();
		stopLossHitMap.clear();
		stopLossMap.clear();
		entryMap.clear();
		col = 0;

	}

	private static void backTestController(String backTestDataFile)
			throws FileNotFoundException, IOException, ParseException {

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(backTestDataFile));
		log.info("***************************************************");
		log.info("***************************************************");
		log.info("***************************************************");
		log.info("Back test results for file - " + backTestDataFile);

		for (Object o : jsonArray) {
			JSONObject person = (JSONObject) o;
			String date = (String) person.get("date");
			String strike = (String) person.get("strike");
			Object lastPriceObj = person.get("lastprice");
			Double lastprice = Double.parseDouble(lastPriceObj + "");

			OptionStrike os = new OptionStrike();
			os.date = convertDate(date);
			os.strike = strike;
			os.lastprice = lastprice;

			int hour = os.date.getHour();
			int minute = os.date.getMinute();
			if (hour == 9 && minute < 30) {
				continue;
			}
			String key = hour + ":" + minute;

			strikeMap.putIfAbsent(key, new ArrayList<OptionStrike>());
			strikeMap.get(key).add(os);

		}

		entryStrikesStrategy();
		stopLossBackTestStrategy();
		profitCalculation();
	}

	private static void profitCalculation() {
		log.info("Profit booking calculation");
		Double points = 0.0;
		Double slippage = 2.0;

		for (String key : entryMap.keySet()) {
			if (stopLossHitMap.containsKey(key)) {

				Double loss = stopLossHitMap.get(key) - entryMap.get(key);
				points -= loss;
				log.info("Stop loss hit for strike: " + key + " - Loss points : " + loss);
			} else {
				// at 3:10 sold logic
				String strikeKey = "15:10";
				Optional<OptionStrike> osOptional = strikeMap.get(strikeKey).stream()
						.filter((os) -> os.strike.equalsIgnoreCase(key)).findFirst();
				if (osOptional.isPresent()) {
					Double soldAt = osOptional.get().lastprice;
					Double profit = entryMap.get(key) - soldAt;

					log.info("Profit points: " + profit + " booked at 3:10 for strike : " + key + " - "
							+ "Strike price at 3:10 " + soldAt);
					points += profit;
				}
			}
		}

		points -= slippage;

		log.info("");
		log.info("Profit earned :: " + points);
		log.info("Profit earned per Lot:: " + points * 25);
		log.info("Profit earned for 4 Lot:: " + points * 100);
		log.info("");
		log.info("Total percentage calculation for 4L :: " + ((points * 100) / 400000) * 100);

		row.createCell(col++).setCellValue(points);
		row.createCell(col++).setCellValue(points * 25);
		row.createCell(col++).setCellValue(points * 100);

	}

	private static void stopLossBackTestStrategy() {

		for (String key : strikeMap.keySet()) {
			for (String slKey : stopLossMap.keySet()) {
				Optional<OptionStrike> osOptional = strikeMap.get(key).stream()
						.filter((os) -> os.strike.equalsIgnoreCase(slKey)).findFirst();
				if (osOptional.isPresent()) {
					OptionStrike os = osOptional.get();
					Double stopLoss = stopLossMap.get(slKey);
					Double price = os.lastprice;

					if (stopLoss <= price) {
						log.info("");
						log.error("Stop loss Hit for Strike : " + os);
						log.info("");
						stopLossHitMap.put(os.strike, os.lastprice);
					}

					if (key.equalsIgnoreCase("13:30")) {
						Double entryPrice = entryMap.get(os.strike);
						if (os.lastprice < entryPrice) {
							log.info("");
							log.info("Stop loss calculated at 1:30 for strike: " + os.strike + " Stop loss: "
									+ os.lastprice * 2.5);
							stopLossMap.put(os.strike, os.lastprice * 2.5);
						} else {
							log.info("At 1:30 PM price was high than entry so not changing stop loss");
						}

					} else if (key.equalsIgnoreCase("14:30")) {
						Double entryPrice = entryMap.get(os.strike);
						if (os.lastprice < entryPrice) {
							stopLossMap.put(os.strike, os.lastprice * 2.5);
							log.info("");
							log.info("Stop loss calculated at 2:30 for strike: " + os.strike + " Stop loss: "
									+ os.lastprice * 2.5);
						} else {
							log.info("At 2:30 PM price was high than entry so not changing stop loss");
						}
					}
				}
			}

			for (String slKey : stopLossHitMap.keySet()) {
				stopLossMap.remove(slKey);
			}

		}
	}

	private static void entryStrikesStrategy() {
		Double ceMin = Double.MAX_VALUE;
		OptionStrike ceStrike = null;
		OptionStrike peStrike = null;
		Double peMin = Double.MAX_VALUE;
		log.info("Iron condor entry will be done at 09:30");

		String key = "9:30";
		ArrayList<OptionStrike> osList = strikeMap.get(key);
		for (OptionStrike os : osList) {
			double diff = Math.abs(os.lastprice - entryPrice);
			if (os.strike.contains("PE")) {
				if (peMin > diff) {
					peStrike = os;
					peMin = diff;
				}
			} else if (os.strike.contains("CE")) {
				if (ceMin > diff) {
					ceStrike = os;
					ceMin = diff;
				}
			}
		}

		log.info("");
		log.info("Day of week : " + ceStrike.date.getDayOfWeek());
		log.info("Entry strikes as below");
		log.info("CE Strike :: " + ceStrike);
		log.info("PE Strike :: " + peStrike);

		entryMap.put(ceStrike.strike, ceStrike.lastprice);
		entryMap.put(peStrike.strike, peStrike.lastprice);

		stopLossMap.put(ceStrike.strike, ceStrike.lastprice * 2.5);
		stopLossMap.put(peStrike.strike, peStrike.lastprice * 2.5);

		log.info("Stop loss calculated as below");
		log.info("CE Strike :: " + ceStrike.lastprice * 2.5);
		log.info("PE Strike :: " + peStrike.lastprice * 2.5);
		log.info("");

		row.createCell(col++).setCellValue(ceStrike.date.toString());
		row.createCell(col++).setCellValue(ceStrike.date.getDayOfWeek().name());

	}

	public static LocalDateTime convertDate(String date) {
		Instant utcDate = Instant.parse(date);
		return LocalDateTime.ofInstant(utcDate, ZoneId.of("Asia/Kolkata"));
	}

}
