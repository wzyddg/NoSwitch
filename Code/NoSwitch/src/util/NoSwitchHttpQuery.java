package util;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class NoSwitchHttpQuery {
	private String url = "";
	
	public NoSwitchHttpQuery(String URL) {
		// TODO Auto-generated constructor stub
		this.url = URL;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{ \"target\" : \""+url+"\" }";
	}
	
	public String sendRequest() throws UnsupportedOperationException, Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpUriRequest request = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(request);
		return ReadInputStream(response.getEntity().getContent());
	}
	
	protected String ReadInputStream(InputStream is) throws Exception {
		InputStreamReader ir = new InputStreamReader(is);
		StringBuilder text = new StringBuilder();
		char[] buffer = new char[1024];
		int length = 0;

		while (((length = ir.read(buffer)) != -1)) {
			text.append(new String(buffer, 0, length));
		}

		return new String(text);
	}
}
