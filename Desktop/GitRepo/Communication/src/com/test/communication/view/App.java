package com.test.communication.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.gson.JsonObject;
import com.test.communication.view.intf.AppListener;

import org.json.simple.JSONObject;

public class App extends JFrame implements MouseMotionListener,MouseListener {

	private final JPanel titlePanel=new JPanel();
	private final JPanel titleButtonsPanel=new JPanel();
	private final JPanel mainPanel=new JPanel();
	private final JPanel displayTextPanel=new JPanel();
	private final JPanel displayTextSubPanel=new JPanel();
	private final JPanel messageBoxPanel=new JPanel();
	private final JTextField messageText=new JTextField();
	private final JTextArea displayText=new JTextArea();
	private final JButton addUserButton=new JButton();
	private final JButton hideButton=new JButton();
	private final JButton exitButton=new JButton();
	private final JButton sendMessageButton=new JButton();
	private final JButton uploadFileButton=new JButton();
	private final JLabel displayMessageLabel=new JLabel();
	private final JFrame connectionTypeFrame=new JFrame();
	private final JPanel connectionTypePanel=new JPanel();
	private final JCheckBox serverCb=new JCheckBox("Server");
	private final JCheckBox clientCb=new JCheckBox("Client");
	private final JButton chooseConBtn=new JButton("Open");
	private final JFrame addUserIpAndPortFrame=new JFrame();
	private final JPanel addUserIpPanel=new JPanel();
	private final JLabel addUserIpLabel=new JLabel("IP");
	private final JTextField addUserIpTxtField=new JTextField();
	private final JPanel addUserPortPanel=new JPanel();
	private final JLabel addUserPortLabel=new JLabel("PORT");
	private final JTextField addUserPortTxtField=new JTextField();
	private final JPanel addUserConfirmBtnPanel=new JPanel();
	private final JButton addUserOkBtn=new JButton("OK");
	private final JButton addUserCancelBtn=new JButton("CANCEL");
	
	private JFileChooser chooser;
	
	String chooseConText;
	String displayMessage="test message";
	String fileName;
	private static int dragX=0;
	private static int dragY=0;
	private int pressedMouseX;
	private int pressedMouseY;
	
	private final List<AppListener> listeners=Collections.synchronizedList(new ArrayList<AppListener>());
	
	
	public App()
	{
      init();	
	}
	
	private void init()
	{
		
		
		setUndecorated(true);
		
		setSize(500, 700);
		
		setLocationRelativeTo(null);
		
		addMouseMotionListener(this);
		
		addMouseListener(this);
		
		initTitlePanel();
		
		initMainPanel();
		
		initDisplayTextAreaPanel();
		
	}
	
	public void open()
	{
		initChooseConnection();
		
//		SwingUtilities.invokeLater(()-> setVisible(true));
		
	}
	
	
	public void addListener(AppListener listener)
	{
		listeners.add(listener);
	}
	
	public void initChooseConnection()
	{
		GridBagConstraints gbc=new GridBagConstraints();
		
		connectionTypePanel.setLayout(new GridBagLayout());
		connectionTypeFrame.getContentPane().add(connectionTypePanel, BorderLayout.CENTER);
		
		gbc.gridx=0;
		gbc.gridy=0;
		connectionTypePanel.add(serverCb, gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		connectionTypePanel.add(clientCb, gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		connectionTypePanel.add(chooseConBtn, gbc);
		
		connectionTypeFrame.pack();
		connectionTypeFrame.setLocationRelativeTo(null);
		connectionTypeFrame.setVisible(true);
		
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initChooseConBtn();
		
	}
	
	public void initChooseConBtn()
	{
		chooseConBtn.addActionListener(e->
		{
			listeners.forEach(listener->listener.chooseConClick());
		}
		);
	}
	
	public void initTitlePanel()
	{
		initTitleButtonsPanel();
		
		getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		titlePanel.setBackground(new Color(5,100,153));
		titlePanel.setPreferredSize(new Dimension(500, 30));
		
		titlePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbCon=new GridBagConstraints();
		
		addUserButton.setPreferredSize(new Dimension(30, 30));
		try {
			addUserButton.setIcon(new ImageIcon(findPath("addUserButton")));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		gbCon.gridwidth=1;
		gbCon.gridheight=1;
		gbCon.anchor=GridBagConstraints.FIRST_LINE_START;
		
		
		titlePanel.add(addUserButton, gbCon);
		
		titleButtonsPanel.setPreferredSize(new Dimension(60, 30));
		titleButtonsPanel.setBackground(new Color(5,100,153));
		
		gbCon.gridx=1;
		gbCon.gridy=0;
		gbCon.gridwidth=1;
		gbCon.gridheight=1;
		gbCon.weightx=1.0;
		gbCon.weighty=0.0;
		gbCon.anchor=GridBagConstraints.FIRST_LINE_END;
		
		titlePanel.add(titleButtonsPanel, gbCon);
		
		
	}
	
	public void initTitleButtonsPanel()
	{
		initHideButton();
		initExitButton();
		initAddUserBtn();
		
		titleButtonsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbCon=new GridBagConstraints();
		
		hideButton.setPreferredSize(new Dimension(30, 30));
		try {
			hideButton.setIcon(new ImageIcon(findPath("hideButton")));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hideButton.setBackground(new Color(5,100,153));
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		gbCon.gridwidth=1;
		gbCon.gridheight=1;
		gbCon.insets=new Insets(0, 0, 0, 0);
		
		titleButtonsPanel.add(hideButton, gbCon);
		
		
		exitButton.setPreferredSize(new Dimension(30, 30));
		try {
			exitButton.setIcon(new ImageIcon(findPath("quitButton")));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exitButton.setBackground(new Color(5,100,153));
		
		gbCon.gridx=1;
		gbCon.gridy=0;
		gbCon.gridwidth=1;
		gbCon.gridheight=1;
		
		titleButtonsPanel.add(exitButton, gbCon);
	}
	
	public void initHideButton()
	{
		hideButton.addActionListener(e ->
		{
			listeners.forEach(listener ->listener.hideClick());
		}
		);
	}
	
	public void initExitButton()
	{
		exitButton.addActionListener(e->
		{
			listeners.forEach(listener -> listener.exitClick());
		}
		);
	}
	
	public void initAddUserBtn()
	{
		addUserButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			  initAddUser();
				
			}
		});
	}
	
	public void initAddUser()
	{
		GridBagConstraints gbCon=new GridBagConstraints();
		
		//User ip label and text field view decleration
		addUserIpAndPortFrame.getContentPane().add(addUserIpPanel,BorderLayout.NORTH);
		
		addUserIpPanel.setLayout(new GridBagLayout());
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		addUserIpPanel.add(addUserIpLabel, gbCon);
		

		addUserIpTxtField.setPreferredSize(new Dimension(100, 20));
		
		gbCon.gridx=1;
		gbCon.gridy=0;
		gbCon.insets=new Insets(0, 20, 0, 0);
		addUserIpPanel.add(addUserIpTxtField, gbCon);
		
		//User port label and text field view decleration
		addUserIpAndPortFrame.getContentPane().add(addUserPortPanel,BorderLayout.CENTER);
		
		addUserPortPanel.setLayout(new GridBagLayout());
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		addUserPortPanel.add(addUserPortLabel,gbCon);
		
		addUserPortTxtField.setPreferredSize(new Dimension(100, 20));
		
		gbCon.gridx=1;
		gbCon.gridy=0;
		gbCon.insets=new Insets(0, 0, 0, 20);
		addUserPortPanel.add(addUserPortTxtField,gbCon);
		
		//User entry confirmation buton view decleration
		addUserIpAndPortFrame.getContentPane().add(addUserConfirmBtnPanel,BorderLayout.SOUTH);
		
		addUserConfirmBtnPanel.setLayout(new GridBagLayout());
		
		addUserOkBtn.setPreferredSize(new Dimension(80, 15));
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		gbCon.insets=new Insets(10, 10, 10, 0);
		addUserConfirmBtnPanel.add(addUserOkBtn,gbCon);
		
		addUserCancelBtn.setPreferredSize(new Dimension(80,15));
		
		gbCon.gridx=1;
		gbCon.gridy=0;
		gbCon.insets=new Insets(10, 10, 10, 10);
		addUserConfirmBtnPanel.add(addUserCancelBtn,gbCon);
		
		addUserIpAndPortFrame.pack();
		addUserIpAndPortFrame.setLocationRelativeTo(displayTextPanel);
		addUserIpAndPortFrame.setVisible(true);
		
		switch (chooseConText) {
		case "server":
			initServeraddUserOkBtn();
			initAddUserCancelBtn();
			break;
		case "client":
			initClientaddUserOkBtn();
			initAddUserCancelBtn();
		default:
			break;
		}
		
	}
	
	public void initServeraddUserOkBtn()
	{
		addUserOkBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String ip=addUserIpTxtField.getText();
				String port=addUserPortTxtField.getText();
				
				listeners.forEach(listener->listener.listenPortAndIp(ip, port));
				
				initServerListener();
				
				addUserIpAndPortFrame.setVisible(false);
			}
		});
	}
	
	public void initClientaddUserOkBtn()
	{
		addUserOkBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String ip=addUserIpTxtField.getText();
				String port=addUserPortTxtField.getText();
				
				listeners.forEach(listener->listener.listenPortAndIp(ip, port));
				
				initClientListener();
				
				addUserIpAndPortFrame.setVisible(false);
			}
		});
	}
	
	public void initAddUserCancelBtn()
	{
		addUserCancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addUserIpAndPortFrame.setVisible(false);
			}
		});
	}
	
	public void initDisplayTextAreaPanel()
	{
		displayTextPanel.setPreferredSize(new Dimension(500, 610));
		
		getContentPane().add(displayTextPanel, BorderLayout.CENTER);
		
		displayTextPanel.setLayout(new BorderLayout());
		
		displayTextSubPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbCon=new GridBagConstraints();
		gbCon.gridwidth=GridBagConstraints.REMAINDER;
		
		gbCon.fill=GridBagConstraints.HORIZONTAL;
		gbCon.weightx=1.0;
		
		messageBoxPanel.setLayout(new BoxLayout(messageBoxPanel, BoxLayout.PAGE_AXIS));
		
		displayTextSubPanel.add(messageBoxPanel, gbCon);
		
		gbCon.weighty=1.0;
		
		displayTextSubPanel.add(new JPanel(), gbCon);
		
		JScrollPane sp=new JScrollPane(displayTextSubPanel);
		
		displayTextPanel.add(sp,BorderLayout.CENTER);
		
		
	}
	
	public void initMainPanel()
	{
//		initSendButton();
		
//		initDisplayMessageLabel();
		
		mainPanel.setPreferredSize(new Dimension(500, 60));
		
		getContentPane().add(mainPanel,BorderLayout.SOUTH);
		
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(new Color(5,100,153));
		
		GridBagConstraints gbCon=new GridBagConstraints();
		
		/*displayMessageLabel.setPreferredSize(new Dimension(100, 100));
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		
		mainPanel.add(displayMessageLabel, gbCon);*/
		
		
		messageText.setPreferredSize(new Dimension(380, 60));
		
		gbCon.gridx=0;
		gbCon.gridy=0;
		gbCon.anchor=GridBagConstraints.LAST_LINE_START;
		gbCon.insets=new Insets(0, 0, 0, 0);
				
		mainPanel.add(messageText, gbCon);
		
		messageText.setEnabled(false);
		
		uploadFileButton.setPreferredSize(new Dimension(60, 60));
		
		try {
			uploadFileButton.setIcon(new ImageIcon(findPath("uploadFileButton")));
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		gbCon.gridx=1;
		gbCon.gridy=0;
		
		mainPanel.add(uploadFileButton, gbCon);
		
		uploadFileButton.setEnabled(false);
		
		sendMessageButton.setPreferredSize(new Dimension(60, 60));
		try {
			sendMessageButton.setIcon(new ImageIcon(findPath("sendButton")));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gbCon.gridx=2;
		gbCon.gridy=0;
		gbCon.anchor=GridBagConstraints.LAST_LINE_END;
		
		mainPanel.add(sendMessageButton, gbCon);
		
		sendMessageButton.setEnabled(false);
		
		initUploadBtn();
	}
	
	
	public void initUploadBtn()
	{
		uploadFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
				    chooser=new JFileChooser(new File(findPath("uploadFilePath")));
					
					chooser.showSaveDialog(null);
					
					String encodingStr=Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(chooser.getSelectedFile().getAbsolutePath())));
					
					JSONObject object=new JSONObject();
					
					object.put("file", encodingStr);
					
					object.put("fileName", chooser.getSelectedFile().getName());
					
					listeners.forEach(listener->listener.getFromFile(object.toString()));
					
					
					
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	
	public void initDisplayOwnMessage(String ownMessageText)
	{
		JPanel textPanel=new JPanel();
		
		textPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel textLabel=new JLabel();

		Border blackline=BorderFactory.createLineBorder(Color.black);
		
		textLabel.setBorder(blackline);
		
		textPanel.add(textLabel);
		
		messageBoxPanel.add(textPanel);
		
		textLabel.setText(ownMessageText);
		
	}
	
	public void initDisplayOwnFile(String ownFileName)
	{
		JPanel filePanel=new JPanel();
		
		filePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel fileLabel=new JLabel();
		
		Border blackline=BorderFactory.createLineBorder(Color.black);
		
		fileLabel.setBorder(blackline);
		
		filePanel.add(fileLabel);
		
		messageBoxPanel.add(filePanel);
		
		fileLabel.setText(ownFileName);
		
		fileLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
                String path=chooser.getSelectedFile().getAbsolutePath();
				
				File file=new File(path);
				
				Desktop desktop=Desktop.getDesktop();
				
				if(file.exists())
				{
					try {
						desktop.open(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		});
	}
	
	public void initDisplayOtherUserMessage(String getTextFromSocket)
	{
        JPanel textPanel=new JPanel();
		
		textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel textLabel=new JLabel();

		Border blackline=BorderFactory.createLineBorder(Color.black);
		
		textLabel.setBorder(blackline);
		
		textPanel.add(textLabel);
		
		messageBoxPanel.add(textPanel);
		
		textLabel.setText(getTextFromSocket);
		
	}
	
	public void initDisplayOtherUserFile(String otherUserFileName)
	{
        JPanel filePanel=new JPanel();
		
		filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel fileLabel=new JLabel();
		
		Border blackline=BorderFactory.createLineBorder(Color.black);
		
		fileLabel.setBorder(blackline);
		
		filePanel.add(fileLabel);
		
		messageBoxPanel.add(filePanel);
		
		fileLabel.setText(otherUserFileName);
		
		fileLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				try {
					String path = findPath("createdFilePath").concat(otherUserFileName);
					
                    File file=new File(path);
					
					Desktop desktop=Desktop.getDesktop();
					
					if(file.exists())
					{
						try {
							desktop.open(file);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} 
				catch (ParserConfigurationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SAXException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
		});
	}
	
	public void createFile(String writeData,String fileName)
	{
		
		Decoder decoder=Base64.getDecoder();
		
		byte[] bytes=decoder.decode(writeData.getBytes());
		
		
		try {
			
			String createdPath=findPath("createdFilePath").concat(fileName);
			
			File file=new File(createdPath);
			
			OutputStream os=new FileOutputStream(file);
			
			os.write(bytes);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setActionChooseConClick()
	{
		if(serverCb.isSelected() && !clientCb.isSelected())
		{
			chooseConText="server";
		}
	    if(clientCb.isSelected() && !serverCb.isSelected())
		{
			chooseConText="client";
		} 
		
	    if(serverCb.isSelected() && clientCb.isSelected())
		{
			JOptionPane.showMessageDialog(connectionTypeFrame, "Please,choose only one user");
			return;
		}
		
		if(serverCb.isSelected() || clientCb.isSelected())
		{
			
		//Open main frame....	
		SwingUtilities.invokeLater(()-> setVisible(true));
		connectionTypeFrame.dispose();
		}
		
//		initConnectionTypeListener();
	}
	
	
	public void initServerListener()
	{
		listeners.forEach(listener->listener.startServer());
//		getFromTextField();
	}
	
	public void initClientListener()
	{
		listeners.forEach(listener->listener.startClient());
//		getFromTextField();
	}
	
	public void listenServerConTrue()
	{
		messageText.setEnabled(true);
		sendMessageButton.setEnabled(true);
		uploadFileButton.setEnabled(true);
		getFromTextField();
	}
	
	public void listenServerConFalse()
	{
		System.out.println("Server Con False");
	}
	
	public void listenClientConTrue()
	{
		messageText.setEnabled(true);
		sendMessageButton.setEnabled(true);
		uploadFileButton.setEnabled(true);
		getFromTextField();
	}
	
	public void listenClientConFalse()
	{
		System.out.println("Client Con False");
	}
	
	public void getFromTextField()
	{
		messageText.addActionListener(e->
		{
			
			String sendText=messageText.getText();
			
			JSONObject object=new JSONObject();
			
			object.put("txtField", sendText);
			
			listeners.forEach(listener->listener.getTextField(object.toString()));
			messageText.setText("");
		}
		);
		
		sendMessageButton.addActionListener(e->
		{
			String sendText=messageText.getText();
			
			JSONObject object=new JSONObject();
			
			object.put("txtField", sendText);
			
			listeners.forEach(listener->listener.getTextField(object.toString()));
			messageText.setText("");
		}
		);

	}
	
	public String findPath(String commXmlTag) throws ParserConfigurationException, SAXException, IOException
	{
		Path path=Paths.get("comm.xml");
		
		File file=new File(path.toString());
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder dBuilder=dbf.newDocumentBuilder();
		
		Document document=dBuilder.parse(file);
		
		String absPath=document.getElementsByTagName(commXmlTag).item(0).getTextContent();
		
		return absPath;
	}
	
	public void setActionHideClick()
	{
	   setState(Frame.ICONIFIED);
	}
	
	public void setActionExitClick()
	{
		System.exit(0);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		pressedMouseX=e.getX();
		pressedMouseY=e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int currentMouseLocX=e.getX();
		int currentMouseLocY=e.getY();
		
		dragX=dragX+currentMouseLocX-pressedMouseX;
		dragY=dragY+currentMouseLocY-pressedMouseY;
		
		setBounds(dragX, dragY, 500, 700);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
