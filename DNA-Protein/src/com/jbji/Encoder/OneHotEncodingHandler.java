package com.jbji.Encoder;

import java.util.*;

import com.jbji.Encoder.EncodeUtility.EncoderHandler;
import com.jbji.Encoder.EncodeUtility.EncoderType;
import com.jbji.Encoder.EncodeUtility.dict.AmoidDict;
import com.jbji.sample.CodeList;
import com.jbji.sample.SeqLib;
import com.jbji.sample.SeqMan;
import com.jbji.sample.Unit;
import com.jbji.sample.excpetion.SeqLibNullDictException;


/*
 * OneHotEncoding
 * 静态方法encode 返回存有向量值的ArrayList;
 * ArrayList直接显式或隐式调用toString方法即可得到结果。
 * 
 * 
 * 
 * */
public class OneHotEncodingHandler extends EncoderHandler{
	public OneHotEncodingHandler() {
		this.type = EncoderType.ONE_HOT_ENCODING;
	}
	@Override
	public void encode(SeqLib lib) throws SeqLibNullDictException{
		
		AmoidDict<String> dict = lib.getDict();
		int size = lib.getDictSize();
		for(SeqMan i : lib.getSeqMan()) {//拿出每一组序列管理器
			//现在，我们正在处理一个语料库，语料库中是蛋白质序列以及附加信息
			for(Unit j : i) {//拿出序列管理器中所有的序列单元
				String tmp = j.getSeq();
				ArrayList<Integer> rst = new ArrayList<Integer>(size);
				int index = 0;
				int sum = 0;
				for(String k : dict) {
					int cnt = count(tmp,k);
					rst.add(index++, cnt);
					sum += cnt;
				}
				j.addEncode(type,new CodeList<Integer>(rst,sum));
			}
		}
		lib.setEncoderType(type);
	}
	
	public int count(String parent, String child) {
		if(parent.contains(child)) {
			return 1;
		}else {
			return 0;
		}
	}
}
