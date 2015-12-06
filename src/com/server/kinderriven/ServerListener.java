package com.server.kinderriven;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * 服务器的启动
 */
public class ServerListener {


	ServerSocket socket;
	private int port = 2048;
	
	private ServerListener(){
		
	}
	public void StartListen(){
		try {
			int Case = 1;
			socket = new ServerSocket(port);
			System.out.println("Server is running...");
			while(true){
				Socket clientSocket = socket.accept();
				ThreadConnect tc = new ThreadConnect(clientSocket, "游客" + Case);
				Case++;
				tc.start();
				ClientManger.getCC().add(tc);
				System.out.println("Runing " 
						+ ClientManger.getCC().getClientNum() 
						+ "  Client(s)");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ServerListener serverListener = new ServerListener();
		serverListener.StartListen();
	}

}
