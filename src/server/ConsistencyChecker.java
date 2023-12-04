package server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ConsistencyChecker {
	
	public void consistencyCheck(Socket socket, DataOutputStream dos, File serverFile, String clientFileName, long clientFileSize) {
		// 파일의 정합성이 보장되지 않는다면 클라이언트로 "inconsistent"를 전송한다.
		if(!(serverFile.getName().equals(clientFileName)) || serverFile.length()!=clientFileSize) {
			serverFile.delete();
			try {
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF("inconsistent");
				dos.flush();
			} catch (IOException e) {
				ServerApp.serverLogger.log("ConsostencyChecker Exception : "+serverFile.getName()+"에 대한 inconsistent를 전송할 수 없습니다.");
			}

			ServerApp.serverLogger.log(serverFile.getName()+" : 정합성이 보장되지 않는 파일입니다.");
		// 파일의 정합성이 보장되다면 클라이언트로 파일명을 전송한다.
		} else {
			ServerApp.serverLogger.info(serverFile.getName()+" : 정합성이 보장된 파일 입니다.");
			
			//저장 완료된 파일 이름 보내기
			AccomplishedFilenameSender accomplishedFilenameSneder = new AccomplishedFilenameSender();
			accomplishedFilenameSneder.accomplishedFilenameSned(socket, serverFile ,dos);
		}
	}
}
