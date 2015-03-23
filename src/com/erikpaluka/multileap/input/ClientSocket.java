package com.erikpaluka.multileap.input;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.parser.ParseException;

import com.erikpaluka.multileap.leap.FrameObj;

@WebSocket
public class ClientSocket {
	public LeapParser parser; 
	
    public Session session;
    
    public boolean connected = false;
    public boolean receivingData = false;
    public boolean receivingLeapData = false;
    public String lastMsg = "";
    public FrameObj lastFrame;
    public ControllerObj controller;
    
    public boolean sessionFailed = false;
 
    public ClientSocket(LeapParser leapParser, ControllerObj c) {
    	parser = leapParser;
    	controller = c;
    }
 
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("WebSocket client: connection closed. Status :" + statusCode + ". Reason: " + reason);
        session.close();
        this.session = null;
        connected = false;

        receivingData = false;
    	receivingLeapData = false;
    	lastMsg = "";
    	lastFrame = null;
    	controller = null;
    	
    	
    	////////////////////////////////////////////////////////////////////////////////////
    	// Status code 1000: Normal closure; 
    	// the connection successfully completed whatever purpose for which it was created.
    	////////////////////////////////////////////////////////////////////////////////////
    	if (statusCode > 1000 && statusCode < 1016) {
    		sessionFailed = true;
    	}
    }
 
	@OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println("WebSocket client: connected: " + session);
        this.session = session;
        connected = true;
    }
    
    @OnWebSocketMessage
    public void onMessage(String msg) throws ParseException {
        //System.out.println("Received msg: " + msg);
        
    	if(msg != null) {
    		
    		receivingData = true;
    		lastMsg = msg;
    		lastFrame = parser.parse(msg, controller);
    		
    		if (lastFrame != null) {
    			//System.out.println(msg);
    			receivingLeapData = true;
    		}
    	} else {
    		receivingData = false;
    		receivingLeapData = false;
    	}
        
    }
    
    public FrameObj getFrame() {
    	return lastFrame;
    }
}
