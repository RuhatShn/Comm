package com.test.communication.control;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.test.communication.comLayer.TcpComLayer;
import com.test.communication.comLayer.intf.TcpComLayerListener;
import com.test.communication.model.Model;
import com.test.communication.model.intf.ModelListener;
import com.test.communication.view.App;
import com.test.communication.view.intf.AppListener;

public class Control implements AppListener, ModelListener,TcpComLayerListener {

	private static Control INSTANCE;
	
	private final App app=new App();
	private final Model model=new Model();
	private final TcpComLayer tcpComLayer=new TcpComLayer();
	
	private final ExecutorService taskQueue=Executors.newSingleThreadExecutor(new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			
			Thread thread=new Thread(r);
			thread.setDaemon(true);
			
			return thread;
		}
	});
	
	private Control()
	{
		registerListeners();
	}
	
	public static Control getInstance()
	{
		if(INSTANCE==null)
		{
			INSTANCE=new Control();
		}
		
		return INSTANCE;
	}
	
	public void start()
	{
		app.open();
	}
	
	private void registerListeners()
	{
		app.addListener(this);
		model.addListener(this);
		tcpComLayer.addListener(this);
	}

	@Override
	public void hideClick() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->model.hideClick());
	}

	@Override
	public void exitClick() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->model.exitClick());
	}

	@Override
	public void sendClick(String getTxtField) {
		// TODO Auto-generated method stub
		taskQueue.execute(()->tcpComLayer.readFromTxtField(getTxtField));
	}

	@Override
	public void hideClick(JButton hideButton) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(()->app.setActionHideClick());
	}

	@Override
	public void exitClick(JButton exitButton) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(()->app.setActionExitClick());
	}


	@Override
	public void chooseConClick() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->model.chooseConClick());
	}

	@Override
	public void chooseConClick(JButton chooseConBtn) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(()->app.setActionChooseConClick());
	}

	
	@Override
	public void getTextField(String getTxtField) {
		// TODO Auto-generated method stub
		
		taskQueue.execute(()->tcpComLayer.readFromTxtField(getTxtField));
		taskQueue.execute(()->model.displayOwnMessageText(getTxtField));
	}

	@Override
	public void serverConnectionTrue() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.listenServerConTrue());
	}

	@Override
	public void serverConnectionFalse() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.listenServerConFalse());
	}

	@Override
	public void clientConnectionTrue() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.listenClientConTrue());
	}

	@Override
	public void clientConnectionFalse() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.listenClientConFalse());
	}

	@Override
	public void startServer() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->tcpComLayer.startServer());
	}

	@Override
	public void startClient() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->tcpComLayer.startClient());
	}

	@Override
	public void serverSocketClosed() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.initServerListener());
	}

	@Override
	public void clientSocketClosed() {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.initClientListener());
	}

	@Override
	public void listenReceiver(String getTxtFromSocket) {
		// TODO Auto-generated method stub
		
		taskQueue.execute(()->app.initDisplayOtherUserMessage(getTxtFromSocket));
		 
	}

	@Override
	public void displayOwnMessage(String ownMessageText) {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.initDisplayOwnMessage(ownMessageText));
	}

	@Override
	public void listenPortAndIp(String ip, String port) {
		// TODO Auto-generated method stub
		taskQueue.execute(()->tcpComLayer.setIpAndPort(ip, port));
	}

	@Override
	public void getFromFile(String getFromFile) {
		// TODO Auto-generated method stub
		taskQueue.execute(()->tcpComLayer.readFromTxtField(getFromFile));
		taskQueue.execute(()->model.displayOwnFileName(getFromFile));
	}

	@Override
	public void listenFileReceiver(String getFileTxtFromSocket,String getFileNameFromSocket) {
		// TODO Auto-generated method stub
		
		taskQueue.execute(()->app.createFile(getFileTxtFromSocket,getFileNameFromSocket));
	}

	@Override
	public void displayOwnFileName(String ownFileName) {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.initDisplayOwnFile(ownFileName));
	}

	@Override
	public void listenFileNameReceiver(String sendFileName) {
		// TODO Auto-generated method stub
		taskQueue.execute(()->app.initDisplayOtherUserFile(sendFileName));
	}

	

	

	
	

	

	
	


}
