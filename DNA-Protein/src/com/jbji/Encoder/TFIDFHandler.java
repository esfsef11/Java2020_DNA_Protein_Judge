package com.jbji.Encoder;

import java.util.ArrayList;

import com.jbji.Encoder.EncodeUtility.DictIDFGenerator;
import com.jbji.Encoder.EncodeUtility.EncoderHandler;
import com.jbji.Encoder.EncodeUtility.EncoderType;
import com.jbji.sample.CodeList;
import com.jbji.sample.SeqLib;
import com.jbji.sample.SeqMan;
import com.jbji.sample.Unit;
import com.jbji.sample.excpetion.SeqLibNullDictException;

/*
 * IF-IDF的想法是，我对BGW的结果进行标准化，因此需要计算
 * 1、词频TF（Unit中Encode尺度），这个可以直接在Unit内部实现，因为我们可以轻松地获得Encode和相应的sum
 * 2、逆文档频率IDF（AmoidDict尺度，与具体的文档库有关），需要计算，语料库文档总数，这个可以在SeqMan中轻松获得
 * 			还需要计算，包含某词的文档总数，这个应当在生成encode的时候就顺便完成
 * 3、TF-IDF（Unit中Encode尺度）
 * */

public class TFIDFHandler extends EncoderHandler{
	public TFIDFHandler() {
		this.type = EncoderType.TF_IDF;
	}
	
	@Override
	public void encode(SeqLib lib) throws SeqLibNullDictException{
		//先进行BagOfWords编码
		new BagOfWordsHandler().encode(lib);
		//再创建词典的IDF
		DictIDFGenerator idfs = new DictIDFGenerator(lib.getDict(),lib.getSeqMan_BenchmarkSet());
		//可以通过迭代器取出idf中的数据
		//现在，我们只要计算TFIDF并将其加入到Encode里面就行了。
		SeqMan[] data_set = lib.getSeqMan();
		for(SeqMan i : data_set) {
			for(Unit j : i) {
				clacUnitTFIDF(idfs, j);
			}
		}
		lib.setEncoderType(type);
	}
	
	@SuppressWarnings("unchecked")
	private void clacUnitTFIDF(DictIDFGenerator idfs, Unit j) {
		CodeList<Integer> k = j.getEncode(EncoderType.BAG_OF_WORDS);
		float freq = (float)k.getFreqSum();
		//Create AnswerArrayList
		int size = k.size();
		ArrayList<Float> tf_idf = new ArrayList<>(size);
		for(int i=0;i<size;i++) {
			tf_idf.add(new Float(0));
		}
		//TF
		int index = 0;
		for(Integer f : k) {
			tf_idf.set(index++, ( (float) f) / freq);
		}
		//TF-IDF
		index = 0;
		for(Float idf :idfs) {
			tf_idf.set(index, tf_idf.get(index) * idf);
			index++;
		}
		//add the encode result
		j.addEncode(EncoderType.TF_IDF, new CodeList<Float>(tf_idf));
	}
	
}
