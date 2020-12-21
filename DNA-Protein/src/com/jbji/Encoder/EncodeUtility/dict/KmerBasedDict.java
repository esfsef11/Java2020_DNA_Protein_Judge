package com.jbji.Encoder.EncodeUtility.dict;

import java.util.HashSet;
import java.util.Iterator;

import com.jbji.sample.SeqMan;
import com.jbji.sample.Unit;

public class KmerBasedDict extends AmoidDict<String>{
	
	private int dim; //kmer分词的大小

	public KmerBasedDict(int dim) {
		this.dim = dim;
		data = new HashSet<String>();
	}
	
	public KmerBasedDict(int dim,SeqMan ... s) {//给定序列管理器和分词大小，建立词典
		this(dim);
		for(SeqMan ss : s) {
			for(Iterator<Unit> i = ss.iterator();i.hasNext();) {
				Unit tmp = (Unit) i.next();
				parseAdd(tmp.getSeq());
			}
		}
	}
	
	private void parseAdd(String str) {//将序列加入词典
		for(int i =0;i+dim < str.length();++i) {
			String tmp = str.substring(i,i+dim);
			if(tmp.matches(protein_regex)) {
				data.add(tmp);
			}
		}
	}
	
	public String toString() {
		return data.toString();
	}
	
}
