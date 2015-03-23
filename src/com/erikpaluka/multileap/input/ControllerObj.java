package com.erikpaluka.multileap.input;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.json.simple.JSONObject;

import com.erikpaluka.multileap.leap.FrameObj;
import com.leapmotion.leap.Controller;

public class ControllerObj {
	
	public int portNum;
	public String ipAddress;
	public String uriString;
	
	public LeapParser leapParser;
	
	public WebSocketClient client;
    public ClientSocket socket;
    public boolean enabledGestures = false;
    

	public ArrayList<FrameObj> frames;
	//number of recent frames stored for VM Leap controller 
	public final int MAX_FRAMES = 200;
	
	// Offset amount from top left of display
	//public float displayOffset = 0;
	public int displayHeightmm;
	public int displayWidthmm;
	public boolean rightOfDisplay = false;
	
	public boolean externalLeap = false;
	public boolean startedExternal = false;
	
	public Controller leapC;
	public boolean ready = false;
	
	
	
	//////////////////////
	// Calibration values
	
	public ArrayList<float[]> calibValuesRightBot = new ArrayList<float[]>();
	public ArrayList<float[]> calibValuesRightTop = new ArrayList<float[]>();
	public ArrayList<float[]> calibValuesLeftBot = new ArrayList<float[]>();
	public ArrayList<float[]> calibValuesLeftTop = new ArrayList<float[]>();
	
	public boolean calibratedRightBot = false;
	public boolean calibratedRightTop = false;
	public boolean calibratedLeftBot = false;
	public boolean calibratedLeftTop = false;
	
	public int tempRightBotX;
	public int tempRightBotY;
	public int tempRightBotZ;
	public int tempRightTopX;
	public int tempRightTopY;
	public int tempRightTopZ;
	
	public int tempLeftBotX;
	public int tempLeftBotY;
	public int tempLeftBotZ;
	public int tempLeftTopX;
	public int tempLeftTopY;
	public int tempLeftTopZ;
	
	public int displayRightBotX = 0;
	public int displayRightBotY = 0;
	public int displayRightBotZ = 0;
	public int displayRightTopX = 0;
	public int displayRightTopY = 0;
	public int displayRightTopZ = 0;
	
	public int displayLeftBotX = 0;
	public int displayLeftBotY = 0;
	public int displayLeftBotZ = 0;
	public int displayLeftTopX = 0;
	public int displayLeftTopY = 0;
	public int displayLeftTopZ = 0;
	
	public ControllerObj(int port, String ip) {
		externalLeap = true;
		leapParser = new LeapParser();
		frames = new ArrayList<FrameObj>();
		
		portNum = port;
		ipAddress = ip;
		
		uriString = "ws://" + ipAddress + ":" + portNum + "/v6.json";
	}
	
	public ControllerObj(Controller c) {
		frames = new ArrayList<FrameObj>();
		leapC = c;
		//ready = true;
	}
	/**
	 * Returns a frame of tracking data from the Leap Motion software.
	 * @param history
	 * @return
	 */
	public FrameObj frame(int history) {
		if (externalLeap) {
			return frames.get(history);
		} else {
			return new FrameObj(leapC.frame(history), this);
		}
	}
	
	/**
	 * Returns a frame of tracking data from the Leap Motion software.
	 * @return
	 */
	public FrameObj frame() {
		
		if (!frames.isEmpty()) {
			return frames.get(0);
		} else {
			
			System.err.println("No frames available.");
			return null;
		}
	}
	
	/**
	 * Adds a frame to the controllers array list
	 * @param frame
	 */
	public void addFrame(FrameObj frame) {
		frames.add(0, frame);
		
		while (frames.size() > MAX_FRAMES) {
			frames.remove(frames.size() - 1);
		}
	}
	
	/**
	 * Updates the controller
	 */
	public void updateController() {
		
		if (externalLeap) {

			if (isConnected()) {

				if (!enabledGestures) {
					sendLeapMsg();
					
				} else if (isReceivingLeapData()){

					if (!ready) {
						ready = true;
					}
					addFrame(socket.getFrame());
				}
			}
		} else {
			
			if (isReceivingLeapData()){

				if (!ready) {
					ready = true;
				}
			}
			addFrame(new FrameObj(leapC.frame(), this));
		}
	}
	
	
	public void startClient() {
		client = new WebSocketClient();
        socket = new ClientSocket(leapParser, this);
        
        try {
            client.start();
            URI uri = new URI(uriString);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request);
            System.out.println("WebSocket client: connecting to : " + uri);
            startedExternal = true;
            
        } catch (URISyntaxException e) {
            System.out.println("URI Syntax Exception with WebSocket client.");
        	e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IO Exception with WebSocket client.");
        	e.printStackTrace();
        } catch (Exception e) {   
        	System.out.println("Exception with WebSocket client.");
        	e.printStackTrace();
        }
	}
	
	public void stopClient() {
		if (externalLeap && startedExternal) {
			try {
				client.stop();
				client.destroy();
			} catch (Exception e) {
				System.out.println("Exception with stopping WebSocket client.");
				e.printStackTrace();
			}
		}		
	}
	
	public boolean isConnected() {
		if (externalLeap) {
			if (startedExternal) {
		
				return socket.connected && socket.receivingData;
			} else {
				return false;
			}
		} else {
			return leapC.isConnected();
		}
	}
	
	public boolean isReceivingLeapData() {
		if (externalLeap) {
			if (startedExternal) {
		
				return socket.connected && socket.receivingLeapData;
			} else {
				return false;
			}
		} else {
			return leapC.isConnected() && leapC.devices().get(0).isStreaming(); // check if this works!
		}
	}
	
	public boolean isReady() {
		return ready;
	}
	
	@SuppressWarnings("unchecked")
	public void sendLeapMsg() {
		enabledGestures = true;
		
		JSONObject msg = new JSONObject();
        msg.put("enableGestures", true);
        msg.put("background", true);
        
        try {
        	socket.session.getRemote().flush();
        	socket.session.getRemote().sendString(msg.toJSONString());
        	
		} catch (IOException e) {
			System.out.println("WebSocket client: IO Exception with sending JSON to Leap Motion controller.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Set leap motion positional data to global frame of reference
	 * @param pos
	 * @return
	 */
	public com.leapmotion.leap.Vector toGlobal(com.leapmotion.leap.Vector pos, String type) {
		if (rightOfDisplay) {
			float x = displayWidthmm + Math.abs(getClosestXBottom().getX()) + pos.getX();
			float y = pos.getY() - getClosestXBottom().getY();
			float z = pos.getZ() - getClosestXBottom().getZ();
			//System.out.println(type + ". Right of display: " + x + " " + y + " " + z + ". Old Pos: " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " ");
			return new com.leapmotion.leap.Vector(x, y, z);
		} else {
			float x = pos.getX() - getClosestXBottom().getX();
			float y = pos.getY() - getClosestXBottom().getY();
			float z = pos.getZ() - getClosestXBottom().getZ();
			//if (type.equalsIgnoreCase("hand"))
			//	System.out.println(type + ". Left of display: " + x + " " + y + " " + z + ". Old Pos: " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " ");
			return new com.leapmotion.leap.Vector(x, y, z);
		}
	}
	
	public com.leapmotion.leap.Vector getClosestXBottom() {
				
		if (displayLeftBotX > 0 || (Math.abs(displayRightBotX) > Math.abs(displayLeftBotX) && (displayLeftBotX != 0 && displayLeftBotY != 0 && displayLeftBotZ != 0))) {
			return new com.leapmotion.leap.Vector(displayLeftBotX, displayLeftBotY, displayLeftBotZ);
		} else {
			return new com.leapmotion.leap.Vector(displayRightBotX, displayRightBotY, displayRightBotZ);
		}
		
	}
	
	public com.leapmotion.leap.Vector getClosestXTop() {
		
		if (Math.abs(displayRightTopX) > Math.abs(displayLeftTopX) && (displayLeftTopX != 0 && displayLeftTopY != 0 && displayLeftTopZ != 0)) {
			return new com.leapmotion.leap.Vector(displayLeftTopX, displayLeftTopY, displayLeftTopZ);
		} else {
			return new com.leapmotion.leap.Vector(displayRightTopX, displayRightTopY, displayRightTopZ);
		}
		
	}
	
	public void checkIfRightOfDisplay() {
		if (displayLeftBotX < 0 || (displayLeftBotX == 0 && displayLeftBotY == 0 && displayLeftBotZ == 0)) {
			rightOfDisplay = true;
		}
	}
	
	public void clearCalibValues() {
		calibValuesRightBot.clear();
		calibValuesRightTop.clear();
		calibValuesLeftBot.clear();
		calibValuesLeftTop.clear();
		
		calibratedRightBot = true;
		calibratedRightTop = true;
		calibratedLeftBot = true;
		calibratedLeftTop = true;
	}
}
