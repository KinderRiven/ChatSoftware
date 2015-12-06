package com.client.kinderriven;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
/*
 * UI界面
 */
public class ClientUI extends JFrame {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField txtl_input;
	private JTextArea txta_output;
	private JButton btn_send;
	private Point preMouse;
	
	public JTextField getTxtl_input() {
		return txtl_input;
	}
	public void setTxtl_input(JTextField txtl_input) {
		this.txtl_input = txtl_input;
	}
	public JTextArea getTxta_output() {
		return txta_output;
	}
	public void setTxta_output(JTextArea txta_output) {
		this.txta_output = txta_output;
	}
	/*
	 * 窗体移动
	 */
	public void windowAction(){
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e){
				preMouse = e.getPoint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			
			public void mouseDragged(MouseEvent e){
				int _x = (int) (e.getPoint().getX() - preMouse.getX());
				int _y = (int) (e.getPoint().getY() - preMouse.getY());
				Point newPos = new Point(getLocation().x + _x, getLocation().y + _y);
				setLocation(newPos);
			}
		});
	}
	/*
	 * 按钮发送
	 */
	public void btnSendAction(){
		
		btn_send.addMouseListener(new MouseAdapter() {
				
			public void mouseClicked(MouseEvent e) {
				ChatClient.getCC().Exit();
			}
		});
	}
	/*
	 * 
	 */
	public void txtInputAction(){
		txtl_input.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					ChatClient.getCC().SendMessage(txtl_input.getText());
					txtl_input.setText("");
				}
			}
		});
	}
	
	public void txtOutputAction(){
		
	}
	public ClientUI() {
		
		getContentPane().setForeground(Color.WHITE);
		
		setAlwaysOnTop(true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		/*
		 * JFrame
		 */
		setTitle("网络编程测试");
		setUndecorated(true);
		setSize(new Dimension(792, 543));
		getContentPane().setLayout(null);
		
		txtl_input = new JTextField();
		txtl_input.setBounds(10, 494, 703, 40);
		txtl_input.setBackground(Color.WHITE);
		txtl_input.setFont(new Font("宋体", Font.BOLD, 22));
		getContentPane().add(txtl_input);
		txtl_input.setColumns(10);
		
		btn_send = new JButton("EXIT");
		btn_send.setBounds(717, 494, 65, 40);
		btn_send.setForeground(Color.WHITE);
		btn_send.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(btn_send);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 13, 772, 477);
		getContentPane().add(scrollPane);
		
		txta_output = new JTextArea();
		txta_output.setBackground(Color.WHITE);
		txta_output.setFont(new Font("楷体", Font.BOLD, 23));
		scrollPane.setViewportView(txta_output);
		
		/*
		 * add Action
		 */
		windowAction();
		btnSendAction();
		txtInputAction();
		txtOutputAction();
		/*
		 * My Setting
		 */
		txta_output.setEditable(false);
		
		JLabel lblHello = new JLabel("Welcome to the Chatroom");
		lblHello.setForeground(Color.BLACK);
		lblHello.setBackground(Color.BLACK);
		lblHello.setHorizontalAlignment(SwingConstants.CENTER);
		lblHello.setFont(new Font("SAO UI", Font.BOLD, 35));
		scrollPane.setColumnHeaderView(lblHello);
		
		setOpacity(0.9f);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI frame = new ClientUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
