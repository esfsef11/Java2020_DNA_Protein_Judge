package com.jbji.Encoder.EncodeUtility;

import java.util.ArrayList;
import java.util.Iterator;

import com.jbji.Encoder.EncodeUtility.dict.AmoidDict;
import com.jbji.sample.CodeList;
import com.jbji.sample.SeqMan;
import com.jbji.sample.Unit;

public class DictIDFGenerator implements Iterable<Float>{
	private AmoidDict<String> dict;
	private ArrayList<Float> has_item_doc_cnt;
	private ArrayList<Float> idf;
	private SeqMan[] seqs;
	private int doc_sum;
	
	public DictIDFGenerator(AmoidDict<String> dict, SeqMan ...seqs) {
		this.dict = dict;
		this.seqs = seqs;
		this.doc_sum = 0;
		has_item_doc_cnt = new ArrayList<>(dict.size());
		for(int i = 0 ; i < dict.size() ; ++i) {
			has_item_doc_cnt.add(new Float(0));
		}
		idf = new ArrayList<>(dict.size());
		initSeqs();
	}
	
	@SuppressWarnings("unchecked")
	private void initSeqs() {
		for(SeqMan seq : this.seqs) {//对于输入的所有已编码序列
			doc_sum += seq.size();
			for(Unit u : seq) {//记录每一个单元中每一个词的出现次数
				CodeList<Integer> tmp = u.getEncode(EncoderType.BAG_OF_WORDS);
				if(tmp != null) {
					int i = 0;
					for(Integer codebit : tmp) if(!codebit.equals(0)) addItemAppearance(i++);
				}
			}
		}
		calcIDF();
	}
	
	private void addItemAppearance(int index) {
		has_item_doc_cnt.set(index, has_item_doc_cnt.get(index)+1);
	}
	
	private void calcIDF() {
		for(int i = 0; i < has_item_doc_cnt.size();i++) {
			Float tmp_ans = (float) Math.log( (double)doc_sum/(double)(has_item_doc_cnt.get(i)+1));
			idf.add(i,tmp_ans);
		}
	}

	@Override
	public Iterator<Float> iterator() {
		return idf.iterator();
	}
}
