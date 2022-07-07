package grow.data.backtest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import grow.data.OptionChain;
import grow.data.OptionChainData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GrowwOptionsDataFetch {
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws IOException {
		getBankNiftyOptions();
		System.exit(0);
	}

	public static HashSet<String> getBankNiftyOptions() {
		HashSet<String> result = new HashSet<>();

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder()
					.url("https://groww.in/v1/api/option_chain_service/v1/option_chain/nifty-bank").method("GET", null)
					.build();
			Response response = client.newCall(request).execute();
			System.out.println(response.code());
			String responseBody = response.body().string();
			System.out.println(responseBody);
			OptionChainData ocData = mapper.readValue(responseBody, OptionChainData.class);
			List<OptionChain> ocList = ocData.getOptionChains();

			for (OptionChain oc : ocList) {
				if (oc.getCallOption() != null && oc.getPutOption() != null) {
					String ce = oc.getCallOption().getGrowwContractId();
					String pe = oc.getPutOption().getGrowwContractId();
					result.add(pe);
					result.add(ce);
				}
			}

			response.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		result.forEach(System.out::println);
		return result;
	}

//	private static LocalDate getDate(String date) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
//		return LocalDate.parse(LocalDate.now(), formatter);
//
//	}

}
