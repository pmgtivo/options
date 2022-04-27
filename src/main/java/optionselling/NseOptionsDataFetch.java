package optionselling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NseOptionsDataFetch {

	public static void main(String[] args) throws IOException {

//		try {
//			callNseServerForOptionChainData();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		String command = "";

		Process process = Runtime.getRuntime().exec(command);
		InputStream stream = process.getInputStream();

	}

	private static void callNseServerForOptionChainData() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url("https://www.nseindia.com/api/option-chain-indices?symbol=NIFTY")
				.method("GET", null).addHeader("authority", "www.nseindia.com").addHeader("Host", "localhost")
				.addHeader("accept-encoding", "gzip, deflate, br")
				.addHeader("accept-language", "en-US,en;q=0.9,kn;q=0.8")
				.addHeader("user-agent",
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36")
				.addHeader("referer",
						"https://www.nseindia.com/option-chain?symbolCode=-10006&symbol=NIFTY&symbol=NIFTY&instrument=-&date=-&segmentLink=17&symbolCount=2&segmentLink=17")
				.addHeader("cookie",
						"_ga=GA1.2.363736446.1633691157; nsit=kCZ3iGwDT3NPuGBmaVG3taYR; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTY1MDg1MDk5MywiZXhwIjoxNjUwODU0NTkzfQ._Jj5TAng6M3oHkNzvLzSdUgEQdIeUGD9tZijvX-Rv2A; AKA_A2=A; ak_bmsc=E4C3F23414F84031F047E2602DDA522A~000000000000000000000000000000~YAAQPUYDF+r+AUaAAQAAzBpjXg9W+RDKdP+ogVDSh2F45RDFveqN4+9d2fn/3bY9lJvEky3AGYMvbyDQxm8TGlOuwBcAl8dyMIYWmUiLgSvHWvHR+763yRpaej3xL6pok3EkP4HXd5fTi3RjeQle3RXT/CpP6bSjNkwb0SuyZ7yMbazY9+TPTA4qbF0oybyuk7a4ALb4HcRuBJp/YTksEyHHfufsjeB9acEji2uFx62UFkWJ1lVWzG+nFvJ2ex22LYpXoKyff/0jHspd+k74XI/1q/nMYQmMWrTwxFqs7f9iXPCldTuofKuwQ3kSfkgzIqzFXsoQrec+KypuE8boL52hBJkVsE1Wv5g971QYrbEMCq92O7jBOGsxD8riTGj+5mPeWAg8KEAvliFX4he52MMC62vUjJ9iPkfTE0/1bOwNanxi8VOazxVpszKiEc+eV+IK8qSpIVNfFjca10hCUDBT+g4HudwyzqIcfs/DYw2BGEkY3jjTFTmVIdyxgg==; RT=\"z=1&dm=nseindia.com&si=c5c5986a-6635-41a2-8ed1-c88863852156&ss=l2e222rs&sl=1&tt=1j5&bcn=%2F%2F684d0d49.akstat.io%2F&ld=2bm&nu=kpaxjfo&cl=1qkv\"; bm_sv=48C7E04E1920C8A236B89AFBB7DE172A~rM39XCULv+vTvee2bfshY/rndyMkYAMnOILOZDMP9glwEG+HT/S6jClopxawZHlYnaBg29DGgHGT5smqB+blj8PeAomhRCbrz+UxrKU9nET6XOKtNp2Y7fi3f2mVaHe/VDFHT87wm5V+UwoN4/VPfrOaHyc9aKB5GErakBmMmU4=")
				.addHeader("Cookie",
						"_ga=GA1.2.363736446.1633691157; nsit=kCZ3iGwDT3NPuGBmaVG3taYR; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTY1MDg1MDk5MywiZXhwIjoxNjUwODU0NTkzfQ._Jj5TAng6M3oHkNzvLzSdUgEQdIeUGD9tZijvX-Rv2A; AKA_A2=A; ak_bmsc=E4C3F23414F84031F047E2602DDA522A~000000000000000000000000000000~YAAQPUYDF+r+AUaAAQAAzBpjXg9W+RDKdP+ogVDSh2F45RDFveqN4+9d2fn/3bY9lJvEky3AGYMvbyDQxm8TGlOuwBcAl8dyMIYWmUiLgSvHWvHR+763yRpaej3xL6pok3EkP4HXd5fTi3RjeQle3RXT/CpP6bSjNkwb0SuyZ7yMbazY9+TPTA4qbF0oybyuk7a4ALb4HcRuBJp/YTksEyHHfufsjeB9acEji2uFx62UFkWJ1lVWzG+nFvJ2ex22LYpXoKyff/0jHspd+k74XI/1q/nMYQmMWrTwxFqs7f9iXPCldTuofKuwQ3kSfkgzIqzFXsoQrec+KypuE8boL52hBJkVsE1Wv5g971QYrbEMCq92O7jBOGsxD8riTGj+5mPeWAg8KEAvliFX4he52MMC62vUjJ9iPkfTE0/1bOwNanxi8VOazxVpszKiEc+eV+IK8qSpIVNfFjca10hCUDBT+g4HudwyzqIcfs/DYw2BGEkY3jjTFTmVIdyxgg==; RT=\"z=1&dm=nseindia.com&si=c5c5986a-6635-41a2-8ed1-c88863852156&ss=l2e222rs&sl=1&tt=1j5&bcn=%2F%2F684d0d49.akstat.io%2F&ld=2bm&nu=kpaxjfo&cl=1qkv\"; bm_sv=48C7E04E1920C8A236B89AFBB7DE172A~rM39XCULv+vTvee2bfshY/rndyMkYAMnOILOZDMP9glwEG+HT/S6jClopxawZHlYnaBg29DGgHGT5smqB+blj8PeAomhRCbrz+UxrKU9nET6XOKtNp2Y7fi3f2mVaHe/VDFHT87wm5V+UwoN4/VPfrOaHyc9aKB5GErakBmMmU4=")
				.addHeader("sec-fetch-site", "same-origin").addHeader("sec-ch-ua-platform", "macOS")
				.addHeader("sec-ch-ua-mobile", "?0").addHeader("sec-ch-ua",
						"\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100\"")
				.build();
		Response response = client.newCall(request).execute();
		assertThat(response.code(), equalTo(200));
		System.out.println(response.body().string());
		response.close();
	}

}
