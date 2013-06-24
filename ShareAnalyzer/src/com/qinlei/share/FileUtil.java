package com.qinlei.share;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileUtil {
	static PrintWriter pw = null;
	
	static PrintWriter getFileOutputStream(String fileName){
		try {
			if(pw == null) pw = new PrintWriter(new FileOutputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			System.out.println("Failed to create or open a file ");
			e.printStackTrace();
		}
		return pw;
	}
	static void closeFileOutputStream(){
		if(pw != null) {
			pw.flush();
			pw.close();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
