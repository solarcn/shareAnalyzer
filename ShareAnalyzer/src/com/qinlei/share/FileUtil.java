package com.qinlei.share;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtil {
	static PrintWriter pw = null;
	
	static void initializeAPrintWriter(String fileName){
		try {
			if(pw == null) {
				pw = new PrintWriter(new FileWriter(fileName,true));
				System.out.println(fileName + " has been created!");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Failed to create or open a file ");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void closeFileOutputStream(){
		if(pw != null) {
			pw.flush();
			pw.close();
			pw = null;
		}
	}
	
	static boolean saveALine(String str){
		if(pw != null) {
			pw.println(str);
			return true;
		}
		System.out.println("PrintWriter has not been inistiallied!");
		return false;
	}
	static void flush(){
		if(pw != null) {
			pw.flush();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
