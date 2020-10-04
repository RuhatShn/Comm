package com.test.communication.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.test.communication.model.intf.ModelListener;

public class Model {
	
	
	private final List<ModelListener> listeners=Collections.synchronizedList(new ArrayList<ModelListener>());
	
	public void addListener(ModelListener listener)
	{
		listeners.add(listener);
	}
	
	public void hideClick()
	{
		final JButton hideButton=new JButton();
		listeners.forEach(listener->listener.hideClick(hideButton));
		
	}
	
	public void exitClick()
	{
	  final JButton exitButton=new JButton();
	  listeners.forEach(listener-> listener.exitClick(exitButton));
	}
	
	public void chooseConClick()
	{
		final JButton chooseConBtn=new JButton();
		listeners.forEach(listener->listener.chooseConClick(chooseConBtn));
	}
	
	
    public void displayOwnMessageText(String ownMessageText)
    {
    	Object object=JSONValue.parse(ownMessageText);
    	
    	JSONObject jsonObject=(JSONObject)object;
    	
    	
    	
	   listeners.forEach(listener->listener.displayOwnMessage((String) jsonObject.get("txtField")));
    }
    
    public void displayOwnFileName(String ownFileName)
    {
    	Object object=JSONValue.parse(ownFileName);
    	
    	JSONObject jsonObject=(JSONObject) object;
    	
    	String fileName=(String) jsonObject.get("fileName");
    	
    	listeners.forEach(listener->listener.displayOwnFileName(fileName));
    	
    }
}
