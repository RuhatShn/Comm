package com.test.communication.main;

import com.test.communication.comLayer.TcpComLayer;
import com.test.communication.control.Control;


public class Communication {

	public static void main(String[] args)
	{
		Control.getInstance().start();
	}
}
