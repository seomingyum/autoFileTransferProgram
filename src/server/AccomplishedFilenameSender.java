package server;

import java.io.DataOutputStream;

import java.io.File;
import java.net.Socket;

public class AccomplishedFilenameSender {
	
	public void accomplishedFilenameSned(Socket socket, File serverFile ,DataOutputStream dos) {
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(serverFile.getName());
			dos.flush();
		} catch (Exception e) {
			Thread thread = new Thread();
			ServerApp.serverLogger.log("AccomplishedFilenameSender Exception : "+thread.toString()+" : " + "(" + serverFile.getName() + ")" + "을 클라이언트로 전송하지 못했습니다.");
		} 
	}
}
