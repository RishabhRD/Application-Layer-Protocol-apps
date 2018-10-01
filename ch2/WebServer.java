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
	public static String getPath(InputStream in) {
		Scanner scan = new Scanner(in);
		String s = "null";
		if(scan.hasNextLine()) {
			s = scan.nextLine();
		}else {
			return null;
		}
		String[] ar = s.split(" ");
		if(ar.length<2)
			return null;
		return ar[1];
	}
	public static byte[] merge(byte[] b1,byte[] b2) {
		byte[] res= new byte[b1.length+b2.length];
		int i=0;
		while(i<b1.length) {
			res[i] = b1[i];
			i++;
		}
		int count=0;
		while(i<b2.length) {
			res[i] = b2[count];
			i++;
			count++;
		}
		return res;
	}
	
     public static void main(String[] args) {
    	 try {
			ServerSocket server = new ServerSocket(11000);
			Socket socket = server.accept();
		    while(true) {
		    	String res = "";
		    	String path = getPath(socket.getInputStream());
		    	if(path==null) res = "400 Bad Request";
		    	Path p = Paths.get(path);
		    	if(!Files.exists(p)) {
		    		res = "404 Not found";
		    	}else {
		    		res = "200 OK";
		    	}
		    	if(!res.equals("200 OK")) {
		    		String s= "Http/1.1 "+res;
		    		OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
		    		writer.write(s);
		    		writer.flush();
		    		writer.close();
		    	}else {
		    		FileInputStream fis = new FileInputStream(path);
		    		byte[] entity = fis.readAllBytes();
		    		fis.close();
		    		int len = entity.length;
		    		byte[] header= ("Http/1.1"+res+"\nContent-length: "+len+"\n").getBytes();
		    		byte[] buffer = merge(header,entity);
		    		OutputStream out  = socket.getOutputStream();
		    		out.write(buffer);
		    		out.close();
		    	}
		    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}
