package com.jbji.UserInterface.runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

import com.jbji.Encoder.BagOfWordsHandler;
import com.jbji.Encoder.OneHotEncodingHandler;
import com.jbji.Encoder.TFIDFHandler;
import com.jbji.Encoder.EncodeUtility.EncoderHandler;
import com.jbji.Encoder.EncodeUtility.ResetHandler;
import com.jbji.sample.SeqLib;
import com.jbji.sample.excpetion.SeqLibNotEncodedException;
import com.jbji.sample.excpetion.SeqLibNullDictException;
import com.jbji.sample.io.FileSaver;
import com.jbji.sample.io.SeqGetter;


public class EncoderConsole implements Runnable{
	private HashMap<String,EncoderHandler> handlers = new HashMap<>();
	private SeqLib lib;
	private int kmer_dim;
	public EncoderConsole() {
		handlers.put("B", new BagOfWordsHandler());
		handlers.put("O", new OneHotEncodingHandler());
		handlers.put("T", new TFIDFHandler());
		handlers.put("R", new ResetHandler());
	}
	public void run() {
		Scanner in = new Scanner(System.in);//系统资源一旦关闭就不能再重启!
		while(true) {
			try{
				showTitle(in);
				initSeqLib(in);
				initDict(in);
				doEncode(in);
			}catch(IOException ex) {
				System.out.println("[错误] 打开数据集文件时出现错误.");
			} catch (SeqLibNullDictException e) {
				System.out.println("[错误] 序列库的词典为空，请先初始化序列库词典");
			} catch (SeqLibNotEncodedException e) {
				System.out.println("[错误] 序列库未编码，请先编码序列库");
			}
		}
	}
	private void doEncode(Scanner in) throws SeqLibNullDictException, UnsupportedEncodingException,
			FileNotFoundException, SeqLibNotEncodedException {
		System.out.println("[特征提取] 选择你想要的编码方式(输入对应大写字母代号):"
				+ "\n\tO - One Hot Encoding 独热编码"
				+ "\n\tB - Bag Of Words "
				+ "\n\tT - TF-IDF");
		String op = in.next();
		EncoderHandler h = handlers.get(op);
		if(h != null) {
			System.out.println("[特征提取] 正在编码，请稍候... "
					+ "\n注:Kmer维度较大时此项耗时可能较长，请耐心等候");
			long startTime = System.currentTimeMillis();
			h.encode(lib);
			long endTime = System.currentTimeMillis();
			System.out.println("[特征提取] 编码完毕！耗时: "+(endTime-startTime) + "ms"
					+ "\n\t由于控制台可能无法完整展示提取结果, 结果仅在保存后可查看"
					+ "\n\t是否保存本次编码结果到文件？Y/N");
			String ops = in.next();
			switch(ops) {
				case "Y":
					String type_str = lib.getEncodeType().toString()+"_";
					String kmer_str = "KmerDim_"+kmer_dim + "_";
					System.out.println("[文件保存] "+type_str+": 正在保存到文件...");
					
					String p_str = type_str+kmer_str+"encoded_positive.txt";
					String n_str = type_str+kmer_str+"encoded_negtive.txt";
					String i_str = type_str+kmer_str+"encoded_independent.txt";
					String dict_str = type_str+kmer_str+"dict.txt";
					save(p_str, lib.getEncodeStringP());
					save(n_str, lib.getEncodeStringN());
					save(i_str, lib.getEncodeStringI());
					save(dict_str, lib.getDictString());
					break;
				case "N":
					System.out.println("[文件保存] 保存取消.");
					break;
				default:
					System.out.println("[文件保存] 操作无效.");
					break;
			}
		}else {
			System.out.print("[特征提取] 操作无效.");
		}
	}
	private void save(String p_str, String to_save_str) throws UnsupportedEncodingException, FileNotFoundException {
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		FileSaver.save(to_save_str, new File(p_str));
		endTime = System.currentTimeMillis();
		System.out.println("[文件保存] 文件"+p_str+"保存完毕. 耗时: "+(endTime-startTime)+"ms");
	}
	private void initDict(Scanner in) {
		System.out.println("[词典初始化] 指定Kmer维度:");
		kmer_dim = in.nextInt();
		long startTime = System.currentTimeMillis();
		lib.initDict(kmer_dim);
		long endTime = System.currentTimeMillis();
		System.out.println("[词典初始化] 初始化完毕,耗时: "+(endTime-startTime) + "ms");
	}
	private void initSeqLib(Scanner in) throws IOException {
		SeqGetter pGetter = inputFile(in,"正样本");
		SeqGetter nGetter = inputFile(in,"负样本");
		SeqGetter iGetter = inputFile(in,"独立样本");
		lib = new SeqLib(pGetter,nGetter,iGetter);
	}
	private SeqGetter inputFile(Scanner in, String type_str) throws IOException {
		System.out.println("[文件输入] 请指定要提取特征的数据集的文件路径("+type_str+"):");
		String loc = in.next();
		return new SeqGetter(new File(loc));
	}
	private void showTitle(Scanner in) {
		final String sp = "----------------------------";
		System.out.println(sp);
		System.out.println("[DNA结合蛋白分析器 - 终端]"
				+ "\n请选择你想进行的操作(输入对应编号):"
				+ "\n\t任意字符\t - 进入程序流程"
				+ "\n\t0\t - 结束程序");
		System.out.println(sp);
		String op = in.next();
		if(op.equals("0")) System.exit(0);
	}
}
