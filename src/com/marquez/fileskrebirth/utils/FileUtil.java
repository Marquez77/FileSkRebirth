package com.marquez.fileskrebirth.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.marquez.fileskrebirth.Main;

public class FileUtil {
	
	public static String encoding = "UTF-8";
	
	public static String[] readFiles(File file) throws IOException {
		String text = readFile(file);
		String[] list = text.split("\r\n");
		return list;
	}
	
	public static String readFile(File file) throws IOException {
		FileInputStream fileInput = new FileInputStream(file); 
		InputStreamReader inputReader = new InputStreamReader(fileInput, encoding); 
		BufferedReader bufferedTextFileReader = new BufferedReader(inputReader);
		StringBuilder contentReceiver = new StringBuilder();
		String buf = "";
		while((buf = bufferedTextFileReader.readLine()) != null) {
			contentReceiver.append(buf).append("\r\n");
		}
		bufferedTextFileReader.close();
		return contentReceiver.toString();
	}
	
	public static void addFile(File file, String text) throws IOException {
		FileOutputStream fileOutput = new FileOutputStream(file, true);
		OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput, encoding);
		PrintWriter writer = new PrintWriter(outputWriter);
		StringBuilder sb = new StringBuilder();
		sb.append(text).append("\r\n");
		writer.write(sb.toString());
		writer.close();
	}
	
	public static void writeFile(File file, String[] text) throws IOException {
		StringBuilder sb = new StringBuilder();
		for(String s : text) {
			if(s == null) continue;
			sb.append(s).append("\r\n");
		}
		writeFile(file, sb.toString());
	}
	
	public static void writeFile(File file, String text) throws IOException {
		FileOutputStream fileOutput = new FileOutputStream(file);
		OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput, encoding);
		BufferedWriter output = new BufferedWriter(outputWriter);
		output.write(text);
		output.flush();
		output.close();
	}
	
	public static void setFile(File file, String key, String value) throws IOException {
		StringBuilder find = new StringBuilder().append(key).append(": ");
		StringBuilder last = new StringBuilder(find).append(value);
		if(file == null || !file.exists()) return;
		boolean b = false;
		BufferedReader r = new BufferedReader(new FileReader(file));
		List<String> list = new ArrayList<String>();
		String s = "";
		while((s = r.readLine()) != null) {
			if(s.startsWith(find.toString())) {
				list.add(last.toString());
				b = true;
				continue;
			}
			list.add(s);
		}
		r.close();
		if(!b) list.add(last.toString());
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append("\r\n");
		}
		BufferedWriter w = new BufferedWriter(new FileWriter(file));
		w.write(sb.toString());
		w.flush();
		w.close();
	}
	
	public static String getFile(File file, String key) throws IOException {
		StringBuilder find = new StringBuilder().append(key).append(": ");
		BufferedReader r = new BufferedReader(new FileReader(file));
		String s = "";
		while((s = r.readLine()) != null) {
			if(s.startsWith(find.toString())) {
				r.close();
				return s.replace(find.toString(), "");
			}
		}
		r.close();
		return "";
	}
	
	
	private static long creating = 0;
	private static int createCount = 0;
	private static long deleting = 0;
	private static int deleteCount = 0;
	
	public static boolean canCreateFile() {
		if(System.currentTimeMillis()-creating >= 1000) {
			creating = System.currentTimeMillis();
			createCount = 1;
			return true;
		}else {
			if(createCount++ >= Main.config.getInt("file creation maximum")) return false;
			else return true;
		}
	}
	
	public static boolean canDeleteFile() {
		if(System.currentTimeMillis()-deleting >= 1000) {
			deleting = System.currentTimeMillis();
			deleteCount = 1;
			return true;
		}else {
			if(deleteCount++ >= Main.config.getInt("file deletion maximum")) return false;
			else return true;
		}
	}
}
