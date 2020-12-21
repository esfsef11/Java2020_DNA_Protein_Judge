package com.jbji.sample.excpetion;

public class SeqLibNotEncodedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeqLibNotEncodedException() {
		super("异常：序列库未编码");
	}
}
