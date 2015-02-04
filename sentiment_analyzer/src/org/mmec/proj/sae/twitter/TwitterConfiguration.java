
package org.mmec.proj.sae.twitter;

import java.net.URI;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import play.libs.WS;
//import play.libs.WS.Response;


public class TwitterConfiguration {

	public static final String CLIENT_ID = "NnhNxNrlnF1qJ8DuXdZBjOlbj";
	public static final String SECRET = "cFgyiguNBmuPyM8demYChVOZSHR1CVZS1YVDPD3Nle5F9yWR03";
	public static final String BEARER_TOKEN = CLIENT_ID + ":" + SECRET;
	public static String BASE64_BEARER_TOKEN;
	static {
		BASE64_BEARER_TOKEN = new String(Base64.encodeBase64(BEARER_TOKEN
				.getBytes())).intern();
	}
	public static String ACCESS_TOKEN;

	public static void validate() throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		URIBuilder uriBuilder = new URIBuilder(
				"https://api.twitter.com/oauth2/token");
		uriBuilder.addParameter("grant_type", "client_credentials");
		URI uri = uriBuilder.build();

		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("Authorization", "Basic " + BASE64_BEARER_TOKEN);
		httpPost.addHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
		try {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String reponseStr = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				JsonObject accessResponse = new JsonParser().parse(reponseStr)
						.getAsJsonObject();
				if (accessResponse.has("access_token")) {
					ACCESS_TOKEN = accessResponse.get("access_token")
							.getAsString();
				}
			} else {
				throw new IllegalStateException("Bad Response");
			}
		} finally {
			httpResponse.close();
		}
	}

	public static void invalidate() throws Exception {
		if (ACCESS_TOKEN == null)
			return;

		CloseableHttpClient httpclient = HttpClients.createDefault();

		URIBuilder uriBuilder = new URIBuilder(
				"https://api.twitter.com/oauth2/invalidate_token");
		uriBuilder.addParameter("access_token", ACCESS_TOKEN);
		URI uri = uriBuilder.build();

		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader("Authorization", "Basic " + BASE64_BEARER_TOKEN);
		httpGet.addHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		httpclient.execute(httpGet);
	}
}
