package com.test.communication.comLayer.intf;

import java.net.Socket;

public interface TcpComLayerListener {

   void serverConnectionTrue();
   void serverConnectionFalse();
   void serverSocketClosed();
   void clientConnectionTrue();
   void clientConnectionFalse();
   void clientSocketClosed();
   void listenReceiver(String getTxtFromSocket);
   void listenFileReceiver(String getFileTxtFromSocket,String getFileName);
   void listenFileNameReceiver(String sendFileName);
}
