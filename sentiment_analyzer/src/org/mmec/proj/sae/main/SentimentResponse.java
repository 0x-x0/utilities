
package org.mmec.proj.sae.main;

import java.util.ArrayList;


public class SentimentResponse {
	private long totalPositive;
	private long totalNegative;
	private long totalNeutral;

	public void setTotlaPositive(long totalPositive) {
		this.totalPositive = totalPositive;
	}

	public void setTotlaNegative(long totalNegative) {
		this.totalNegative = totalNegative;

	}

	public void setTotlaNeutral(long totalNeutral) {
		this.totalNeutral = totalNeutral;
	}

	public long getTotalPositive() {
		return this.totalPositive;
	}

	public long getTotalNegative() {
		return this.totalNegative;
	}

	public long getTotalNeutral() {
		return this.totalNeutral;
	}

	ArrayList<TweetSentiment> tweets;

	public ArrayList<TweetSentiment> getTweets() {
		return tweets;
	}

	public void setTweets(ArrayList<TweetSentiment> tweets) {
		this.tweets = tweets;
	}

}
