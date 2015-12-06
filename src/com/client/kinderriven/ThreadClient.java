package com.client.kinderriven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * 涉及客户端和服务器的连接
 */

public class ThreadClient {
	
	
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	
	public ThreadClient(){
		
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
		
	}

	void  ClientConnect(String address, int port){
			try {
				socket = new Socket(address, port);
				//System.out.println("Connect successfule!");
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				pw = new PrintWriter(socket.getOutputStream());
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	public static void main(String[] args) {
		
	}

}
