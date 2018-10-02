package ch2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class WebServer {

	
     public static void main(String[] args) {
    	 try {
			ServerSocket server = new ServerSocket(11000);
			
		    while(true) {
		    	Socket socket = server.accept();
		    	ServerThread process = new ServerThread(socket);
		    	process.start();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}
