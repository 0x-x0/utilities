package org.mmec.proj.sae;

//import org.mmec.proj.sae.SentenceDetector; 

import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Tokenizer {

	private TokenizerME tokenizer;

	public Tokenizer() throws Exception {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("en-token.bin");
		TokenizerModel model = new TokenizerModel(is);
		tokenizer = new TokenizerME(model);
	}

	public synchronized String[] tokenize(String strData) throws Exception {
		return tokenizer.tokenize(strData);
	}
}
