package com.jbji.sample;

import com.jbji.Encoder.EncodeUtility.EncoderType;
import com.jbji.Encoder.EncodeUtility.dict.AmoidDict;
import com.jbji.Encoder.EncodeUtility.dict.KmerBasedDict;
import com.jbji.sample.excpetion.SeqLibNotEncodedException;
import com.jbji.sample.excpetion.SeqLibNullDictException;
import com.jbji.sample.io.SeqGetter;

/*
 * 序列库
 * 包含一堆序列管理器和一本词典
 * 
 * 
 * */
public class SeqLib{
	private AmoidDict<String> dict;
	private SeqMan pMan;//管理正样本
	private SeqMan nMan;//管理负样本
	private SeqMan iMan;//管理独立样本
	private EncoderType codeType;
	
	public SeqLib(SeqGetter pGetter,SeqGetter nGetter,SeqGetter iGetter) {
		//Three Sequence Managers
		pMan = new SeqMan(pGetter);
		nMan = new SeqMan(nGetter);
		iMan = new SeqMan(iGetter);
		//initialize the dictionary
		dict = null;
		codeType = EncoderType.UNDEFINED;
	}
	
	
	public void initDict(int kmer_dim) {
		dict = new KmerBasedDict(kmer_dim,pMan,nMan);
	}
	public EncoderType getEncodeType() {
		return codeType;
	}
	public SeqMan[] getSeqMan() {
		return new SeqMan[]{pMan,nMan,iMan};
	}
	public SeqMan[] getSeqMan_BenchmarkSet() {
		return new SeqMan[] {pMan,nMan};
	}
	public SeqMan[] getSeqMan_IndependentSet() {
		return new SeqMan[] {iMan};
	}
	public void setEncoderType(EncoderType t) {
		codeType = t;
	}
	public String getEncodeStringP() throws SeqLibNotEncodedException{
		return "正样本的编码结果\n" + getEncodeString(pMan);
	}
	public String getEncodeStringN() throws SeqLibNotEncodedException{
		return "负样本的编码结果\n" + getEncodeString(nMan);
	}
	public String getEncodeStringI() throws SeqLibNotEncodedException{
		return "独立样本的编码结果\n" + getEncodeString(iMan);
	}
	private String getEncodeString(SeqMan i) throws SeqLibNotEncodedException {
		if(codeType == EncoderType.UNDEFINED) throw new SeqLibNotEncodedException();
		StringBuilder s  = new StringBuilder();
		for(Unit j : i) {//拿出序列管理器中所有的序列单元
			s.append(">").append(j.getDesc()).append("\n");
			s.append(j.getEncode(codeType).toString()).append("\n");
		}
		return s.toString();
	}
	public AmoidDict<String> getDict() throws SeqLibNullDictException{
		if(dict == null) throw new SeqLibNullDictException();
		return dict;
	}
	public int getDictSize() throws SeqLibNullDictException {
		if(dict == null) throw new SeqLibNullDictException();
		return dict.size();
	}
	public String getDictString() throws SeqLibNullDictException {
		if(dict == null) throw new SeqLibNullDictException();
		return "词典维度:" + dict.size() + "\n词典:\n" + dict.toString();
	}
	
}
