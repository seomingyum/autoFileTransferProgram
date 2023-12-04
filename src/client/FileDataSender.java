package client;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class FileDataSender {
	public void fileDataSender(File file, BufferedInputStream bis, DataOutputStream dos) {
		try {
			// 파일 데이터 읽어옴
			byte[] data = new byte[1024];
			while(true) {
				int num = bis.read(data);
				if(num==-1) {
					dos.writeInt(-1);
					break;
				}
				// 서버로 파일 데이터 전송
				dos.writeInt(num);
				dos.write(data, 0, num);
			}
			dos.flush();
		} catch (IOException e) {
			ClientApp.clientLogger.log("FileDataSender Exception : 파일데이터를 전송할 수 없습니다.");
		}
	}
}
