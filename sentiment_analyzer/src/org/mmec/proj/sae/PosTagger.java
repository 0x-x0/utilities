package org.mmec.proj.sae;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;


public class PosTagger {
	private static final String PATH = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/wtpwebapps/SentimentAnlyzer/WEB-INF/classes/";
	private POSTaggerME tagger;

	public PosTagger() {
		POSModel model = new POSModelLoader()
		.load(new File(PATH+"en-pos-maxent.bin"));
		//URL resource = Thread.currentThread().getContextClassLoader().getResource("en-pos-maxent.bin");
		//POSModel model = new POSModelLoader().load(new File(resource.getFile()));
		tagger = new POSTaggerME(model);
		}

	public synchronized String[] tagPOS(String[] tokens) throws IOException {
		return tagger.tag(tokens);

	}

}
