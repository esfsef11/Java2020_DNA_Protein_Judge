package com.jbji.UserInterface.runner;

import javax.swing.*;

import com.jbji.UserInterface.frame.EncoderProtein;


/*
 * 样本选择窗口
 * 调用蛋白质编码器Frame进行操作
 * 
 * 
 * 
 * 
 * */

public class EncoderWindow implements Runnable{
	public void run() {
		//将Runnable加入事件队列
		SwingUtilities.invokeLater(()->{
			new EncoderProtein().setVisible(true);
		});
	}
}
