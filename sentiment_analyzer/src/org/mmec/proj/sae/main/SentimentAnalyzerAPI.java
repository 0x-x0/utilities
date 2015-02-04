
package org.mmec.proj.sae.main;

import java.util.ArrayList;
import java.util.List;

import org.mmec.proj.sae.PosTagger;
import org.mmec.proj.sae.SWNSentimentDetector;
import org.mmec.proj.sae.Tokenizer;
import org.mmec.proj.sae.twitter.TwitterConfiguration;
import org.mmec.proj.sae.twitter.TwitterSearcher;


public class SentimentAnalyzerAPI {
	private static final String PATH_TO_SWN_FILE = "C:/Users/localhost1/Desktop/project/lib/SentiWordNet.txt";

	public SentimentResponse process(String keyword) throws Exception {

		TwitterConfiguration.validate();
		SWNSentimentDetector sentiwordnet = null;
		SentimentResponse response = new SentimentResponse();
		ArrayList<TweetSentiment> tweetsenti = new ArrayList<TweetSentiment>();
		response.setTweets(tweetsenti);
		try {
			Tokenizer tokenizer = new Tokenizer();
			PosTagger posTagger = new PosTagger();
			sentiwordnet = new SWNSentimentDetector(PATH_TO_SWN_FILE);
			TwitterSearcher indexer = new TwitterSearcher();
			List<String> tweets = indexer.searchTweets(keyword);
			if (tweets != null && !tweets.isEmpty())
				for (String eachTweet : tweets) {
					TweetSentiment eachSenti = tagSentiment(eachTweet,
							sentiwordnet, tokenizer, posTagger);
					tweetsenti.add(eachSenti);
					String sentiment = eachSenti.getSentiment();
					if (sentiment.equals("POSITIVE")) {
						long totalPositive = response.getTotalPositive();
						totalPositive++;
						response.setTotlaPositive(totalPositive);
					} else if (sentiment.equals("NEGATIVE")) {
						long totalNegative = response.getTotalNegative();
						totalNegative++;
						response.setTotlaNegative(totalNegative);
					} else {
						long totalNeutral = response.getTotalNeutral();
						totalNeutral++;
						response.setTotlaNeutral(totalNeutral);
					}
				}
		} finally {
			TwitterConfiguration.invalidate();
		}
		return response;
	}

	private static TweetSentiment tagSentiment(String eachTweet,
			SWNSentimentDetector sentiwordnet, Tokenizer tokenizer,
			PosTagger posTagger) throws Exception {

		String[] tokens = tokenizer.tokenize(eachTweet);
		String[] tagsPOS = posTagger.tagPOS(tokens);

		for (int i = 0; i < tokens.length; i++) {
			if (tagsPOS[i] == "NN") {
				tagsPOS[i] = "n";
			} else if (tagsPOS[i] == "RB") {
				tagsPOS[i] = "r";
			} else if (tagsPOS[i] == "VB") {
				tagsPOS[i] = "v";
			} else
				tagsPOS[i] = "a";
		}

		double totalSentimentScore = 0;

		for (int i = 0; i < tokens.length; i++) {
			double value = sentiwordnet.extract(tokens[i], tagsPOS[i]);
			totalSentimentScore += value;
		}

		return toSentiment(eachTweet, totalSentimentScore);
	}

	private static TweetSentiment toSentiment(String eachTweet,
			double totalSentimentScore) {

		TweetSentiment sentiment = new TweetSentiment();
		sentiment.setTweet(eachTweet);
		if (totalSentimentScore > 0) {
			sentiment.setSentiment("POSITIVE");
		} else if (totalSentimentScore < 0) {
			sentiment.setSentiment("NEGATIVE");
		} else if (totalSentimentScore == 0) {
			sentiment.setSentiment("NEUTRAL");
		}
		return sentiment;
	}

	public static void main(String[] args) throws Exception {
		String keyword = null;
		SentimentResponse sentimentResponse = new SentimentAnalyzerAPI()
				.process(keyword);
		ArrayList<TweetSentiment> tweets = sentimentResponse.getTweets();
		for (TweetSentiment tweetSentiment : tweets) {
			System.out.println(tweetSentiment.getSentiment() + " : "
					+ tweetSentiment.getTweet());
		}
	}

}
