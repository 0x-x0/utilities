
package org.mmec.proj.sae.twitter;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TwitterSearcher {
	private static final String BASE_URL = "https://api.twitter.com/1.1/search/tweets.json?lang=en&count=50";
	private static SimpleDateFormat dateforat;

	static {
		dateforat = new SimpleDateFormat("EEE MMM dd HH:mm:ss +SSSS yyyy");
		dateforat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public List <String> searchTweets(String keyWord) {

		List<String> tweets = new ArrayList<String>();
		try {
			StringTokenizer tokenizer = new StringTokenizer(keyWord, ",");
			while (tokenizer.hasMoreElements()) {
				String eachToken = (String) tokenizer.nextElement();
				try {
					JsonObject jsonOutput = executeRequest(eachToken);
					JsonArray results = jsonOutput.get("statuses")
							.getAsJsonArray();
					for (JsonElement eachElem : results) {
						JsonObject eachTweetInfo = eachElem.getAsJsonObject();

						String tweet = eachTweetInfo.get("text").getAsString();
						tweets.add(tweet);
					}
				}
					catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(tweets);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
	

	private JsonObject executeRequest(String keyWord) throws Exception {
		// HTTP request response code
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(BASE_URL);
		uriBuilder.addParameter("q", keyWord);
		URI uri = uriBuilder.build();
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader("Authorization", "Bearer "
				+ TwitterConfiguration.ACCESS_TOKEN);
		CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
		try {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String reponseStr = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				return new JsonParser().parse(reponseStr).getAsJsonObject();
			} else {
				throw new IllegalStateException("Bad Response");
			}
		} finally {
			httpResponse.close();
		}
	}

}
