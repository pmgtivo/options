package optionselling;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NseOptionsDataFetch {

	public static void main(String[] args) throws IOException {

		try {
			callGrowwApi();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void callGrowwApi() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(
				"https://groww.in/v1/api/stocks_fo_data/v1/charting_service/chart/exchange/NSE/segment/FNO/NIFTY22MAY16900CE/daily?intervalInMinutes=1")
				.method("GET", null)
				.build();
		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		System.out.println(response.body().string());
		response.close();
	}


}
