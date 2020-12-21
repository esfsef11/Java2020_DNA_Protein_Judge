package com.jbji.Encoder.EncodeUtility.dict;

import java.util.Iterator;
import java.util.Set;

public abstract class AmoidDict<T> implements Iterable<T>{
	
	protected Set<T> data;
	protected final static String protein_regex = "[ACDEFGHIKLMNPQRSTVWY]+";
	
	public int size() {
		return data.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}

}
