package util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

	public static String executeRequest(String targetURL) throws IOException {
		    URL request = new URL(targetURL);
	        URLConnection yc = request.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String outputLine= "";
            String inputLine= "";
	        while ((inputLine = in.readLine()) != null) 
	            outputLine += inputLine;
	        in.close();
		    return outputLine;
	
	}




}
