package server;

import java.io.DataInputStream;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

public class JasonCatcher {
	
	public JSONObject jasonCatch(Socket socket, DataInputStream dis) {
		JSONObject root=null;
		try {
			String json = dis.readUTF();
			root = new JSONObject(json);
		} catch (IOException e) {
			ServerApp.serverLogger.log("JasonCatcher Exception : jason을 수신받을 수 없습니다.");
		}
		return root;
	}
}
