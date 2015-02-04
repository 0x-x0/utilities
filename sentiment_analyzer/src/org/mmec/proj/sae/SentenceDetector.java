package org.mmec.proj.sae;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

public class SentenceDetector {

	private SentenceDetectorME sdetector;

	public SentenceDetector() throws Exception {
		InputStream is = new FileInputStream("en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		sdetector = new SentenceDetectorME(model);
	}

	public synchronized String[] makeSentences(String strData)
			throws InvalidFormatException, IOException {
		return sdetector.sentDetect(strData);
	}

}
