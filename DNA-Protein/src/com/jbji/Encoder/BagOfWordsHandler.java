package com.jbji.Encoder;

import com.jbji.Encoder.EncodeUtility.EncoderType;

public class BagOfWordsHandler extends OneHotEncodingHandler{
	
	public BagOfWordsHandler() {
		this.type = EncoderType.BAG_OF_WORDS;
	}
	
	@Override
	public int count(String parent, String child) {
		return parent.split(child).length-1;
	}
	
}
