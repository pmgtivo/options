package grow.data.backtest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grow.derivatives.model.OptionChainStrike;
import com.grow.derivatives.model.OptionData;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GrowwOptionsDataFetch {
	private static ObjectMapper mapper = new ObjectMapper();
	private static List<String> contractIds = new ArrayList<>();
	private static String parentDir = "/Users/pmg/Documents/bankNiftyStrikeWiseData/";

	public static void main(String[] args) throws IOException {

		try {
			callNiftOptions();
			callNiftyApi();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void callNiftOptions() throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder()
				.url("https://groww.in/v1/api/option_chain_service/v1/option_chain/derivatives/nifty")
				.method("GET", null).build();
		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		String responseBody = response.body().string();
		System.out.println(responseBody);
		OptionData ocData = mapper.readValue(responseBody, OptionData.class);
		List<OptionChainStrike> ocList = ocData.getOptionChain().getOptionChains();

		for (OptionChainStrike oc : ocList) {
			String ce = oc.getCallOption().getGrowwContractId();
			String pe = oc.getPutOption().getGrowwContractId();
			contractIds.add(pe);
			contractIds.add(ce);
		}

		response.close();
		contractIds.forEach(System.out::println);
	}

	private static void callNiftyApi() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(
				"https://groww.in/v1/api/charting_service/v2/chart/exchange/NSE/segment/CASH/NIFTY/daily?intervalInMinutes=1")
				.method("GET", null).build();
		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		System.out.println(response.body().string());
		response.close();
	}

	private static void callNiftyOptionStrikes() throws IOException {

		for (String strike : contractIds) {
			String URL = "https://groww.in/v1/api/stocks_fo_data/v1/charting_service/chart/exchange/NSE/segment/FNO/"
					+ strike + "/daily?intervalInMinutes=1";
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder().url(URL).method("GET", null).build();
			Response response = client.newCall(request).execute();
			System.out.println(response.code());
			System.out.println(response.body().string());
			response.close();
		}

	}
	
//	private static LocalDate getDate(String date) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
//		return LocalDate.parse(LocalDate.now(), formatter);
//
//	}

}
