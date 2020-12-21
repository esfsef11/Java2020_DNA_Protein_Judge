package com.jbji.Encoder.EncodeUtility;

import com.jbji.sample.SeqLib;
import com.jbji.sample.SeqMan;
import com.jbji.sample.Unit;
import com.jbji.sample.excpetion.SeqLibNullDictException;

/*
 * 一个可以重置编码的编码器
 * 
 * 
 * 
 * */

public class ResetHandler extends EncoderHandler{
	public ResetHandler() {
		this.type = EncoderType.UNDEFINED;
	}

	@Override
	public void encode(SeqLib lib) throws SeqLibNullDictException {
		for(SeqMan i : lib.getSeqMan()) {
			for(Unit j : i) {
				j.resetEncode();
			}
		}
		lib.setEncoderType(type);
	}
}
