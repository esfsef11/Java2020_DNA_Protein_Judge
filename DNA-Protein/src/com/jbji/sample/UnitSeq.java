package com.jbji.sample;

/*
 * 
 * 读入的一个基本的蛋白质序列单元，存储了描述信息和序列信息
 * 提供setData和toString方法
 * 
 * 
 * 
 * */

public class UnitSeq{
	private String description;
	private String sequence;
	//constructors
	public UnitSeq() {
		description = null;
		sequence = null;
	}
	public UnitSeq(String _description,String _sequence) {
		description = _description;
		sequence = _sequence;
	}
	//拷贝构造
	public UnitSeq(UnitSeq _toCopy) {
		this(_toCopy.description,_toCopy.sequence);
	}
	//methods
	public void setData(String _description,String _sequence) {
		this.description = _description;
		this.sequence = _sequence;
	}
	public boolean contains(CharSequence s) {
		return sequence.contains(s);
	}
	public String getDesc() {
		return description;
	}
	public String getSeq() {
		return sequence;
	}
	public String toString() {
		return "Description: "+description+"\n"+"Sequence: "+sequence;
	}
	@Override
	public int hashCode() {
		if(sequence != null && description != null) {
			return sequence.hashCode() + description.hashCode();
		}
		return 0;
	}
}
