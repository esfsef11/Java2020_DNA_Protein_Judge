package com.jbji.sample;

import java.util.HashMap;

import com.jbji.Encoder.EncodeUtility.EncoderType;

public class Unit {
	private UnitSeq origin;
	private HashMap<EncoderType,CodeList> encodes;
	
	public Unit() {
		this.origin = null;
		this.encodes = new HashMap<EncoderType,CodeList>();
	}
	public Unit(UnitSeq origin) {
		this();
		this.origin = new UnitSeq(origin);
	}
	
	public void addEncode(EncoderType e , CodeList u) {
		encodes.put(e,u);
	}
	
	public CodeList getEncode(EncoderType e) {
		return encodes.get(e);
	}
	
	public void resetEncode() {
		encodes.clear();
	}
	
	//methods for origin
	public String getDesc() {
		return origin.getDesc();
	}
	public String getSeq() {
		return origin.getSeq();
	}
	@Override
	public int hashCode() {
		return 47+3*origin.hashCode();
	}
	public String toString() {
		return origin.toString();
	}
}
