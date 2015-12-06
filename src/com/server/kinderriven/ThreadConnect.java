package com.server.kinderriven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/*
 * 单个客户端的监听
 * 这里使用多线程进行监听
 */


public class ThreadConnect extends Thread{

	Socket socket;
	String name;
	PrintWriter pw;
	BufferedReader br;
	public ThreadConnect(Socket socket, String name){
		try {
			this.socket = socket;
			this.name = name;
			pw = new PrintWriter(this.socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(String str){
		if(pw != null){
			pw.println(str);
			pw.flush();
		}
	}
	public void run(){
		try {
			String str;
			
			while((str = br.readLine()) != null){
				str = "[" + name + "]" + str;
				ClientManger.getCC().sendMessage(str);
			}
		} catch (IOException e) {
			
			
		} finally{
			br = null;
			pw = null;
			ClientManger.getCC().delThread(this);
		}
	}
	public static void main(String[] args) {
		
	}

}
