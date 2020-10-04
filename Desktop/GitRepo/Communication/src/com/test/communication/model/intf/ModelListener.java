package com.test.communication.model.intf;

import javax.swing.JButton;

public interface ModelListener {
	
	void hideClick(JButton hideButton);
	void exitClick(JButton exitButton);
	void chooseConClick(JButton chooseConBtn);
	void displayOwnMessage(String ownMessageText);
	void displayOwnFileName(String ownFileName);
	

}
