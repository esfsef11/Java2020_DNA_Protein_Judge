package com.jbji.UserInterface.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.jbji.Encoder.BagOfWordsHandler;
import com.jbji.Encoder.OneHotEncodingHandler;
import com.jbji.Encoder.TFIDFHandler;
import com.jbji.Encoder.EncodeUtility.EncoderHandler;
import com.jbji.sample.SeqLib;
import com.jbji.sample.excpetion.SeqLibNotEncodedException;
import com.jbji.sample.excpetion.SeqLibNullDictException;
import com.jbji.sample.io.FileSaver;
import com.jbji.sample.io.SeqGetter;

//这里直接继承JFrame进行组件设置，可以直接调用JFrame继承下来的方法
public class EncoderProtein extends JFrame{
	private JButton buttonAbout;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem menuOpenP;
	private JMenuItem menuOpenN;
	private JMenuItem menuOpenI;
	private JMenuItem menuSaveAll;
	private JMenuItem menuSaveD;
	private JMenuItem menuSaveP;
	private JMenuItem menuSaveN;
	private JMenuItem menuSaveI;
	private JMenuItem menuClose;
	
	private JMenu runMenu;
	private JMenuItem menuRunBagOfWords;
	private JMenuItem menuRunOneHotEncoding;
	private JMenuItem menuRunTFIDF;
	
	private JMenu helpMenu;
	private JMenuItem menuHelp;
	private JMenuItem menuAbout;
	
	private JLabel logLabel;
	private JTextArea textArea;
	private JLabel stateBar;
	
	private File pFile;
	private File nFile;
	private File iFile;
	private int kmer_dim;
	private SeqLib lib;
	
	public EncoderProtein() {
		initComponents();
		initEventListeners();
		this.setLocationRelativeTo(null);
		pFile = null;
		nFile = null;
		iFile = null;
		kmer_dim = 1;
	}
	private void initComponents() {
		setTitle("DNA结合蛋白分析器 - 特征提取");
		setSize(500,350);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		initFileMenu();
		initRunMenu();
		initHelpMenu();
		initMenuBar();
		initTextArea();
		initStateBar();
	}
	private void initFileMenu() {
		fileMenu = new JMenu("文件");
		menuOpenP = new JMenuItem("打开正数据集(DNA结合蛋白数据集)");
		menuOpenN = new JMenuItem("打开负数据集(非DNA结合蛋白数据集)");
		menuOpenI = new JMenuItem("打开独立数据集");
		menuSaveAll = new JMenuItem("保存全部数据至当前文件夹");
		menuSaveD = new JMenuItem("保存Kmer词典");
		menuSaveP = new JMenuItem("保存编码数据 - 正样本");
		menuSaveN = new JMenuItem("保存编码数据 - 负样本");
		menuSaveI = new JMenuItem("保存编码数据 - 独立数据集");
		menuClose = new JMenuItem("退出程序");
		
		fileMenu.add(menuOpenP);
		fileMenu.add(menuOpenN);
		fileMenu.add(menuOpenI);
		fileMenu.addSeparator();
		fileMenu.add(menuSaveAll);
		fileMenu.addSeparator();
		fileMenu.add(menuSaveD);
		fileMenu.addSeparator();
		fileMenu.add(menuSaveI);
		fileMenu.add(menuSaveP);
		fileMenu.add(menuSaveN);
		fileMenu.addSeparator();
		fileMenu.add(menuClose);
	}
	private void initRunMenu() {
		runMenu = new JMenu("运行");
		menuRunBagOfWords = new JMenuItem("Bag Of Words 编码");
		menuRunOneHotEncoding = new JMenuItem("One Hot Encoding 编码");
		menuRunTFIDF = new JMenuItem("TF-IDF 编码");
		
		runMenu.add(menuRunBagOfWords);
		runMenu.add(menuRunOneHotEncoding);
		runMenu.add(menuRunTFIDF);
	}
	private void initHelpMenu() {
		helpMenu = new JMenu("帮助");
		menuAbout = new JMenuItem("关于");
		menuHelp = new JMenuItem("如何使用这个程序");
		
		helpMenu.add(menuHelp);
		helpMenu.addSeparator();
		helpMenu.add(menuAbout);
	}
	private void initMenuBar() {
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}
	private void initTextArea() {
		logLabel = new JLabel(" ----- 程序日志 Logs ----- ");
		getContentPane().add(logLabel,BorderLayout.NORTH);
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane panel = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		log("欢迎使用DNA结合蛋白分析器(仅特征提取)!");
		logHelp();
	}
	private void logHelp() {
		log("[简易使用指南]"
				+ "\n选择菜单项[文件]来打开数据集文件，"
				+ "\n然后选择菜单[运行]来执行序列编码，"
				+ "\n最后，在菜单[文件]中保存编码数据");
	}
	private void initStateBar() {
		stateBar = new JLabel("状态: 就绪");
		stateBar.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(stateBar, BorderLayout.SOUTH);
	}
	private void initButtons() {
		buttonAbout = new JButton("关于");
		getContentPane().add(buttonAbout);
	}
	private void initFileMenuListeners() {
		menuOpenP.addActionListener(
				(event) -> {
					pFile = openFile();
					if(pFile != null) log("打开DNA结合蛋白数据集文件:"+pFile);
				});
		menuOpenN.addActionListener(
				(event) -> {
					nFile = openFile();
					if(nFile != null) log("打开非DNA结合蛋白数据集文件:"+nFile);
				});
		menuOpenI.addActionListener(
				(event) -> {
					iFile = openFile();
					if(iFile != null) log("打开独立数据集文件:"+iFile);
				});
		menuClose.addActionListener(
				event ->{
					System.exit(0);
				});
		menuSaveAll.addActionListener(l->{
			try {
				saveDict();
				saveP();
				saveN();
				saveI();
			}catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "错误：特征提取未进行，未生成编码结果，无法保存至文件");
				log("错误：特征提取未进行，未生成编码结果，无法保存至文件");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "错误：保存时发生未知错误");
				log("错误：保存时发生未知错误");
			}
			
		});
		menuSaveD.addActionListener(l->{
			try {
				saveDict();
				log("保存完毕");
				status("保存完毕");
			}catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "错误：特征提取未进行，未生成编码结果，无法保存至文件");
				log("错误：特征提取未进行，未生成编码结果，无法保存至文件");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "错误：保存时发生未知错误");
				log("错误：保存时发生未知错误");
			}
		});
		menuSaveP.addActionListener(l->{
			try {
				saveP();
				log("保存完毕");
				status("保存完毕");
			}catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "错误：特征提取未进行，未生成编码结果，无法保存至文件");
				log("错误：特征提取未进行，未生成编码结果，无法保存至文件");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "错误：保存时发生未知错误");
				log("错误：保存时发生未知错误");
			}

		});
		menuSaveN.addActionListener(l->{
			try {
				saveN();
				log("保存完毕");
				status("保存完毕");
			}catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "错误：特征提取未进行，未生成编码结果，无法保存至文件");
				log("错误：特征提取未进行，未生成编码结果，无法保存至文件");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "错误：保存时发生未知错误");
				log("错误：保存时发生未知错误");
			}
		});
		menuSaveI.addActionListener(l->{
			try {
				saveI();
				log("保存完毕");
				status("保存完毕");
			}catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "错误：特征提取未进行，未生成编码结果，无法保存至文件");
				log("错误：特征提取未进行，未生成编码结果，无法保存至文件");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "错误：保存时发生未知错误");
				log("错误：保存时发生未知错误");
			}
		});
		
	}
	private void saveDict() throws UnsupportedEncodingException, FileNotFoundException, SeqLibNullDictException {
		String type_str = lib.getEncodeType().toString()+"_";
		String kmer_str = "KmerDim_"+kmer_dim + "_";
		String dict_str = type_str+kmer_str+"dict.txt";
		save(dict_str, lib.getDictString());
	}
	private void saveP() throws UnsupportedEncodingException, FileNotFoundException, SeqLibNullDictException, SeqLibNotEncodedException {
		String type_str = lib.getEncodeType().toString()+"_";
		String kmer_str = "KmerDim_"+kmer_dim + "_";
		String p_str = type_str+kmer_str+"encoded_positive.txt";
		save(p_str, lib.getEncodeStringP());
	}
	private void saveN() throws UnsupportedEncodingException, FileNotFoundException, SeqLibNullDictException, SeqLibNotEncodedException {
		String type_str = lib.getEncodeType().toString()+"_";
		String kmer_str = "KmerDim_"+kmer_dim + "_";
		String n_str = type_str+kmer_str+"encoded_negtive.txt";
		save(n_str, lib.getEncodeStringN());
	}
	private void saveI() throws UnsupportedEncodingException, FileNotFoundException, SeqLibNullDictException, SeqLibNotEncodedException {
		String type_str = lib.getEncodeType().toString()+"_";
		String kmer_str = "KmerDim_"+kmer_dim + "_";
		String i_str = type_str+kmer_str+"encoded_independent.txt";
		save(i_str, lib.getEncodeStringI());
	}
	private File openFile() {
		  JFileChooser fileChooser = new JFileChooser();
	      fileChooser.setFileSelectionMode(
	         JFileChooser.FILES_ONLY );
	         //显示打开文件对话框
	      int result = fileChooser.showOpenDialog( null );
	 
	      // user clicked Cancel button on dialog
	      if ( result == JFileChooser.CANCEL_OPTION )
	         return null;

	      return fileChooser.getSelectedFile();
	      
	}
	private void initHelpMenuListeners() {
		menuHelp.addActionListener(e->{
			logHelp();
		});
		menuAbout.addActionListener((event)->
		{
			JOptionPane.showOptionDialog(null,
					"DNA结合蛋白分析器 - 仅特征提取\n作者：jbji(李春良)\n\t(计算机学院 2019级本科生)\n学号：1120193226",
					"关于DNA结合蛋白分析器",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,null,null);
		});
	}
	private void initEventListeners() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initFileMenuListeners();
		initHelpMenuListeners();
		initRunMenuListeners();
	}
	private void initRunMenuListeners() {
		menuRunBagOfWords.addActionListener(l->{
			EncoderHandler h = new BagOfWordsHandler();
			if(checkFile()) return;
			runHandler(h);
		});
		menuRunOneHotEncoding.addActionListener(l->{
			EncoderHandler h = new OneHotEncodingHandler();
			if(checkFile()) return;
			runHandler(h);
		});
		menuRunTFIDF.addActionListener(l->{
			EncoderHandler h = new TFIDFHandler();
			if(checkFile()) return;
			runHandler(h);
		});
	}
	private void runHandler(EncoderHandler h) {
		log("正在处理: " + h.getType());
		if(specifyKmer()) {
			try {
				status("正在建立序列库");
				long startTime = System.currentTimeMillis();
				lib = new SeqLib(new SeqGetter(pFile),new SeqGetter(nFile),new SeqGetter(iFile));
				long endTime = System.currentTimeMillis();
				log("序列库建立完毕,耗时: "+(endTime-startTime) + "ms");
				
				status("正在初始化词典");
				startTime = System.currentTimeMillis();
				lib.initDict(kmer_dim);
				endTime = System.currentTimeMillis();
				log("词典初始化完毕,耗时: "+(endTime-startTime) + "ms");
				
				status("正在处理编码");
				
				startTime = System.currentTimeMillis();
				h.encode(lib);
				endTime = System.currentTimeMillis();
				log("编码"+h.getType()+"处理完毕,耗时: "+(endTime-startTime) + "ms");
				log("现在你可以选择菜单中的[文件]来保存编码结果");
				
			} catch (IOException e) {
				status("就绪");
				log("处理文件时发生错误，操作取消");
			} catch (SeqLibNullDictException e) {
				status("就绪");
				log("序列库词典未生成，操作取消");
			}
			status("完成"+h.getType()+"特征提取，Kmer = "+kmer_dim);
		}else {
			log("操作取消");
		}
	}
	private boolean checkFile() {
		if(iFile==null || pFile == null || nFile == null) {
			final String error = "错误：数据集文件不足，请先选择三个数据集文件.";
			JOptionPane.showMessageDialog(null, error);
			log(error);
			return true;
		}
		return false;
	}
	private void status(String status_str) {
		stateBar.setText("状态: "+status_str);
	}
	private void log(String log_str) {
		textArea.append(">>" + log_str + "\n");
	}
	private void save(String p_str, String to_save_str) throws UnsupportedEncodingException, FileNotFoundException {
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		FileSaver.save(to_save_str, new File(p_str));
		endTime = System.currentTimeMillis();
		log("文件"+p_str+"保存完毕. 耗时: "+(endTime-startTime)+"ms");
	}
	private boolean specifyKmer() {
		status("正在处理");
		String s = JOptionPane.showInputDialog("请输入Kmer的维度(词典中分词大小):");
		if(s == null) return false;
		try {
			int val = Integer.valueOf(s);
			if(val >0) {
				kmer_dim = val;
				log("Kmer值(分词大小)："+kmer_dim);
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "错误：输入的Kmer维度无效.");
				return false;
			}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "错误：输入的数字格式错误.");
			return false;
		}
	}
}
