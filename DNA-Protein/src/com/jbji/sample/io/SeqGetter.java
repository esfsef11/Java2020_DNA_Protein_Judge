package com.jbji.sample.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbji.sample.UnitSeq;
/*
 * 样本获取器，
 * 输入是一个文件地址
 * 输出是以一连串next方法获取的SampleUnit样本单元序列
 * 此处设计考虑是，我们可以使用容器存储样本单元，或者仅仅是对样本单元进行输出
 * 
 * */
public class SeqGetter {
	private String data;
	private Matcher matcher;
	private static final String regex = "^>([^\\s]+)\\s*$\\s*^([\\w\\s]+)\\s*$";
	public SeqGetter(File file) throws IOException{
		try(BufferedReader in = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)))){
			StringBuilder tmp = new StringBuilder();
			String line;
			while((line = in.readLine()) != null) {
				tmp.append(line).append("\n");
			}
			data = tmp.toString();
			initMatcher();
		}
	}
	public SeqGetter(String fasta_str) {
		data = new String(fasta_str);
		initMatcher();
	}
	private void initMatcher() {
		Pattern pattern = Pattern.compile(regex,Pattern.MULTILINE);
		matcher = pattern.matcher(data);
	}
	public String nextProtein(UnitSeq s){
		if(matcher.find()) {
			if(s!=null) s.setData(matcher.group(1), matcher.group(2));
			return matcher.group(0);
		}
		return null;
	}
}
