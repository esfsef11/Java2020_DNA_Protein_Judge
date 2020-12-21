package com.jbji.sample;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.jbji.sample.io.SeqGetter;

/*
 * Sequence Manager 序列管理器
 * 可迭代，迭代结果给出每个序列单元
 * 
 * 
 * 
 * */

public class SeqMan implements Iterable<Unit>{
	private Set<Unit> data;
	
	public SeqMan(SeqGetter getter) {
		data = new HashSet<Unit>();
		UnitSeq tmp = new UnitSeq();
		while(getter.nextProtein(tmp)!=null) {
			Unit _tmp = new Unit(tmp);
			data.add(_tmp);
		}
	}
	
	public Iterator<Unit> iterator() {
		return data.iterator();
	}
	public int size() {
		return data.size();
	}
	
	public String toString() {
		return data.toString();
	}
}
