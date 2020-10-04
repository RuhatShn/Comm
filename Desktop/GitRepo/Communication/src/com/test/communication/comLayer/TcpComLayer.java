package com.test.communication.comLayer;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.event.MouseWheelEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

import javax.swing.JButton;

import com.test.communication.comLayer.intf.TcpComLayerListener;
import com.test.communication.model.Model;
import com.test.communication.model.intf.ModelListener;

public class TcpComLayer  {

	private int port=5123;
	
	private String host="";
	
	private final String delimeter="\0";
	
	private  Socket socket;
	
	private final LinkedBlockingQueue<byte[]> sendQueue=new LinkedBlockingQueue<byte[]>();
	
	private final byte[] closure=new byte[0];
	
	private String getText;
	
	private static TcpComLayer INSTANCE;
	
	private Boolean checkConnection=false;
	

	private List<TcpComLayerListener> listeners=Collections.synchronizedList(new ArrayList<TcpComLayerListener>());
	
	public void addListener(TcpComLayerListener listener)
	{
		listeners.add(listener);
	}
		
	
	public void setIpAndPort(String ip,String port)
	{
		this.host=ip;
		
		this.port=Integer.parseInt(port);
		
	}
	
	public void startServer()
	{
		Thread ssThread=new Thread(new Runnable() {
			

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try (Socket socket=new ServerSocket(port).accept())
				{
					TcpComLayer.this.socket=socket;
					
					listeners.forEach(listener->listener.serverConnectionTrue());
					
					
					Thread thread=new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							startSendFromQueue();
						}
					});thread.start();
					
					receive();
					
		           
				} 
				catch (Exception e) {
					// TODO: handle exception
					listeners.forEach(listener->listener.serverConnectionFalse());
				}
			}
		});ssThread.start();
		
		
	}
	
	
	
	public void startClient()
		
	{
		
		Thread scThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(socket==null)
				{
					try (Socket socket=new Socket(host, port)) 
					{
						TcpComLayer.this.socket=socket;
						
						
						listeners.forEach(listener->listener.clientConnectionTrue());
						
						Thread thread=new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								startSendFromQueue();
							}
						});thread.start();
						
						receive();
						
					 
					} 
					catch (Exception e) {
						// TODO: handle exception
						listeners.forEach(listener->listener.clientConnectionFalse());
					}
				}
			}
		});scThread.start();
		
		
	}
	
	
	
	public void startSendFromQueue() 
	{

		try (OutputStream outputStream=new BufferedOutputStream(socket.getOutputStream())) 
		{
			
			
		while(!socket.isClosed())
		{
				try 
				{
				 byte[] messageBytes=sendQueue.take();
				 if(messageBytes.equals(closure))
				 {
			
					 continue;
				 }
				
				  
				 
				 outputStream.write(messageBytes);
				 outputStream.flush();
				 
				} 
				catch (Exception e){
					// TODO: handle exception
					
				}
			
		}	
			
		} 
		catch (Exception e) {
			// TODO: handle exception
//               listeners.forEach(listener->listener.clientSocketClosed());
//             listeners.forEach(listener->listener.serverSocketClosed());
			
		}
		
	}
	
	public void receive()
	{
		
		
		try (Scanner scanner=new Scanner(socket.getInputStream(),"UTF-8"))
		{
			
			
			scanner.useDelimiter(delimeter);
			
			while(!socket.isClosed())
			{
				String message=scanner.next();
				
				Object object=JSONValue.parse(message);
				
				JSONObject jsonObject=(JSONObject)object;
				
				if(jsonObject.containsKey("txtField"))
				{
					
					listeners.forEach(listener->listener.listenReceiver((String) jsonObject.get("txtField")));
				}
				
				if(jsonObject.containsKey("file"))
				{
					
					String file=(String) jsonObject.get("file");
					
					String fileName=(String) jsonObject.get("fileName");
					
					listeners.forEach(listener->listener.listenFileReceiver(file,fileName));
					
					listeners.forEach(listener->listener.listenFileNameReceiver(fileName));
				}
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		
		sendQueue.offer(closure);
	}
	
	
	public void readFromTxtField(String getTxtField)
	{
		try  
		{
			  			
				String message=getTxtField;
			
				
				sendQueue.offer((message+delimeter).getBytes("UTF-8"));

				
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
