package com.server.kinderriven;

import java.util.Vector;
/*
 * 线程的综合管理
 */
public class ClientManger {

	private static ClientManger cm = new ClientManger();
	
	Vector<ThreadConnect>vecThreadClient = new Vector<ThreadConnect>();
	
	private ClientManger(){
		
	}
	
	public int getClientNum(){
		return vecThreadClient.size();
	}
	
	public static ClientManger getCC(){
		return cm;
	}
	
	public void add(ThreadConnect tc){
		vecThreadClient.addElement(tc);
	}
	
	public void sendMessage(String str){
		for(int i = 0; i < vecThreadClient.size(); i++){
			ThreadConnect tc = vecThreadClient.elementAt(i);
			tc.sendMessage(str);
		}
	}
	
	public void delThread(ThreadConnect threadConnect){
		for(int i = 0; i < vecThreadClient.size(); i++){
			ThreadConnect tc = vecThreadClient.elementAt(i);
			if(tc.equals(threadConnect)){
				vecThreadClient.removeElementAt(i);
				break;
			}
		}
	}
	public static void main(String[] args) {
		
	}

}
