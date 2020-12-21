package com.jbji;

import com.jbji.UserInterface.runner.*;

public class Main {

	public static void main(String[] args) {
		Thread window = new Thread(new EncoderWindow());
		window.start();
		Thread console = new Thread(new EncoderConsole());
		console.start();
	}

}
