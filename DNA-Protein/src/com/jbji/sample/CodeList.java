package com.jbji.sample;

import java.util.ArrayList;
import java.util.Iterator;

public class CodeList<T extends Number> implements Iterable<T>{
	
	private ArrayList<T> freq;
	private int freqSum;
	
	public CodeList(ArrayList<T> codes) {
		this.freq = codes;
		this.freqSum = -1;
	}
	public CodeList(ArrayList<T> codes,int freqSum) {
		this.freq = codes;
		this.freqSum = freqSum;
	}
	
	public int getFreqSum() {
		return freqSum;
	}
	
	public int size() {
		return freq.size();
	}
	
	@Override
	public String toString() {
		return freq.toString();
	}
	
	@Override
	public int hashCode() {
		if(freq != null) {
			return freq.hashCode();
		}
		return 0;
	}

	@Override
	public Iterator<T> iterator() {
		return freq.iterator();
	}
}
