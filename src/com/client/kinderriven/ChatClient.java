package com.client.kinderriven;

import java.awt.EventQueue;
import java.io.IOException;


/*
 * 用于连接UI界面以及Socket通信
 */


public class ChatClient {
	
	private static ChatClient chatClient = new ChatClient();
	private static String ipAddress = "192.168.116.130"; 		//"192.168.116.130";
	private static int port = 2048;
	
	private ThreadClient threadClient;					//聊天socket连接
	private ClientUI UI;								//聊天UI界面
	
	private ChatClient(){
		threadClient = new ThreadClient();
		UI = new ClientUI();
	}
	
	public void Start(){
		ShowWindow(true);
		ClientConnect();
		getMessage();
	}
	
	public void ClientConnect(){
		threadClient.ClientConnect(ipAddress, port);
	}
	/*
	 * Exit
	 */
	public void Exit(){
		System.exit(0);
	}
	public void ShowWindow(boolean ok){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI.setVisible(ok);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static ChatClient getCC(){
		return chatClient;
	}
	
	public void getMessage(){
		new Thread(){
			
			public void run() {
				try {
					String str;
					while((str = threadClient.getBr().readLine()) != null){
						UI.getTxta_output().append("\n " + str);
					}
					threadClient.setBr(null);
					threadClient.setPw(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void SendMessage(String str){
		if(threadClient.getPw() != null){
			
			threadClient.getPw().println(str);
			threadClient.getPw().flush();
		}
		else{
			UI.getTxta_output().append("\n " + "[错误]:未连接到服务器");
		}
	}
	public static void main(String[] args) {
		
		ChatClient.getCC().Start();
		
	}
}
