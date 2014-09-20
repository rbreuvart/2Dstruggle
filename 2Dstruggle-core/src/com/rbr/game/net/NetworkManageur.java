package com.rbr.game.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.ConfigPref;
import com.rbr.game.screen.game.ScreenGame;

public class NetworkManageur {
	
	
	private Thread thListeneur;
    private List<String> addresses ;	
    private int communicationPort;
    
    private Array<String> listMSG ;
    
    
    public int getCommunicationPort() {
		return communicationPort;
	}
	public void setCommunicationPort(int communicationPort) {
		this.communicationPort = communicationPort;
	}
	public Thread getThListeneur() {
			return thListeneur;
	}
	public void setThListeneur(Thread thListeneur) {
		this.thListeneur = thListeneur;
	}
	public List<String> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
	public Array<String> getListMSG() {
		return listMSG;
	}
	public void setListMSG(Array<String> listMSG) {
		this.listMSG = listMSG;
	}



	//stage Comunication
    private Label labelDetails;
   // private Label labelMessage;
    private TextButton button;
    private TextArea textIPAddress;
    private TextArea textMessage;
    
   
    
	public NetworkManageur(ScreenGame screenGame) {
		listMSG = new Array<String>();
		communicationPort = ConfigPref.Net_CommunicationPort;
		createIpAdressInterface();
		createListeneurThread(screenGame);
	}

	
	private void createIpAdressInterface(){
		addresses = new ArrayList<String>();
		 // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
      
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        
       
	}
	
	private void createListeneurThread(ScreenGame screenGame){
		Stage stage = screenGame.getStage();
		 // Vertical group groups contents vertically.  I suppose that was probably pretty obvious
       // VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();//.space(2).pad(5).fill();//.space(3).reverse().fill();
        // Set the bounds of the group to the entire virtual display
      //  vg.setBounds(0, 0, ConfigPref.viewPortWidth-200, 300);
        
       
        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        Skin skin =  screenGame.getMainGame().getManager().get(ConfigPref.file_UiSkin, Skin.class);
        Table table = new Table(skin);
        // Create our controls
        labelDetails = new Label(ipAddress,skin);
       // labelMessage = new Label("Hello world",skin);
        button = new TextButton("Send message",skin);
        button.setColor(1, 1, 1, 0.7f);
        
        textIPAddress = new TextArea("192.168.0.29",skin);
        if (Gdx.app.getType().equals(ApplicationType.Android)) {
        	textIPAddress.setText("192.168.0.30");
		}
        textIPAddress.setColor(1, 1, 1, 0.5f);
        textMessage = new TextArea("",skin);
        textMessage.setColor(1, 1, 1, 0.5f);
        
        table.setFillParent(true);
        table.align(Align.left+Align.top);
        // Add them to scene
        table.add(labelDetails).align(Align.left);
        table.row();
        table.add(textIPAddress).width(100).align(Align.left);
        table.row();
        table.add(textMessage).width(300).align(Align.left);
        table.row();
        table.add(button).height(50).width(100).align(Align.left);
        table.row();
       
        
        // Add scene to stage
        stage.addActor(table);
	    
        /**
         * Lie les MSG qui entre sur le Port
         */
		Runnable runlisteneur = new Runnable(){
            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                // 0 means no timeout.  Probably not the greatest idea in production!
                serverSocketHint.acceptTimeout = 0;
                
                // Create the socket server using TCP protocol and listening on 9021
                // Only one app can listen to a port at a time, keep in mind many ports are reserved
                // especially in the lower numbers ( like 21, 80, etc )
                ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, communicationPort, serverSocketHint);
                
                // Loop forever
                while(true){
                    // Create a socket
                    Socket socket = serverSocket.accept(null);
                    
                    // Read data from the socket into a BufferedReader
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                    
                    try {
                        // Read to the next newline (\n) and display that text on labelMessage
                    	
                    	listMSG.add(ConfigPref.Net_PatternMsgRecu+buffer.readLine());
                       // labelMessage.setText(buffer.readLine()+"\n"+labelMessage.getText());    
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
		};
	
		thListeneur = new Thread(runlisteneur);
		thListeneur.start();
		
		
		/**
		 * envoie un msg
		 */
        // Wire up a click listener to our button
        button.addCaptureListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                
                // When the button is clicked, get the message text or create a default string value
                String textToSend = new String();
                if(textMessage.getText().length() == 0)
                    textToSend = "Doesn't say much but likes clicking buttons\n";
                else
                    textToSend = textMessage.getText() + ("\n"); // Brute for a newline so readline gets a line
                
                SocketHints socketHints = new SocketHints();
                // Socket will time our in 4 seconds
                socketHints.connectTimeout = ConfigPref.Net_TimeOut;
                
                if (StringUtils.countMatches(textIPAddress.getText(), ".")==3) {
                	textIPAddress.setColor(1, 1, 1, 0.5f);

                	//create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
                    Socket socket = Gdx.net.newClientSocket(Protocol.TCP, textIPAddress.getText(), communicationPort, socketHints);
                    try {
                        // write our entered message to the stream
                        socket.getOutputStream().write(textToSend.getBytes());
                        listMSG.add(ConfigPref.Net_PatternMsgEmit+textToSend);    
                    } catch (IOException e) {
                        e.printStackTrace();
                        listMSG.add(ConfigPref.Net_PatternMsgEmit+e.getMessage());  
                    }
				}else{
					textIPAddress.setColor(1, 0.5f, 0.5f, 1);
				
				}
               
            }
        });
		
	}


	

}
