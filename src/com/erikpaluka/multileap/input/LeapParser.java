package com.erikpaluka.multileap.input;

import kuro.json.JSONAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.erikpaluka.multileap.leap.CircleGestObj;
import com.erikpaluka.multileap.leap.FingerListObj;
import com.erikpaluka.multileap.leap.FingerObj;
import com.erikpaluka.multileap.leap.FrameObj;
import com.erikpaluka.multileap.leap.GestureListObj;
import com.erikpaluka.multileap.leap.HandListObj;
import com.erikpaluka.multileap.leap.HandObj;
import com.erikpaluka.multileap.leap.InteractionBoxObj;
import com.erikpaluka.multileap.leap.KeyTapGestObj;
import com.erikpaluka.multileap.leap.PointableListObj;
import com.erikpaluka.multileap.leap.PointableObj;
import com.erikpaluka.multileap.leap.ScreenTapGestObj;
import com.erikpaluka.multileap.leap.SwipeGestObj;
import com.erikpaluka.multileap.leap.ToolListObj;
import com.erikpaluka.multileap.leap.ToolObj;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Matrix;

public class LeapParser {

	JSONParser jParser;
	final int ID_ADDITION = 10_000_000;
	public ControllerObj controller;
	

	public LeapParser() {
		jParser = new JSONParser();
	}
	
	public FrameObj parse(String msg, ControllerObj controller) {
		//System.out.println(msg + "\n");

		this.controller = controller;
		
		try {
			JSONAdapter jAdapter = new JSONAdapter(jParser.parse(msg));

			if (jAdapter.containsKey("currentFrameRate")) {
				
				FrameObj frame = new FrameObj(controller);
			
				frame.frameRate = convertToFloat(jAdapter.getObject("currentFrameRate"));
				frame.id = jAdapter.getInteger("id");
				frame.timeStamp = jAdapter.getInteger("timestamp");
				
				setGestures(frame, jAdapter.get("gestures"));
				setHands(frame, jAdapter.get("hands"));
				
				setIntBox(frame, jAdapter.get("interactionBox"));
				setPointables(frame, jAdapter.get("pointables"));
				
				JSONAdapter floatAdapter = jAdapter.get("r").get(0);
				float[] fArrayOne = getFloatArray(floatAdapter);
				
				floatAdapter = jAdapter.get("r").get(1);
				float[] fArrayTwo = getFloatArray(floatAdapter);
				
				floatAdapter = jAdapter.get("r").get(2);
				float[] fArrayThree = getFloatArray(floatAdapter);
				
				frame.r = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				frame.s = convertToFloat(jAdapter.getObject("s"));
				
				floatAdapter = jAdapter.get("t");
				frame.t = getFloatArray(floatAdapter);

				return frame;
			}		
		} catch (ParseException e) {
			System.out.println("ParseException for LeapVM message.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setGestures(FrameObj frame, JSONAdapter gestureAdapter) {
		JSONArray jArray = ((JSONArray) gestureAdapter.get());
		//frame.gestures = new GestureObj[jArray.size()];
		frame.gestureList = new GestureListObj(controller);
		JSONObject jObject;
		
		for (int i = 0; i < jArray.size(); i++) {
			
			jObject = (JSONObject) jArray.get(i);
			String type = ((String) jObject.get("type"));
			
			if (type.equalsIgnoreCase("circle")) {
				
				CircleGestObj circle = new CircleGestObj(controller);
				circle.center = LeapParser.getFloatArray(jObject.get("center"));
				circle.duration = (long) jObject.get("duration");
				circle.handIds = LeapParser.getLongArray(jObject.get("handIds"));
				circle.id = (long) jObject.get("id") + ID_ADDITION;
				circle.normal = LeapParser.getFloatArray(jObject.get("normal"));
				circle.pointableIds = LeapParser.getLongArray(jObject.get("pointableIds"));
				circle.progress = ((Number) jObject.get("progress")).doubleValue();
				circle.radius = ((Number) jObject.get("radius")).doubleValue();
				circle.state = setGestureState((String) jObject.get("state"));
				circle.type = Gesture.Type.TYPE_CIRCLE;
				circle.frame = frame;
				
				frame.gestureList.gestures.add(circle);
				
			} else if (type.equalsIgnoreCase("swipe")) {
				
				SwipeGestObj swipe = new SwipeGestObj(controller);
				swipe.direction = LeapParser.getFloatArray(jObject.get("direction"));
				swipe.duration = (long) jObject.get("duration");
				swipe.handIds = LeapParser.getLongArray(jObject.get("handIds"));
				swipe.id = (long) jObject.get("id") + ID_ADDITION;
				swipe.pointableIds = LeapParser.getLongArray(jObject.get("pointableIds"));
				swipe.position = LeapParser.getFloatArray(jObject.get("position"));
				swipe.speed = ((Number) jObject.get("speed")).doubleValue();
				swipe.startPosition = LeapParser.getFloatArray(jObject.get("startPosition"));
				swipe.state = setGestureState((String) jObject.get("state"));
				swipe.type = Gesture.Type.TYPE_SWIPE;
				swipe.frame = frame;
				
				frame.gestureList.gestures.add(swipe);
				
			} else if (type.equalsIgnoreCase("screenTap")) {
				
				ScreenTapGestObj screenTap = new ScreenTapGestObj(controller);
				screenTap.direction = LeapParser.getFloatArray(jObject.get("direction"));
				screenTap.duration = (long) jObject.get("duration");
				screenTap.handIds = LeapParser.getLongArray(jObject.get("handIds"));
				screenTap.id = (long) jObject.get("id") + ID_ADDITION;
				screenTap.pointableIds = LeapParser.getLongArray(jObject.get("pointableIds"));
				screenTap.position = LeapParser.getFloatArray(jObject.get("position"));
				screenTap.state = setGestureState((String) jObject.get("state"));
				screenTap.type = Gesture.Type.TYPE_SCREEN_TAP;
				screenTap.frame = frame;
				
				frame.gestureList.gestures.add(screenTap);
				
			} else if (type.equalsIgnoreCase("keyTap")) {
				
				KeyTapGestObj keyTap = new KeyTapGestObj(controller);
				
				if(((JSONArray) jObject.get("direction")).get(0) != null) {
					keyTap.direction = LeapParser.getFloatArray(jObject.get("direction"));
				}
				
				keyTap.duration = (long) jObject.get("duration");
				keyTap.handIds = LeapParser.getLongArray(jObject.get("handIds"));
				keyTap.id = (long) jObject.get("id") + ID_ADDITION;
				keyTap.pointableIds = LeapParser.getLongArray(jObject.get("pointableIds"));
				keyTap.position = LeapParser.getFloatArray(jObject.get("position"));
				keyTap.state = setGestureState((String) jObject.get("state"));
				keyTap.type = Gesture.Type.TYPE_KEY_TAP;
				keyTap.frame = frame;
				
				frame.gestureList.gestures.add(keyTap);
				
			}
		}	
	}
	
	public void setHands(FrameObj frame, JSONAdapter handAdapter) {
		JSONAdapter jAdapter = new JSONAdapter(handAdapter.get());
		//frame.hands = new HandObj[jAdapter.size()];
		frame.handList = new HandListObj(controller);
		JSONAdapter childAdapter;
				
		for (int i = 0; i < jAdapter.size(); i++) {
			
			childAdapter = jAdapter.get(i);	
			HandObj curHand = new HandObj(controller);
			
			JSONAdapter floatAdapter = childAdapter.get("armBasis").get(0);
			float[] fArrayOne = LeapParser.getFloatArray(floatAdapter);

			floatAdapter = childAdapter.get("armBasis").get(1);
			float[] fArrayTwo = LeapParser.getFloatArray(floatAdapter);
			
			floatAdapter = childAdapter.get("armBasis").get(2);
			float[] fArrayThree = LeapParser.getFloatArray(floatAdapter);
			
			//curHand.armBasis = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
			
			com.leapmotion.leap.Vector v1 = new com.leapmotion.leap.Vector(fArrayOne[0], fArrayOne[1], fArrayOne[2]);
			com.leapmotion.leap.Vector v2 = new com.leapmotion.leap.Vector(fArrayTwo[0], fArrayTwo[1], fArrayTwo[2]);
			com.leapmotion.leap.Vector v3 = new com.leapmotion.leap.Vector(fArrayThree[0], fArrayThree[1], fArrayThree[2]);

			curHand.arm.basis = new Matrix(v1, v2, v3);
			curHand.arm.width = childAdapter.getNumber("armWidth").floatValue();
			curHand.confidence = LeapParser.convertToFloat(childAdapter.getObject("confidence"));
			
			float[] dirArray = LeapParser.getFloatArray(childAdapter.get("direction"));
			curHand.direction = new com.leapmotion.leap.Vector(dirArray[0], dirArray[1], dirArray[2]);
			
			curHand.elbow = LeapParser.getFloatArray(childAdapter.get("elbow"));
			curHand.grabStrength = LeapParser.convertToFloat(childAdapter.getObject("grabStrength"));
			
			curHand.id = ((Number) childAdapter.getObject("id")).intValue() + ID_ADDITION;
			
			float[] pNormArray = LeapParser.getFloatArray(childAdapter.get("palmNormal"));
			curHand.palmNormal = new com.leapmotion.leap.Vector(pNormArray[0], pNormArray[1], pNormArray[2]);
			
			float[] pPosArray = LeapParser.getFloatArray(childAdapter.get("palmPosition"));
			curHand.palmPosition = new com.leapmotion.leap.Vector(pPosArray[0], pPosArray[1], pPosArray[2]);
			
			double[] pVelArray = LeapParser.getDoubleArray(childAdapter.getObject("palmVelocity"));
			curHand.palmVelocity = new com.leapmotion.leap.Vector((float) pVelArray[0], (float) pVelArray[1], (float) pVelArray[2]);
			
			curHand.palmWidth = childAdapter.getNumber("palmWidth").floatValue();
			curHand.pinchStrength = LeapParser.convertToFloat(childAdapter.getObject("pinchStrength"));
			
			floatAdapter = childAdapter.get("r").get(0);
			fArrayOne = LeapParser.getFloatArray(floatAdapter);

			floatAdapter = childAdapter.get("r").get(1);
			fArrayTwo = LeapParser.getFloatArray(floatAdapter);
			
			floatAdapter = childAdapter.get("r").get(2);
			fArrayThree = LeapParser.getFloatArray(floatAdapter);
			
			curHand.r = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
			curHand.s = LeapParser.convertToFloat(childAdapter.getObject("s"));
			float[] sphereCenter = LeapParser.getFloatArray(childAdapter.get("sphereCenter"));
			curHand.sphereCenter = new com.leapmotion.leap.Vector(sphereCenter[0], sphereCenter[1], sphereCenter[2]);
			
			curHand.sphereRadius = childAdapter.getNumber("sphereRadius").floatValue();
			
			float[] stabPalmPos = LeapParser.getFloatArray(childAdapter.get("stabilizedPalmPosition"));
			curHand.stabilizedPalmPosition = new com.leapmotion.leap.Vector(stabPalmPos[0], stabPalmPos[1], stabPalmPos[2]);
			
			curHand.t = LeapParser.getFloatArray(childAdapter.get("t"));
			curHand.timeVisible = LeapParser.convertToDouble(childAdapter.getObject("timeVisible"));
			curHand.type = childAdapter.getString("type");
			float[] wrist = LeapParser.getFloatArray(childAdapter.get("wrist"));
			curHand.wrist = new com.leapmotion.leap.Vector(wrist[0], wrist[1], wrist[2]);
			
			curHand.frame = frame;
			
			frame.handList.hands.add(curHand);
		}
	}
	
	public void setIntBox(FrameObj frame, JSONAdapter intBoxAdapter) {
		frame.interactionBox = new InteractionBoxObj();
		
		float[] intCenterArray = LeapParser.getFloatArray(intBoxAdapter.get("center"));
		frame.interactionBox.center = new com.leapmotion.leap.Vector(intCenterArray[0], intCenterArray[1], intCenterArray[2]); 
		
		float[] intSizeArray = LeapParser.getFloatArray(intBoxAdapter.get("size"));
		frame.interactionBox.size = new com.leapmotion.leap.Vector(intSizeArray[0], intSizeArray[1], intSizeArray[2]); 
	}
	
	public void setPointables(FrameObj frame, JSONAdapter pointableAdapter) {
		
		JSONAdapter jAdapter = new JSONAdapter(pointableAdapter.get());		
		//frame.pointables = new PointableObj[jAdapter.size()];
		frame.pointableList = new PointableListObj(controller);
		frame.fingerList = new FingerListObj(controller);
		frame.toolList = new ToolListObj(controller);
		
		JSONAdapter childAdapter;
		
		for (int i = 0; i < jAdapter.size(); i++) {
			
			childAdapter = jAdapter.get(i);	
			PointableObj pointable = new PointableObj(controller);

			/*if (!childAdapter.getBoolean("tool")) {

				JSONAdapter floatAdapter = childAdapter.get("bases").get(0).get(0);
				float[] fArrayOne = LeapParser.getFloatArray(floatAdapter);
	
				floatAdapter = childAdapter.get("bases").get(0).get(1);
				float[] fArrayTwo = LeapParser.getFloatArray(floatAdapter);
				
				floatAdapter = childAdapter.get("bases").get(0).get(2);
				float[] fArrayThree = LeapParser.getFloatArray(floatAdapter);
				float[][] one = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				floatAdapter = childAdapter.get("bases").get(1).get(0);
				fArrayOne = LeapParser.getFloatArray(floatAdapter);
	
				floatAdapter = childAdapter.get("bases").get(1).get(1);
				fArrayTwo = LeapParser.getFloatArray(floatAdapter);
				
				floatAdapter = childAdapter.get("bases").get(1).get(2);
				fArrayThree = LeapParser.getFloatArray(floatAdapter);
				float[][] two = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				floatAdapter = childAdapter.get("bases").get(2).get(0);
				fArrayOne = LeapParser.getFloatArray(floatAdapter);
	
				floatAdapter = childAdapter.get("bases").get(2).get(1);
				fArrayTwo = LeapParser.getFloatArray(floatAdapter);
				
				floatAdapter = childAdapter.get("bases").get(2).get(2);
				fArrayThree = LeapParser.getFloatArray(floatAdapter);
				float[][] three = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				pointable.bases = new float[][][]{one, two, three};
				pointable.btipPosition = LeapParser.getFloatArray(childAdapter.get("btipPosition"));
				pointable.carpPosition = LeapParser.getFloatArray(childAdapter.get("carpPosition"));
				pointable.dipPosition = LeapParser.getFloatArray(childAdapter.get("dipPosition"));
				pointable.extended = childAdapter.getBoolean("extended");
				pointable.mcpPosition = LeapParser.getFloatArray(childAdapter.get("mcpPosition"));
				pointable.pipPosition = LeapParser.getFloatArray(childAdapter.get("pipPosition"));
				pointable.type = childAdapter.getInteger("type");
			}*/
			
			float[] dirArray = LeapParser.getFloatArray(childAdapter.get("direction"));
			pointable.direction = new com.leapmotion.leap.Vector(dirArray[0], dirArray[1], dirArray[2]); 
			
			pointable.id = childAdapter.getLong("id") + ID_ADDITION;
			pointable.length = LeapParser.convertToFloat(childAdapter.getObject("length"));
			
			float[] stabTipPosArray = LeapParser.getFloatArray(childAdapter.get("stabilizedTipPosition"));
			pointable.stabilizedTipPosition = new com.leapmotion.leap.Vector(stabTipPosArray[0], stabTipPosArray[1], stabTipPosArray[2]); 
			
			pointable.timeVisible = LeapParser.convertToDouble(childAdapter.getObject("timeVisible"));
			
			float[] tipPosArray = LeapParser.getFloatArray(childAdapter.get("tipPosition"));
			pointable.tipPosition = new com.leapmotion.leap.Vector(tipPosArray[0], tipPosArray[1], tipPosArray[2]); 
			
			double[] tipVelArray = LeapParser.getDoubleArray(childAdapter.getObject("tipVelocity"));
			pointable.tipVelocity = new com.leapmotion.leap.Vector((float) tipVelArray[0], (float) tipVelArray[1], (float) tipVelArray[2]); 
			
			pointable.tool = childAdapter.getBoolean("tool");
			pointable.touchDistance = LeapParser.convertToFloat(childAdapter.getObject("touchDistance"));
			
			String tempTouchZone = childAdapter.getString("touchZone");
			
			if (tempTouchZone.equalsIgnoreCase("none")) {
				pointable.touchZone = com.leapmotion.leap.Pointable.Zone.ZONE_NONE;
				
			} else if (tempTouchZone.equalsIgnoreCase("hovering")) {
				pointable.touchZone = com.leapmotion.leap.Pointable.Zone.ZONE_HOVERING;
				
			} else if (tempTouchZone.equalsIgnoreCase("touching")) {
				pointable.touchZone = com.leapmotion.leap.Pointable.Zone.ZONE_TOUCHING;
			}
			
			pointable.width = LeapParser.convertToFloat(childAdapter.getObject("width"));
			pointable.frame = frame;
			
			if (!childAdapter.containsKey("extended")) {
				pointable.extended = false;
			} else {
				pointable.extended = childAdapter.getBoolean("extended");
			}

			pointable.handId = childAdapter.getLong("handId") + ID_ADDITION;
			//pointable.hand = frame.hand((int) pointable.handId);
			
			//pointable.hand.pointableList.pointables.add(pointable);
			frame.pointableList.pointables.add(pointable);
			
			
			if (!childAdapter.getBoolean("tool")) {

				JSONAdapter floatAdapter = childAdapter.get("bases").get(0).get(0);
				float[] fArrayOne = LeapParser.getFloatArray(floatAdapter);
	
				floatAdapter = childAdapter.get("bases").get(0).get(1);
				float[] fArrayTwo = LeapParser.getFloatArray(floatAdapter);
				
				floatAdapter = childAdapter.get("bases").get(0).get(2);
				float[] fArrayThree = LeapParser.getFloatArray(floatAdapter);
				float[][] one = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				floatAdapter = childAdapter.get("bases").get(1).get(0);
				fArrayOne = LeapParser.getFloatArray(floatAdapter);
	
				floatAdapter = childAdapter.get("bases").get(1).get(1);
				fArrayTwo = LeapParser.getFloatArray(floatAdapter);
				
				floatAdapter = childAdapter.get("bases").get(1).get(2);
				fArrayThree = LeapParser.getFloatArray(floatAdapter);
				float[][] two = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				floatAdapter = childAdapter.get("bases").get(2).get(0);
				fArrayOne = LeapParser.getFloatArray(floatAdapter);
	
				floatAdapter = childAdapter.get("bases").get(2).get(1);
				fArrayTwo = LeapParser.getFloatArray(floatAdapter);
				
				floatAdapter = childAdapter.get("bases").get(2).get(2);
				fArrayThree = LeapParser.getFloatArray(floatAdapter);
				float[][] three = new float[][]{fArrayOne, fArrayTwo, fArrayThree};
				
				FingerObj finger = new FingerObj(pointable, controller);
				
				finger.bases = new float[][][]{one, two, three};
				finger.btipPosition = LeapParser.getFloatArray(childAdapter.get("btipPosition"));
				finger.carpPosition = LeapParser.getFloatArray(childAdapter.get("carpPosition"));
				finger.dipPosition = LeapParser.getFloatArray(childAdapter.get("dipPosition"));
				finger.mcpPosition = LeapParser.getFloatArray(childAdapter.get("mcpPosition"));
				finger.pipPosition = LeapParser.getFloatArray(childAdapter.get("pipPosition"));
				int tempType = childAdapter.getInteger("type");
				
				if (tempType == 0) {
					finger.type = Finger.Type.TYPE_THUMB;
				} else if (tempType == 1) {
					finger.type = Finger.Type.TYPE_INDEX;
				} else if (tempType == 2) {
					finger.type = Finger.Type.TYPE_MIDDLE;
				} else if (tempType == 3) {
					finger.type = Finger.Type.TYPE_RING;
				} else if (tempType == 4) {
					finger.type = Finger.Type.TYPE_PINKY;
				}
				
			//	finger.hand.fingerList.fingers.add(finger);
				frame.fingerList.fingers.add(finger);
				
			} else {
				frame.toolList.tools.add(new ToolObj(pointable, controller));
			}
		}
	}
		
	public Gesture.State setGestureState(String state) {
		if (state.equalsIgnoreCase("start")) {
			return Gesture.State.STATE_START;
		} else if (state.equalsIgnoreCase("update")) {
			return Gesture.State.STATE_UPDATE;
		} else if (state.equalsIgnoreCase("stop")) {
			return Gesture.State.STATE_STOP;
		} else {
			return Gesture.State.STATE_INVALID;
		}
	}
	public static float[] getFloatArray(JSONAdapter floatAdapter) {
		float one, two, three;
		
		one = convertToFloat(floatAdapter.getObject(0));
		two = convertToFloat(floatAdapter.getObject(1));
		three = convertToFloat(floatAdapter.getObject(2));
		
		return new float[]{one, two, three};
	}
	
	public static float[] getFloatArray(Object obj) {
		JSONArray jArray = (JSONArray) obj;
		float[] floatArray = new float[jArray.size()];
		
		for (int i = 0; i < jArray.size(); i++) {
			floatArray[i] = convertToFloat(jArray.get(i));
		}
		
		return floatArray;
	}
	
	public static long[] getLongArray(Object obj) {
		JSONArray jArray = (JSONArray) obj;
		long[] longArray = new long[jArray.size()];
		
		for (int i = 0; i < jArray.size(); i++) {
			longArray[i] = convertToLong(jArray.get(i));
		}
		
		return longArray;
	}
	
	public static double[] getDoubleArray(Object obj) {
		JSONArray jArray = (JSONArray) obj;
		double[] dArray = new double[jArray.size()];
		
		for (int i = 0; i < jArray.size(); i++) {
			dArray[i] = convertToDouble(jArray.get(i));
		}
		
		return dArray;
	}
	
	@SuppressWarnings("null")
	public static float convertToFloat(Object num) {
		
		if (num == null) {
			return (Float) null;
		}
		
		return ((Number) num).floatValue();
	}
	
	@SuppressWarnings("null")
	public static long convertToLong(Object num) {
		
		if (num == null) {
			return (Long) null;
		}
		
		return ((Number) num).longValue();
	}
	
	@SuppressWarnings("null")
	public static double convertToDouble(Object num) {
		
		if (num == null) {
			return (Double) null;
		}
		
		return ((Number) num).doubleValue();
	}
	
}
