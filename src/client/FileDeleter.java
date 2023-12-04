package client;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class FileDeleter {
	public void fileDelete(DataInputStream dis, File file) {
		try {
			String accomplishedFileName = dis.readUTF();
			
			if(accomplishedFileName.equals("inconsistent")) {
				// 서버로 부터 "inconsistent"을 수신 받았다면 파일 삭제를 하지 않음
				ClientApp.clientLogger.log("서버에 전송한 파일의 정합성이 보장되지 않아 파일을 삭제할 수 없습니다.");
			} else {
				// 서버로 부터 파일명을 수신 받았다면 파일 삭제
				File accomplishedFile = new File(ClientApp.clientDir + File.separator + accomplishedFileName);
				accomplishedFile.delete();
				ClientApp.clientLogger.info("파일전송 완료: "+accomplishedFileName);						
			}
		} catch (IOException e) {
			ClientApp.clientLogger.log("FileDeleter Exception");		
		}

	}
}
