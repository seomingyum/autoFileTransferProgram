package server;

import java.io.BufferedOutputStream;

import java.io.DataInputStream;
import java.net.Socket;

import org.json.JSONObject;

public class FileDataCatcher {

	public void fileDataCatcher(JSONObject root, BufferedOutputStream bos, DataInputStream dis) {
		try {
			// 클라이언트로 부터 받은 파일데이터 수신
			byte[] data = new byte[1024];
			while(true) {
				int num = dis.readInt();
				if(num==-1) {
					break;
				}
				dis.read(data, 0 , num);
				
				// 설정한 디렉토리로 파일 전송
				bos.write(data, 0, num);
			}
			bos.flush();
		} catch (Exception e) {
			ServerApp.serverLogger.log("FileDataCatcher Exception : 파일데이터를 수신받을 수 없습니다.");
		}
	}
}
