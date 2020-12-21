package com.jbji.sample.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileSaver {
	public static void save(String to_save, File file_name) throws UnsupportedEncodingException, FileNotFoundException {
		try(PrintWriter out = new PrintWriter(//处理unicode
				new BufferedWriter(//writer的缓冲区
						new OutputStreamWriter(//构建桥梁 输入stream 输出writer
								new FileOutputStream(file_name),"utf8")))){
			out.println(to_save);
		}
	}
}
