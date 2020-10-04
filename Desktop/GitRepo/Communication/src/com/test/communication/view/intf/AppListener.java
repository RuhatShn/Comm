package com.test.communication.view.intf;

import java.net.Socket;

public interface AppListener {
   
	void hideClick();
	void exitClick();
	void chooseConClick();
	void sendClick(String getTxtField);
	void getTextField(String getTxtField);
	void getFromFile(String getFromFile);
	void startServer();
	void startClient();
	void listenPortAndIp(String ip,String port);
	
}
