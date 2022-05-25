package grow.data.backtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;

import grow.data.Option;
import grow.data.OptionChain;
import grow.data.OptionChainData;

public class BNFStrangleTest {
	private static String fileFilterStr = "_niftybank.json";
	private static double entryPoint = 20.0;
	private static double stopLossTimes = 2.5;
	private static double slippage = 2.0;
	private static ObjectMapper mapper = new ObjectMapper();
	private static LinkedHashMap<String, OptionChainData> strikeMap = new LinkedHashMap<>();

	private static HashMap<String, Double> stopLossMap = new HashMap<>();
	private static HashMap<String, Double> stopLossHitMap = new HashMap<>();
	private static HashMap<String, Double> entryMap = new HashMap<>();
	final static Logger log = Logger.getLogger(BNFStrangleTest.class);
	private static String parentDir = "/Users/pmg/Documents/bankNiftyData/";
	
	private static int rowNum = 0;
	private static int col = 0;
	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;
	private static Row row = null;

	public static void main(String[] args) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Transactions");
		row = sheet.createRow(rowNum++);
		row.createCell(col++).setCellValue("DATE");
		row.createCell(col++).setCellValue("DAY");
		row.createCell(col++).setCellValue("PROFIT POINTS");
		row.createCell(col++).setCellValue("PROFITS PER LOT");
		row.createCell(col++).setCellValue("PROFITS FOR 4 LOTS");
		
		File directoryPath = new File(parentDir);
		String contents[] = directoryPath.list();

		List<String> filterDir = Arrays.stream(contents)
				.filter(name -> !name.endsWith(".sh") && !name.equalsIgnoreCase(".DS_Store"))
				.collect(Collectors.toList());
		Collections.sort(filterDir);
		for (String file : filterDir) {
			LocalDate currentDate = getDate(file);
			log.info("");
			log.info("STARTED PROCESSING FOR THE DATE :: " + currentDate);
			log.info("Day of week : " + currentDate.getDayOfWeek());
			List<String> filesList = FetchOcFiles(parentDir + file);
			Collections.sort(filesList);
			parseData(parentDir + file, filesList);
			col = 0;
			row = sheet.createRow(rowNum++);

			row.createCell(col++).setCellValue(currentDate.toString());
			row.createCell(col++).setCellValue(currentDate.getDayOfWeek().name());
			
			entryStrikesStrategy();
			stopLossBackTestStrategy();
			profitCalculation();
			clearAllData();
		}
		writeReportToFile();

	}

	private static void writeReportToFile() {
		try {
			FileOutputStream outputStream = new FileOutputStream("BNFStragleTestReport.xlsx");
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

	}

	private static void profitCalculation() {
		log.info("Profit booking calculation");
		Double points = 0.0;

		for (String strikeKey : entryMap.keySet()) {
			if (stopLossHitMap.containsKey(strikeKey)) {

				Double loss = stopLossHitMap.get(strikeKey) - entryMap.get(strikeKey);
				points -= loss;
				log.info("Stop loss hit for strike: " + strikeKey + " - Loss points : " + loss);
			} else {
				// at 3:10 sold logic
				String timeKey = "15:0";
				OptionChainData ocData = strikeMap.get(timeKey);
				Optional<OptionChain> optionChainOptional = ocData.getOptionChains().stream()
						.filter(os -> (os.getCallOption().getGrowwContractId().equalsIgnoreCase(strikeKey)
								|| os.getPutOption().getGrowwContractId().equalsIgnoreCase(strikeKey)))
						.findFirst();

				if (optionChainOptional.isPresent()) {
					OptionChain oc = optionChainOptional.get();
					Option option = null;
					if (oc.getCallOption().getGrowwContractId().equalsIgnoreCase(strikeKey)) {
						option = oc.getCallOption();
					} else {
						option = oc.getPutOption();
					}
					Double soldAt = option.getLtp();
					Double profit = entryMap.get(strikeKey) - soldAt;

					log.info("Profit points: " + profit + " booked at 3:00 for strike : " + strikeKey + " - "
							+ "Strike price at 3:00 " + soldAt);
					log.info("options details: " + option);
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
				OptionChainData ocData = strikeMap.get(key);

				// call option SL check
				Optional<OptionChain> callOcOptional = ocData.getOptionChains().stream()
						.filter((oc) -> oc.getCallOption().getGrowwContractId().equals(slKey)).findFirst();

				if (callOcOptional.isPresent()) {
					Option op = callOcOptional.get().getCallOption();
					Double ltp = op.getLtp();
					Double stopLoss = stopLossMap.get(slKey);
//					log.info("Stop loss calculate for option " + op + " strike: " + slKey);
					if (stopLoss <= ltp) {
						log.info("");
						log.error("Stop loss Hit for Strike : " + op);
						log.info("");
						stopLossHitMap.put(slKey, stopLoss);
					} else {
						recalculateStopLoss(key, slKey, op);
					}
				}

				// put option SL check
				Optional<OptionChain> putOcOptional = ocData.getOptionChains().stream()
						.filter((oc) -> oc.getPutOption().getGrowwContractId().equals(slKey)).findFirst();

				if (putOcOptional.isPresent()) {
					Option op = putOcOptional.get().getPutOption();
					Double ltp = op.getLtp();
					Double stopLoss = stopLossMap.get(slKey);
//					log.info("Stop loss calculate for option " + op + " strike: " + slKey);
					if (stopLoss <= ltp) {
						log.info("");
						log.error("Stop loss Hit for Strike : " + op + "-> Trade Time:: " + key);
						log.info("");
						stopLossHitMap.put(slKey, stopLoss);
					} else {
						recalculateStopLoss(key, slKey, op);
					}
				}

			}

			for (String slKey : stopLossHitMap.keySet()) {
				stopLossMap.remove(slKey);
			}

		}
	}

	private static void recalculateStopLoss(String time, String strike, Option option) {
		if (time.equalsIgnoreCase("13:30")) {
			Double entryPrice = entryMap.get(strike);
			if (option.getLtp() < entryPrice) {
				log.info("");
				log.info("Stop loss calculated at 1:30 for strike: " + strike + " Stop loss: " + option.getLtp() * stopLossTimes);
				stopLossMap.put(strike, option.getLtp() * stopLossTimes);
			} else {
				log.info("At 1:30 PM price was high than entry so not changing stop loss");
			}

		} else if (time.equalsIgnoreCase("14:30")) {
			Double entryPrice = entryMap.get(strike);
			if (option.getLtp() < entryPrice) {
				stopLossMap.put(strike, option.getLtp() * stopLossTimes);
				log.info("");
				log.info("Stop loss calculated at 2:30 for strike: " + strike + " Stop loss: " + option.getLtp() * stopLossTimes);
			} else {
				log.info("At 2:30 PM price was high than entry so not changing stop loss");
			}
		}
	}

	private static void entryStrikesStrategy() {
		Double ceMin = Double.MAX_VALUE;
		Option ceStrike = null;
		Option peStrike = null;
		Double peMin = Double.MAX_VALUE;
		log.info("Short straddle entry will be done at 09:25");

		String key = "9:25";
		OptionChainData ocData = strikeMap.get(key);

		for (OptionChain oc : ocData.getOptionChains()) {
			double callLtp = oc.getCallOption().getLtp();
			double putLtp = oc.getPutOption().getLtp();

			double callLtpDiff = Math.abs(callLtp - entryPoint);
			double putLtpDiff = Math.abs(putLtp - entryPoint);

			if (peMin > putLtpDiff) {
				peStrike = oc.getPutOption();
				peMin = putLtpDiff;

			}
			if (ceMin > callLtpDiff) {
				ceStrike = oc.getCallOption();
				ceMin = callLtpDiff;
			}

		}

		log.info("");
		log.info("Entry strikes as below");
		log.info("CE Strike :: " + ceStrike);
		log.info("PE Strike :: " + peStrike);

		entryMap.put(ceStrike.getGrowwContractId(), ceStrike.getLtp());
		entryMap.put(peStrike.getGrowwContractId(), peStrike.getLtp());

		stopLossMap.put(ceStrike.getGrowwContractId(), ceStrike.getLtp() * stopLossTimes);
		stopLossMap.put(peStrike.getGrowwContractId(), peStrike.getLtp() * stopLossTimes);

		log.info("Stop loss calculated as below");
		log.info("CE Strike :: " + ceStrike.getLtp() * stopLossTimes);
		log.info("PE Strike :: " + peStrike.getLtp() * stopLossTimes);
		log.info("");

	}

	private static void parseData(String parentPath, List<String> filesList) {

		for (String file : filesList) {
			OptionChainData ocData = null;
			try {
				ocData = mapper.readValue(new File(parentPath + "/" + file), OptionChainData.class);
				LocalDateTime dateTime = getDateFromFile(file);

				if (dateTime != null) {
					int hour = dateTime.getHour();
					int minute = dateTime.getMinute();
					String key = hour + ":" + minute;
					if ((hour == 9 && minute <= 24) || (hour == 15 && minute > 0)) {
						continue;
					}

					strikeMap.put(key, ocData);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static LocalDateTime getDateFromFile(String file) {
		String[] temp = file.split("_");
		if (temp != null && temp.length == 2) {
			String dateStr = temp[0].trim();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
			return LocalDateTime.parse(dateStr, formatter);

		}
		return null;
	}

	private static LocalDate getDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return LocalDate.parse(date, formatter);

	}

	private static List<String> FetchOcFiles(String pathForOcData) {
		File directoryPath = new File(pathForOcData);
		String contents[] = directoryPath.list();

		return Arrays.stream(contents)
				.filter(name -> name.endsWith(fileFilterStr) && !name.equalsIgnoreCase(".DS_Store"))
				.collect(Collectors.toList());

	}

	private static Long formatStrikePrice(Long strikePrice) {
		return strikePrice / 100;
	}
}
