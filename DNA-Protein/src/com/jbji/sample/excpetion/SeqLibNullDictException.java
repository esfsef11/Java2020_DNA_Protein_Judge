package com.jbji.sample.excpetion;

public class SeqLibNullDictException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeqLibNullDictException() {
		super("异常：序列库的词典未定义，请先利用序列管理器生成序列库词典再继续");
	}
}
