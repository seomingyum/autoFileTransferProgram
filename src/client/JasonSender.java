package client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class JasonSender {

	public void jsonSend(Socket socket, String json, File file, DataOutputStream dos) {
		Thread thread = Thread.currentThread();
		ClientApp.clientLogger.info(thread.toString()+" : " +"("+file.getName()+")"+"전송 시작");
		try {
			dos.writeUTF(json);
			dos.flush();
		} catch (IOException e) {
			ClientApp.clientLogger.log("JasonSender Exception : jason을 전송할 수 없습니다.");
		}
	}

}
