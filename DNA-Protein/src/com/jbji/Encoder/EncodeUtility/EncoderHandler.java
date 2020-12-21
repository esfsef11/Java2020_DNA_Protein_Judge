package com.jbji.Encoder.EncodeUtility;

import com.jbji.sample.SeqLib;
import com.jbji.sample.excpetion.SeqLibNullDictException;

public abstract class EncoderHandler {
	
	protected EncoderType type;
	public abstract void encode(SeqLib lib) throws SeqLibNullDictException;
	public EncoderType getType(){
		return type;
	}
}
